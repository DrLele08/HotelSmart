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
-- Password: Password1!
INSERT INTO Utente(ksRuolo, cf, nome, cognome, email, password, dataNascita, tokenAuth)
VALUES(1,"admamm98b16i823a","Admin","Amministratore","admin@gmail.com",
"0cef1fb10f60529028a71f58e54ed07b",
"1998-04-16", "YWRtaW5AZ21haWwuY29tOlBhc3N3b3JkMSE");
INSERT INTO Utente(ksRuolo, cf, nome, cognome, email, password, dataNascita, tokenAuth)
VALUES(2,"opropt99f22u653d","Operator","Operatore","operator@gmail.com",
"0cef1fb10f60529028a71f58e54ed07b",
"1999-06-22", "b3BlcmF0b3JAZ21haWwuY29tOlBhc3N3b3JkMSE");
INSERT INTO Utente(ksRuolo, cf, nome, cognome, email, password, dataNascita, tokenAuth)
VALUES(3,"tststr00b09i982u","Test","Tester","test@gmail.com",
"0cef1fb10f60529028a71f58e54ed07b",
"2000-02-09", "dGVzdEBnbWFpbC5jb206UGFzc3dvcmQxIQ");

																							

-- Popolazione Stanza
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,0,1,1,105,10);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,1,0,1,80,5);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,0,2,1,145,0);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,1,3,0,115,15);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,0,2,0,80,8);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,1,3,1,180,23);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,0,1,1,88,4);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,1,4,1,265,0);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,0,1,2,142,14);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,1,4,0,144,22);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,0,0,1,95,15);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,1,0,2,108,18);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,0,1,1,95,4);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,1,0,3,188,18);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,0,3,0,113,0);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,1,5,0,155,21);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,0,3,1,178,23);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(0,1,1,0,45,0);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,0,1,0,55,6);
INSERT INTO Stanza(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto)
VALUES(1,1,2,0,71,8);

-- Popolazione PrenotazioneStanza
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 1, 1, '2022-05-04', '2022-09-08', 100, 'aaa', 'token1', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(2, 3, 2, '2022-05-04', '2022-09-08', 100, 'aaa', 'token2', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(3, 6, 3, '2022-05-04', '2022-09-08', 100, 'aaa', 'token3', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(1, 2, 4, '2022-05-04', '2022-09-08', 100, 'aaa', 'token4', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(2, 5, 5, '2022-05-04', '2022-09-08', 100, 'aaa', 'token5', 'aaa', 0);
INSERT INTO PrenotazioneStanza(ksUtente, ksStanza, ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione)
VALUES(3, 8, 6, '2022-05-04', '2022-09-08', 100, 'aaa', 'token6', 'aaa', 0);

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