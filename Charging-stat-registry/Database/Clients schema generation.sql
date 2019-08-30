-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Owner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Owner` (
  `ID` Varchar(12) NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Email` VARCHAR(45) NULL,
  `Phone` VARCHAR(20) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Car`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Car` (
  `ID` INT NOT NULL,
  `Model name` VARCHAR(45) NULL,
  `Model year` YEAR(4) NOT NULL,
  `Date purchased` DATE NOT NULL,
  `Owner_ID` Varchar(12) NOT NULL,
  PRIMARY KEY (`ID`, `Owner_ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  INDEX `fk_Car_Owner_idx` (`Owner_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Car_Owner`
    FOREIGN KEY (`Owner_ID`)
    REFERENCES `mydb`.`Owner` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Charging station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Charging station` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Charging station_has_Car`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Charging station_has_Car` (
  `Charging station_ID` INT NOT NULL,
  `Car_ID` INT NOT NULL,
  `Car_Owner_ID` Varchar(12) NOT NULL,
  PRIMARY KEY (`Charging station_ID`, `Car_ID`, `Car_Owner_ID`),
  INDEX `fk_Charging station_has_Car_Car1_idx` (`Car_ID` ASC, `Car_Owner_ID` ASC) VISIBLE,
  INDEX `fk_Charging station_has_Car_Charging station1_idx` (`Charging station_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Charging station_has_Car_Charging station1`
    FOREIGN KEY (`Charging station_ID`)
    REFERENCES `mydb`.`Charging station` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Charging station_has_Car_Car1`
    FOREIGN KEY (`Car_ID` , `Car_Owner_ID`)
    REFERENCES `mydb`.`Car` (`ID` , `Owner_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
