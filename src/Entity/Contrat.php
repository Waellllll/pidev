<?php

namespace App\Entity;

use App\Repository\ContratRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use App\Form\FileType;
use App\Entity\TypeContrat;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: ContratRepository::class)]
class Contrat
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("contrat")]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\LessThanOrEqual("today UTC", message: "Date n est pa valable!")]
    #[Groups("contrat")]
    private ?\DateTimeInterface $datesignature = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\LessThanOrEqual("today UTC", message: "Date n est pa valable!")]
    #[Groups("contrat")]
    private ?\DateTimeInterface $dateexpiration = null;


    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:'montant is required')]
    #[Groups("contrat")]
    private ?string $montant = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrat")]
    private ?string $imagecontrat = null;
    #[Groups("contrat")]
    #[ORM\OneToMany(mappedBy: 'contrat', targetEntity: TypeContrat::class)]

    private Collection $nom;





    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDatesignature(): ?\DateTimeInterface
    {
        return $this->datesignature;
    }

    public function setDatesignature(\DateTimeInterface $datesignature): self
    {
        $this->datesignature = $datesignature;

        return $this;
    }

    public function getDateexpiration(): ?\DateTimeInterface
    {
        return $this->dateexpiration;
    }

    public function setDateexpiration(\DateTimeInterface $dateexpiration): self
    {
        $this->dateexpiration = $dateexpiration;

        return $this;
    }


    public function getmontant(): ?string
    {
        return $this->montant;
    }

    public function setmontant(string $montant): self
    {
        $this->montant = $montant ;

        return $this;
    }

    public function __toString(): string
    {
        return $this->getDatesignature()->format('Y-m-d');
    }

    public function getImagecontrat(): ?string
    {
        return $this->imagecontrat;
    }

    public function setImagecontrat(string $imagecontrat): self
    {
        $this->imagecontrat = $imagecontrat ;

        return $this;
    }

    /**
     * @return Collection<int, TypeContrat>
     */
    public function getNom(): Collection
    {
        return $this->nom;
    }

    public function addNom(TypeContrat $nom): self
    {
        if (!$this->nom->contains($nom)) {
            $this->nom->add($nom);
            $nom->setContrat($this);
        }

        return $this;
    }

    public function removeNom(TypeContrat $nom): self
    {
        if ($this->nom->removeElement($nom)) {
            // set the owning side to null (unless already changed)
            if ($nom->getContrat() === $this) {
                $nom->setContrat(null);
            }
        }

        return $this;
    }


}
