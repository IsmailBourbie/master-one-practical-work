CREATE TABLE `client` (
  _id  INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE company (
  _id  INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE `order` (
  _id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  price      VARCHAR(255) NOT NULL,
  city_start VARCHAR(255) NOT NULL,
  city_end   VARCHAR(255) NOT NULL,
  id_client  INTEGER      NOT NULL,
  id_societe INTEGER      NOT NULL,
  FOREIGN KEY (id_societe) REFERENCES company (_id),
  FOREIGN KEY (id_client) REFERENCES client (_id)
);

CREATE TABLE driver (
  _id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(255) NOT NULL,
  id_societe INTEGER,
  FOREIGN KEY (id_societe) REFERENCES company (_id)
);

CREATE TABLE transport (
  _id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(255) NOT NULL,
  id_societe INTEGER,
  FOREIGN KEY (id_societe) REFERENCES company (_id)
);

CREATE TABLE article (
  _id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  id_commande  INTEGER      NOT NULL,
  id_transport INTEGER      NOT NULL,
  FOREIGN KEY (id_commande) REFERENCES `order` (_id),
  FOREIGN KEY (id_transport) REFERENCES transport (_id)
);

