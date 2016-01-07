package ar.com.osdepym.template.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Usuario;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class EstadisticasDao {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + EstadisticasDao.class);

	UsuarioDao usuarioDao = new UsuarioDao();
	SectorDao sectorDao = new SectorDao();
	/**
	 * Genera reporte de los turnos no llamados
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List<TurnoDto>
	 */
	public List<TurnoDto> turnosNoLlamados(Date fechaDesde, Date fechaHasta) {
		LOGGER.debug(LoggerVariables.CALCULANDO_ESTADISTICAS
				+ ": Clientes que no fueron llamados en rango de fecha: "
				+ fechaDesde + " - " + fechaHasta);

		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		TurnoDto turnoDto;
		List<TurnoDto> turnos = new ArrayList<TurnoDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.sql.Date fechaD = new java.sql.Date(fechaDesde.getTime());
		java.sql.Date fechaH = new java.sql.Date(fechaHasta.getTime());
		String query = " select  time(t.fecha_ticket) as horaTurno, time(t.fecha_atencion) as horaLlamado, time(t.fecha_fin) as horaFin, t.id_turno, t.id_sector, t.numero_turno, t.id_cod_sector, t.fecha_ticket, t.fecha_atencion, t.fecha_fin, t.id_usuario, t.id_puesto, t.atendido, t.llamado from sat.turno t  where llamado like 'NO' and DATE(fecha_ticket) between DATE( ? ) and DATE( ? )   order  by   t.fecha_ticket,  t.id_cod_sector,   t.numero_turno   ";

		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setDate(1, fechaD);
			preparedStatement.setDate(2, fechaH);

			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {

				Usuario usuario = usuarioDao.obtenerUsuarioPorId(resultSet
						.getInt("id_usuario"));
				Sector sector = sectorDao.obtenerSectorPorCodigo(resultSet
						.getString("id_cod_sector"));

				turnoDto = new TurnoDto();
				turnoDto.setNumero_turno(resultSet.getString("numero_turno"));
				turnoDto.setId_cod_sector(sector.getCodSector());
				turnoDto.setNomSector(sector.getNomSector());
				turnoDto.setFecha_ticket(sdf.format(resultSet
						.getDate("fecha_ticket")));
				turnoDto.setNomUsuario(usuario.getNomUsuario());
				turnoDto.setAtendido(resultSet.getString("atendido"));
				turnoDto.setLlamado(resultSet.getString("llamado"));
				turnoDto.setHoraTurno(resultSet.getString("horaTurno"));
				turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				turnoDto.setHoraFin(resultSet.getString("horaFin"));
				turnos.add(turnoDto);
			}
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

		return turnos;
	}

	/**
	 * Genera reporte de turnos atendidos por usuario
	 * @param id_usuario
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List<TurnoDto>
	 */
	public List<TurnoDto> clientesAtendidosPuesto(int id_usuario,
			Date fechaDesde, Date fechaHasta) {

		LOGGER.debug(LoggerVariables.CALCULANDO_ESTADISTICAS
				+ ": Clientes Atendidos por el usuario " + id_usuario);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		String query = " SELECT time(fecha_ticket) as horaTurno, time(fecha_atencion) as horaLlamado, time(fecha_fin) as horaFin,  id_turno, id_sector, numero_turno, id_cod_sector, fecha_ticket, fecha_atencion, fecha_fin, id_usuario, id_puesto,  atendido, llamado from sat.turno where id_usuario = ? and llamado like 'SI' and date(fecha_ticket) between date( ? ) and ( ? )  order  by   fecha_ticket,  id_cod_sector,   numero_turno    ";
		TurnoDto turnoDto;
		List<TurnoDto> turnos = new ArrayList<TurnoDto>();
		ResultSet resultSet;
		java.sql.Date fechaD = new java.sql.Date(fechaDesde.getTime());
		java.sql.Date fechaH = new java.sql.Date(fechaHasta.getTime());

		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setInt(1, id_usuario);
			preparedStatement.setDate(2, fechaD);
			preparedStatement.setDate(3, fechaH);

			resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {

				Usuario usuario = usuarioDao.obtenerUsuarioPorId(resultSet
						.getInt("id_usuario"));
				Sector sector = sectorDao.obtenerSectorPorCodigo(resultSet
						.getString("id_cod_sector"));

				turnoDto = new TurnoDto();
				turnoDto.setNumero_turno(resultSet.getString("numero_turno"));
				turnoDto.setId_cod_sector(sector.getCodSector());
				turnoDto.setNomSector(sector.getNomSector());
				turnoDto.setFecha_ticket(sdf.format(resultSet
						.getDate("fecha_ticket")));
				turnoDto.setNomUsuario(usuario.getNomUsuario());
				turnoDto.setAtendido(resultSet.getString("atendido"));
				turnoDto.setLlamado(resultSet.getString("llamado"));
				turnoDto.setHoraTurno(resultSet.getString("horaTurno"));
				turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				turnoDto.setHoraFin(resultSet.getString("horaFin"));

				turnos.add(turnoDto);
			}
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
		return turnos;
	}
	/**
	 * Genera reporte de turnos atendidos en la sucursal
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List<TurnoDto>
	 */
	public List<TurnoDto> clientesAtendidosPorSucursal(Date fechaDesde,
			Date fechaHasta) {
		LOGGER.debug(LoggerVariables.CALCULANDO_ESTADISTICAS
				+ ": Clientes atendidos por los sectores de esta sucursal ");
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		TurnoDto turnoDto;
		List<TurnoDto> turnos = new ArrayList<TurnoDto>();
		String query = " select  time(t.fecha_ticket) as horaTurno, time(t.fecha_atencion) as horaLlamado, time(t.fecha_fin) as horaFin, t.id_turno, t.id_sector, t.numero_turno, t.id_cod_sector, t.fecha_ticket, t.fecha_atencion, t.fecha_fin, t.id_usuario, t.id_puesto, t.atendido, t.llamado  from sat.turno t, sat.sector s where llamado like 'SI' and  DATE(fecha_ticket) between DATE( ? ) and DATE( ? )  and t.id_sector=s.id_sector and s.habilitado like 'SI'   order  by   t.fecha_ticket,  t.id_cod_sector,   t.numero_turno    ";

		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			//preparedStatement.setDate(1,new java.sql.Date(fechaDesde.getTime()));
			//preparedStatement.setDate(2,new java.sql.Date(fechaHasta.getTime()));

			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {

				Usuario usuario = usuarioDao.obtenerUsuarioPorId(resultSet
						.getInt("id_usuario"));
				Sector sector = sectorDao.obtenerSectorPorCodigo(resultSet
						.getString("id_cod_sector"));

				turnoDto = new TurnoDto();
				turnoDto.setNumero_turno(resultSet.getString("numero_turno"));
				turnoDto.setId_cod_sector(sector.getCodSector());
				turnoDto.setNomSector(sector.getNomSector());
				turnoDto.setFecha_ticket(sdf.format(resultSet
						.getDate("fecha_ticket")));
				turnoDto.setNomUsuario(usuario.getNomUsuario());
				turnoDto.setAtendido(resultSet.getString("atendido"));
				turnoDto.setLlamado(resultSet.getString("llamado"));
				turnoDto.setHoraTurno(resultSet.getString("horaTurno"));
				turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				turnoDto.setHoraFin(resultSet.getString("horaFin"));
				turnos.add(turnoDto);
			}

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

		return turnos;
	}
	/**
	 * Recupera turnos en espera del sector del momento
	 * @param idSector
	 * @return String[]
	 */
	public String[] generarEstadisticas(int idSector) {
		LOGGER.debug(LoggerVariables.PREPARANDO_PANEL);
		String[] datos = new String[4];
		datos[0] = "0";
		datos[1] = "00:00";
		datos[2] = "00:00";
		datos[3] = "0";
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		String query = " select count(*) as clientesEspera from sat.turno where id_sector = ? and llamado like 'NO' and date(fecha_ticket) = date (now()) ";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setInt(1, idSector);
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				datos[0] = resultSet.getString("clientesEspera");
			}

			query = "select max(TIMEDIFF(now(),fecha_ticket)) as tiempo from sat.turno where  id_sector = ? and date(fecha_ticket) = date (now()) and llamado like 'NO' ";
			preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setInt(1, idSector);
			resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				String tiempo = resultSet.getString("tiempo");
				if (tiempo != null && !tiempo.equals("")) {

					datos[1] = tiempo.substring(0, 5);
				}
			}

			query = " select avg(TIMEDIFF(fecha_fin,fecha_atencion)) as segundos from sat.turno where  id_sector = ? and date(fecha_ticket) = date (now()) and atendido like 'SI' ";

			preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setInt(1, idSector);
			resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				Double minu = resultSet.getDouble("segundos") / 60;
				Integer min = minu.intValue();
				Double hore;
				Integer hora = 0;
				if (min > 60) {
					hore = (double) (min / 60);
					hora = hore.intValue();
					min = min - 60;
				}
				String horas;
				if(hora < 10){
					horas = "0"+hora.toString();
				}else{
					horas = hora.toString();
				}
				String minus;
				if (min < 10) {
					minus = "0" + min.toString();
				} else {
					minus = min.toString();
				}
				datos[2] = horas + ":" + minus;
			}

		} catch (SQLException e) {
			e.getMessage();
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());

		} catch (NullPointerException e2) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e2.getMessage());
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

		return datos;
	}

}
