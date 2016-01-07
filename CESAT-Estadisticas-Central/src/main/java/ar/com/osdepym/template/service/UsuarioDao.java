package ar.com.osdepym.template.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Usuario;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class UsuarioDao {


	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + UsuarioDao.class);
	

	
	/**
	 * Recupera usuario por nombre
	 * @param nomUsuario
	 * @return Usuario
	 */
	public Usuario obtenerUsuario(String nomUsuario) {
		
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR+"-Usuario por nombre");
		
		Connection connection = new ConnectionMysql().createConnection();
		Usuario usuario = new Usuario();
		String query = "select * from cesat.usuario where nom_usuario = ?  ";
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
		String query = "select * from cesat.usuario where id_usuario = ?  ";
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
		String query = " update cesat.usuario set password = ? where id_usuario = ?  ";

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
