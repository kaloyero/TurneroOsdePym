package ar.com.osdepym.template.common.validation;

// Llamador

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.service.UsuarioDao;

public class ValidacionUsuario {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + ValidacionUsuario.class);

	private UsuarioDao usuarioDao = new UsuarioDao();
	/**
	 * Valida usuario y contraseña
	 * @param user
	 * @param passw
	 * @param usuario
	 * @return Usuario
	 */
	public Usuario execute(String user, String passw, Usuario usuario) {
		LOGGER.debug(LoggerVariables.VALIDAR_USUARIO);
		Connection connection = new ConnectionMysql().createConnection();
		Connection connection2 = new ConnectionMysql().createConnection();
		if (connection == null) {
			return new Usuario();
		}

		int perfil = 0; // 1- admin 2 - Usuario 3 - estadisticas
		int rol = 0; // 1- admin 2 - Usuario 3 - estadisticas
		Date fechaDate = new Date();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String fecha = sdf.format(fechaDate);
		System.out.println("Fecha " + fecha);

		usuario = new Usuario();
		usuario.setCod_sectores(new ArrayList<String>());
		usuario.setNom_sectores(new ArrayList<String>());
		usuario.setNro_sectores(new ArrayList<String>());

		try {
			LOGGER.debug(LoggerVariables.VERIFICAR_USER_PASS);
			String query = "SELECT * from SAT.usuario  WHERE nom_usuario = ? and  password like ? and habilitado like 'SI' ";

			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, user);
			preparedStmt.setString(2, passw);
			// preparedStmt.setInt(3, 2);

			ResultSet rs = preparedStmt.executeQuery();

			boolean existe = false;
			boolean existe2 = true;
			int id_sector;
			while (rs.next()) {
				existe = true;
				perfil = rs.getInt("id_perfil");
				usuario.setNomUsuario(rs.getString("nom_usuario"));
				usuario.setPerfil(rs.getString("id_perfil"));
				usuario.setId_usuario(rs.getInt("id_usuario"));
				// ---------------------------------------------------------------------
				List<Sector> sectores = new ArrayList<Sector>();
				sectores = usuarioDao.recuperarSectoresUsuario(usuario
						.getId_usuario());

				if (sectores.isEmpty()) {
					usuario.setLetra_sectores(new ArrayList<String>());
					usuario.setCod_sectores(new ArrayList<String>());
					usuario.setNom_sectores(new ArrayList<String>());
					existe2 = false;
				} else {
					usuario.setSectores(sectores);
				}

				if (!existe2) {
					// codigo 101 - No existe el Sector no esta habilitado
					usuario.setCod_error(101);
					usuario.setMensaje_error("El usuario no existe o no tiene sectores asociados");
					LOGGER.debug(usuario.getMensaje_error());
					return usuario;
				}
				// ---------------------------------------------------------------------
				usuario.setId_usuario(rs.getInt("id_usuario"));
			}

			if (!existe) {
				System.out.println("No existe el usuario " + user
						+ " o no esta habilitado (SI) ");
				// codigo 102 - No existe el usuario no esta habilitado
				usuario.setCod_error(102);
				usuario.setMensaje_error("No existe el usuario o datos erroneos");
				LOGGER.debug(usuario.getMensaje_error());
				return usuario;
			}

			System.out.println(usuario.toString() + "\n");

		} catch (Exception e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			e.getMessage();
			e.printStackTrace();
			System.out.println("Error SQL" + e.getMessage());

		} finally {
			try {
				if (connection != null) {
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
					connection.close();
				}
			} catch (SQLException e) {
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.getMessage();
				e.printStackTrace();
				System.out.println("Error de conexion 1" + e.getMessage());
			}
			try {
				if (connection2 != null) {
					connection2.close();
					LOGGER.info(LoggerVariables.CONEXION_CERRADA);
				}
			} catch (SQLException e) {
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.getMessage();
				e.printStackTrace();
				System.out.println("Error de conexion 0" + e.getMessage());
			}
		}
		return usuario;
	}
}
