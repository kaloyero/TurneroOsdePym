package ar.com.osdepym.turnerorestapi.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de consultas genericas y comunes
 * devuelve la conexion a la base de datos
 * 
 * @author Alejandro Masciotra
 *
 */
public class BaseDao {
    private static final String MYSQL_DRIVERS = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SAT";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connect = null;

	/**
	 * Devuelve la conexion
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected Statement getConn() throws ClassNotFoundException, SQLException{
	
        // Cargo los drivers de MySQL
        Class.forName(MYSQL_DRIVERS);
        // Configuracion
        connect = DriverManager.getConnection(DB_URL, USER, PASSWORD);

        // statements allow to issue SQL queries to the database
        return connect.createStatement();
	}
}
