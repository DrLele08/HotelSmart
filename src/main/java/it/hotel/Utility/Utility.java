package it.hotel.Utility;

import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.stato.Stato;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utility
{
    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static final int lenghtAuth=64;
    public static final String SESSION_USER="idUsSe";
    public static final String COOKIE_ID="idUsCo";
    public static final String COOKIE_TOKEN="toUsCo";
    public static final String CHECK_LOGIN="ACTIVE_LOGIN";
    public static List<Ruolo> listRuoli=new ArrayList<>();
    public static List<Stato> listStato=new ArrayList<>();

    public static boolean isActive(String s)
    {
        return UtilityDAO.isActive(s);
    }

    public static Date dataConverter(String dataStr) throws ParseException {
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dataUtil = in.parse(dataStr);
        Date data = new Date(dataUtil.getTime());
        return data;
    }
}
