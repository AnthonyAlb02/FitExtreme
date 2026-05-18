USE ecommerce;


-- CATEGORIE

INSERT INTO Categoria (Nome) VALUES
('Abbigliamento'),
('Accessori'),
('Attrezzatura');


-- TAGLIE 

INSERT INTO Taglia (Nome_Taglia) VALUES
('S'), ('M'), ('L'), ('XL');


-- UTENTI

INSERT INTO Utente 
(Email, Data_Registrazione, Password_Hash, Nome, Cognome, Telefono, 
 Indirizzo_Spedizione, Session_ID, IP_Address, Livello_Accesso, 
 Area_Competenza, Ruolo)
VALUES
('mario.rossi@example.com', CURDATE(), 'c9f0f895fb98ab9159f51fd0297e236d1d6d1f5f0f1c3b4e3e2d1c0b9a8f7e6d4c3b2a1908f7e6d5c4b3a291817161514131211100f0e0d0c0b0a09080706
', 'Mario', 'Rossi', '3331234567',
 'Via Roma 10, Napoli', NULL, NULL, NULL, NULL, 'registrato'),

('luca.bianchi@example.com', CURDATE(), 'hash456', 'Luca', 'Bianchi', '3339876543',
 'Via Milano 22, Salerno', NULL, NULL, NULL, NULL, 'registrato'),

('guest1@example.com', CURDATE(), NULL, NULL, NULL, NULL,
 NULL, 'SESSION123', '192.168.1.10', NULL, NULL, 'guest'),

('admin@example.com', CURDATE(), 'adminhash', 'Admin', 'Master', '3330001111',
 'Via Università 1, Fisciano', NULL, NULL, 10, 'Gestione Sistema', 'admin');


-- ARTICOLI 

INSERT INTO Articolo 
(Nome_Articolo, Descrizione, Prezzo_Listino, Qta_Disponibile, ID_Categoria, Immagine)
VALUES
('Maglietta Sportiva Uomo', 'Maglietta traspirante per allenamento', 19.99, 0, 1, '1.jpeg'),
('Pantaloncini Running', 'Shorts leggeri per corsa', 24.99, 40, 1, '1.jpeg'),
('Felpa Fitness', 'Felpa termica per palestra', 39.99, 30, 1, '1.jpeg'),

('Guanti da Palestra', 'Guanti antiscivolo per sollevamento pesi', 14.99, 60, 2, '1.jpeg'),
('Fascia da Braccio Smartphone', 'Fascia elastica per smartphone', 9.99, 80, 2, '1.jpeg'),
('Borraccia Termica 750ml', 'Acciaio inox, mantiene la temperatura', 17.99, 70, 2, '1.jpeg'),

('Manubri 5kg', 'Coppia di manubri da 5kg', 29.99, 25, 3, '1.jpeg'),
('Elastici Fitness Set', 'Set di elastici con diverse resistenze', 22.99, 50, 3, '1.jpeg'),
('Tappetino Yoga', 'Tappetino antiscivolo 180x60cm', 19.99, 40, 3, '1.jpeg');


-- TAGLIE PER ABBIGLIAMENTO


-- Articolo 1: Maglietta Sportiva Uomo
INSERT INTO Articolo_Taglia VALUES
(1, 1, 10),  -- S
(1, 2, 15),  -- M
(1, 3, 15),  -- L
(1, 4, 10);  -- XL

-- Articolo 2: Pantaloncini Running
INSERT INTO Articolo_Taglia VALUES
(2, 1, 8),
(2, 2, 12),
(2, 3, 12),
(2, 4, 8);

-- Articolo 3: Felpa Fitness
INSERT INTO Articolo_Taglia VALUES
(3, 1, 5),
(3, 2, 10),
(3, 3, 10),
(3, 4, 5);

-- RECENSIONI

INSERT INTO Recensione 
(ID_Utente, ID_Articolo, Voto, Commento, Data_Recensione)
VALUES
(1, 1, 5, 'Ottima qualità, molto comoda!', CURDATE()),
(2, 4, 4, 'Buoni guanti, presi per palestra.', CURDATE()),
(1, 9, 5, 'Tappetino perfetto per yoga.', CURDATE());


-- ORDINI

INSERT INTO Ordine 
(ID_Utente, ID_Amministratore, Data_Ordine, Stato_Avanzamento, Importo_Totale)
VALUES
(1, 4, CURDATE(), 'In elaborazione', 39.98),
(2, 4, CURDATE(), 'Spedito', 29.99);


-- DETTAGLI ORDINE

INSERT INTO Dettaglio_Ordine 
(ID_Ordine, ID_Articolo, Quantita, Prezzo_Acquisto, Subtotale)
VALUES
(1, 1, 2, 19.99, 39.98),
(2, 7, 1, 29.99, 29.99);


-- FATTURE

INSERT INTO Fattura 
(ID_Ordine, Numero_Fattura, Data_Emissione)
VALUES
(1, 'FT-2026-001', CURDATE()),
(2, 'FT-2026-002', CURDATE());


-- PAGAMENTI

INSERT INTO Pagamento 
(ID_Ordine, Data_Transazione, Metodo_Pagamento, Importo_Saldato)
VALUES
(1, CURDATE(), 'Carta di Credito', 39.98),
(2, CURDATE(), 'PayPal', 29.99);
