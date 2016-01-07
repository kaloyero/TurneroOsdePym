package ar.com.osdepym.template.common.validation;

// Llamador

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Usuario;

public class LlamarTurno {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + LlamarTurno.class);

	/**
	 * Metodo para setear Atendido.
	 * 
	 * @param id_turno
	 * @param atendido
	 * @return RolSectores
	 */
	public synchronized Usuario execute(int id_turno, boolean atendido) {
		LOGGER.debug(LoggerVariables.PREPARANDO_ATENDIDO);
		Usuario rolSector = new Usuario();

		Connection connection = new ConnectionMysql().createConnection();
		int rol = 0; // 1- admin 2 - Usuario 3 - estadisticas
		Date fechaDate = new Date();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String fecha = sdf.format(fechaDate);
		System.out.println("Fecha " + fecha);
		String minTicket;

		int registroUpdate = 0;
		try {
			String q = "select * from SAT.turno where id_turno = ? ";
			PreparedStatement preparedStmt = connection.prepareStatement(q);
			preparedStmt.setInt(1, id_turno);
			ResultSet resultSet = preparedStmt.executeQuery();
			int usuario;
			if (resultSet.next()) {
				sdf = new java.text.SimpleDateFormat("mm");
				minTicket = sdf
						.format(resultSet.getTimestamp("fecha_atencion"));
				usuario = resultSet.getInt("id_usuario");
				q = "select * from SAT.usuario where id_usuario = ? ";
				preparedStmt = connection.prepareStatement(q);
				preparedStmt.setInt(1, usuario);
				ResultSet rs = preparedStmt.executeQuery();
				if (rs.next()) {
					rolSector.setNomUsuario(rs.getString("nom_usuario"));
				}
			}

			if (atendido) {
				sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// actualiza el llamado
				String query = "update SAT.turno set fecha_fin = ?,   atendido = ?   where id_turno = ? ";
				preparedStmt = connection.prepareStatement(query);

				preparedStmt.setString(1, fecha);
				preparedStmt.setString(2, "SI"); //
				preparedStmt.setInt(3, id_turno);
				registroUpdate = preparedStmt.executeUpdate();

				// actualiza la tabla de turnos
				System.out.println("Actualizo la tabla de turnos id "
						+ id_turno + " atendido ");
				LOGGER.debug("Turno actualizado - ATENDIDO");

			} else {

				// actualiza el llamado
				String query = "update SAT.turno set fecha_fin = ?,   atendido = ?   where id_turno = ? ";

				preparedStmt = connection.prepareStatement(query);
				preparedStmt.setString(1, fecha);
				preparedStmt.setString(2, "NO"); //
				preparedStmt.setInt(3, id_turno);
				registroUpdate = preparedStmt.executeUpdate();

				// actualiza la tabla de turnos
				System.out.println("Actualizo la tabla de turnos id "
						+ id_turno + " NO atendido ");
				LOGGER.debug("Turno actualizado - NO ATENDIDO");

			}
			preparedStmt.close();
			if (registroUpdate == 0) {
				System.out.println("Error actualizando el registro de turnos  "
						+ id_turno);
				rolSector.setCod_error(204);
				rolSector.setMensaje_error("Error SQL en el UPDATE ");
				LOGGER.debug(rolSector.getMensaje_error());
				return rolSector;
			}

		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			e.getMessage();
			e.printStackTrace();

			System.out.println("Error de conexion " + e.getMessage());
			rolSector.setCod_error(205);
			rolSector.setMensaje_error("Error SQL ");
			return rolSector;
		} finally {
			try {
				if (connection != null) {
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
					connection.close();
				}
			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				System.out.println("Error de conexion 1" + e.getMessage());
				rolSector.setCod_error(206);
				rolSector.setMensaje_error("Error cerrando la conexion");
				return rolSector;
			}

		}
		return rolSector;
	}

	// Proximo turno

	/**
	 * Metodo para llamar al siguiente turno
	 * 
	 * @param puesto
	 * @param sector
	 * @param id_usuario
	 * @param rolSector
	 * @return RolSectores
	 */
	public synchronized Usuario execute(int puesto, int sector,
			int id_usuario, Usuario rolSector) {

		LOGGER.debug(LoggerVariables.PREPARANDO_LLAMAR);

		Connection connection = new ConnectionMysql().createConnection();
		int rol = 0; // 1- admin 2 - Usuario 3 - estadisticas

		Date fechaDate = new Date();
		rolSector.setLetra_sectores(new ArrayList<String>());
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String fecha = sdf.format(fechaDate);
		System.out.println("Fecha " + fecha);

		String error = "";
		try {

			String query = "select * from SAT.turno where llamado like ? and id_sector = ? and DATE(fecha_ticket)=DATE(NOW()) order by numero_turno and fecha_ticket ";

			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, "NO");
			preparedStmt.setInt(2, sector);

			ResultSet rs = preparedStmt.executeQuery();

			int numero = 0;
			int id_turno = 0;
			String cod_sector = "";
			while (rs.next()) {
				cod_sector = rs.getString("id_cod_sector");
				numero = rs.getInt("numero_turno");
				id_turno = rs.getInt("id_turno");
				rolSector.setNumero("" + numero);
				rolSector.setId_turno("" + id_turno);
				rolSector.getLetra_sectores().add(cod_sector);

				break;
			}
			if (id_turno == 0) {
				rolSector.setCod_error(105);
				rolSector.setMensaje_error("No existen turnos a llamar");
				LOGGER.debug(rolSector.getMensaje_error());
				return rolSector;
			}
			// actualiza el llamado
			query = "update SAT.turno set fecha_atencion = ?, id_usuario = ?,  llamado = ?,  id_puesto = ?  where id_turno = ? ";

			PreparedStatement preparedStmt2 = connection
					.prepareStatement(query);
			preparedStmt2.setString(1, fecha);
			preparedStmt2.setInt(2, id_usuario);
			preparedStmt2.setString(3, "SI"); //
			preparedStmt2.setInt(4, puesto); // puesto
			preparedStmt2.setInt(5, id_turno);
			preparedStmt2.executeUpdate();

			preparedStmt.close();
			preparedStmt2.close();

			LOGGER.debug("Se ha actualizado la tabla de Turnos");

			// actualiza la tabla de turnos
			System.out.println("Actualizo la tabla de turnos id " + id_turno);

		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			e.getMessage();
			e.printStackTrace();
			error = e.getMessage();
			System.out.println("Error de conexion 0" + e.getMessage());
			int xdebug = 999;
			rolSector.setCod_error(201);
			rolSector.setMensaje_error("Error SQL ");
			return rolSector;
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
				error = e.getMessage();
				System.out.println("Error de conexion 1" + e.getMessage());
				rolSector.setCod_error(202);
				rolSector.setMensaje_error("Error cerrando la conexion");
				return rolSector;
			}

		}
		return rolSector;
	}

}