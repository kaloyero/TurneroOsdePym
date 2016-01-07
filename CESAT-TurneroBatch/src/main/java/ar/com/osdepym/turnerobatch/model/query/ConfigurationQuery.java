package ar.com.osdepym.turnerobatch.model.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ar.com.osdepym.turnerobatch.model.dao.BaseDao;

/**
 * Query para interactuar con la tabla Configuracion
 * 
 * @author Alejandro Masciotra
 *
 */
public class ConfigurationQuery extends BaseDao{

    private Statement statement = null;
    private ResultSet resultSet = null;
    
    /**
     * Devuelve de la base de datos el periodo de repeticion con que se repetir[a la actualizacion de turnos
     * 
     * @return batch period
     */
    public int getBatchPeriod() {
    	int batchPeriod = 60;
    	try {
        	
        	statement = getConn();
            // resultSet gets the result of the SQL query
            resultSet = statement.executeQuery("select * from configuracion where clave like 'periodtime' ;");

            while (resultSet.next()) {
                String  value = resultSet.getString("value");
                batchPeriod = Integer.parseInt(value); 
            }

        } catch (Exception e) {
System.out.println(e);
        } finally {

            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                statement = null;
            }
        }
    	return batchPeriod;
    }

	
}
