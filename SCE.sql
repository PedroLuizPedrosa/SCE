
CREATE SCHEMA IF NOT EXISTS `sce`;
USE `sce` ;

-- -----------------------------------------------------
-- Table `sce`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sce`.`categoria` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `sce`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sce`.`produto` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `cod_barras` VARCHAR(45) NULL,
  `nome` VARCHAR(45) NULL,
  `descricao` VARCHAR(45) NULL,
  `quantidade` INT NULL,
  `categoria_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_produto_categoria_idx` (`categoria_id` ASC),
  CONSTRAINT `fk_produto_categoria`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `sce`.`categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);