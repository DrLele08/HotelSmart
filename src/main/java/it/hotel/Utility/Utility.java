package it.hotel.Utility;

import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.stato.Stato;

import java.util.ArrayList;

public class Utility
{
    public static final int lenghtAuth=64;
    public static final String SESSION_USER="idUsSe";
    public static final String COOKIE_ID="idUsCo";
    public static final String COOKIE_TOKEN="toUsCo";
    public static final String CHECK_LOGIN="ACTIVE_LOGIN";
    public static ArrayList<Ruolo> listRuoli=new ArrayList<>();
    public static ArrayList<Stato> listStato=new ArrayList<>();

    public static boolean isActive(String s)
    {
        return UtilityDAO.isActive(s);
    }
}
