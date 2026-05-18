
-- DATABASE E-COMMERCE 


DROP DATABASE IF EXISTS ecommerce;
CREATE DATABASE ecommerce;
USE ecommerce;


-- TABELLA UTENTE

CREATE TABLE Utente (
    ID_Utente INT AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Data_Registrazione DATE NOT NULL,

    Password_Hash VARCHAR(255),
    Nome VARCHAR(100),
    Cognome VARCHAR(100),
    Telefono VARCHAR(30),
    Indirizzo_Spedizione VARCHAR(255),

    Session_ID VARCHAR(255),
    IP_Address VARCHAR(45),

    Livello_Accesso INT,
    Area_Competenza VARCHAR(255),

    Ruolo ENUM('registrato','guest','admin') NOT NULL
);

-- ============================================================
-- TABELLA CATEGORIA
-- ============================================================
CREATE TABLE Categoria (
    ID_Categoria INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL UNIQUE
);


-- TABELLA ARTICOLO

CREATE TABLE Articolo (
    ID_Articolo INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Articolo VARCHAR(255) NOT NULL,
    Descrizione TEXT,
    Prezzo_Listino DECIMAL(10,2) NOT NULL,
    Qta_Disponibile INT NOT NULL,
    ID_Categoria INT,
    Immagine VARCHAR(255),   -- ⭐ AGGIUNTA

    FOREIGN KEY (ID_Categoria) REFERENCES Categoria(ID_Categoria)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);


-- TABELLA TAGLIA 

CREATE TABLE Taglia (
    ID_Taglia INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Taglia VARCHAR(10) NOT NULL UNIQUE
);


-- TABELLA ARTICOLO_TAGLIA 
-
CREATE TABLE Articolo_Taglia (
    ID_Articolo INT NOT NULL,
    ID_Taglia INT NOT NULL,
    Quantita INT NOT NULL,

    PRIMARY KEY (ID_Articolo, ID_Taglia),

    FOREIGN KEY (ID_Articolo) REFERENCES Articolo(ID_Articolo)
        ON DELETE CASCADE,

    FOREIGN KEY (ID_Taglia) REFERENCES Taglia(ID_Taglia)
        ON DELETE CASCADE
);


-- TABELLA RECENSIONE

CREATE TABLE Recensione (
    ID_Recensione INT AUTO_INCREMENT PRIMARY KEY,
    ID_Utente INT NOT NULL,
    ID_Articolo INT NOT NULL,
    Voto TINYINT NOT NULL CHECK (Voto BETWEEN 1 AND 5),
    Commento TEXT,
    Data_Recensione DATE NOT NULL,

    FOREIGN KEY (ID_Utente) REFERENCES Utente(ID_Utente)
        ON DELETE CASCADE,
    FOREIGN KEY (ID_Articolo) REFERENCES Articolo(ID_Articolo)
        ON DELETE CASCADE
);


-- TABELLA ORDINE

CREATE TABLE Ordine (
    ID_Ordine INT AUTO_INCREMENT PRIMARY KEY,
    ID_Utente INT NOT NULL,
    ID_Amministratore INT,
    Data_Ordine DATE NOT NULL,
    Stato_Avanzamento VARCHAR(50) NOT NULL,
    Importo_Totale DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (ID_Utente) REFERENCES Utente(ID_Utente)
        ON DELETE CASCADE,

    FOREIGN KEY (ID_Amministratore) REFERENCES Utente(ID_Utente)
        ON DELETE SET NULL
);


-- TABELLA DETTAGLIO ORDINE

CREATE TABLE Dettaglio_Ordine (
    ID_Dettaglio INT AUTO_INCREMENT PRIMARY KEY,
    ID_Ordine INT NOT NULL,
    ID_Articolo INT NOT NULL,
    Quantita INT NOT NULL,
    Prezzo_Acquisto DECIMAL(10,2) NOT NULL,
    Subtotale DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (ID_Ordine) REFERENCES Ordine(ID_Ordine)
        ON DELETE CASCADE,
    FOREIGN KEY (ID_Articolo) REFERENCES Articolo(ID_Articolo)
        ON DELETE CASCADE
);


-- TABELLA FATTURA

CREATE TABLE Fattura (
    ID_Fattura INT AUTO_INCREMENT PRIMARY KEY,
    ID_Ordine INT NOT NULL UNIQUE,
    Numero_Fattura VARCHAR(50) NOT NULL UNIQUE,
    Data_Emissione DATE NOT NULL,

    FOREIGN KEY (ID_Ordine) REFERENCES Ordine(ID_Ordine)
        ON DELETE CASCADE
);


-- TABELLA PAGAMENTO

CREATE TABLE Pagamento (
    ID_Pagamento INT AUTO_INCREMENT PRIMARY KEY,
    ID_Ordine INT NOT NULL UNIQUE,
    Data_Transazione DATE NOT NULL,
    Metodo_Pagamento VARCHAR(50) NOT NULL,
    Importo_Saldato DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (ID_Ordine) REFERENCES Ordine(ID_Ordine)
        ON DELETE CASCADE
);
