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
	cf CHAR(16) NOT NULL,
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
	cf CHAR(16) NOT NULL,
	nome VARCHAR(65) NOT NULL,
	cognome VARCHAR(65) NOT NULL,
	dataNascita DATE NOT NULL,
	PRIMARY KEY (idPersona),
	FOREIGN KEY (ksUtente) REFERENCES Utente(idUtente)
);

CREATE TABLE Stanza
(
	idStanza INT NOT NULL AUTO_INCREMENT,
	animaleDomestico BOOLEAN NOT NULL,
	fumatore BOOLEAN NOT NULL,
	lettiSingoli INT NOT NULL,
	lettiMatrimoniali INT NOT NULL,
	costoNotte DOUBLE NOT NULL,
	sconto DOUBLE NOT NULL DEFAULT 0,
	PRIMARY KEY (idStanza)
);

CREATE TABLE Foto
(
	idFoto INT NOT NULL AUTO_INCREMENT,
	ksStanza INT NOT NULL,
	percorso VARCHAR(128) NOT NULL,
	PRIMARY KEY (idFoto),
	FOREIGN KEY (ksStanza) REFERENCES Stanza(idStanza)
);

CREATE TABLE Stato
(
	idStato INT NOT NULL AUTO_INCREMENT,
	stato VARCHAR(45) NOT NULL,
	PRIMARY KEY (idStato)
);

CREATE TABLE PrenotazioneStanza
(
	idPrenotazioneStanza INT NOT NULL AUTO_INCREMENT,
	ksUtente INT NOT NULL,
	ksStanza INT NOT NULL,
	ksStato INT NOT NULL,
	dataInizio DATE NOT NULL,
	dataFine DATE NOT NULL,
	prezzoFinale DOUBLE NOT NULL,
	tokenStripe VARCHAR(255),
	tokenQr CHAR(48) UNIQUE,
	commenti VARCHAR(255),
	valutazione INT DEFAULT -1,
	PRIMARY KEY (idPrenotazioneStanza),
	FOREIGN KEY (ksUtente) REFERENCES Utente(idUtente),
	FOREIGN KEY (ksStanza) REFERENCES Stanza(idStanza),
	FOREIGN KEY (ksStato) REFERENCES Stato(idStato)
);

CREATE TABLE PersonePrenotazione
(
	idPersonaPrenotazione INT NOT NULL AUTO_INCREMENT,
	ksPersona INT NOT NULL,
	ksPrenotazioneStanza INT NOT NULL,
	PRIMARY KEY (idPersonaPrenotazione),
	FOREIGN KEY (ksPersona) REFERENCES PersonaAggiuntiva(idPersona),
	FOREIGN KEY (ksPrenotazioneStanza) REFERENCES PrenotazioneStanza(idPrenotazioneStanza)
);

CREATE TABLE Utility
(
	idUtility INT NOT NULL AUTO_INCREMENT,
	tipo VARCHAR(45) NOT NULL,
	valore INT NOT NULL,
	PRIMARY KEY (idUtility)
);

CREATE TABLE Servizio
(
	idServizio INT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45) NOT NULL,
	descrizione VARCHAR(45) NOT NULL,
	foto VARCHAR(128) NOT NULL,
	costo DOUBLE NOT NULL,
	limitePosti INT NOT NULL,
	PRIMARY KEY (idServizio)
);

CREATE TABLE PrenotazioneServizio
(
	idPrenotazioneServizio INT NOT NULL AUTO_INCREMENT,
	ksPrenotazioneStanza INT NOT NULL,
	ksServizio INT NOT NULL,
	numPersone INT NOT NULL,
    dataPrenotazioneServizio DATE NOT NULL,
	PRIMARY KEY (idPrenotazioneServizio),
	FOREIGN KEY (ksPrenotazioneStanza) REFERENCES PrenotazioneStanza(idPrenotazioneStanza),
	FOREIGN KEY (ksServizio) REFERENCES Servizio(idServizio)
);

