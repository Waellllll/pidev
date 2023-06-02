<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230224131932 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE contrat DROP FOREIGN KEY FK_603499936C6E55B5');
        $this->addSql('DROP INDEX IDX_603499936C6E55B5 ON contrat');
        $this->addSql('ALTER TABLE contrat CHANGE nom nom_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE contrat ADD CONSTRAINT FK_60349993C8121CE9 FOREIGN KEY (nom_id) REFERENCES type_contrat (id)');
        $this->addSql('CREATE INDEX IDX_60349993C8121CE9 ON contrat (nom_id)');
        $this->addSql('ALTER TABLE type_contrat CHANGE salaire salaire VARCHAR(255) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE contrat DROP FOREIGN KEY FK_60349993C8121CE9');
        $this->addSql('DROP INDEX IDX_60349993C8121CE9 ON contrat');
        $this->addSql('ALTER TABLE contrat CHANGE nom_id nom INT DEFAULT NULL');
        $this->addSql('ALTER TABLE contrat ADD CONSTRAINT FK_603499936C6E55B5 FOREIGN KEY (nom) REFERENCES type_contrat (id)');
        $this->addSql('CREATE INDEX IDX_603499936C6E55B5 ON contrat (nom)');
        $this->addSql('ALTER TABLE type_contrat CHANGE salaire salaire INT NOT NULL');
    }
}
