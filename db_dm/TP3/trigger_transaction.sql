CREATE TABLE client (
  `id`     INT          NOT NULL AUTO_INCREMENT,
  `name`   VARCHAR(255) NOT NULL,
  `amount` DOUBLE       NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE log (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  operation varchar(10)  NOT NULL,
  name      varchar(255) NOT NULL,
  amount    INT          NOT NULL
);


INSERT INTO client
VALUES (NULL, 'Younes', 3000);
INSERT INTO client
VALUES (NULL, 'Ismail', 4000);
INSERT INTO client
VALUES (NULL, 'Houari', 2000);
INSERT INTO client
VALUES (NULL, 'Amine', 1000);

CREATE TRIGGER historical_insert
  AFTER INSERT
  ON client
  FOR EACH ROW
  BEGIN
    INSERT INTO log
    SET id        = NULL,
        operation = 'INSERT',
        name      = NEW.name,
        amount    = NEW.amount;
  END;

CREATE TRIGGER historical_update
  AFTER UPDATE
  ON client
  FOR EACH ROW
  BEGIN
    INSERT INTO log
    SET id        = NULL,
        operation = 'UPDATE',
        name      = NEW.name,
        amount    = NEW.amount;
  END;

CREATE TRIGGER historical_delete
  BEFORE DELETE
  ON client
  FOR EACH ROW
  BEGIN
    INSERT INTO log
    SET id        = NULL,
        operation = 'DELETE',
        name      = OLD.name,
        amount    = OLD.amount;
  END;

INSERT INTO client
VALUES (NULL, 'Sid Ali', 3000);

UPDATE client
SET amount = 2000
WHERE id = 1;

CREATE TABLE test (
  `id`   INT    NOT NULL AUTO_INCREMENT,
  number DECIMAL(20,0) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO test VALUES(null , factorial(8));
INSERT INTO test VALUES(null , factorial(12));


SELECT factorial(8) as Value


DROP PROCEDURE IF EXISTS inscription;
CREATE PROCEDURE inscription(first INT, second VARCHAR(255))
  BEGIN
    DECLARE var INT;
    SELECT COUNT(*) INTO var FROM client;
    IF DATEDIFF(SYSDATE(), '2008-11-30') < 0
    THEN
      INSERT INTO client
      SET id     = NULL,
          amount = first,
          name   = second;
    ELSE

    END IF;
  END;

CALL inscription(2300 , 'Ilyes');


CREATE FUNCTION factorial(n DECIMAL(3, 0))
  RETURNS DECIMAL(20, 0)
DETERMINISTIC
  BEGIN
    DECLARE factorial DECIMAL(20, 0) DEFAULT 1;
    DECLARE counter DECIMAL(3, 0);
    SET counter = n;
    factorial_loop : REPEAT
      SET factorial = factorial * counter;
      SET counter = counter - 1;
    UNTIL counter = 1
    END REPEAT;
    RETURN factorial;
  END;

