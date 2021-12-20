package it.hotel.model.utente;

public class UtenteService
{
    private UtenteDAO dao;

    public UtenteService()
    {
        dao=new UtenteDAO();
    }

    public Utente doLogin(String email,String pwd)
    {
        if(email.trim().isEmpty() && pwd.trim().isEmpty())
            return null;
        return null;
    }
}
