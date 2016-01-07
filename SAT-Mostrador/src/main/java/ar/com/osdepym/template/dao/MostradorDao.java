package ar.com.osdepym.template.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Turno;

public class MostradorDao {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.MOSTRADOR
			+ "-" + MostradorDao.class);
	/**
	 * Metodo para obtener los últimos cinco números llamados.
	 * 
	 * @return ArrayList<Turno>
	 */
	public ArrayList<Turno> getUltimosTurnos() {
		LOGGER.debug(LoggerVariables.PREPARANDO_BUSCAR);
		Turno turno = new Turno();
		Connection connection = new ConnectionMysql().createConnection();
		ArrayList<Turno> turnos = new ArrayList<Turno>();

		String query = "select * from SAt.turno  inner join SAT.sector on SAt.turno.id_sector =SAt.sector.id_sector inner join SAT.puesto on SAt.puesto.id_puesto =SAt.turno.id_puesto where llamado ='SI' and  DATE(fecha_atencion) = DATE( NOW()) order by fecha_atencion desc limit 5";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				turno = new Turno();
				turno.setNumeroTurno(rs.getString("cod_sector")
						+ rs.getInt("numero_turno"));
				turno.setSectorAtencion(rs.getString("nro_puesto"));

				turnos.add(turno);
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

		return turnos;

	}

}
