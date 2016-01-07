package ar.com.osdepym.template.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;

import com.mysql.jdbc.Statement;

public class SectorDao {

	private static Logger LOGGER = Logger
			.getLogger(LoggerVariables.ADMINISTRADOR + "-" + SectorDao.class);
	/**
	 * Metodo listar sectores
	 * @return ArrayList<Sector>
	 */
	public ArrayList<Sector> listaSectores() {
		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<Sector> sectores = new ArrayList<Sector>();
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);

		String query = "SELECT * from SAT.sector";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				Sector sector = new Sector();
				sector.setDT_RowId(rs.getInt("id_sector"));
				// sector.setSector(rs.getInt("sector"));
				sector.setNombreSector(rs.getString("nom_sector"));
				sector.setCodigoSector(rs.getString("cod_sector"));
				sector.setHabilitado(rs.getString("habilitado"));
				sectores.add(sector);
			}

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
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			}
		}

		return sectores;
	}
	/**
	 * Modificar sector
	 * @param sectorEditar
	 * @return Sector
	 */
	public Sector editarSector(Sector sectorEditar) {
		Connection connection = new ConnectionMysql().createConnection();
		String query = "update SAT.sector set cod_sector = ?,  nom_sector = ?,  habilitado = ?  where id_sector = ? ";
		PreparedStatement preparedStmt;
		LOGGER.debug(LoggerVariables.PREPARANDO_EDIT);

		try {
			preparedStmt = connection.prepareStatement(query);
			// preparedStmt.setInt(1, sectorEditar.getSector());
			preparedStmt.setString(1, sectorEditar.getCodigoSector());
			preparedStmt.setString(2, sectorEditar.getNombreSector());
			preparedStmt.setString(3, sectorEditar.getHabilitado());
			preparedStmt.setInt(4, sectorEditar.getDT_RowId());
			preparedStmt.executeUpdate();
			return sectorEditar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			String error = e.getMessage();
			if (error.contains("'UK_cod_sector'")) {
				sectorEditar
						.setError("No se permiten Codigo de Sector duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sectorEditar.getError());

				return sectorEditar;
			}
			if (error.contains("'UK_nom_sector'")) {
				sectorEditar
						.setError("No se permiten Nombres de sector duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sectorEditar.getError());

				return sectorEditar;
			}
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
					connection.close();
				}
			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sectorEditar.getError());
			}
		}

		return null;
	}

	/**
	 * Crear sector
	 * @param sectorInsertar
	 * @return Sector
	 */
	public Sector insertarSector(Sector sectorInsertar) {
		Connection connection = new ConnectionMysql().createConnection();
		String query = "insert into sat.sector (cod_sector,nom_sector,habilitado) values(?,?,?) ";
		LOGGER.debug(LoggerVariables.PREPARANDO_INSERT);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			// preparedStmt.setInt(1, sectorInsertar.getSector());
			preparedStmt.setString(1, sectorInsertar.getCodigoSector());
			preparedStmt.setString(2, sectorInsertar.getNombreSector());
			preparedStmt.setString(3, sectorInsertar.getHabilitado());
			// preparedStmt.setInt(5, sectorEditar.getDT_RowId());
			preparedStmt.execute();
			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();

			while (generatedKeys.next()) {
				sectorInsertar.setDT_RowId(generatedKeys.getInt(1));
			}
			return sectorInsertar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			String error = e.getMessage();
			if (error.contains("'UK_cod_sector'")) {
				sectorInsertar
						.setError("No se permiten Codigo de Sector duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sectorInsertar.getError());

				return sectorInsertar;
			}
			if (error.contains("'UK_nom_sector'")) {
				sectorInsertar
						.setError("No se permiten Nombres de sector duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sectorInsertar.getError());

				return sectorInsertar;
			}
			e.printStackTrace();
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

		return null;
	}
	/**
	 * Eliminar Sector
	 * @param id
	 */
	public void eliminarSector(int id) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_DELETE);

		String query = "delete from sat.sector where id_sector = ? ";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.executeUpdate();
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
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			}
		}
	}
	
	public ArrayList<Sector> borrarSector() {
		return null;
	}
	 /**
	  * Metodo para obtener conexion a DB
	  * @return
	  */
	public Connection obtenerConexion() {
		return new ConnectionMysql().createConnection();
	}

}
