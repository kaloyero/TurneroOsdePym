package ar.com.osdepym.template.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Puesto;

import com.mysql.jdbc.Statement;

public class PuestoDao {

	private static Logger LOGGER = Logger
			.getLogger(LoggerVariables.ADMINISTRADOR + "-" + PuestoDao.class);
	
	/**
	 * Listar Puestos
	 * @return ArrayList<Puesto>
	 */
	public ArrayList<Puesto> listaPuestos() {
		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<Puesto> puestos = new ArrayList<Puesto>();
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);

		String query = "SELECT * from SAT.puesto";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				Puesto puesto = new Puesto();
				puesto.setDT_RowId(rs.getInt("id_puesto"));
				puesto.setPuesto(rs.getInt("nro_puesto"));
				puesto.setIp(rs.getString("ip"));
				puesto.setHabilitado(rs.getString("habilitado"));
				puestos.add(puesto);
			}
			preparedStmt.close();

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
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			}
		}

		return puestos;
	}
	/**
	 * Editar puesto
	 * @param puestoEditar
	 * @return Puesto
	 */
	public Puesto editarPuesto(Puesto puestoEditar) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_EDIT);

		String query = "update SAT.puesto set nro_puesto = ?, ip = ?,habilitado=?  where id_puesto = ? ";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, puestoEditar.getPuesto());
			preparedStmt.setString(2, puestoEditar.getIp());
			preparedStmt.setString(3, puestoEditar.getHabilitado());
			preparedStmt.setInt(4, puestoEditar.getDT_RowId());
			preparedStmt.executeUpdate();

			preparedStmt.close();

			return puestoEditar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			String error = e.getMessage();
			if (error.contains("'UK_ip'")) {
				puestoEditar.setError("No se permiten Ips Duplicadas");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ puestoEditar.getError());

				return puestoEditar;
			}
			if (error.contains("'UK_nro_puesto'")) {
				puestoEditar.setError("No se permiten puestos Duplicadas");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ puestoEditar.getError());
				return puestoEditar;
			}

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

		return null;
	}
	/**
	 * Crear puesto
	 * @param puestoInsertar
	 * @return Puesto
	 */
	public Puesto insertarPuesto(Puesto puestoInsertar) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_INSERT);

		String query = "insert into sat.puesto (nro_puesto,ip,habilitado) values(?,?,?) ";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStmt.setInt(1, puestoInsertar.getPuesto());
			preparedStmt.setString(2, puestoInsertar.getIp());
			preparedStmt.setString(3, puestoInsertar.getHabilitado());
			preparedStmt.execute();
			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();

			while (generatedKeys.next()) {
				puestoInsertar.setDT_RowId(generatedKeys.getInt(1));
			}
			preparedStmt.close();

			return puestoInsertar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			String error = e.getMessage();
			if (error.contains("'UK_ip'")) {
				puestoInsertar.setError("No se permiten Ips Duplicadas");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ puestoInsertar.getError());

				return puestoInsertar;
			}
			if (error.contains("'UK_nro_puesto'")) {
				puestoInsertar.setError("No se permiten puestos Duplicadas");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ puestoInsertar.getError());
				return puestoInsertar;
			}

			e.printStackTrace();
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

		return null;
	}
	/**
	 * Eliminar Puesto
	 * @param id
	 */
	public void eliminarPuesto(int id) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_DELETE);

		String query = "delete from sat.puesto where id_puesto = ? ";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.executeUpdate();

			preparedStmt.close();

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
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			}
		}
	}
	/**
	 * Metodo paa obtener una conexion a DB
	 * @return Connection
	 */
	public Connection obtenerConexion() {
		return new ConnectionMysql().createConnection();
	}

}
