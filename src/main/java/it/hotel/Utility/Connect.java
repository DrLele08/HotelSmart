package it.hotel.Utility;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;
/**
 * @author Sais Raffaele, Giovanni De Pierro, Pierpaolo Cammardella, Alessandro d'Esposito
 */
public class Connect
{
    private static DataSource datasource;

    public static Connection getConnection() throws SQLException
    {
        if (datasource == null)
        {
            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:mysql://localhost:3306/HotelSmart?serverTimezone=" + TimeZone.getDefault().getID());
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername("root");
            p.setPassword("password");
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setRemoveAbandonedTimeout(60);
            p.setRemoveAbandoned(true);
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
        return datasource.getConnection();
    }
}
