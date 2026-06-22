USE ecommerce;

-- ===================== CATEGORIE =====================

INSERT INTO Categoria (Nome) VALUES
('Abbigliamento'),
('Accessori'),
('Attrezzatura'),
('Integrazione');


-- ===================== UTENTI =====================

INSERT INTO Utente
(Email, Data_Registrazione, Password_Hash, Nome, Cognome, Telefono,
 Indirizzo_Spedizione, Session_ID, IP_Address, Livello_Accesso,
 Area_Competenza, Ruolo)
VALUES
('mario.rossi@example.com', CURDATE(),
 'c9f0f895fb98ab9159f51fd0297e236d', 'Mario', 'Rossi', '3331234567',
 'Via Roma 10, Napoli', NULL, NULL, NULL, NULL, 'registrato'),

('luca.bianchi@example.com', CURDATE(),
 'hash456', 'Luca', 'Bianchi', '3339876543',
 'Via Milano 22, Salerno', NULL, NULL, NULL, NULL, 'registrato'),

('guest1@example.com', CURDATE(),
 NULL, NULL, NULL, NULL,
 NULL, 'SESSION123', '192.168.1.10', NULL, NULL, 'guest'),

('admin@example.com', CURDATE(),
 '0f3f2f4f4c6f4d2f8b8c3f3d9b5e4c6a', 'Admin', 'Master', '3330001111',
 'Via Università 1, Fisciano', NULL, NULL, 10, 'Gestione Sistema', 'admin');


-- ===================== ARTICOLI =====================

INSERT INTO Articolo
(Nome_Articolo, Descrizione, Prezzo_Listino, Qta_Disponibile, ID_Categoria, Immagine)
VALUES
('Proteine idrolizzate (cacao)', 'Proteine idrolizzate gusto cacao',        39.99, 50, 4, 'prot.png'),
('Scaldacollo',                  'Scaldacollo per temperature proibitive',  14.99, 40, 1, 'caldo.png'),
('Cintura da sollevamento regolabile', 'Cintura per sollevamento pesi',     89.99, 30, 1, 'cinta.png'),
('Guanti da Palestra',           'Guanti antiscivolo per sollevamento pesi',14.99, 60, 2, 'guanti.png'),
('Fascia da Braccio Smartphone', 'Fascia elastica per smartphone',           9.99, 80, 2, 'fascia.png'),
('Borraccia Termica 750ml',      'Acciaio inox, mantiene la temperatura',   17.99, 70, 2, 'borraccia.png'),
('Manubri 5kg',                  'Coppia di manubri da 5kg',                29.99, 25, 3, '5chili.png'),
('Elastici Fitness Set',         'Set di elastici con diverse resistenze',  22.99, 50, 3, 'elastici.png'),
('Tappetino Yoga',               'Tappetino antiscivolo 180x60cm',          19.99, 40, 3, 'tappetino.png');
('Proteine idrolizzate (cocco)', 'Proteine idrolizzate gusto cocco',          69.99, 40, 4, 'cocco.png');
('Barretta proteica ai cereali',  'Barretta proteica multicereali',          0.99, 40, 4, 'barretta.png');

('Corde per Saltare Professionali', 'Corda speed rope con cuscinetti a sfera', 12.99, 65, 2, 'corde.png'),
('Foam Roller 45cm', 'Rullo in schiuma ad alta densità per recupero muscolare', 24.99, 35, 3, 'foam.png'),
('Shaker Proteine 600ml', 'Shaker BPA-free con scomporto pillole', 8.99, 100, 2, 'shaker.png'),
('Ginocchiere Compressione', 'Ginocchiere elastiche rinforzate coppia', 19.99, 55, 1, 'ginocchiere.png'),
('Polsiere Sollevamento', 'Polsiere imbottite con chiusura velcro', 11.99, 70, 1, 'polsiere.png'),
('Kettlebell 8kg', 'Kettlebell in ghisa con rivestimento in gomma', 34.99, 20, 3, 'kettlebell.png'),
('Zaino Sportivo 30L', 'Zaino impermeabile con scomparto scarpe', 39.99, 45, 2, 'zaino.png'),
('Fasce Sudore Testa', 'Set 3 fasce elastiche assorbenti', 7.99, 90, 2, 'fasce.png'),
('Palla Medica 5kg', 'Palla medica in pelle sintetica antiscivolo', 27.99, 30, 3, 'palla.png'),
('Calze Compressione Sport', 'Calze tecniche compressive coppia', 15.99, 75, 1, 'calze.png'),
('Smartwatch Fitness Tracker', 'Smartwatch con cardiofrequenzimetro e GPS', 89.99, 25, 2, 'smartwatch.png'),
('Tappetino Meditazione', 'Tappetino extra spesso 183x61cm', 29.99, 35, 3, 'meditazione.png'),
('BCAA Aminoacidi', 'Integratore aminoacidi ramificati gusto limone', 24.99, 60, 4, 'bcaa.png'),
('Pre-Workout Energy', 'Integratore pre-workout gusto frutta tropicale', 32.99, 45, 4, 'preworkout.png');





-- ===================== RECENSIONI =====================

INSERT INTO Recensione
(ID_Utente, ID_Articolo, Voto, Commento, Data_Recensione)
VALUES
(1, 1, 5, 'Ottima qualità, molto comoda!',                            CURDATE()),
(2, 4, 4, 'Buoni guanti, presi per palestra.',                        CURDATE()),
(1, 9, 5, 'Tappetino perfetto per yoga.',                             CURDATE()),
(2, 1, 4, 'Maglietta molto comoda, veste bene. Consigliata!',         CURDATE()),
(1, 2, 5, 'Pantaloncini leggerissimi, perfetti per correre.',          CURDATE()),
(2, 3, 3, 'Felpa buona ma un po'' calda. Qualità comunque ottima.',   CURDATE()),
(1, 4, 4, 'Guanti resistenti, ottima presa sui pesi.',                CURDATE()),
(2, 7, 5, 'Manubri solidi, impugnatura perfetta. Ottimo acquisto.',   CURDATE()),
(1, 8, 5, 'Set elastici completo, rapporto qualità/prezzo top.',      CURDATE()),
(2, 9, 4, 'Tappetino comodo e antiscivolo, perfetto per stretching.', CURDATE());


-- ===================== ORDINI =====================

INSERT INTO Ordine
(ID_Utente, ID_Amministratore, Data_Ordine, Stato_Avanzamento, Importo_Totale)
VALUES
(1, 4, CURDATE(), 'In elaborazione', 39.98),
(2, 4, CURDATE(), 'Spedito',         29.99);


-- ===================== DETTAGLI ORDINE =====================

INSERT INTO Dettaglio_Ordine
(ID_Ordine, ID_Articolo, Quantita, Prezzo_Acquisto, Subtotale)
VALUES
(1, 1, 2, 19.99, 39.98),
(2, 7, 1, 29.99, 29.99);


-- ===================== FATTURE =====================

INSERT INTO Fattura
(ID_Ordine, Numero_Fattura, Data_Emissione)
VALUES
(1, 'FT-2026-001', CURDATE()),
(2, 'FT-2026-002', CURDATE());


-- ===================== PAGAMENTI =====================

INSERT INTO Pagamento
(ID_Ordine, Data_Transazione, Metodo_Pagamento, Importo_Saldato)
VALUES
(1, CURDATE(), 'Carta di Credito', 39.98),
(2, CURDATE(), 'PayPal',           29.99);