package ar.com.osdepym.template.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Usuario;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class LoginDao {
	
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.ADMINISTRADOR+"-"+LoginDao.class);

	/**
	 * Valida usuario y password para el login
	 * @param nomUsuario
	 * @param password
	 * @return Usuario
	 */
	public Usuario login(String nomUsuario, String password) {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario("0");
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();

		String query = "SELECT * from SAT.usuario  WHERE nom_usuario = ? and  password like ? and habilitado like 'SI' ";
		try {
			LOGGER.debug(LoggerVariables.INICIANDO);
			if (connection != null) {
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nomUsuario);
			preparedStmt.setString(2, password);
			ResultSet resultSet = (ResultSet) preparedStmt.executeQuery();
			
			if(resultSet.next()){
				usuario.setIdUsuario(resultSet.getString("id_usuario"));
				usuario.setNombreUsuario(resultSet.getString("nom_usuario"));
				usuario.setPerfilId(resultSet.getInt("id_perfil"));
			}else{
				usuario.setError("Usuario o contraseña erróneos");
			}
			
			preparedStmt.close();
			} else {
				usuario.setError("No hay conexion con la Base de Datos");
			}
		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+ e.getMessage());

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
				LOGGER.error(LoggerVariables.ERROR);
			}
		}

		return usuario;

	}

}
