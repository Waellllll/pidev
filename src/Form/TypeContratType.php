<?php

namespace App\Form;

use App\Entity\TypeContrat;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use App\Entity\Contrat;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use App\Form\TypeContratType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
class TypeContratType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('prenom')
            ->add('salaire')
            ->add('numtel')
            ->add('contrat',EntityType::class, [
                // looks for choices from this entity
                'class' => Contrat::class,
                // uses the User.username property as the visible option string
                'choice_label' => 'id',
                // used to render a select box, check boxes or radios
                'multiple' => false,
                'expanded' => false,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TypeContrat::class,
        ]);
    }
}
