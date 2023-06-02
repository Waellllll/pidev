<?php

namespace App\Controller;

use App\Form\ContratType;
use App\Form\TypeContratType;
use App\Repository\ContratRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use App\Entity\Contrat;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use setasign\Fpdi\Tcpdf\Fpdi;
use Dompdf\Css\Color;

class ContratController extends AbstractController
{
    #-----------JSONSTART-------------
    #[Route("/all", name: "listcontrat")]
    public function getcontrats(ContratRepository $repo, SerializerInterface $serializer)
    {
        $contrats = $repo->findAll();
        //* Nous utilisons la fonction normalize qui transforme le tableau d'objets
        //* contrats en  tableau associatif simple.
        // $contratsNormalises = $normalizer->normalize($contrats, 'json', ['groups' => "contrats"]);

        // //* Nous utilisons la fonction json_encode pour transformer un tableau associatif en format JSON
        // $json = json_encode($contratsNormalises);

        $json = $serializer->serialize($contrats, 'json', ['groups' => "contrat"]);

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
        return new Response($json);
    }

    #[Route("/contrat/{id}", name: "contrat")]
    public function contratId($id, NormalizerInterface $normalizer, ContratRepository $repo)
    {
        $contrat = $repo->find($id);
        $contratNormalises = $normalizer->normalize($contrat, 'json', ['groups' => "contrat"]);
        return new Response(json_encode($contratNormalises));
    }


    #[Route("/addcontratJSON/new", name: "addcontratJSON")]
    public function addcontratJSON(Request $req,   NormalizerInterface $Normalizer)
    {
         $em = $this->getDoctrine()->getManager();
        $contrat = new Contrat();
        $datesignature = new \DateTime($req->get('datesignature'));
        $contrat->setDatesignature($datesignature);
        $dateexpiration = new \DateTime($req->get('dateexpiration'));
        $contrat->setDateexpiration($dateexpiration);
        $contrat->setmontant($req->get('montant'));
        $contrat->setImagecontrat($req->get('imagecontrat'));
        $em->persist($contrat);
        $em->flush();

        $jsonContent = $Normalizer->normalize($contrat, 'json', ['groups' => 'contrat']);
        return new Response(json_encode($jsonContent));

    }

    #[Route("updatecontratJSON/{id}", name: "updatecontratJSON")]
    public function updatecontratJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $contrat = $em->getRepository(Contrat::class)->find($id);
        $datesignature = new \DateTime($req->get('datesignature'));
        $contrat->setDatesignature($datesignature);
        $dateexpiration = new \DateTime($req->get('dateexpiration'));
        $contrat->setDateexpiration($dateexpiration);
        $contrat->setmontant($req->get('montant'));
        $contrat->setImagecontrat($req->get('imagecontrat'));
        $em->flush();
        $jsonContent = $Normalizer->normalize($contrat, 'json', ['groups' => 'contrat']);
        return new Response("contrat updated successfully " . json_encode($jsonContent));
    }

    #[Route("deletecontratJSON/{id}", name: "deletecontratJSON")]
    public function deletecontratJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $contrat = $em->getRepository(Contrat::class)->find($id);
        $em->remove($contrat);
        $em->flush();
        $jsonContent = $Normalizer->normalize($contrat, 'json', ['groups' => 'contrat']);
        return new Response("contrat deleted successfully " . json_encode($jsonContent));
    }
    #-----------JSONEND---------------
    #[Route('/contrat', name: 'app_contrat')]
    public function index(): Response
    {
        return $this->render('contrat/index.html.twig', [
            'controller_name' => 'ContratController',
        ]);
    }
    #[Route('/contratf', name: 'app_contratf')]
    public function index1(): Response
    {
        return $this->render('contrat/indexf.html.twig', [
            'controller_name' => 'ContratController',
        ]);
    }

    #[Route('/readcontrat', name: 'contrat_read')]
    public function read(ManagerRegistry $doctrine): Response
    {
        $contrat = $doctrine->getRepository(Contrat::class)->findAll();
        return $this->render('contrat/readcontrat.html.twig',
            ["contrat" => $contrat]);
    }
    #[Route('/readfcontrat', name: 'contrat_readf')]
    public function readf(ManagerRegistry $doctrine): Response
    {
        $contrat = $doctrine->getRepository(Contrat::class)->findAll();
        return $this->render('contrat/readfcontrat.html.twig',
            ["contrat" => $contrat
            ]);
    }
    #[Route('/addcontrat', name: 'contrat_add')]
    public function  add(ManagerRegistry $doctrine, Request  $request) : Response
    { $contrat = new Contrat();
        $form = $this->createForm(ContratType::class, $contrat);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid() )
        {
                $imagecontrat = $form->get('imagecontrat')->getData();

                if ($imagecontrat) {
                    $originalFilename = pathinfo($imagecontrat->getClientOriginalName(), PATHINFO_FILENAME);
                    $newFilename = $originalFilename.'-'.uniqid().'.'.$imagecontrat->guessExtension();

                    try {
                        $imagecontrat->move(
                            $this->getParameter('image_directory'),
                            $newFilename
                        );
                    } catch (FileException $e) {
                        // handle exception
                    }

                    $contrat->setImagecontrat($newFilename);
                }
            $em = $doctrine->getManager();
            $em->persist($contrat);
            $em->flush();
            return $this->redirectToRoute('contrat_read');
        }
        return $this->renderForm("contrat/addcontrat.html.twig", ["f"=>$form]) ;
    }

    #[Route('/updatecontrat/{id}', name: 'contrat_update')]
    public function  update(ManagerRegistry $doctrine,$id,  Request  $request) : Response
    {

        $contrat = $doctrine
        ->getRepository(Contrat::class)
        ->find($id);
        $form = $this->createForm(ContratType::class, $contrat);
        $form->add('update', SubmitType::class) ;
        $form->handleRequest($request);
        if ($form->isSubmitted()&& $form->isValid())
        {
            $imagecontrat = $form->get('imagecontrat')->getData();
            if ($imagecontrat) {
                $originalFilename = pathinfo($imagecontrat->getClientOriginalName(), PATHINFO_FILENAME);
                $newFilename = $originalFilename.'-'.uniqid().'.'.$imagecontrat->guessExtension();

                try {
                    $imagecontrat->move(
                        $this->getParameter('image_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // handle exception
                }

                $contrat->setImagecontrat($newFilename);
            }
            $em = $doctrine->getManager();
            $em->flush();
            return $this->redirectToRoute('contrat_read');
        }
        return $this->renderForm("contrat/updatecontrat.html.twig",
            ["f"=>$form]) ;


    }
    #[Route("/deletecontrat/{id}", name:'contrat_delete')]
    public function delete($id, ManagerRegistry $doctrine)
    {$c = $doctrine
        ->getRepository(Contrat::class)
        ->find($id);
        $em = $doctrine->getManager();
        $em->remove($c);
        $em->flush() ;
        return $this->redirectToRoute('contrat_read');
    }
    //Exporter pdf (composer require dompdf/dompdf)


    #[Route("/pdf", name:"PDF", methods:"GET")]
    public function generatePdf(): Response
    {
        $repository = $this->getDoctrine()->getRepository(Contrat::class);
        $contrat = $repository->findAll();

        $html = $this->renderView('contrat/pdf.html.twig', [
            'contrat' => $contrat,
        ]);


        $dompdf = new Dompdf();

        $dompdf->loadHtml($html);
        $dompdf->setPaper('A4', 'portrait');
        $dompdf->render();

        return new Response(
            $dompdf->output(),
            200,
            [
                'Content-Type' => 'application/pdf',
                'Content-Disposition' => 'inline; filename="tous-les-contrats.pdf"',
            ]
        );
    }
    #[Route('/ajax', name: 'ajax_search', methods: ['GET'])]
    public function searchforAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $requestString = $request->get('q');

        $entities =  $em->getRepository('AppBundle:Entity')->findEntitiesByString($requestString);

        if(!$entities) {
            $result['entities']['error'] = "Not Found";
        } else {
            $result['entities'] = $this->getRealEntities($entities);
        }

        return new Response(json_encode($result));
    }
    public function getRealEntities($entities){

        foreach ($entities as $entity){
            $realEntities[$entity->getId()] = $entity->getFoo();
        }

        return $realEntities;
    }

}
