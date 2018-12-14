CREATE TABLE Famille (
  CodeFamille VARCHAR(40) PRIMARY KEY,
  Libelle     VARCHAR(50)
);

CREATE TABLE Fournisseur (
  NumFournisseur INT PRIMARY KEY AUTO_INCREMENT,
  Societe        VARCHAR(40),
  Civilite       VARCHAR(5),
  Nom            VARCHAR(40),
  Prenom         VARCHAR(50),
  Adresse        VARCHAR(150),
  CodePostal     VARCHAR(5),
  Ville          VARCHAR(40),
  Pays           VARCHAR(40),
  Telephone      VARCHAR(20),
  Mobile         VARCHAR(20),
  Fax            VARCHAR(20),
  Email          VARCHAR(40),
  Observation    TEXT
);
CREATE TABLE FraisPort (
  CodePort     VARCHAR(20) PRIMARY KEY,
  LibFraisPort VARCHAR(40),
  Montant      Double
);

CREATE TABLE Login (
  IDLogin        INT PRIMARY KEY AUTO_INCREMENT,
  NomUtilisateur VARCHAR(40),
  ModeDePasse    VARCHAR(40)
);
CREATE TABLE ModeLivraison (
  IDModeLivraison  INT PRIMARY KEY AUTO_INCREMENT,
  LibModeLivraison VARCHAR(40)
);
CREATE TABLE ModeReglement (
  IDModeReglement  INT PRIMARY KEY AUTO_INCREMENT,
  LibModeReglement VARCHAR(40)
);
CREATE TABLE Prameters (
  IDParameter  INT PRIMARY KEY AUTO_INCREMENT,
  NomParamater VARCHAR(30),
  Valeur       TEXT
);

CREATE TABLE Societe (
  NomSociete VARCHAR(100) PRIMARY KEY,
  Adresse    VARCHAR(150),
  CodePostal VARCHAR(5),
  Ville      VARCHAR(40),
  Telephone  VARCHAR(20),
  Fax        VARCHAR(20),
  Email      VARCHAR(40),
  Logo       BLOB
);

CREATE TABLE Stock (
  Reference       VARCHAR(20) PRIMARY KEY,
  QteEnStock      INT,
  QteStockVirtuel INT,
  AuteurModif     VARCHAR(40),
  DateModif       DATE
);
CREATE TABLE TVA (
  TauxTVA REAL PRIMARY KEY
);


CREATE TABLE ActionPossible (
  IDActionPossible  INT PRIMARY KEY AUTO_INCREMENT,
  LibActionPossible VARCHAR(40)
);

CREATE TABLE Client (
  NumClient          INT PRIMARY KEY AUTO_INCREMENT,
  Societe            VARCHAR(40),
  Civilite           VARCHAR(5),
  NomClient          VARCHAR(40),
  Prenom             VARCHAR(50),
  Adresse            VARCHAR(150),
  CodePostal         VARCHAR(5),
  Ville              VARCHAR(40),
  Pays               VARCHAR(40),
  Telephone          VARCHAR(20),
  Mobile             VARCHAR(20),
  Fax                VARCHAR(20),
  Email              VARCHAR(40),
  Type               INT,
  LivreMemeAdresse   BOOLEAN,
  FactureMemeAdresse BOOLEAN,
  ExemptTVA          BOOLEAN,
  SaisiPar           VARCHAR(40),
  SaisiLe            DATE,
  AuteurModif        VARCHAR(40),
  DateModif          DATE,
  Observation        TEXT
);

CREATE TABLE Cedex (
  IDCedex    INT PRIMARY KEY AUTO_INCREMENT,
  CodePostal VARCHAR(5),
  Ville      VARCHAR(40)
);


CREATE TABLE ActionRealise (
  IDActionRealise  INT PRIMARY KEY AUTO_INCREMENT,
  IDActionPossible INT,
  NumClient        INT,
  Login            VARCHAR(50),
  DateAction       DATE,
  FOREIGN KEY (IDActionPossible) REFERENCES
    ActionPossible (IDActionPossible),
  FOREIGN KEY (NumClient) REFERENCES Client (NumClient)
);
CREATE TABLE Adr_Facturation (
  IDAdresseFacturation INT PRIMARY KEY AUTO_INCREMENT,
  NumClient            INT,
  Civilite             VARCHAR(5),
  Contact              VARCHAR(40),
  Adresse              VARCHAR(150),
  CodePostal           VARCHAR(5),
  Ville                VARCHAR(40),
  Pays                 VARCHAR(40),
  Telephone            VARCHAR(20),
  Mobile               VARCHAR(20),
  Fax                  VARCHAR(20),
  Email                VARCHAR(40),
  Observation          TEXT,
  FOREIGN KEY (NumClient) REFERENCES Client (NumClient)
);
CREATE TABLE Adr_Livraison (
  IDAdresseLivraison INT PRIMARY KEY AUTO_INCREMENT,
  NumClient          INT,
  Civilite           VARCHAR(5),
  Contact            VARCHAR(40),
  Adresse            VARCHAR(150),
  CodePostal         VARCHAR(5),
  Ville              VARCHAR(40),
  Pays               VARCHAR(40),
  Telephone          VARCHAR(20),
  Mobile             VARCHAR(20),
  Fax                VARCHAR(20),
  Email              VARCHAR(40),
  Observation        TEXT,
  FOREIGN KEY (NumClient) REFERENCES Client (NumClient)

);
CREATE TABLE Avoir (
  NumAvoir             INT PRIMARY KEY AUTO_INCREMENT,
  DateAvoir            DATE,
  NumClient            INT,
  IDAdresseFacturation INT,
  IDModeReglement      INT,
  TotalHT              DOUBLE,
  TotalTVA             Double,
  TotalTTC             Double,
  Utilise              BOOLEAN,
  SaisiPar             VARCHAR(40),
  SaisiLe              DATE,
  Observation          TEXT,
  FOREIGN KEY (NumClient) REFERENCES Client (NumClient)

);

CREATE TABLE Commande (
  NumCommande          INT PRIMARY KEY AUTO_INCREMENT,
  DateCommande         Date,
  NumClient            INT,
  IDAdresseLivraison   INT,
  IDModeLivraison      INT,
  IDAdresseFacturation INT,
  IDModeReglement      INT,
  TotalHT              DOUBLE,
  TotalTVA             Double,
  TotalTTC             Double,
  TotalFraisPort       Double,
  EtatCommande         INT,
  SaisiPar             VARCHAR(40),
  SaisiLe              DATE,
  Observation          TEXT,
  FOREIGN KEY (NumClient) REFERENCES Client (NumClient),
  FOREIGN KEY (IDModeReglement) REFERENCES ModeReglement (IDModeReglement),
  FOREIGN KEY (IDAdresseFacturation) REFERENCES Adr_Facturation (IDAdresseFacturation),
  FOREIGN KEY (IDAdresseLivraison) REFERENCES Adr_Livraison (IDAdresseLivraison)
);

CREATE TABLE Devis (
  IDDevis     INT PRIMARY KEY AUTO_INCREMENT,
  DateDevis   DATE,
  NumClient   INT,
  TotalHT     DOUBLE,
  TotalTVA    Double,
  TotalTTC    Double,
  SaisiPar    VARCHAR(40),
  SaisiLe     DATE,
  Observation TEXT,
  FOREIGN KEY (NumClient) REFERENCES Client (NumClient)

);

CREATE TABLE Facture (
  NumFacture           INT PRIMARY KEY AUTO_INCREMENT,
  DateFacture          Date,
  NumClient            INT,
  IDAdresseFacturation INT,
  IDModeReglement      INT,
  TotalHT              DOUBLE,
  TotalTVA             Double,
  TotalTTC             Double,
  TotalFraisPort       Double,
  Remise               DECIMAL,
  Acquitee             Boolean,
  SaisiPar             VARCHAR(40),
  SaisiLe              DATE,
  Observation          TEXT,
  FOREIGN KEY (NumClient) REFERENCES Client (NumClient),
  FOREIGN KEY (IDModeReglement) REFERENCES ModeReglement (IDModeReglement),
  FOREIGN KEY (IDAdresseFacturation) REFERENCES Adr_Facturation (IDAdresseFacturation)
);

CREATE TABLE Produit (
  Reference       VARCHAR(20) PRIMARY KEY,
  GenCode         VARCHAR(40),
  CodeBar         VARCHAR(40),
  LibProd         VARCHAR(40),
  Description     TEXT,
  PrixHT          DOUBLE,
  QteReappro      INT,
  QteMini         INT,
  TauxTVA         DOUBLE,
  Photo           BLOB,
  NumFournisseur  INT,
  PlusAuCatalogue Boolean,
  SaisiPar        VARCHAR(40),
  SaisiLe         DATE,
  CodeFamille     VARCHAR(40),
  CodePort        VARCHAR(20),
  FOREIGN KEY (TauxTVA) REFERENCES TVA (TauxTVA),
  FOREIGN KEY (CodePort) REFERENCES FraisPort (CodePort),
  FOREIGN KEY (CodeFamille) REFERENCES Famille (CodeFamille),
  FOREIGN KEY (NumFournisseur) REFERENCES Fournisseur (NumFournisseur)
);
CREATE TABLE LigneCde (
  IDLigneCde     INT PRIMARY KEY AUTO_INCREMENT,
  NumCommande    INT,
  Reference      VARCHAR(20),
  LibProd        VARCHAR(40),
  Quantite       INT,
  PrixVente      DOUBLE,
  Remise         DOUBLE,
  Livre          BOOLEAN,
  Total          DOUBLE,
  TauxTVA        DOUBLE,
  IDDevis        INT,
  OrdreAffichage INT,
  FOREIGN KEY (TauxTVA) REFERENCES TVA (TauxTVA),
  FOREIGN KEY (Reference) REFERENCES Produit (Reference),
  FOREIGN KEY (NumCommande) REFERENCES Commande (NumCommande)
);

CREATE TABLE LigneFac (
  IDLigneFac     INT PRIMARY KEY AUTO_INCREMENT,
  NumFacture     INT,
  Reference      VARCHAR(20),
  LibProd        VARCHAR(40),
  Quantite       INT,
  PrixVente      DOUBLE,
  Remise         DOUBLE,
  TauxTVA        DOUBLE,
  IDLigneCde     INT,
  OrdreAffichage INT,
  FOREIGN KEY (Reference) REFERENCES Produit (Reference),
  FOREIGN KEY (NumFacture) REFERENCES Facture (NumFacture),
  FOREIGN KEY (IDLigneCde) REFERENCES LigneCde (IDLigneCde)
);

CREATE TABLE LigneAvoir (
  IDLigneAvoir INT PRIMARY KEY AUTO_INCREMENT,
  NumAvoir     INT,
  Reference    VARCHAR(20),
  LibProd      VARCHAR(40),
  Quantite     INT,
  Prix         DOUBLE,
  TauxTVA      DOUBLE,
  IDLigneFac   INT,
  FOREIGN KEY (Reference) REFERENCES Produit (Reference),
  FOREIGN KEY (NumAvoir) REFERENCES Avoir (NumAvoir),
  FOREIGN KEY (IDLigneFac) REFERENCES LigneFac (IDLigneFac)
);

CREATE TABLE LigneDevis (
  IDLigneDevis   INT PRIMARY KEY AUTO_INCREMENT,
  Reference      VARCHAR(20),
  LibProd        VARCHAR(40),
  Quantite       INT,
  Remise         DOUBLE,
  PrixVente      DOUBLE,
  TauxTVA        DOUBLE,
  IDDevis        INT,
  OrdreAffichage INT,
  FOREIGN KEY (Reference) REFERENCES Produit (Reference),
  FOREIGN KEY (TauxTVA) REFERENCES TVA (TauxTVA),
  FOREIGN KEY (IDDevis) REFERENCES Devis (IDDevis)
);



CREATE TABLE EntreeStock (
  IDEntree       INT PRIMARY KEY AUTO_INCREMENT,
  DateAppro      DATE,
  Reference      VARCHAR(20),
  Quantite       INT,
  PrixAchat      DOUBLE,
  NumFournisseur INT,
  SaisiPar       VARCHAR(40),
  SaisiLe        DATE,
  Observation    TEXT,
  FOREIGN KEY (NumFournisseur) REFERENCES Fournisseur (NumFournisseur),
  FOREIGN KEY (Reference) REFERENCES Produit (Reference)
);
CREATE TABLE Reglement (
  IDReglement     INT PRIMARY KEY AUTO_INCREMENT,
  DateReglement   DATE,
  IDModeReglement INT,
  NumFacture      INT,
  SaisiPar        VARCHAR(40),
  SaisiLe         DATE,
  Observation     TEXT,
  FOREIGN KEY (IDModeReglement) REFERENCES ModeReglement (IDModeReglement),
  FOREIGN KEY (NumFacture) REFERENCES Facture (NumFacture)
);

CREATE TABLE SortieStock (
  IDSortie    INT PRIMARY KEY AUTO_INCREMENT,
  Reference   VARCHAR(20),
  Quantite    INT,
  DateSortie  DATE,
  Motif       VARCHAR(40),
  SaisiPar    VARCHAR(40),
  SaisiLe     DATE,
  Observation TEXT,
  FOREIGN KEY (Reference) REFERENCES Produit (Reference)
);
