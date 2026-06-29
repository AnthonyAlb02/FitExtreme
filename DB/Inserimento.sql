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
 SHA2('password123', 256), 'Mario', 'Rossi', '3331234567',
 'Via Roma 10, Napoli', NULL, NULL, NULL, NULL, 'registrato'),

('luca.bianchi@example.com', CURDATE(),
 SHA2('password456', 256), 'Luca', 'Bianchi', '3339876543',
 'Via Milano 22, Salerno', NULL, NULL, NULL, NULL, 'registrato'),


('admin@example.com', CURDATE(),
 SHA2('AdminMaster1234_', 256), 'Admin', 'Master', '3330001111',
 'Via Università 1, Fisciano', NULL, NULL, 10, 'Gestione Sistema', 'admin'),

('admin.master@fitextreme.it', CURDATE(),
 SHA2('AdminMaster1234_', 256), 'Admin', 'Master', '0000000000',
 'Sistema', NULL, NULL, 10, 'Gestione Sistema', 'admin');


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
('Manubri 5kg',                  'Coppia di manubri da 5kg',                29.99, 0, 3, '5chili.png'),
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
(1, 1, 5, 'Proteine ottime, gusto cacao molto naturale e senza grumi.', CURDATE()),
(2, 1, 4, 'Buon prodotto, si scioglie bene ma gusto un po’ intenso.', CURDATE()),

(1, 2, 5, 'Scaldacollo perfetto per correre la mattina presto.', CURDATE()),
(2, 2, 3, 'Materiale buono ma un po’ leggero per l’inverno.', CURDATE()),

(1, 3, 5, 'Cintura solidissima, mi ha migliorato la stabilità negli squat.', CURDATE()),
(2, 3, 4, 'Ottima qualità, ma la chiusura poteva essere più robusta.', CURDATE()),

(1, 4, 5, 'Guanti comodissimi, presa migliorata tantissimo!', CURDATE()),
(2, 4, 4, 'Buoni per allenarsi, ma leggermente stretti.', CURDATE()),

(1, 5, 4, 'Fascia smartphone utile e stabile durante la corsa.', CURDATE()),
(2, 5, 5, 'Perfetta! Non scivola mai e tiene bene il telefono.', CURDATE()),

(1, 6, 5, 'Borraccia eccellente, mantiene il freddo per ore.', CURDATE()),
(2, 6, 4, 'Buona qualità, ma un po’ grande per la mia borsa.', CURDATE()),

(1, 7, 5, 'Manubri ben bilanciati, ottima impugnatura.', CURDATE()),
(2, 7, 5, 'Perfetti per allenarsi a casa, consigliati.', CURDATE()),

(1, 8, 5, 'Set elastici completo, resistenze ben calibrate.', CURDATE()),
(2, 8, 4, 'Buoni, ma l’elastico più duro è davvero impegnativo.', CURDATE()),

(1, 9, 5, 'Tappetino comodo e antiscivolo, ottimo per yoga.', CURDATE()),
(2, 9, 4, 'Buona qualità, ma avrei preferito fosse più spesso.', CURDATE()),

(1, 10, 5, 'Proteine al cocco buonissime, gusto delicato.', CURDATE()),
(2, 10, 4, 'Ottime, ma prezzo un po’ alto.', CURDATE()),

(1, 11, 5, 'Barretta buonissima, perfetta come snack post-allenamento.', CURDATE()),
(2, 11, 4, 'Buona ma un po’ dolce per i miei gusti.', CURDATE());



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






-- ===================== PAGAMENTI =====================

INSERT INTO Pagamento
(ID_Ordine, Data_Transazione, Metodo_Pagamento, Importo_Saldato)
VALUES
(1, CURDATE(), 'Carta di Credito', 39.98),
(2, CURDATE(), 'PayPal',           29.99);