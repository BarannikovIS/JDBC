CREATE DATABASE  `cooking` ;

CREATE TABLE  `cooking`.`Author` (
`id` INT( 4 ) NOT NULL PRIMARY KEY ,
`name` VARCHAR( 30 ) NOT NULL ,
`surname` VARCHAR( 30 ) NOT NULL ,
`lastname` VARCHAR( 30 ) NOT NULL ,
`profession` VARCHAR( 30 ) NOT NULL
);

INSERT INTO  `cooking`.`Author` VALUES ( 1,  'Rostislav',  'Gamov',  'Svyatoslovovich',  'Perfumer' );
INSERT INTO  `cooking`.`author` VALUES ( 2,  'Bazhen',  ' Astafjevs',  'Tihomirovich',  'Milker poisonous snakes');
INSERT INTO  `cooking`.`author` VALUES ( 3,  'Yaropolk',  ' Kremer',  'Kazimirovich',  'Fumele');

DELIMITER $$ 

CREATE PROCEDURE `getSurnameProcedure` () 
LANGUAGE SQL 
DETERMINISTIC 
SQL SECURITY DEFINER 
COMMENT 'Procedure' 

BEGIN 
SELECT surname from author where id=1; 
END$$