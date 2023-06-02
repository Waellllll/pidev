<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Symfony\Component\Routing\Annotation\Route;

class AudioController extends AbstractController
{
    #[Route('/audio', name: 'app_audio')]
    public function index(): Response
    {
        return $this->render('audio/index.html.twig', [
            'controller_name' => 'AudioController',
        ]);
    }

    #[Route('/song/{filename}', name: 'audio', methods: ['GET'])]
    public function audio($filename)
    {

        $audioFilePath = $this->getParameter('kernel.project_dir') . '/public/audio/' . $filename;
        $response = new BinaryFileResponse($audioFilePath);
        $response->setContentDisposition(ResponseHeaderBag::DISPOSITION_INLINE);
        $response->headers->set('Content-Type', 'audio/mpeg');
        return $response;
    }
}
