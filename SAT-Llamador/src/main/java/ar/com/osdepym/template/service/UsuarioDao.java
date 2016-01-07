package ar.com.osdepym.template.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Usuario;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class UsuarioDao {

	private SectorDao sectorDao = new SectorDao();

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + UsuarioDao.class);
	
	/**
	 * Recupera los sectores del usuario
	 * @param usuario
	 * @return List<Sector>
	 */
	public List<Sector> recuperarSectoresUsuario(int usuario) {

		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR+"- Sectores del usuario");

		Connection connection = new ConnectionMysql().createConnection();
		List<Sector> listaSectores = new ArrayList<Sector>();
		try {

			String query = "select s.nom_sector as nomSector from sat.sector as s, sat.usuario_sector as us where us.id_usuario = ? and s.id_sector = us.id_sector and s.habilitado like 'SI'  ";
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);

			preparedStatement.setInt(1, usuario);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			while (resultSet.next()) {
				String nomSect = resultSet.getString("nomSector");
				Sector sector = new Sector();
				sector = sectorDao.obtenerSectorPorNombre(nomSect);

				listaSectores.add(sector);
			}
			preparedStatement.close();

			return listaSectores;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			return listaSectores;
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
	
	/**
	 * Recupera usuario por nombre
	 * @param nomUsuario
	 * @return Usuario
	 */
	public Usuario obtenerUsuario(String nomUsuario) {
		
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR+"-Usuario por nombre");
		
		Connection connection = new ConnectionMysql().createConnection();
		Usuario usuario = new Usuario();
		String query = "select * from SAT.usuario where nom_usuario = ?  ";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setString(1, nomUsuario);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			if (resultSet.next()) {
				usuario.setId_usuario(resultSet.getInt("id_usuario"));
				usuario.setNomUsuario(resultSet.getString("nom_usuario"));
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
		return usuario;
	}
	
	/**
	 * Recupera usuario por Id
	 * @param idUsuario
	 * @return Usuario
	 */
	public Usuario obtenerUsuarioPorId(int idUsuario) {
		
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR+" -Usuario por id");
		
		Connection connection = new ConnectionMysql().createConnection();
		Usuario usuario = new Usuario();
		String query = "select * from SAT.usuario where id_usuario = ?  ";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setInt(1, idUsuario);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			if (resultSet.next()) {
				usuario.setId_usuario(resultSet.getInt("id_usuario"));
				usuario.setNomUsuario(resultSet.getString("nom_usuario"));
				usuario.setPerfil(resultSet.getString("id_perfil"));
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

		return usuario;
	}

	/**
	 * Actualiza contraseña del usuario
	 * @param idUsuario
	 * @param contrasenaNueva
	 */
	public void cambiarContraseña(int idUsuario, String contrasenaNueva) {
		
		LOGGER.debug(LoggerVariables.PREPARANDO_CAMBIAR_PASS);
		
		Connection connection = new ConnectionMysql().createConnection();
		String query = " update SAT.usuario set password = ? where id_usuario = ?  ";

		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setString(1, contrasenaNueva);
			preparedStatement.setInt(2, idUsuario);

			preparedStatement.executeUpdate();

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
				e.getMessage();
				e.printStackTrace();
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			}
		}
	}
}
