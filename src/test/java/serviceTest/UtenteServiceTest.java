package serviceTest;

import it.hotel.controller.exception.PermissionDeniedException;
import it.hotel.controller.services.RuoloService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import it.hotel.model.utente.utenteExceptions.EmailNotFoundException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UtenteServiceTest extends Mockito {

    private UtenteService service;
    private UtenteDAO dao;
    private Connection conn;

    @Before
    public void setUp()
    {
        service=Mockito.spy(new UtenteService());
        dao=Mockito.mock(UtenteDAO.class);
        conn=Mockito.mock(Connection.class);
    }

    @Test
    public void testDoLoginIdFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doAuthenticate(conn, 1, "token")).thenReturn(new Utente
                (1, 1, "cf", "nome", "cognome","email", new Date(0), "token"));
        Utente utente = service.doLogin(1, "token");
        Assert.assertNotEquals(utente, null);
    }

    @Test
    public void testDoLoginIdIllegalArgumentException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        Assert.assertThrows(IllegalArgumentException.class, ()->service.doLogin(1, ""));
    }

    @Test
    public void testDoLoginIdRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.doLogin(1, "token"));
    }

    @Test
    public void testDoLoginEmailFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.isEmailInDatabase(conn, "email")).thenReturn(true);
        when(dao.doAuthenticate(conn, "email", "password")).thenReturn(new Utente
                (1, 1, "cf", "nome", "cognome","email", new Date(0), "token"));
        Utente utente = service.doLogin("email", "password");
        Assert.assertNotEquals(utente, null);
    }

    @Test
    public void testDoLoginEmailIllegalArgumentException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        Assert.assertThrows(IllegalArgumentException.class, ()-> service.doLogin("", ""));
    }

    @Test
    public void testDoLoginEmailRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doThrow(new SQLException()).when(dao).isEmailInDatabase(conn, "email");
        Assert.assertThrows(RuntimeException.class, ()-> service.doLogin("email", "password"));
    }

    @Test
    public void testDoLoginEmailEmailNotFoundException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(false).when(dao).isEmailInDatabase(conn, "email");
        Assert.assertThrows(EmailNotFoundException.class, ()-> service.doLogin("email", "password"));
    }

    @Test
    public void testDoRegistrazioneRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doThrow(new SQLException()).when(dao).isEmailInDatabase(conn, "email");
        Assert.assertThrows(RuntimeException.class,()->
                service.doRegistrazione("asdfghjklasdfghj", "nome", "cognome", "email", new Date(0), "Password?9"));
    }

    @Test
    public void testDoRegistrazioneIllegalArgumentException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        Assert.assertThrows(IllegalArgumentException.class,()->
                service.doRegistrazione("cf", "nome", "cognome", "email", new Date(0), "password"));
    }

    @Test
    public void testDoRegistrazioneEmailAlreadyExistingException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.isEmailInDatabase(conn, "email")).thenReturn(true);
        Assert.assertThrows(EmailAlreadyExistingException.class,()->
                service.doRegistrazione("asdfghjklasdfghj", "nome", "cognome", "email", new Date(0), "Password?9"));
    }

    @Test
    public void testDoRegistrazionePasswordNotValidException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.isEmailInDatabase(conn, "email")).thenReturn(false);
        Assert.assertThrows(PasswordNotValidException.class,()->
                service.doRegistrazione("asdfghjklasdfghj", "nome", "cognome", "email", new Date(0), "Password"));
    }

    @Test
    public void testDoRegistrazioneFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.isEmailInDatabase(conn, "email")).thenReturn(false);
        when(service.generateToken(true, false)).thenReturn("token");
        RuoloService ruoloService = Mockito.mock(RuoloService.class);
        when(ruoloService.getByRuolo("UTENTE")).thenReturn(3);
        when(service.getRuoloService()).thenReturn(ruoloService);
        Date data = new Date(0);
        when(dao.doInsert(conn, 3, "asdfghjklasdfghj", "nome", "cognome", "email", data, "token", "Password?9")).thenReturn((new Utente
                (1, 3, "cf", "nome", "cognome","email", data, "token")));
        Utente utente = service.doRegistrazione("asdfghjklasdfghj", "nome", "cognome", "email", data, "Password?9");
        Assert.assertNotEquals(utente, null);
    }

    @Test
    public void testEditPasswordPermissionDeniedException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doGetRuolo(conn, 1, "token")).thenReturn(-1);
        Assert.assertThrows(PermissionDeniedException.class, ()->
                service.editPassword(1, "token", "oldPassword?9", "newPassword?9"));
    }

    @Test
    public void testEditPasswordPasswordNotValidException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doGetRuolo(conn, 1, "token")).thenReturn(3);
        when(dao.isPasswordValid(conn, 1, "password")).thenReturn(false);
        Assert.assertThrows(PasswordNotValidException.class, ()->
                service.editPassword(1, "token", "password", "newPassword?9"));
    }

    @Test
    public void testEditPasswordIllegalArgumentException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        Assert.assertThrows(IllegalArgumentException.class, ()->
                service.editPassword(1, "token", "password", "newPassword"));
    }

    @Test
    public void testEditPasswordRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(3).when(dao).doGetRuolo(conn, 1, "token");
        doThrow(new SQLException()).when(dao).doGetRuolo(conn, 1, "token");
        Assert.assertThrows(RuntimeException.class, ()->
                service.editPassword(1, "token", "password", "newPassword?9"));
    }

    @Test
    public void testEditPasswordFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doGetRuolo(conn, 1, "token")).thenReturn(3);
        when(dao.isPasswordValid(conn, 1, "password")).thenReturn(true);
        service.editPassword(1, "token", "password", "newPassword?9");
    }

    @Test
    public void testCreateDAO() {
        UtenteDAO dao = service.createDAO();
        Assert.assertNotEquals(dao, null);
    }

    @Test
    public void testGenerateToken() {
        String token = service.generateToken(true, true);
        Assert.assertNotEquals(token, null);
    }

    @Test
    public void testEditAnagraficaIllegalArgumentException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        Assert.assertThrows(IllegalArgumentException.class, ()->
                service.editAnagrafica(1, "token", "nome", "cognome", "cf", "data", "email"));
    }

    @Test
    public void testEditAnagraficaParseException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        Assert.assertThrows(ParseException.class, ()->
                service.editAnagrafica(1, "token", "nome", "cognome", "asdfghjklasdfghj",
                        "data", "email"));
    }

    @Test
    public void testEditAnagraficaEmailAlreadyExistingException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.isEmailInDatabase(conn, "email")).thenReturn(true);
        Assert.assertThrows(EmailAlreadyExistingException.class, ()->
                service.editAnagrafica(1, "token", "nome", "cognome", "asdfghjklasdfghj",
                        "2000-01-01", "email"));
    }

    @Test
    public void testEditAnagraficaSQLException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.isEmailInDatabase(conn, "email")).thenReturn(false);
        doThrow(new SQLException()).when(conn).setAutoCommit(true);
        Assert.assertThrows(SQLException.class, ()->
                service.editAnagrafica(1, "token", "nome", "cognome", "asdfghjklasdfghj",
                        "2000-01-01", "email"));
    }

    @Test
    public void testEditAnagraficaFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.isEmailInDatabase(conn, "email")).thenReturn(false);
        doNothing().when(dao).doChangeAnagrafica(conn, 1, "token", "nome", "cognome", "asdfghjklasdfghj", null, "email");
        service.editAnagrafica(1, "token", "nome", "cognome", "asdfghjklasdfghj",
                        "2000-01-01", "email");
    }

    @Test
    public void testGetAllReturnZero() throws Exception
    {
        List<Utente> emptyList=new ArrayList<>();
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.getUtenti(conn)).thenReturn(emptyList);
        List<Utente> result=service.getAll();
        Assert.assertEquals(result.size(),0);
    }

    @Test
    public void testGetAllRuntimeException() throws Exception
    {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getAll());
    }

    @Test
    public void testGetAllFine() throws Exception
    {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.getUtenti(conn)).thenReturn(new ArrayList<>());
        service.getAll();
    }

    @Test
    public void testEditRuoloByIdRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.editRuoloById(1, 1));
    }

    @Test
    public void testEditRuoloByIdFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(dao).doChangeRuolo(conn, 1, 1);
        service.editRuoloById(1, 1);
    }

    @Test
    public void testGetUtenteByPrenotazioneStanzaRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getUtenteByPrenotazioneStanza(1));
    }

    @Test
    public void testGetUtenteByPrenotazioneStanzaUtenteNotFoundException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doThrow(new UtenteNotFoundException()).when(dao).doSelectByPrenotazioneStanza(conn, 1);
        Assert.assertThrows(UtenteNotFoundException.class, ()->
                service.getUtenteByPrenotazioneStanza(1));
    }

}
