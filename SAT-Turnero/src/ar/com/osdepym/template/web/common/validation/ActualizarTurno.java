package ar.com.osdepym.template.web.common.validation;

//Turnero
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.RolSectores;
import ar.com.osdepym.template.entity.Ticket;


public class ActualizarTurno {

	Connection connection;
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.TURNERO+"-"+ActualizarTurno.class);

	/**
	 * Crea el turno en la base de datos.
	 * @param sector
	 * @param rolSector
	 * @param letraSector
	 * @return Ticket
	 */
	public synchronized Ticket execute(int sector, RolSectores rolSector,
			String letraSector) {
		LOGGER.debug("Creando nuevo turno");

		connection = new ConnectionMysql().createConnection();
		int rol = 0; // 1- admin 2 - Usuario 3 - estadisticas
		Date fechaDate = new Date();
		Ticket ticket = new Ticket();
		int numero = 0;

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String fecha = sdf.format(fechaDate);
		System.out.println("Fecha " + fecha);
		String error = "";
		if (connection == null) {
			ticket.setError("Error de Base de Datos");
			LOGGER.error(LoggerVariables.ERROR+"-"+ticket.getError());

			return ticket;
		}
		try {

			// String query =
			// "SELECT * from SAT.turno  WHERE id_sector = ? AND numero_turno des ";
			String query = "SELECT * from SAT.turno  WHERE id_sector = ? and DATE(fecha_ticket) = DATE( NOW() ) order by numero_turno asc ";

			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, sector);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				numero = rs.getInt("numero_turno");
				numero++;
				// break;
			}
			if (numero == 0) {
				numero++;
			}
			// int id_usuario = new
			// Integer(rolSector.getId_usuario()).intValue();
			// int puesto = new Integer(rolSector.getPuesto()).intValue();
			int nro_sector = new Integer(rolSector.getNro_sectores().get(0))
					.intValue();
			String cod_sector = rolSector.getNro_sectores().get(0);
			LOGGER.debug("Insertando nuevo registro en la base");

			query = " insert into SAT.turno (id_sector, numero_turno ,id_cod_sector,fecha_ticket, fecha_atencion, fecha_fin, id_usuario, id_puesto,atendido,llamado)"
					+ " values (  ?, ?, ?,? ,?, ?, ?, ?,?,?)";

			preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt (1, 1); // id_turno
			preparedStmt.setObject(1, rolSector.getNro_sectores().get(0)); // id_sector
			preparedStmt.setInt(2, numero); // numero
			preparedStmt.setString(3, rolSector.getCod_sectores().get(0)); // id_cod_sector
			preparedStmt.setString(4, fecha); // fecha ticket
			preparedStmt.setString(5, "0000-00-00"); // fecha atencion
			preparedStmt.setString(6, "0000-00-00"); // fecha fin
			preparedStmt.setObject(7, 0); // id_usuario
			preparedStmt.setObject(8, 0); // id_puesto
			preparedStmt.setString(9, "NO"); // atendido
			preparedStmt.setString(10, "NO"); // llamado

			// execute the preparedstatement
			preparedStmt.execute();

			// actualiza el llamado
			// query =
			// "update SAT.turno set numer_turno = ?, id_usuario = ?,  llamado = ?,  id_puesto = ?  where id_turno = ? ";

			// PreparedStatement preparedStmt2 =
			// connection.prepareStatement(query);
			// preparedStmt2.setString (1, fecha);
			// preparedStmt2.setString (2, ""+id_usuario);
			// preparedStmt2.setString (3, "SI"); //
			// preparedStmt2.setInt (4, 34); // puesto
			// preparedStmt2.setInt (5, id_turno);
			// preparedStmt2.executeUpdate();

			// PreparedStatement preparedStmt2 =
			// connection.prepareStatement(query);
			// preparedStmt2.setInt (1, numero);
			// preparedStmt2.setString (2, fecha);
			// preparedStmt2.setInt (3, sector);
			// preparedStmt2.executeUpdate();
			java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat(
					"dd-MM-yyyy HH:mm");

			System.out.println("numero nuevo sector \n" + sector + " numero "
					+ numero);
			ticket.setFechaEmision(sdf2.format(fechaDate));
			ticket.setNumeroMostrar(letraSector + numero);
			ticket.setTurnosDelante(this.getTurnosDelante(sector));

		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

			e.getMessage();
			e.printStackTrace();
			error = e.getMessage();
			System.out.println("Error de conexion 0" + e.getMessage());
			int xdebug = 999;
			ticket.setError("Error de Base de Datos");
			return ticket;
		} catch (Exception e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

			e.getMessage();
			e.printStackTrace();
			error = e.getMessage();
			System.out.println("Error de conexion 0" + e.getMessage());
			int xdebug = 999;
			ticket.setError("Error de Base de Datos");

			return ticket;
		} finally {
			try {
				if (connection != null){
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
					connection.close();}
			} catch (SQLException e) {
				e.getMessage();
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());
				e.printStackTrace();
				error = e.getMessage();
				System.out.println("Error de conexion 1" + e.getMessage());
				ticket.setError("Error de Base de Datos");

				return ticket;
			}

		}
		
		return ticket;
	}
	
	/**
	 * Obtiene los turnos por delante que tiene el actual numero
	 * @param sector
	 * @return int
	 * @throws SQLException
	 */
	public int getTurnosDelante(int sector) throws SQLException {
		int turnosDelante=0;
		LOGGER.debug("Calculando turnos por delante");

		String query = "SELECT count(*) from SAT.turno  WHERE id_sector = ? and atendido='NO'  and llamado like 'NO'  and  DATE(fecha_ticket) = DATE( NOW() ) ";

		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, sector);

		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()){
			 turnosDelante =  rs.getInt(1);
		}
		//Le resto mi turno que ya se ingreso
		return turnosDelante - 1;
	}

}