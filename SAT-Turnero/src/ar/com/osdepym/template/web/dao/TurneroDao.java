package ar.com.osdepym.template.web.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class TurneroDao {
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.TURNERO
			+ "-" + TurneroDao.class);
	/**
	 * Obtiene la ip del turnero.
	 * @return String
	 */
	public String obtenerIp() {
		String ip = "";
		LOGGER.debug("Buscando IP");

		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		String query = "select * from sat.sat ";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			if (resultSet.next()) {
				ip = resultSet.getString("IP");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());

			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
					connection.close();
				}
			} catch (SQLException e) {
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.printStackTrace();
			}
		}
		return ip;
	}

	public String obtenerPassword() {

		String password = "";
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		String query = "select * from sat.sat ";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString("Totem");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());

			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
				}
			} catch (SQLException e) {
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.printStackTrace();
			}
		}
		return password;

	}

}
