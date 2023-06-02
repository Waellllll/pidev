<?php

namespace App\Entity;

use App\Repository\TypeContratRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use App\Form\FileType;
use App\Entity\TypeContrat;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
#[ORM\Entity(repositoryClass: TypeContratRepository::class)]
class TypeContrat
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("typecontrat")]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Groups("typecontrat")]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
    #[Groups("typecontrat")]
    private ?string $prenom = null;

    #[ORM\Column(length: 255)]
    #[Groups("typecontrat")]
    private ?string $salaire = null;

    #[ORM\Column(length: 255)]
    #[Groups("typecontrat")]
    private ?string $numtel = null;

    #[ORM\ManyToOne(inversedBy: 'nom')]
    #[ORM\JoinColumn(name:"contrat_id",referencedColumnName: 'id')]
    private ?Contrat $contrat = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;
        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getSalaire(): ?string
    {
        return $this->salaire;
    }

    public function setSalaire(string $salaire): self
    {
        $this->salaire = $salaire;

        return $this;
    }

    public function getNumtel(): ?string
    {
        return $this->numtel;
    }

    public function setNumtel(string $numtel): self
    {
        $this->numtel = $numtel;

        return $this;
    }

    public function getContrat(): ?Contrat
    {
        return $this->contrat;
    }

    public function setContrat(?Contrat $contrat): self
    {
        $this->contrat = $contrat;

        return $this;
    }

}
