<?php

namespace App\Controller;

use App\Entity\TypeContrat;
use App\Form\TypeContratType;
use App\Repository\TypeContratRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Dompdf\Dompdf;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

#[Route('/type/contrat')]
class TypeContratController extends AbstractController
{
    #-----------JSONSTART-------------
    #[Route("/All", name: "list")]
    //* Dans cette fonction, nous utilisons les services NormlizeInterface et contratRepository,
        //* avec la méthode d'injection de dépendances.
    public function gettypecontrats(TypeContratRepository $repo, SerializerInterface $serializer)
    {
        $typecontrats = $repo->findAll();
        //* Nous utilisons la fonction normalize qui transforme le tableau d'objets
        //* contrats en  tableau associatif simple.
        // $contratsNormalises = $normalizer->normalize($contrats, 'json', ['groups' => "contrats"]);

        // //* Nous utilisons la fonction json_encode pour transformer un tableau associatif en format JSON
        // $json = json_encode($contratsNormalises);

        $json = $serializer->serialize($typecontrats, 'json', ['groups' => "typecontrat"]);

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
        return new Response($json);
    }

    #[Route("/typecontrat/{id}", name: "typecontrat")]
    public function contrattypeId($id, NormalizerInterface $normalizer, TypeContratRepository $repo)
    {
        $typecontrat = $repo->find($id);
        $contratNormalises = $normalizer->normalize($typecontrat, 'json', ['groups' => "typecontrat"]);
        return new Response(json_encode($contratNormalises));
    }


    #[Route("/addtypecontratJSON/new", name: "addtypecontratJSON")]
    public function addcontrattypeJSON(Request $req,   NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $typecontrat = new TypeContrat();
        $typecontrat->setNom($req->get('nom'));
        $typecontrat->setprenom($req->get('prenom'));
        $typecontrat->setsalaire($req->get('salaire'));
        $typecontrat->setnumtel($req->get('numtel'));
        $typecontrat->setContrat($req->get('contrat_id'));
        $em->persist($typecontrat);
        $em->flush();

        $jsonContent = $Normalizer->normalize($typecontrat, 'json', ['groups' => 'typecontrat']);
        return new Response(json_encode($jsonContent));
    }

    #[Route("/updatetypecontratJSON/{id}", name: "updatetypecontratJSON")]
    public function updatecontrattypeJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $typecontrat = $em->getRepository(TypeContrat::class)->find($id);
        $typecontrat->setNom($req->get('nom'));
        $typecontrat->setprenom($req->get('prenom'));
        $typecontrat->setsalaire($req->get('salaire'));
        $typecontrat->setnumtel($req->get('numtel'));
        $typecontrat->setcontrat($req->get('contrat'));
        $em->flush();
        $jsonContent = $Normalizer->normalize($typecontrat, 'json', ['groups' => 'typecontrat']);
        return new Response("type contrat updated successfully " . json_encode($jsonContent));
    }

    #[Route("/deletetypecontratJSON/{id}", name: "deletetypecontratJSON")]
    public function deletecontrattypeJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $typecontrat = $em->getRepository(TypeContrat::class)->find($id);
        $em->remove($typecontrat);
        $em->flush();
        $jsonContent = $Normalizer->normalize($typecontrat, 'json', ['groups' => 'typecontrat']);
        return new Response("type contrat deleted successfully " . json_encode($jsonContent));
    }
    #-----------JSONEND---------------
    #[Route('/', name: 'app_type_contrat_index')]
    public function index(ManagerRegistry $doctrine): Response
    {
        $typeContrat = $doctrine->getRepository(TypeContrat::class)->findAll();
        return $this->render('type_contrat/index.html.twig', [
            'type_contrats' => $typeContrat
        ]);
    }
    #[Route('/f', name: 'app_type_contrat_indexf', methods: ['GET'])]
    public function index1(ManagerRegistry $doctrine): Response
    {
        $typeContrat = $doctrine->getRepository(TypeContrat::class)->findAll();
        return $this->render('type_contrat/indexf.html.twig', [
            'type_contrats' => $typeContrat
        ]);
    }
    #[Route('/new', name: 'app_type_contrat_new', methods: ['GET', 'POST'])]
    public function new(ManagerRegistry $doctrine,Request $request, TypeContratRepository $typeContratRepository): Response
    {
        $typeContrat = new TypeContrat();
        $form = $this->createForm(TypeContratType::class, $typeContrat);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $typeContratRepository->save($typeContrat, true);
            $em = $doctrine->getManager();
            $em->persist($typeContrat);
            $em->flush();
            return $this->redirectToRoute('app_type_contrat_index');
        }

        return $this->renderForm('type_contrat/new.html.twig', [
            'type_contrat' => $typeContrat,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_type_contrat_show', methods: ['GET'])]
    public function show(TypeContrat $typeContrat): Response
    {
        return $this->render('type_contrat/show.html.twig', [
            'type_contrat' => $typeContrat,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_type_contrat_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, TypeContrat $typeContrat, TypeContratRepository $typeContratRepository): Response
    {
        $form = $this->createForm(TypeContratType::class, $typeContrat);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $typeContratRepository->save($typeContrat, true);

            return $this->redirectToRoute('app_type_contrat_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('type_contrat/edit.html.twig', [
            'type_contrat' => $typeContrat,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_type_contrat_delete', methods: ['POST'])]
    public function delete(Request $request, TypeContrat $typeContrat, TypeContratRepository $typeContratRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$typeContrat->getId(), $request->request->get('_token'))) {
            $typeContratRepository->remove($typeContrat, true);
        }

        return $this->redirectToRoute('app_type_contrat_index', [], Response::HTTP_SEE_OTHER);
    }
}
