--  content_manager
-- (Indica un sistema di gestione dei contenuti)

CREATE DATABASE content_manager;
SHOW DATABASES;

USE content_manager;
CREATE TABLE utente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,    -- Nome utente unico
    password_hash VARCHAR(255) NOT NULL,      -- Password cifrata
    email VARCHAR(255) NOT NULL UNIQUE,       -- Email unica
    ruolo ENUM('autore', 'revisore', 'admin') DEFAULT 'autore', -- Ruolo utente
    data_creazione DATETIME DEFAULT NOW()     -- Data di registrazione
);

CREATE TABLE articolo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titolo VARCHAR(255) NOT NULL,
    sottotitolo VARCHAR(255),
    testo TEXT NOT NULL,
    categoria VARCHAR(100),
    data_creazione DATETIME DEFAULT NOW(),
    stato_autorizzazione VARCHAR(50) DEFAULT 'bozza',  -- Stato autorizzativo
    visibilita TINYINT DEFAULT 0,                      -- Visibilità (0 = privato, 1 = pubblico)
    autore_id INT,                                     -- Relazione con la tabella utente
    FOREIGN KEY (autore_id) REFERENCES utente(id)
    ON UPDATE CASCADE 
    ON DELETE SET NULL
);


CREATE TABLE documento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    descrizione TEXT,
    struttura_json JSON NOT NULL,
    creato_da INT,                                  -- Relazione con la tabella utente
    FOREIGN KEY (creato_da) REFERENCES utente(id)
    ON UPDATE CASCADE 
    ON DELETE SET NULL
);

CREATE TABLE tipo_documento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descrizione TEXT,
    creato_da INT,                                  -- Relazione con la tabella utente
    FOREIGN KEY (creato_da) REFERENCES utente(id)
    ON UPDATE CASCADE 
    ON DELETE SET NULL
);
