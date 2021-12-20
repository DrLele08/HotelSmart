-- Popolazione DB

-- Popolazione Utility
INSERT INTO Utility (Tipo, Valore) VALUES("ACTIVE_LOGIN",1);
INSERT INTO Utility (Tipo, Valore) VALUES("ACTIVE_SIGNUP",1);
INSERT INTO Utility (Tipo, Valore) VALUES("ACTIVE_SEARCH",1);

-- Popolazione Ruolo
INSERT INTO Ruolo(Tipo) VALUES("Admin");
INSERT INTO Ruolo(Tipo) VALUES("Assistente");
INSERT INTO Ruolo(Tipo) VALUES("Utente");
INSERT INTO Ruolo(Tipo) VALUES("Bannato");

-- Popolazione Stato Prenotazione
INSERT INTO Stato(Stato) VALUES("IN ATTESA DI PAGAMENTO");
INSERT INTO Stato(Stato) VALUES("CONFERMATA");
INSERT INTO Stato(Stato) VALUES("IN CORSO");
INSERT INTO Stato(Stato) VALUES("ARCHIVIATA");
INSERT INTO Stato(Stato) VALUES("RIMBORSATA");
INSERT INTO Stato(Stato) VALUES("ANNULLATA");


-- Popolazione Utente SHA-256
INSERT INTO Utente(ksRuolo,CF,Nome,Cognome,Email,Password,DataNascita,TokenAuth) VALUES(1,"","Raffaele","Sais","saisraffaele08@gmail.com",
																							"7c0eff32d087e2bd940faa00d92c5aef4b2436d9e4a759d710902f346ff64ee5"
																							,"2000-07-30","wocZmXKWrxp4z5ZMRrCo3InhR8RKWzMqVBwNan6TxVpzZQNI2gGdLXRMddWDCutp");
																							

-- Popolazione Stanza
INSERT INTO Stanza(AnimaleDom,Fumatore,LettiSingoli,LettiMatrimoniali,CostoNotte,Sconto) VALUES(0,1,0,2,9,0);
INSERT INTO Stanza(AnimaleDom,Fumatore,LettiSingoli,LettiMatrimoniali,CostoNotte,Sconto) VALUES(1,1,0,1,7,0);																						
INSERT INTO Stanza(AnimaleDom,Fumatore,LettiSingoli,LettiMatrimoniali,CostoNotte,Sconto) VALUES(1,0,2,2,18,3);																						
INSERT INTO Stanza(AnimaleDom,Fumatore,LettiSingoli,LettiMatrimoniali,CostoNotte,Sconto) VALUES(0,0,1,1,13,0);																						
INSERT INTO Stanza(AnimaleDom,Fumatore,LettiSingoli,LettiMatrimoniali,CostoNotte,Sconto) VALUES(0,1,2,0,10,0);

-- Popolazione Servizi
INSERT INTO Servizio(Nome,Descrizione,Foto,Costo,LimitePosti) VALUES("Pool Party","Super pool party","Servizi/Pool_Party.jpeg",4,100);
INSERT INTO Servizio(Nome,Descrizione,Foto,Costo,LimitePosti) VALUES("Ristorante","Cena nel nostro lussuoso ristorante","Servizi/Ristorante.jpeg",12,250);

-- Popolazione Foto Stanze
INSERT INTO Foto(ksStanza,Percorso) VALUES(1,"Stanza/1.jpeg");
INSERT INTO Foto(ksStanza,Percorso) VALUES(1,"Stanza/2.jpeg");

INSERT INTO Foto(ksStanza,Percorso) VALUES(2,"Stanza/3.jpeg");
INSERT INTO Foto(ksStanza,Percorso) VALUES(2,"Stanza/4.jpeg");

INSERT INTO Foto(ksStanza,Percorso) VALUES(3,"Stanza/5.jpeg");
INSERT INTO Foto(ksStanza,Percorso) VALUES(3,"Stanza/6.jpeg");

INSERT INTO Foto(ksStanza,Percorso) VALUES(4,"Stanza/7.jpeg");
INSERT INTO Foto(ksStanza,Percorso) VALUES(4,"Stanza/8.jpeg");

INSERT INTO Foto(ksStanza,Percorso) VALUES(5,"Stanza/9.jpeg");
INSERT INTO Foto(ksStanza,Percorso) VALUES(5,"Stanza/10.jpeg");																																																																															