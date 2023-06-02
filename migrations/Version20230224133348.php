<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230224133348 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE contrat DROP FOREIGN KEY FK_60349993C8121CE9');
        $this->addSql('DROP INDEX IDX_60349993C8121CE9 ON contrat');
        $this->addSql('ALTER TABLE contrat DROP nom_id');
        $this->addSql('ALTER TABLE type_contrat ADD contrat_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE type_contrat ADD CONSTRAINT FK_4815F6661823061F FOREIGN KEY (contrat_id) REFERENCES contrat (id)');
        $this->addSql('CREATE INDEX IDX_4815F6661823061F ON type_contrat (contrat_id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE contrat ADD nom_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE contrat ADD CONSTRAINT FK_60349993C8121CE9 FOREIGN KEY (nom_id) REFERENCES type_contrat (id)');
        $this->addSql('CREATE INDEX IDX_60349993C8121CE9 ON contrat (nom_id)');
        $this->addSql('ALTER TABLE type_contrat DROP FOREIGN KEY FK_4815F6661823061F');
        $this->addSql('DROP INDEX IDX_4815F6661823061F ON type_contrat');
        $this->addSql('ALTER TABLE type_contrat DROP contrat_id');
    }
}
