CREATE TABLE `adb_dm`.`client` (
  `id`     INT          NOT NULL AUTO_INCREMENT,
  `name`   VARCHAR(255) NOT NULL,
  `amount` DOUBLE       NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

INSERT INTO client
VALUES (NULL, 'Younes', 3000);
INSERT INTO client
VALUES (NULL, 'Ismail', 4000);
INSERT INTO client
VALUES (NULL, 'Houari', 2000);
INSERT INTO client
VALUES (NULL, 'Amine', 1000);

-- Creating trigger for checking Work Rule
CREATE TRIGGER max_amount
  BEFORE UPDATE
  ON client
  FOR EACH ROW
  BEGIN
    IF (OLD.amount - NEW.amount < 2500)
    THEN
      SET New.amount = NEW.amount;
    ELSE
      SET New.amount = OLD.amount;
    end if;
  END;

-- taking 1000 DA From Younes Account
UPDATE client SET amount = 2000 WHERE id = 1;

-- taking 3000 DA From Ismail Account : this should not work
UPDATE client SET amount = 1000 WHERE id = 2;
