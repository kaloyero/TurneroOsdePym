package ar.com.osdepym.template.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sucursal;

public class SucursalDao {

	private static Logger LOGGER = Logger
			.getLogger(LoggerVariables.ADMINISTRADOR + "-" + SucursalDao.class);

	/**
	 * Metodo para listar sucursales
	 * @return ArrayList<Sucursal>
	 */
	public ArrayList<Sucursal> listaSucursal() {
		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<Sucursal> sucursales = new ArrayList<Sucursal>();
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);

		String query = "SELECT * from SAT.SAT";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				Sucursal sucursal = new Sucursal();
				sucursal.setDT_RowId(rs.getInt("id_sat"));
				sucursal.setCodSucursal(rs.getString("cod_sucursal"));
				sucursal.setNombreSucursal(rs.getString("nom_sucursal"));
				sucursal.setCodigoTotem(rs.getInt("Totem"));
				sucursal.setIp(rs.getString("ip"));

				sucursales.add(sucursal);
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
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.getMessage();
				e.printStackTrace();
			}
		}
		return sucursales;

	}

	/**
	 * Editar sucursal
	 * @param sucursalEditar
	 * @return Sucursal
	 */
	public Sucursal editarSucursal(Sucursal sucursalEditar) {
		Connection connection = new ConnectionMysql().createConnection();
		String query = "update SAT.SAT set cod_sucursal = ?, nom_sucursal = ?,Totem=?,ip=?,fech_act=?  where id_sat = ? ";
		LOGGER.debug(LoggerVariables.PREPARANDO_EDIT);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, sucursalEditar.getCodSucursal());
			preparedStmt.setString(2, sucursalEditar.getNombreSucursal());
			preparedStmt.setInt(3, sucursalEditar.getCodigoTotem());
			preparedStmt.setString(4, sucursalEditar.getIp());
			preparedStmt.setDate(5, new Date(System.currentTimeMillis()));
			preparedStmt.setInt(6, sucursalEditar.getDT_RowId());
			preparedStmt.executeUpdate();

			preparedStmt.close();

			return sucursalEditar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			String error = e.getMessage();
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			// sucursalEditar.setError("No se permiten puestos Duplicadas");
			return sucursalEditar;

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

}
