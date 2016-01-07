package ar.com.osdepym.template.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Puesto;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class PuestoDao {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + PuestoDao.class);
	/**
	 * Recupera el puesto por id
	 * @param idPuesto
	 * @return Puesto
	 */
	public Puesto obtenerPuestoPorId(int idPuesto) {
		LOGGER.debug(LoggerVariables.OBTENIENDO_PUESTO + " por id " + idPuesto);

		Puesto puesto = new Puesto();
		puesto.setIdPuesto("0");
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		try {
			String query = "select * from SAT.puesto where id_puesto = ? and habilitado like ? ";
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setInt(1, idPuesto);
			preparedStatement.setString(2, "SI");
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				puesto.setDireccionIp(resultSet.getString("ip"));
				puesto.setHabilitado(resultSet.getString("habilitado"));
				puesto.setIdPuesto(resultSet.getString("id_puesto"));
				puesto.setPuesto(resultSet.getString("nro_puesto"));
			}
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
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
			}
		}

		return puesto;

	}

	/**
	 * Recupera el puesto por IP
	 * @param ip
	 * @return Puesto
	 */
	public Puesto obtenerPuestoPorIp(String ip) {
		LOGGER.debug(LoggerVariables.OBTENIENDO_PUESTO + " por ip " + ip);

		Puesto puesto = new Puesto();
		puesto.setIdPuesto("0");
		puesto.setPuesto("0");
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		try {
			String query = "select * from SAT.puesto where ip like ? and habilitado like ? ";
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setString(1, ip);
			preparedStatement.setString(2, "SI");
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				puesto.setDireccionIp(resultSet.getString("ip"));
				puesto.setHabilitado(resultSet.getString("habilitado"));
				puesto.setIdPuesto(resultSet.getString("id_puesto"));
				puesto.setPuesto(resultSet.getString("nro_puesto"));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
				}
			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			}
		}
		return puesto;
	}

}
