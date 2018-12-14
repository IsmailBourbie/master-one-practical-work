/* Create Database with name tp4*/
CREATE DATABASE tp4;

/* Create 'etu' table */
CREATE TABLE etu (
    pknumsecu CHAR(13) PRIMARY KEY,
    knumetu VARCHAR(20) UNIQUE NOT NULL,
    nom VARCHAR(50),
    prenom VARCHAR(50)
);

/* Create 'uv' Table*/
CREATE TABLE uv (
    pkcode CHAR(4) NOT NULL,
    fketu CHAR(13) NOT NULL,
    PRIMARY KEY(pkcode, fketu),
    FOREIGN KEY (fketu) REFERENCES etu(pknumsecu)
);

/* Insert data in etu table*/
INSERT INTO etu VALUES('1800675001066', 'AB3937098X', 'Dupont', 'Pierre');
INSERT INTO etu VALUES('2820475001124', 'XGB67668', 'Durnand', 'Anne');

/* Insert data in uv table*/
INSERT INTO uv VALUES ('NF17', '1800675001066');
INSERT INTO uv VALUES ('NF26', '1800675001066');
INSERT INTO uv VALUES ('NF29', '1800675001066');

/* Clear the table uv*/
DELETE FROM uv;

/* Clear the table etu*/
DELETE FROM etu;

/* Insert data in etu using CSV files */
\copy etu (pknumsecu, knumetu, nom, prenom) FROM 'etus.csv' WITH CSV DELIMITER ';'

/* Insert data in uv using CSV files */
\copy uv (fketu, pkcode) FROM 'uvs.csv' WITH CSV DELIMITER ';'