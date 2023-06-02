<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230505234100 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE contrat DROP nomcontrat, CHANGE montant montant VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE type_contrat DROP FOREIGN KEY FK_4815F6661823061F');
        $this->addSql('ALTER TABLE type_contrat ADD prenom VARCHAR(255) NOT NULL, ADD salaire VARCHAR(255) NOT NULL, ADD numtel VARCHAR(255) NOT NULL, CHANGE contrat_id contrat_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE type_contrat ADD CONSTRAINT FK_4815F6661823061F FOREIGN KEY (contrat_id) REFERENCES contrat (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE contrat ADD nomcontrat VARCHAR(255) DEFAULT NULL, CHANGE montant montant INT NOT NULL');
        $this->addSql('ALTER TABLE type_contrat DROP FOREIGN KEY FK_4815F6661823061F');
        $this->addSql('ALTER TABLE type_contrat DROP prenom, DROP salaire, DROP numtel, CHANGE contrat_id contrat_id INT NOT NULL');
        $this->addSql('ALTER TABLE type_contrat ADD CONSTRAINT FK_4815F6661823061F FOREIGN KEY (contrat_id) REFERENCES contrat (id) ON UPDATE CASCADE ON DELETE CASCADE');
    }
}
