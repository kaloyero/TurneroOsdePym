package ar.com.osdepym.template.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.entity.UsuarioSector;

import com.mysql.jdbc.Statement;

public class UsuarioDao {
	private static Logger LOGGER = Logger
			.getLogger(LoggerVariables.ADMINISTRADOR + "-" + UsuarioDao.class);
	
	/**
	 * Metodo listar usuarios
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> listaUsuarios() {
		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);

		String query = "SELECT * FROM cesat.usuario INNER JOIN cesat.perfil ON cesat.usuario.id_perfil = cesat.perfil.id_perfil";

		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setDT_RowId(rs.getInt("id_usuario"));
				usuario.setNombreUsuario(rs.getString("nom_usuario"));
				usuario.setPassword(rs.getString("password"));
				// usuario.setSectorId(rs.getString("id_sector"));
				usuario.setNombrePerfil(rs.getString("nom_perfil"));
				// usuario.setNombrePuesto(rs.getString("puesto"));
				usuario.setPerfilId(rs.getInt("id_perfil"));
				// usuario.setPuestoId(rs.getInt("id_puesto"));
				usuario.setHabilitado(rs.getString("habilitado"));

				usuarios.add(usuario);
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

		return usuarios;
	}

	/**
	 * Metodo para editar usuario
	 * @param usuarioEditar
	 * @return Usuario
	 */
	public Usuario editarUsuario(Usuario usuarioEditar) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_EDIT);

		String queryCantidadAdministradores = "Select * from cesat.usuario inner join cesat.perfil on cesat.usuario.id_perfil =cesat.perfil.id_perfil where cesat.perfil.nom_perfil ='Admin'and cesat.usuario.Habilitado ='SI' and cesat.usuario.id_usuario <> "
				+ usuarioEditar.getDT_RowId();
		String query = "update cesat.usuario set nom_usuario = ?, password = ?,  id_perfil = ?,  habilitado = ?  where id_usuario = ? ";
		// String
		// queryPuesto="Select * from SAT.puesto where id_puesto ="+usuarioEditar.getPuestoId();
		String queryPerfil = "Select * from cesat.perfil where id_perfil ="
				+ usuarioEditar.getPerfilId();
		PreparedStatement preparedStmt;
		// PreparedStatement preparedStmtPuesto ;
		PreparedStatement preparedStmtPerfil;
		PreparedStatement preparedStmtCheckAdmin;

		// Chequeo que si el estado es Habilitado NO ,no sea el unico
		// administrador que habia
		if (usuarioEditar.getHabilitado().equalsIgnoreCase("NO")) {
			try {
				LOGGER.debug(LoggerVariables.VERIFICANDO_ADMIN);

				preparedStmtCheckAdmin = connection
						.prepareStatement(queryCantidadAdministradores);
				ResultSet rsAdmin = preparedStmtCheckAdmin.executeQuery();
				if (!rsAdmin.next()) {

					usuarioEditar
							.setError("No puede borrar al unico Administrador");
					LOGGER.info(usuarioEditar.getError());

					return usuarioEditar;
				}
				preparedStmtCheckAdmin.close();
			} catch (SQLException e) {
				LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
				}
			}
		}
		try {
			LOGGER.debug(LoggerVariables.VERIFICANDO_HABILITADO);
			connection = new ConnectionMysql().createConnection();
			preparedStmt = connection.prepareStatement(query);
			// If usuarioEditar.getHabilitado() == NO and Usuairo Admin{
			// Verificar si existe alguno Habilitado siendo Admin
			// Si existe,permitir el update
			preparedStmt.setString(1, usuarioEditar.getNombreUsuario());
			preparedStmt.setString(2, usuarioEditar.getPassword());
			preparedStmt.setInt(3, usuarioEditar.getPerfilId());
			// preparedStmt.setInt(4, usuarioEditar.getPuestoId());
			preparedStmt.setString(4, usuarioEditar.getHabilitado());
			preparedStmt.setInt(5, usuarioEditar.getDT_RowId());
			preparedStmt.executeUpdate();

			// preparedStmtPuesto = connection.prepareStatement(queryPuesto);
			preparedStmtPerfil = connection.prepareStatement(queryPerfil);

			// ResultSet rsPuesto = preparedStmtPuesto.executeQuery();
			ResultSet rsPerfil = preparedStmtPerfil.executeQuery();

			while (rsPerfil.next()) {
				usuarioEditar.setNombrePerfil(rsPerfil.getString("nom_perfil"));
			}
			// while (rsPuesto.next()) {
			// usuarioEditar.setNombrePuesto(rsPuesto.getString("puesto"));
			// }
			preparedStmt.close();
			preparedStmtPerfil.close();

			return usuarioEditar;
			// Recupero la fila cambiada

		} catch (SQLException e) {
			String error = e.getMessage();
			if (error.contains("'UK_nom_usuario'")) {
				usuarioEditar
						.setError("No se permiten nombre de Usuarios duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ usuarioEditar.getError());

				return usuarioEditar;
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
	 * Metodo para crear un nuevo usuario
	 * @param usuarioInsertar
	 * @return Usuario
	 */
	public Usuario insertarUsuario(Usuario usuarioInsertar) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_INSERT);

		String query = "insert into cesat.usuario (nom_usuario,password,id_perfil,fecha,habilitado) values(?,?,?,DATE( NOW() ),?) ";
		// String
		// queryPuesto="Select * from SAT.puesto where id_puesto ="+usuarioInsertar.getPuestoId();
		String queryPerfil = "Select * from cesat.perfil where id_perfil ="
				+ usuarioInsertar.getPerfilId();
		PreparedStatement preparedStmt;
		PreparedStatement preparedStmtPerfil;
		try {
			preparedStmt = connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStmt.setString(1, usuarioInsertar.getNombreUsuario());
			preparedStmt.setString(2, usuarioInsertar.getPassword());
			preparedStmt.setInt(3, usuarioInsertar.getPerfilId());
			// preparedStmt.setInt(4, usuarioInsertar.getPuestoId());
			preparedStmt.setString(4, usuarioInsertar.getHabilitado());
			preparedStmt.execute();
			// preparedStmtPuesto = connection.prepareStatement(queryPuesto);
			preparedStmtPerfil = connection.prepareStatement(queryPerfil);

			// ResultSet rsPuesto = preparedStmtPuesto.executeQuery();
			ResultSet rsPerfil = preparedStmtPerfil.executeQuery();

			while (rsPerfil.next()) {
				usuarioInsertar.setNombrePerfil(rsPerfil
						.getString("nom_perfil"));
			}
			// while (rsPuesto.next()) {
			// usuarioInsertar.setNombrePuesto(rsPuesto.getString("puesto"));
			// }
			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();

			while (generatedKeys.next()) {
				usuarioInsertar.setDT_RowId(generatedKeys.getInt(1));
			}
			preparedStmt.close();
			preparedStmtPerfil.close();
			return usuarioInsertar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String error = e.getMessage();
			if (error.contains("'UK_nom_usuario'")) {
				usuarioInsertar
						.setError("No se permiten nombre de Usuarios duplicados");
				LOGGER.error(LoggerVariables.ERROR + "-"
						+ usuarioInsertar.getError());

				return usuarioInsertar;
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
	 * Metodo para eliminar el usuario
	 * @param id
	 */
	public void eliminarUsuario(int id) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_DELETE);

		String query = "delete from cesat.usuario where id_usuario= ? ";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.executeUpdate();
			LOGGER.debug(LoggerVariables.ELIMINADO + id);

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
	/**
	 * Obtener lista de usuariosSectores
	 * @return ArrayList<UsuarioSector>
	 */
	public ArrayList<UsuarioSector> listaUsuarioSectores() {
		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<UsuarioSector> usuariosSector = new ArrayList<UsuarioSector>();
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);

		String query = "SELECT * FROM SAT.usuario_sector INNER JOIN SAT.usuario ON SAT.usuario.id_usuario = SAT.usuario_sector.id_usuario INNER JOIN SAT.sector ON SAT.usuario_sector.id_sector = SAT.sector.id_sector";

		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				UsuarioSector usuariosector = new UsuarioSector();
				usuariosector.setDT_RowId(rs.getInt("id"));
				usuariosector.setNombreUsuario(rs.getString("nom_usuario"));
				usuariosector.setUsuarioId(rs.getInt("id_usuario"));
				usuariosector.setSectorId(rs.getInt("id_sector"));
				usuariosector.setSectorNombre(rs.getString("nom_sector"));
				usuariosector.setCodigoSector(rs.getString("cod_sector"));

				usuariosSector.add(usuariosector);
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

		return usuariosSector;
	}
	/**
	 * Eliminar usuario sector
	 * @param id
	 */
	public void eliminarUsuarioSector(int id) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_DELETE);

		String query = "delete from sat.usuario_sector where id=?";
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
				LOGGER.error(LoggerVariables.ERROR);
			}
		}

	}
	
	/**
	 * etodo para asignar un sector a un usuarios. Crea un nuevo registro en usuario sector
	 * @param usuarioSectorInsertar
	 * @return UsuarioSector
	 */
	public UsuarioSector insertarUsuarioSector(
			UsuarioSector usuarioSectorInsertar) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_INSERT);

		String query = "insert into sat.usuario_sector (id_usuario,id_sector) values(?,?) ";
		String querySector = "Select * from SAT.sector where id_sector ="
				+ usuarioSectorInsertar.getSectorId();
		String queryUsuario = "Select * from SAT.usuario where id_usuario ="
				+ usuarioSectorInsertar.getUsuarioId();
		PreparedStatement preparedStmt;
		PreparedStatement preparedStmtSector;
		PreparedStatement preparedStmtUsuario;

		try {
			preparedStmt = connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);

			preparedStmt.setInt(1, usuarioSectorInsertar.getUsuarioId());
			preparedStmt.setInt(2, usuarioSectorInsertar.getSectorId());

			preparedStmt.execute();
			preparedStmtSector = connection.prepareStatement(querySector);
			preparedStmtUsuario = connection.prepareStatement(queryUsuario);

			ResultSet rsSector = preparedStmtSector.executeQuery();
			ResultSet rsUsuario = preparedStmtUsuario.executeQuery();

			while (rsSector.next()) {

				usuarioSectorInsertar.setCodigoSector(rsSector
						.getString("cod_sector"));
				usuarioSectorInsertar.setSectorNombre(rsSector
						.getString("nom_sector"));
			}
			while (rsUsuario.next()) {
				usuarioSectorInsertar.setNombreUsuario(rsUsuario
						.getString("nom_usuario"));
			}
			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();

			while (generatedKeys.next()) {
				usuarioSectorInsertar.setDT_RowId(generatedKeys.getInt(1));
			}

			preparedStmt.close();
			preparedStmtSector.close();
			preparedStmtUsuario.close();

			return usuarioSectorInsertar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());
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
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());
			}
		}

		return null;
	}

	public UsuarioSector editarUsuarioSector(UsuarioSector usuarioSectorEditar) {
		Connection connection = new ConnectionMysql().createConnection();
		LOGGER.debug(LoggerVariables.PREPARANDO_EDIT);

		String query = "update SAT.usuario_sector set id_usuario = ?,   id_sector = ?  where id = ? ";
		String querySector = "Select * from SAT.sector where id_sector ="
				+ usuarioSectorEditar.getSectorId();
		String queryUsuario = "Select * from SAT.usuario where id_usuario ="
				+ usuarioSectorEditar.getUsuarioId();
		PreparedStatement preparedStmt;
		PreparedStatement preparedStmtSector;
		PreparedStatement preparedStmtUsuario;

		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setInt(1, usuarioSectorEditar.getUsuarioId());
			preparedStmt.setInt(2, usuarioSectorEditar.getSectorId());
			preparedStmt.setInt(3, usuarioSectorEditar.getDT_RowId());

			preparedStmt.executeUpdate();

			preparedStmtSector = connection.prepareStatement(querySector);
			preparedStmtUsuario = connection.prepareStatement(queryUsuario);

			ResultSet rsSector = preparedStmtSector.executeQuery();
			ResultSet rsUsuario = preparedStmtUsuario.executeQuery();

			while (rsSector.next()) {
				usuarioSectorEditar.setCodigoSector(rsSector
						.getString("cod_sector"));
				usuarioSectorEditar.setSectorNombre(rsSector
						.getString("nom_sector"));
			}
			while (rsUsuario.next()) {
				usuarioSectorEditar.setNombreUsuario(rsUsuario
						.getString("nom_usuario"));
			}

			preparedStmt.close();
			preparedStmtSector.close();
			preparedStmtUsuario.close();

			return usuarioSectorEditar;
			// Recupero la fila cambiada
		} catch (SQLException e) {
			LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());
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
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());
			}
		}

		return null;
	}

}
