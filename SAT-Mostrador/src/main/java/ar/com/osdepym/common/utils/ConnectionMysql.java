package ar.com.osdepym.common.utils;

// Turnero 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ConnectionMysql {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.MOSTRADOR
			+ "-" + ConnectionMysql.class);
	private static final String SERVER_NAME = "127.0.0.1";
	private static final int PORT_NUMBER = 3306;
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "";
	public static final String DB_NAME = "SAT";

	public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionMysql().execute();
		System.out.println("Conexion: " + con);
		Connection connection = new ConnectionMysql().execute();
		connection.close();
	}

	/*
	 * 
	 * mysql> GRANT ALL PRIVILEGES ON *.* TO username@localhost
	 * 
	 * IDENTIFIED BY 'password' WITH GRANT OPTION;
	 * 
	 * 
	 * 
	 * link -->
	 * http://exampledepot.8waytrips.com/search/luceneapi_node/create%20table
	 */

	/**
	 * Crea conexión a la base de datos.
	 * @return Connection
	 */
	public Connection createConnection() {
		MysqlDataSource ds = this.newDs();
		Connection connection = null;
		try {
			connection = ds.getConnection();
			LOGGER.info("Se ha establecido la conexión a DB");
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("Error de conexion 0" + e.getMessage());
			LOGGER.error("No se ha podido establecer la conexión a DB "
					+ e.getMessage());
		} finally {

		}
		return connection;
	}
	
	/**
	 * Crea el modelo de datos en la base, de no existir el mismo.
	 * @return  Connection
	 */
	public Connection execute() {
		MysqlDataSource ds = this.newDs();
		Connection connection = null;
		Date fechaDate = new Date();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String fecha = sdf.format(fechaDate);
		System.out.println("Fecha " + fecha);

		// create a sql date object so we can use it in our INSERT statement
		// Calendar calendar = Calendar.getInstance();
		// java.sql.Date startDate = new
		// java.sql.Date(calendar.getTime().getTime());
		//
		System.out.println("Fecha " + fecha);

		try {
			connection = ds.getConnection();
			Statement statement = connection.createStatement();

			// create the DB ..
			// statement.executeUpdate("CREATE DATABASE IF NOT EXISTS "+DB_NAME);
			//
			// // use DB ..
			// statement.executeUpdate("USE "+DB_NAME);
			//
			// //
			// --------------------------------------------------------------------------------------------------------
			// sat
			// // create table "sat"
			// statement.executeUpdate("DROP TABLE IF EXISTS sat");
			// statement.executeUpdate("CREATE TABLE sat("
			// + "id_sat INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			// + "cod_sucursal varchar(5) NOT NULL default '00001',"
			// + "nom_sucursal varchar(30) NOT NULL default 'Centro',"
			// + "versionSAT varchar(8) NOT NULL default '1.0',"
			// + "fech_act DATETIME NOT NULL );");
			// // the mysql insert statement
			// String query2 =
			// " insert into sat(id_sat, cod_sucursal,  nom_sucursal, versionSAT, fech_act)"
			// + " values (?, ?, ?, ?, ?)";
			//
			// // create the mysql insert preparedstatement
			// PreparedStatement preparedStmt2 =
			// connection.prepareStatement(query2);
			// preparedStmt2.setInt (1, 1);
			// preparedStmt2.setString (2, "00001");
			// preparedStmt2.setString (3,"Centro" );
			// preparedStmt2.setString(4, "1.0");
			// preparedStmt2.setString(5, fecha);
			//
			// // execute the preparedstatement
			// preparedStmt2.execute();
			//
			//
			//
			// //
			// --------------------------------------------------------------------------------------------------------
			// puesto
			// // create table "Puesto"
			// statement.executeUpdate("DROP TABLE IF EXISTS puesto");
			// statement.executeUpdate("CREATE TABLE puesto("
			// + "id_puesto INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, "
			// + "puesto INT(2) NOT NULL, "
			// + "ip varchar(15) NOT NULL);");
			//
			// statement.executeUpdate("INSERT INTO puesto(id_puesto,puesto, ip)"
			// + "VALUES('1','1','192.168.1.101');");
			// statement.executeUpdate("INSERT INTO puesto(id_puesto, puesto, ip)"
			// + "VALUES('2','2','192.168.1.102');");
			//
			//
			//
			// //
			// --------------------------------------------------------------------------------------------------------
			// sector
			// // create table "sector"
			// statement.executeUpdate("DROP TABLE IF EXISTS sector");
			// statement.executeUpdate("CREATE TABLE sector("
			// + "id_sector  INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			// + "sector   INT(1) NOT NULL,"
			// + "cod_sector varchar(1) NOT NULL,"
			// + "nom_sector varchar(15) NOT NULL,"
			// + "habilitado varchar(2) NOT NULL);"); // NO / SI
			//
			// statement.executeUpdate("INSERT INTO sector(id_sector, sector, cod_sector, nom_sector, habilitado)"
			// + "VALUES('1','1','A','Afiliados','SI')");
			//
			// statement.executeUpdate("INSERT INTO sector(id_sector, sector, cod_sector, nom_sector, habilitado)"
			// + "VALUES('2','2','B','Comercial','SI')");
			//
			//
			// //
			// --------------------------------------------------------------------------------------------------------
			// perfil
			// // create table "perfil"
			// statement.executeUpdate("DROP TABLE IF EXISTS perfil");
			// statement.executeUpdate("CREATE TABLE perfil("
			// + "id_perfil  INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			// + "perfil   INT(1) NOT NULL,"
			// + "nom_perfil varchar(15) NOT NULL);");
			//
			// statement.executeUpdate("INSERT INTO perfil(id_perfil,perfil,  nom_perfil )"
			// + "VALUES('1','1','Administrador')");
			//
			// statement.executeUpdate("INSERT INTO perfil(id_perfil,perfil,  nom_perfil )"
			// + "VALUES('2','2','Usuario')");
			//
			// statement.executeUpdate("INSERT INTO perfil(id_perfil,perfil,  nom_perfil )"
			// + "VALUES('3','3','Estadistica')");
			//
			//
			// //
			// --------------------------------------------------------------------------------------------------------
			// usuario
			// // create table "Usuario"
			// statement.executeUpdate("DROP TABLE IF EXISTS usuario");
			// statement.executeUpdate("CREATE TABLE usuario("
			// + "id_usuario  INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			// + "nom_usuario   varchar(15) NOT NULL,"
			// + "password   varchar(15) NOT NULL,"
			// + "id_sector   varchar(15) NOT NULL,"
			// + "id_perfil   int(1) NOT NULL,"
			// + "id_puesto   int(2) NOT NULL,"
			// + "fecha  DATETIME NOT NULL);") ;
			//
			//
			//
			// // the mysql insert statement
			// String query =
			// " insert into usuario (id_usuario, nom_usuario, password, id_sector, id_perfil, id_puesto,fecha)"
			// + " values (?, ?, ?, ?, ?, ?,?)";
			//
			// // create the mysql insert preparedstatement
			// PreparedStatement preparedStmt =
			// connection.prepareStatement(query);
			// preparedStmt.setInt (1, 1);
			// preparedStmt.setString (2, "Usuario_1");
			// preparedStmt.setString (3, "1234");
			// preparedStmt.setString (4,"1,2" );
			// preparedStmt.setInt(5, 2);
			// preparedStmt.setInt (6, 1);
			// preparedStmt.setString(7, fecha);
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 2);
			// preparedStmt.setString (2, "Usuario_2");
			// preparedStmt.setString (3, "1234");
			// preparedStmt.setString (4,"1,2" );
			// preparedStmt.setInt(5, 2);
			// preparedStmt.setInt(6, 2);
			// preparedStmt.setString(7, fecha);
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 3);
			// preparedStmt.setString (2, "Admin_1");
			// preparedStmt.setString (3, "1234");
			// preparedStmt.setString (4,"1" );
			// preparedStmt.setInt(5, 1);
			// preparedStmt.setInt(6, 1);
			// preparedStmt.setString(7, fecha);
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 4);
			// preparedStmt.setString (2, "Admin_2");
			// preparedStmt.setString (3, "1234");
			// preparedStmt.setString (4,"1" );
			// preparedStmt.setInt(5, 1);
			// preparedStmt.setInt(6, 1);
			// preparedStmt.setString(7, fecha);
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 5);
			// preparedStmt.setString (2, "Estadis_1");
			// preparedStmt.setString (3, "1234");
			// preparedStmt.setString (4,"3" );
			// preparedStmt.setInt(5, 3);
			// preparedStmt.setInt(6, 1);
			// preparedStmt.setString(7, fecha);
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			//
			//
			// //
			// --------------------------------------------------------------------------------------------------------
			// turno
			// // create table "turno "
			// statement.executeUpdate("DROP TABLE IF EXISTS turno");
			// statement.executeUpdate("CREATE TABLE turno("
			// + "id_turno  INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT," // 1
			// + "id_sector   int(1) NOT NULL," // 2
			// + "numero_turno   int(2) NOT NULL," // 3
			// + "id_cod_sector   varchar(1) NOT NULL," // 4
			// + "fecha_ticket  DATETIME  NOT NULL," // 5
			// + "fecha_atencion  DATETIME  NOT NULL," // 6
			// + "fecha_fin  DATETIME  NOT NULL," // 7
			// + "id_usuario  int(5) NOT NULL," // 8
			// + "id_puesto  int(2) NOT NULL," //
			// + "atendido    varchar(2) NOT NULL," // 13 NO / SI
			// + "llamado varchar(2) NOT NULL);"); // 14 NO / SI
			//
			//
			// // the mysql insert statement
			// query =
			// " insert into turno (id_turno ,id_sector, numero_turno ,id_cod_sector,fecha_ticket, fecha_atencion, fecha_fin, id_usuario, id_puesto,minutos_ticket,minutos_atencion,minutos_fin,atendido,llamado)"
			// + " values ( ?, ?, ?, ?,? ,?, ?, ?, ?,?,?,?,?,?)";
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 1); // id_turno
			// preparedStmt.setInt (2, 1); // id_sector
			// preparedStmt.setInt (3, 1 ); // numero
			// preparedStmt.setString(4, "A"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 1); // id_usuario
			// preparedStmt.setInt(9, 1); // id_puesto
			// preparedStmt.setLong(10, 960); // minutos_ticket
			// preparedStmt.setInt(11, 10); // minutos atencion
			// preparedStmt.setInt(12, 30); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 2); // id_turno
			// preparedStmt.setInt (2, 1); // id_sector
			// preparedStmt.setInt (3, 2 ); // numero
			// preparedStmt.setString(4, "A"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 965); // minutos_ticket
			// preparedStmt.setInt(11, 15); // minutos atencion
			// preparedStmt.setInt(12, 25); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 3); // id_turno
			// preparedStmt.setInt (2, 1); // id_sector
			// preparedStmt.setInt (3, 3); // numero
			// preparedStmt.setString(4, "A"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 965); // minutos_ticket
			// preparedStmt.setInt(11, 15); // minutos atencion
			// preparedStmt.setInt(12, 25); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 4); // id_turno
			// preparedStmt.setInt (2, 2); // id_sector
			// preparedStmt.setInt (3,1 ); // numero
			// preparedStmt.setString(4, "B"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 970); // minutos_ticket
			// preparedStmt.setInt(11, 5); // minutos atencion
			// preparedStmt.setInt(12, 20); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1,5 ); // id_turno
			// preparedStmt.setInt (2, 2); // id_sector
			// preparedStmt.setInt (3,2 ); // numero
			// preparedStmt.setString(4, "B"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 980); // minutos_ticket
			// preparedStmt.setInt(11, 10); // minutos atencion
			// preparedStmt.setInt(12, 10); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 6); // id_turno
			// preparedStmt.setInt (2, 1); // id_sector
			// preparedStmt.setInt (3, 4 ); // numero
			// preparedStmt.setString(4, "A"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 965); // minutos_ticket
			// preparedStmt.setInt(11, 15); // minutos atencion
			// preparedStmt.setInt(12, 25); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 7); // id_turno
			// preparedStmt.setInt (2, 1); // id_sector
			// preparedStmt.setInt (3, 5 ); // numero
			// preparedStmt.setString(4, "A"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 965); // minutos_ticket
			// preparedStmt.setInt(11, 15); // minutos atencion
			// preparedStmt.setInt(12, 25); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 8); // id_turno
			// preparedStmt.setInt (2, 1); // id_sector
			// preparedStmt.setInt (3, 6 ); // numero
			// preparedStmt.setString(4, "A"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 965); // minutos_ticket
			// preparedStmt.setInt(11, 15); // minutos atencion
			// preparedStmt.setInt(12, 25); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			// preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 9); // id_turno
			// preparedStmt.setInt (2, 1); // id_sector
			// preparedStmt.setInt (3, 7 ); // numero
			// preparedStmt.setString(4, "A"); // id_cod_sector
			// preparedStmt.setString(5, fecha); //fecha ticket
			// preparedStmt.setString(6, fecha); // fecha atencion
			// preparedStmt.setString(7, fecha); // fecha fin
			// preparedStmt.setInt(8, 2); // id_usuario
			// preparedStmt.setInt(9, 2); // id_puesto
			// preparedStmt.setLong(10, 965); // minutos_ticket
			// preparedStmt.setInt(11, 15); // minutos atencion
			// preparedStmt.setInt(12, 25); // minutos fin
			// preparedStmt.setString(13, "NO"); // atendido
			// preparedStmt.setString(14, "NO"); // llamado
			//
			// // execute the preparedstatement
			// preparedStmt.execute();
			//
			//
			//
			// //
			// ----------------------------------------------------------------------------------------------------------------
			//
			// //
			// ----------------------------------------------------------------------------------------------------------------
			//
			//
			//
			//
			// Execute the query, and get a Java ResultSet
			ResultSet rs = statement.executeQuery("SELECT * from sat");

			// Iterate through the Java ResultSet
			int id_sat;
			String name;
			while (rs.next()) {
				id_sat = rs.getInt("id_sat");
				String cod_sucursal = rs.getString("cod_sucursal");
				String nom_sucursal = rs.getString("nom_sucursal");
				String versionSAT = rs.getString("versionSAT");
				String fech_act = rs.getString("fech_act");
				// Print the results
				System.out.println("SAT -  Id: {" + id_sat + "}  sucursal: {"
						+ cod_sucursal + "} nombre sucursal: {" + nom_sucursal
						+ "} " + " versionSAT: {" + versionSAT
						+ "}  feccha actualizacion: {" + fech_act + "} \n");
			}
			;
			System.out.println("\n");

		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			System.out.println("Fecha: " + fecha.toString());
			e.getMessage();
			e.printStackTrace();
			System.out.println("Error de conexion 0" + e.getMessage());

		} finally {
			try {
				if (connection != null) {
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
					connection.close();
				}
			} catch (SQLException e) {
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.getMessage();
				e.printStackTrace();
				System.out.println("Error de conexion 1" + e.getMessage());
			}
		}
		return connection;
	}
	/**
	 * Crea un nuevo data source
	 * @return MysqlDataSource
	 */
	private MysqlDataSource newDs() {
		MysqlDataSource ds = new MysqlConnectionPoolDataSource();
		ds.setServerName(SERVER_NAME);
		ds.setPort(PORT_NUMBER);
		ds.setUser(USER_NAME);
		ds.setPassword(PASSWORD);
		LOGGER.info("Se ha creado un data source");
		return ds;
	}

	/**
	 * Crea una nueva conexión a la base de datos
	 * @return Connection
	 */
	public Connection newCon() {
		MysqlDataSource ds = this.newDs();
		Connection connection = null;
		try {
			connection = ds.getConnection();
			LOGGER.info("Se ha establecido la conexión a DB");
		} catch (SQLException e) {
			System.out.println("Error de conexion 2" + e.getMessage());
			e.printStackTrace();
			LOGGER.error("No se ha podido establecer la conexión a DB "
					+ e.getMessage());
		}
		return connection;
	}

}