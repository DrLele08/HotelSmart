-- Popolazione DB

-- Popolazione Utility
INSERT INTO Utility (tipo, valore) VALUES("ACTIVE_LOGIN",1);
INSERT INTO Utility (tipo, valore) VALUES("ACTIVE_SIGNUP",1);
INSERT INTO Utility (tipo, valore) VALUES("ACTIVE_SEARCH",1);

-- Popolazione Ruolo
INSERT INTO Ruolo(ruolo) VALUES("Admin");
INSERT INTO Ruolo(ruolo) VALUES("Assistente");
INSERT INTO Ruolo(ruolo) VALUES("Utente");

-- Popolazione Stato Prenotazione
INSERT INTO Stato(stato) VALUES("IN ATTESA DI PAGAMENTO");
INSERT INTO Stato(stato) VALUES("CONFERMATA");
INSERT INTO Stato(stato) VALUES("IN CORSO");
INSERT INTO Stato(stato) VALUES("ARCHIVIATA");
INSERT INTO Stato(stato) VALUES("RIMBORSATA");
INSERT INTO Stato(stato) VALUES("ANNULLATA");


-- Popolazione Utente SHA-256
INSERT INTO Utente(ksRuolo, cf, nome, cognome, email, password, dataNascita, tokenAuth)
VALUES(1,"","Raffaele","Sais","saisraffaele08@gmail.com",
"7c0eff32d087e2bd940faa00d92c5aef4b2436d9e4a759d710902f346ff64ee5",
"2000-07-30", "wocZmXKWrxp4z5ZMRrCo3InhR8RKWzMqVBwNan6TxVpzZQNI2gGdLXRMddWDCutp");
																							

-- Popolazione Stanza
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,1,0,2,9,0);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,1,0,1,7,0);																						
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,0,2,2,18,3);																						
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,0,1,1,13,0);																						
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,1,2,0,10,0);

-- Popolazione PrenotazioneStanza
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 1, 1, '2022-05-04', '2022-09-08', 100, 'aaa', 'token1', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 1, 2, '2022-05-04', '2022-09-08', 100, 'aaa', 'token2', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 1, 3, '2022-05-04', '2022-09-08', 100, 'aaa', 'token3', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 1, 4, '2022-05-04', '2022-09-08', 100, 'aaa', 'token4', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 1, 5, '2022-05-04', '2022-09-08', 100, 'aaa', 'token5', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 1, 6, '2022-05-04', '2022-09-08', 100, 'aaa', 'token6', 'aaa', 0);

-- Popolazione Servizi
INSERT INTO Servizio(nome, descrizione, foto, costo, limitePosti) 
VALUES("Pool Party","Super pool party","Servizi/Pool_Party.jpeg",4,100);
INSERT INTO Servizio(nome, descrizione, foto, costo, limitePosti) 
VALUES("Ristorante","Cena nel nostro lussuoso ristorante","Servizi/Ristorante.jpeg",12,250);

-- Popolazione Foto Stanze
INSERT INTO Foto(ksStanza, percorso) VALUES(1,"Stanza/1.jpeg");
INSERT INTO Foto(ksStanza, percorso) VALUES(1,"Stanza/2.jpeg");

INSERT INTO Foto(ksStanza, percorso) VALUES(2,"Stanza/3.jpeg");
INSERT INTO Foto(ksStanza, percorso) VALUES(2,"Stanza/4.jpeg");

INSERT INTO Foto(ksStanza, percorso) VALUES(3,"Stanza/5.jpeg");
INSERT INTO Foto(ksStanza, percorso) VALUES(3,"Stanza/6.jpeg");

INSERT INTO Foto(ksStanza, percorso) VALUES(4,"Stanza/7.jpeg");
INSERT INTO Foto(ksStanza, percorso) VALUES(4,"Stanza/8.jpeg");

INSERT INTO Foto(ksStanza, percorso) VALUES(5,"Stanza/9.jpeg");
INSERT INTO Foto(ksStanza, percorso) VALUES(5,"Stanza/10.jpeg");																																																																															