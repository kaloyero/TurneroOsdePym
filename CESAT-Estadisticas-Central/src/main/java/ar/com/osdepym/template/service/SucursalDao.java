package ar.com.osdepym.template.service;

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
			.getLogger("SucursalDao-" + SucursalDao.class);

	/**
	 * Metodo para listar sucursales
	 * @return ArrayList<Sucursal>
	 */
	public ArrayList<Sucursal> listaSucursal() {
		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<Sucursal> sucursales = new ArrayList<Sucursal>();
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);

		String query = "SELECT * from cesat.sat_sucursales order by nom_sucursal";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				Sucursal sucursal = new Sucursal();
				sucursal.setId(rs.getInt("id_sat"));
				sucursal.setValue(rs.getString("nom_sucursal"));
				
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

	public String getUltimaComprobacionById(int idSucursal) {
		// TODO Auto-generated method stub
		Connection connection = new ConnectionMysql().createConnection();
		String ultimaActualizacion="";
		String query = "SELECT  CAST(fech_ult_conex AS CHAR) as fecha from cesat.sat_sucursales where id_sat= " +idSucursal;
		System.out.println(query);
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				ultimaActualizacion=rs.getString("fecha");		
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
		return ultimaActualizacion;
	}

	public String getHorarioUltimaActualizacion() {
		//Connection connection = new ConnectionMysql().createConnection();
		
		Connection connection = new ConnectionMysql().createConnection();
		String ultimaActualizacion="";
		String query = "select max(fech_ult_conex) as fecha from CESAT.sat_sucursales";
		System.out.println(query);
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				ultimaActualizacion=rs.getString("fecha");		
				
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
		return ultimaActualizacion;
	}

	public String getNombreSucursal(String sucursalSeleccionada) {
		int sucursal=Integer.parseInt(sucursalSeleccionada);
		Connection connection = new ConnectionMysql().createConnection();
		String nombreSucursal="";
		String query = "select nom_sucursal from CESAT.sat_sucursales where id_sat ="+sucursal;
		System.out.println(query);
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				nombreSucursal=rs.getString("nom_sucursal");		
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
		return nombreSucursal;
	}


}
