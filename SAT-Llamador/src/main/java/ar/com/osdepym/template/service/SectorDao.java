package ar.com.osdepym.template.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class SectorDao {
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + SectorDao.class);

	private Sector sector;
	/**
	 * Recupera sector por nombre
	 * @param nomSector
	 * @return Sector
	 */
	public Sector obtenerSectorPorNombre(String nomSector) {
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR + " sector por nombre "
				+ nomSector);
		sector = new Sector();
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		try {
			String query = "select * from SAT.sector where nom_sector like ? and habilitado like ? ";
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setString(1, nomSector);
			preparedStatement.setString(2, "SI");
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			while (resultSet.next()) {
				sector.setCodSector(resultSet.getString("cod_sector"));
				sector.setHabilitado(resultSet.getString("habilitado"));
				sector.setNomSector(resultSet.getString("nom_sector"));
				sector.setSector(resultSet.getString("id_sector"));
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

		return sector;

	}
	
	/**
	 * Recupera sector por código
	 * @param codSector
	 * @return Sector
	 */
	public Sector obtenerSectorPorCodigo(String codSector) {

		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR + " sector por codigo "
				+ codSector);

		sector = new Sector();
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		try {

			String query = "select * from SAT.sector where cod_sector like ?  ";
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setString(1, codSector);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			while (resultSet.next()) {
				sector.setCodSector(resultSet.getString("cod_sector"));
				sector.setHabilitado(resultSet.getString("habilitado"));
				sector.setNomSector(resultSet.getString("nom_sector"));
				sector.setSector(resultSet.getString("id_sector"));
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

		return sector;

	}
	
	/**
	 * Recupera todos los sectores
	 * @return List<Sector>
	 */
	public List<Sector> obtenerTodo() {

		List<Sector> sectores = new ArrayList<Sector>();
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR + " Todo");

		try {

			String query = "select * from SAT.sector ";
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			while (resultSet.next()) {
				sector = new Sector();
				sector.setCodSector(resultSet.getString("cod_sector"));
				sector.setHabilitado(resultSet.getString("habilitado"));
				sector.setNomSector(resultSet.getString("nom_sector"));
				sector.setSector(resultSet.getString("id_sector"));
				if (sector.getHabilitado().equals("SI")) {
					sectores.add(sector);
				}
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

		return sectores;
	}

}
