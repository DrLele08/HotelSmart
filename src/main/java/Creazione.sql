DROP DATABASE IF EXISTS HotelSmart;
CREATE DATABASE HotelSmart;
use HotelSmart;

CREATE TABLE Ruolo
(
	idRuolo INT NOT NULL AUTO_INCREMENT,
	ruolo VARCHAR(45) NOT NULL,
	PRIMARY KEY (idRuolo)
);

CREATE TABLE Utente
(
	idUtente INT NOT NULL AUTO_INCREMENT,
	ksRuolo INT NOT NULL,
	CF CHAR(16) NOT NULL,
	nome VARCHAR(65) NOT NULL,
	cognome VARCHAR(65) NOT NULL,
	email VARCHAR(65) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	dataNascita DATE NOT NULL,
	tokenAuth CHAR(64) NOT NULL,
	PRIMARY KEY (idUtente),
	FOREIGN KEY (ksRuolo) REFERENCES Ruolo(idRuolo)
);

CREATE TABLE PersonaAggiuntiva
(
	idPersona INT NOT NULL AUTO_INCREMENT,
	ksUtente INT NOT NULL,
	CF CHAR(16) NOT NULL,
	Nome VARCHAR(65) NOT NULL,
	Cognome VARCHAR(65) NOT NULL,
	DataNascita DATE NOT NULL,
	PRIMARY KEY (idPersona),
	FOREIGN KEY (ksUtente) REFERENCES Utente(idUtente)
);

CREATE TABLE Stanza
(
	idStanza INT NOT NULL AUTO_INCREMENT,
	AnimaleDom BOOLEAN NOT NULL,
	Fumatore BOOLEAN NOT NULL,
	LettiSingoli INT NOT NULL,
	LettiMatrimoniali INT NOT NULL,
	CostoNotte DOUBLE NOT NULL,
	Sconto DOUBLE NOT NULL DEFAULT 0,
	PRIMARY KEY (idStanza)
);

CREATE TABLE Foto
(
	idFoto INT NOT NULL AUTO_INCREMENT,
	ksStanza INT NOT NULL,
	Percorso VARCHAR(128) NOT NULL,
	PRIMARY KEY (idFoto),
	FOREIGN KEY (ksStanza) REFERENCES Stanza(idStanza)
);

CREATE TABLE Stato
(
	idStato INT NOT NULL AUTO_INCREMENT,
	Stato VARCHAR(45) NOT NULL,
	PRIMARY KEY (idStato)
);

CREATE TABLE PrenotazioneStanza
(
	idPrenotazione INT NOT NULL AUTO_INCREMENT,
	ksUtente INT NOT NULL,
	ksStanza INT NOT NULL,
	ksStato INT NOT NULL,
	DataInizio DATE NOT NULL,
	DataFine DATE NOT NULL,
	PrezzoFinale FLOAT NOT NULL,
	TokenStripe VARCHAR(255),
	TokenQR CHAR(48),
	Commenti VARCHAR(255),
	Valutazione INT DEFAULT -1,
	PRIMARY KEY (idPrenotazione),
	FOREIGN KEY (ksUtente) REFERENCES Utente(idUtente),
	FOREIGN KEY (ksStanza) REFERENCES Stanza(idStanza),
	FOREIGN KEY (ksStato) REFERENCES Stato(idStato)
);

CREATE TABLE PersonePrenotazione
(
	idPersonaPrenotazione INT NOT NULL AUTO_INCREMENT,
	ksPersona INT NOT NULL,
	ksPrenotazione INT NOT NULL,
	PRIMARY KEY (idPersonaPrenotazione),
	FOREIGN KEY (ksPersona) REFERENCES PersonaAggiuntiva(idPersona),
	FOREIGN KEY (ksPrenotazione) REFERENCES PrenotazioneStanza(idPrenotazione)
);

CREATE TABLE Utility
(
	idUtility INT NOT NULL AUTO_INCREMENT,
	Tipo VARCHAR(45) NOT NULL,
	Valore INT NOT NULL,
	PRIMARY KEY (idUtility)
);

CREATE TABLE Servizio
(
	idServizio INT NOT NULL AUTO_INCREMENT,
	Nome VARCHAR(45) NOT NULL,
	Descrizione VARCHAR(45) NOT NULL,
	Foto VARCHAR(128) NOT NULL,
	Costo FLOAT NOT NULL,
	LimitePosti INT NOT NULL,
	PRIMARY KEY (idServizio)
);

CREATE TABLE PrenotazioneServizio
(
	idPrenotazioneServizio INT NOT NULL AUTO_INCREMENT,
	ksPrenotazione INT NOT NULL,
	ksServizio INT NOT NULL,
	NumPersone INT NOT NULL,
	PRIMARY KEY (idPrenotazioneServizio),
	FOREIGN KEY (ksPrenotazione) REFERENCES PrenotazioneStanza(idPrenotazione),
	FOREIGN KEY (ksServizio) REFERENCES Servizio(idServizio)
);

