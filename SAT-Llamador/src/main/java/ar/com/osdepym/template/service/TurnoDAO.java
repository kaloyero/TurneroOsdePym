package ar.com.osdepym.template.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Turno;
import ar.com.osdepym.template.entity.Usuario;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class TurnoDAO {
	UsuarioDao usuarioDao = new UsuarioDao();
	SectorDao sectorDao = new SectorDao();
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + TurnoDAO.class);
	/**
	 * Recupera cantidad de turnos en espera
	 * @param sector
	 * @return Integer
	 */
	public Integer numerosEnEspera(int sector) {
		
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR+" turnos en espera");

		int nrosEspera = 0;
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		try {

			String query = "select count(*) as cuenta from sat.turno where id_sector = ? and llamado like ? and DATE(fecha_ticket)=DATE(now())";

			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);

			preparedStatement.setInt(1, sector);

			preparedStatement.setString(2, "NO");

			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {
				nrosEspera = resultSet.getInt("cuenta");
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
		return nrosEspera;
	}
	
	/**
	 * Recupera turno por id
	 * @param idTurno
	 * @return Turno
	 */
	public Turno obtenerTurnoPorId(int idTurno) {
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR+" turno por id "+idTurno);
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		Turno turno = new Turno();
		try {
			String query = "select * from sat.turno where id_turno = ? ";

			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);

			preparedStatement.setInt(1, idTurno);

			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {

				Usuario usuario = usuarioDao.obtenerUsuarioPorId(resultSet
						.getInt("id_usuario"));
				Sector sector = sectorDao.obtenerSectorPorCodigo(resultSet
						.getString("id_cod_sector"));

				turno.setId_turno(resultSet.getInt("id_turno"));
				turno.setId_sector(resultSet.getInt("id_sector"));
				turno.setNumero_turno(resultSet.getInt("numero_turno"));
				turno.setId_cod_sector(sector.getCodSector());
				turno.setNomSector(sector.getNomSector());
				turno.setFecha_ticket(resultSet.getDate("fecha_ticket"));
				turno.setId_usuario(new Integer(usuario.getId_usuario()));
				turno.setNomUsuario(usuario.getNomUsuario());
				turno.setId_puesto(resultSet.getInt("id_puesto"));
				turno.setAtendido(resultSet.getString("atendido"));
				turno.setLlamado(resultSet.getString("llamado"));

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

		return turno;
	}
}
