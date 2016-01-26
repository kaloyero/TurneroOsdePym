package ar.com.osdepym.turnerobatch.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Dao generico:
 *  - tiene la conexion a la base de datos
 *  - contiene funciones generales y comunes
 * 
 * @author Alejandro Masciotra
 *
 */
public class BaseDao {
    private static final String MYSQL_DRIVERS = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CESAT";
    private static final String USER = "root";
    private static final String PASSWORD = "";

	protected Statement statement = null;
	protected ResultSet resultSet = null;
    
    private Connection connect = null;
    
	/**tos
	 * Devuelve la conexion con la base de da
	 * 
	 * @return statement
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
	
	protected void setAutocomitFalse(){
		try {
			connect.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Error autocommit");
		}
	}
	
	protected void setCommitTran(){
		try {
			connect.commit();
		} catch (SQLException e) {
			System.out.println("Error Commit transaction");
		}
	}

	protected void closeConn() {
		
		if (resultSet != null) {
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
	
	
}
