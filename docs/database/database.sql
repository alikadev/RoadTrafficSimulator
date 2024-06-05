-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema rts_main
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rts_main
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rts_main` DEFAULT CHARACTER SET utf8mb3 ;
USE `rts_main` ;

-- -----------------------------------------------------
-- Table `rts_main`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rts_main`.`account` (
  `id` VARCHAR(64) NOT NULL,
  `password` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `rts_main`.`setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rts_main`.`setting` (
  `id` VARCHAR(128) NOT NULL,
  `default` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

-- Fill table with the data

INSERT INTO setting VALUES ('RTS-C1-R01-speedLimit', 50.0);
INSERT INTO setting VALUES ('RTS-C1-R01-size', 40.0);
INSERT INTO setting VALUES ('RTS-C1-R01-traffic', 30.0);

INSERT INTO setting VALUES ('RTS-C1-R02-speedLimit', 30.0);
INSERT INTO setting VALUES ('RTS-C1-R02-size', 40.0);
INSERT INTO setting VALUES ('RTS-C1-R02-traffic', 0.0);

-- -----------------------------------------------------
-- Table `rts_main`.`accountsetting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rts_main`.`accountsetting` (
  `account` VARCHAR(64) NOT NULL,
  `setting` VARCHAR(128) NOT NULL,
  `value` DOUBLE NOT NULL,
  `setname` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`setting`, `account`, `setname`),
  INDEX `fk_AccountSetting_Setting1_idx` (`setting` ASC) VISIBLE,
  INDEX `fk_AccountSetting_Account` (`account` ASC) VISIBLE,
  CONSTRAINT `fk_AccountSetting_Account`
    FOREIGN KEY (`account`)
    REFERENCES `rts_main`.`account` (`id`),
  CONSTRAINT `fk_AccountSetting_Setting1`
    FOREIGN KEY (`setting`)
    REFERENCES `rts_main`.`setting` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
