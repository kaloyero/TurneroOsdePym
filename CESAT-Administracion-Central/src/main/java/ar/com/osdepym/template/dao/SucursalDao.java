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

		//String query = "SELECT * from cesat.sat_sucursales";
		String query ="SELECT CAST(fech_act AS CHAR) as fech_act,CAST(fech_ult_conex AS CHAR) as fech_ult_conex,id_sat,cod_sucursal,nom_sucursal,habilitado,ip from cesat.sat_sucursales";
		PreparedStatement preparedStmt; 
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				Sucursal sucursal = new Sucursal();
				sucursal.setDT_RowId(rs.getInt("id_sat"));
				sucursal.setCodSucursal(rs.getString("cod_sucursal"));
				sucursal.setNombreSucursal(rs.getString("nom_sucursal"));
				//sucursal.setCodigoTotem(rs.getInt("Totem"));
				sucursal.setIp(rs.getString("ip"));
				sucursal.setHabilitado(rs.getString("habilitado"));
				//rs.getObject("fech_act");
				//rs.get("fech_act");
				//sucursal.setFechaActualizacion(rs.getDate("fech_act").toString());
				sucursal.setFechaActualizacion(rs.getString("fech_act"));
				sucursal.setFechaConexion(rs.getString("fech_ult_conex"));

				//sucursal.setFechaActualizacion(rs.getDate("fech_act").toString());
				//sucursal.setFechaConexion(rs.getDate("fech_ult_conex").toString());

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
		String query = "update cesat.sat_sucursales set cod_sucursal = ?, nom_sucursal = ?,ip=?,fech_act=? ,habilitado=? where id_sat = ? ";
		LOGGER.debug(LoggerVariables.PREPARANDO_EDIT);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, sucursalEditar.getCodSucursal());
			preparedStmt.setString(2, sucursalEditar.getNombreSucursal());

			preparedStmt.setString(3, sucursalEditar.getIp());
			preparedStmt.setDate(4, new Date(System.currentTimeMillis()));
			preparedStmt.setString(5, sucursalEditar.getHabilitado());

			preparedStmt.setInt(6, sucursalEditar.getDT_RowId());
			

			preparedStmt.executeUpdate();

			preparedStmt.close();
			return sucursalEditar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			String error = e.getMessage();
			if (error.contains("'UK_Sucursal'")) {
				sucursalEditar
						.setError("No se permiten nombre de Sucursal duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sucursalEditar.getError());

				return sucursalEditar;
			}
			if (error.contains("'UK_Ip'")) {
				sucursalEditar
						.setError("No se permiten Ips Duplicadas");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sucursalEditar.getError());

				return sucursalEditar;
			}
			if (error.contains("'UK_cod'")) {
				sucursalEditar
						.setError("No se permiten Codigos Duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sucursalEditar.getError());

				return sucursalEditar;
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
	public Sucursal insertarSucursal(Sucursal sucursalInsertar) {
		Connection connection = new ConnectionMysql().createConnection();
		//String query = "update SAT.SAT set cod_sucursal = ?, nom_sucursal = ?,Totem=?,ip=?,fech_act=?  where id_sat = ? ";
		String query = "insert into cesat.sat_sucursales ( cod_sucursal,nom_sucursal,ip,fech_act,habilitado) values(?,?,?,?,?) ";
		LOGGER.debug(LoggerVariables.PREPARANDO_EDIT);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, sucursalInsertar.getCodSucursal());
			preparedStmt.setString(2, sucursalInsertar.getNombreSucursal());
			preparedStmt.setString(3, sucursalInsertar.getIp());

			preparedStmt.setDate(4, new Date(System.currentTimeMillis()));
			preparedStmt.setString(5, sucursalInsertar.getHabilitado());

			preparedStmt.executeUpdate();
			


			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();

			while (generatedKeys.next()) {
				sucursalInsertar.setDT_RowId(generatedKeys.getInt(1));
			}
			
			preparedStmt.close();

			return sucursalInsertar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			String error = e.getMessage();
			if (error.contains("'UK_Sucursal'")) {
				sucursalInsertar
						.setError("No se permiten nombre de Sucursal duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sucursalInsertar.getError());

				return sucursalInsertar;
			}
			if (error.contains("'UK_Ip'")) {
				sucursalInsertar
						.setError("No se permiten Ips Duplicadas");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sucursalInsertar.getError());

				return sucursalInsertar;
			}
			if (error.contains("'UK_cod'")) {
				sucursalInsertar
						.setError("No se permiten Codigos Duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ sucursalInsertar.getError());

				return sucursalInsertar;
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

}
