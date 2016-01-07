package ar.com.osdepym.template.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.ConnectionMysql;
import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Usuario;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class EstadisticasDao {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + EstadisticasDao.class);

	UsuarioDao usuarioDao = new UsuarioDao();

	/**
	 * Genera reporte de los turnos no llamados
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List<TurnoDto>
	 */
	public List<TurnoDto> turnosNoLlamados(Date fechaDesde, Date fechaHasta,
			String sucursal) {
		LOGGER.debug(LoggerVariables.CALCULANDO_ESTADISTICAS
				+ ": Clientes que no fueron llamados en rango de fecha: "
				+ fechaDesde + " - " + fechaHasta);

		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		TurnoDto turnoDto;
		List<TurnoDto> turnos = new ArrayList<TurnoDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		java.sql.Date fechaD = new java.sql.Date(fechaDesde.getTime());
		java.sql.Date fechaH = new java.sql.Date(fechaHasta.getTime());
		String query = "select  CAST(fecha_atencion as CHAR) as atencionCasteada ,CAST(fecha_ticket as CHAR) as ticketCasteada,CAST(fecha_fin as CHAR) as finCasteada,time(fecha_ticket) as horaTurno, time(fecha_atencion) as horaLlamado, time(fecha_fin) as horaFin,fecha_ticket,fecha_atencion,fecha_fin id_turno, id_cod_sector, numero_turno, nom_sector, nom_usuario, atendido, llamado  from cesat.turnos_cesat  where llamado like 'NO' and  DATE(fecha_ticket) between DATE( ? ) and DATE( ? ) and id_sucursal =? order  by   fecha_ticket,  id_cod_sector,   numero_turno ; ";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setDate(1, fechaD);
			preparedStatement.setDate(2, fechaH);
			preparedStatement.setInt(3, Integer.parseInt(sucursal));

			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {

				// Usuario usuario = usuarioDao.obtenerUsuarioPorId(resultSet
				// .getInt("id_usuario"));
				// Sector sector = sectorDao.obtenerSectorPorCodigo(resultSet
				// .getString("id_cod_sector"));

				turnoDto = new TurnoDto();

				turnoDto.setNumero_turno(resultSet.getString("numero_turno"));
				turnoDto.setId_cod_sector(resultSet.getString("id_cod_sector"));
				turnoDto.setNomSector(resultSet.getString("nom_sector"));
				//turnoDto.setFecha_ticket(sdf.format(resultSet.getDate("fecha_ticket")));
				turnoDto.setNomUsuario(resultSet.getString("nom_usuario"));
				turnoDto.setAtendido(resultSet.getString("atendido"));
				turnoDto.setLlamado(resultSet.getString("llamado"));
				
				if (resultSet.getString("atencionCasteada")==null){
					turnoDto.setHoraAtencion("-");
				}else{
					turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				}
				if (resultSet.getString("ticketCasteada")==null){
					turnoDto.setFecha_ticket("-");
					turnoDto.setHoraTurno("-");
				}else{
					turnoDto.setFecha_ticket(sdf.format(resultSet.getDate("fecha_ticket")));
					turnoDto.setHoraTurno(resultSet.getString("horaTurno"));

					//turnoDto.setHoraTurno(sdf.format(resultSet.getDate("horaTurno")));
				}
				if (resultSet.getString("finCasteada")==null){
					turnoDto.setHoraFin("-");

				}else{
					//turnoDto.setHoraFin(sdf.format(resultSet.getDate("horaFin")));
					turnoDto.setHoraFin(resultSet.getString("horaFin"));

					

				}
				//if (resultSet.getString("horaTurno").equalsIgnoreCase("0000-00-00 ")){
				//turnoDto.setHoraTurno(resultSet.getString("horaTurno"));
				//turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				//turnoDto.setHoraFin(resultSet.getString("horaFin"));

				//}else{
				//	turnoDto.setHoraTurno(resultSet.getDate("horaTurnoDate").toString());

				//}
				//turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				//turnoDto.setHoraFin(resultSet.getString("horaFin"));
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
	 * 
	 * @param id_usuario
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List<TurnoDto>
	 */
	public List<TurnoDto> clientesAtendidosPuesto(int id_usuario,
			Date fechaDesde, Date fechaHasta, String sucursal) {

		LOGGER.debug(LoggerVariables.CALCULANDO_ESTADISTICAS
				+ ": Clientes Atendidos por el usuario " + id_usuario);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		String query = "SELECT CAST (fecha_atencion as CHAR) as atencionCasteada ,CAST (fecha_ticket as CHAR) as ticketCasteada,CAST (fecha_fin as CHAR) as finCasteada,(fecha_ticket) as horaTurno, time(fecha_atencion) as horaLlamado, time(fecha_fin) as horaFin, id_turno, id_cod_sector, numero_turno, nom_sector, nom_usuario, atendido, llamado  from cesat.turnos_cesat  where   llamado like 'SI'  and date(fecha_ticket) between date( ? ) and ( ? ) and id_sucursal=?  order  by   fecha_ticket,  id_cod_sector,   numero_turno ;";
		TurnoDto turnoDto;
		List<TurnoDto> turnos = new ArrayList<TurnoDto>();
		ResultSet resultSet;
		java.sql.Date fechaD = new java.sql.Date(fechaDesde.getTime());
		java.sql.Date fechaH = new java.sql.Date(fechaHasta.getTime());

		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setDate(1, fechaD);
			preparedStatement.setDate(2, fechaH);
			preparedStatement.setInt(3, Integer.parseInt(sucursal));

			resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {

				// Usuario usuario =
				// usuarioDao.obtenerUsuarioPorId(resultSet.getInt("id_usuario"));
				// Sector sector =
				// sectorDao.obtenerSectorPorCodigo(resultSet.getString("id_cod_sector"));
				turnoDto = new TurnoDto();

				turnoDto.setNumero_turno(resultSet.getString("numero_turno"));
				turnoDto.setId_cod_sector(resultSet.getString("id_cod_sector"));
				turnoDto.setNomSector(resultSet.getString("nom_sector"));
				if (resultSet.getString("atencionCasteada").equalsIgnoreCase("")){
					turnoDto.setHoraAtencion("-");
				}else{
					turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				}
				if (resultSet.getString("ticketCasteada").equalsIgnoreCase("")){
					turnoDto.setHoraAtencion("-");
				}else{
					turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				}
				//turnoDto.setFecha_ticket(resultSet.getString("horaTurno"));
				turnoDto.setNomUsuario(resultSet.getString("nom_usuario"));
				turnoDto.setAtendido(resultSet.getString("atendido"));
				turnoDto.setLlamado(resultSet.getString("llamado"));
				turnoDto.setHoraTurno(resultSet.getString("horaTurno"));
				;
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
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List<TurnoDto>
	 */
	public List<TurnoDto> clientesAtendidosPorSucursal(Date fechaDesde,
			Date fechaHasta, String sucursal) {
		LOGGER.debug(LoggerVariables.CALCULANDO_ESTADISTICAS
				+ ": Clientes atendidos por los sectores de esta sucursal ");
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		TurnoDto turnoDto;
		List<TurnoDto> turnos = new ArrayList<TurnoDto>();
		String query = "select   CAST(fecha_atencion as CHAR) as atencionCasteada ,CAST(fecha_ticket as CHAR) as ticketCasteada,CAST(fecha_fin as CHAR) as finCasteada,time(fecha_ticket) as horaTurno, time(fecha_atencion) as horaLlamado, time(fecha_fin) as horaFin, fecha_ticket,id_turno, id_cod_sector, numero_turno, nom_sector, nom_usuario, atendido, llamado  from cesat.turnos_cesat  where llamado like 'SI' and  date(fecha_ticket) between date( ? ) and ( ? ) and id_sucursal=?     order  by   fecha_ticket,  id_cod_sector,   numero_turno ; ";

		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setDate(1,
					new java.sql.Date(fechaDesde.getTime()));
			preparedStatement.setDate(2,
					new java.sql.Date(fechaHasta.getTime()));
			preparedStatement.setInt(3, Integer.parseInt(sucursal));

			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
			while (resultSet.next()) {

				// Usuario usuario =
				// usuarioDao.obtenerUsuarioPorId(resultSet.getInt("id_usuario"));
				// Sector sector =
				// sectorDao.obtenerSectorPorCodigo(resultSet.getString("id_cod_sector"));

				turnoDto = new TurnoDto();
				turnoDto.setNumero_turno(resultSet.getString("numero_turno"));
				turnoDto.setId_cod_sector(resultSet.getString("id_cod_sector"));
				turnoDto.setNomSector(resultSet.getString("nom_sector"));
				///

				turnoDto.setNomUsuario(resultSet.getString("nom_usuario"));
				turnoDto.setAtendido(resultSet.getString("atendido"));
				turnoDto.setLlamado(resultSet.getString("llamado"));
				//turnoDto.setHoraTurno(sdf.format(resultSet.getDate("horaTurno")));
				//turnoDto.setHoraAtencion(sdf.format(resultSet.getDate("horaLlamado")));
				//turnoDto.setHoraFin(sdf.format(resultSet.getDate("horaFin")));
				if (resultSet.getString("atencionCasteada")==null){
					turnoDto.setHoraAtencion("-");
				}else{
					turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
				}
				if (resultSet.getString("ticketCasteada")==null){
					turnoDto.setFecha_ticket("-");
					turnoDto.setHoraTurno("-");
				}else{
					turnoDto.setFecha_ticket(sdf.format(resultSet.getDate("fecha_ticket")));
					turnoDto.setHoraTurno(resultSet.getString("horaTurno"));

					//turnoDto.setHoraTurno(sdf.format(resultSet.getDate("horaTurno")));
				}
				if (resultSet.getString("finCasteada")==null){
					turnoDto.setHoraFin("-");

				}else{
					//turnoDto.setHoraFin(sdf.format(resultSet.getDate("horaFin")));
					turnoDto.setHoraFin(resultSet.getString("horaFin"));

					

				}
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
	 * 
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
				if (hora < 10) {
					horas = "0" + hora.toString();
				} else {
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

	public List<TurnoDto> resumenSucursales(Date fechaDesde, Date fechaHasta) {
		LOGGER.debug(LoggerVariables.PREPARANDO_PANEL);
		List<TurnoDto> turnos = new ArrayList<TurnoDto>();
		
		
		Connection connection = (Connection) new ConnectionMysql()
				.createConnection();
		try {
			

			String query = "select cod_sucursal,id_sucursal,nombre_sucursal, id_cod_sector ,nom_sector,AVG(ifnull(timestampdiff( MINUTE,fecha_atencion,fecha_fin),0)) as promedioAtencion,nom_sector,round(AVG(ifnull(timestampdiff( MINUTE,fecha_atencion,fecha_fin),0)),2) as promedioAtencionRound,Max(ifnull(timestampdiff( MINUTE,fecha_atencion, fecha_fin),0)) as maximaAtencion,AVG(ifnull(timestampdiff( MINUTE,fecha_ticket,fecha_atencion),0)) "
					+ "as promedioEspera,Round(AVG(ifnull(timestampdiff( MINUTE,fecha_ticket,fecha_atencion),0)),0) as promedioEsperaRound,"
					+ "Max(ifnull(timestampdiff( MINUTE,fecha_ticket,fecha_atencion),0)) as maximaEspera,"
					+ "COUNT(DISTINCT nom_usuario) as cantidadUsuarios,COUNT(*) as cantidadTurnos,"
					+ "sum(IF(atendido='NO', 1, 0)) as cantidaCancelados ,sum(IF(atendido='SI', 1, 0)) as cantidaAtendidos,sum(IF(llamado='SI', 1, 0)) as cantidallam "
					+ "from cesat.turnos_cesat where  date(fecha_ticket) between date( ? ) and ( ? )"
					+ "group by id_sucursal,  nombre_sucursal,id_cod_sector,nom_sector";
			  
			PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			preparedStatement.setDate(1,
					new java.sql.Date(fechaDesde.getTime()));
			preparedStatement.setDate(2,
					new java.sql.Date(fechaHasta.getTime()));
			ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();

			while (resultSet.next()) {
				TurnoDto turnoDto = new TurnoDto();

		
				turnoDto.setIdSucursal((resultSet.getInt("id_sucursal")));
				turnoDto.setCodSucursal((resultSet.getString("cod_sucursal")));
				turnoDto.setNombreSucursal((resultSet.getString("nombre_sucursal")));
				turnoDto.setId_cod_sector((resultSet.getString("id_cod_sector")));
				turnoDto.setNomSector((resultSet.getString("nom_sector")));
				turnoDto.setCantidadTurnos((resultSet.getInt("cantidadTurnos")));
				turnoDto.setCantidadLlamados((resultSet.getInt("cantidallam")));

				turnoDto.setCantidadAtendidos(resultSet.getInt("cantidaAtendidos"));


				turnoDto.setTiempPromedioAtencion(resultSet.getInt("promedioAtencionRound"));
				turnoDto.setTiempPromedioAtencionTexto(getTiempo(resultSet.getInt("promedioAtencionRound")));

				turnoDto.setTiempMaximoAtencion(resultSet.getInt("maximaAtencion"));
				turnoDto.setTiempMaximoAtencionTexto(getTiempo(resultSet.getInt("maximaAtencion")));
				
				turnoDto.setTiempPromedioEspera(resultSet.getInt("promedioEsperaRound"));
				turnoDto.setTiempPromedioEsperaTexto(getTiempo(resultSet.getInt("promedioEsperaRound")));

				turnoDto.setTiempMaximoEspera(resultSet.getInt("maximaEspera"));
				turnoDto.setTiempMaximoEsperaTexto(getTiempo(resultSet.getInt("maximaEspera")));

				turnoDto.setCantidadDistintosUsuarios(resultSet.getInt("cantidadUsuarios"));
				turnoDto.setCantidadCancelados(resultSet.getInt("cantidaCancelados"));
				turnoDto.setNombreSucursal((resultSet.getString("nombre_sucursal")));

				
				turnos.add(turnoDto);

			
			
			
			
			/*
			
			 query = "select Max(DATEDIFF( fecha_atencion,fecha_ticket)) as data from cesat.turnos_cesat where id_sucursal="+codigoSucursal;

			 preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			 resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				Integer tiempMaximoEspera = resultSet.getInt("data");
				turnoDto.setTiempMaximoEspera(tiempMaximoEspera);
				
			}
			query = "select AVG(DATEDIFF( fecha_atencion,fecha_ticket)) as data from cesat.turnos_cesat where id_sucursal="+codigoSucursal;;
			 preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				Integer tiempPromedioEspera = resultSet.getInt("data");
				turnoDto.setTiempPromedioEspera(tiempPromedioEspera);
				
			}
			query = "select Max(DATEDIFF( fecha_fin,fecha_atencion)) as data from cesat.turnos_cesat where id_sucursal="+codigoSucursal;
			 preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				Integer tiempMaximoAtencion = resultSet.getInt("data");
				turnoDto.setTiempMaximoAtencion(tiempMaximoAtencion);
			}
			query = "select AVG(DATEDIFF( fecha_fin,fecha_atencion)) as data from cesat.turnos_cesat where id_sucursal="+codigoSucursal;
			 preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				Integer tiempPromedioAtencion = resultSet.getInt("data");
				turnoDto.setTiempPromedioAtencion(tiempPromedioAtencion);
				
			}
			//select distinct id_cod_sector,AVG(DATEDIFF( fecha_fin,fecha_atencion)) as data ,Max(DATEDIFF( fecha_fin,fecha_atencion)),AVG(DATEDIFF( fecha_atencion,fecha_ticket)), Max(DATEDIFF( fecha_fin,fecha_atencion)),COUNT(DISTINCT nom_usuario)from cesat.turnos_cesat group by id_cod_sector
			query = "SELECT COUNT(DISTINCT nom_usuario) as data FROM  cesat.turnos_cesat where id_sucursal="+codigoSucursal;
			 preparedStatement = (PreparedStatement) connection
					.prepareStatement(query);
			resultSet = (ResultSet) preparedStatement.executeQuery();

			if (resultSet.next()) {
				Integer cantidadDistintosUsuarios = resultSet.getInt("data");
				turnoDto.setCantidadDistintosUsuarios(cantidadDistintosUsuarios);
				
			}
			*/
			}

			return turnos;

				} catch (SQLException e) {
					e.getMessage();
					e.printStackTrace();
					LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
				}
		return null;
}

	public List<TurnoDto> turnosSucursales(Date fechaDesde, Date fechaHasta) {
		
			LOGGER.debug(LoggerVariables.CALCULANDO_ESTADISTICAS
					+ ": Clientes atendidos por los sectores de esta sucursal ");
			Connection connection = (Connection) new ConnectionMysql()
					.createConnection();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			TurnoDto turnoDto;
			List<TurnoDto> turnos = new ArrayList<TurnoDto>();
			String query = "select nombre_sucursal,cod_sucursal,CAST(fecha_atencion as CHAR) as atencionCasteada ,CAST(fecha_ticket as CHAR) as ticketCasteada,CAST(fecha_fin as CHAR) as finCasteada,time(fecha_ticket) as horaTurno, time(fecha_atencion) as horaLlamado, time(fecha_fin) as horaFin, fecha_ticket,id_turno, id_cod_sector, numero_turno, nom_sector, nom_usuario, atendido, llamado  from cesat.turnos_cesat  where llamado like 'SI' and  date(fecha_ticket) between date( ? ) and ( ? )  order  by   fecha_ticket,  id_cod_sector,   numero_turno ; ";

			try {
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(query);
				preparedStatement.setDate(1,
						new java.sql.Date(fechaDesde.getTime()));
				preparedStatement.setDate(2,
						new java.sql.Date(fechaHasta.getTime()));
				//preparedStatement.setInt(3, Integer.parseInt(sucursal));

				ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
				while (resultSet.next()) {

					// Usuario usuario =
					// usuarioDao.obtenerUsuarioPorId(resultSet.getInt("id_usuario"));
					// Sector sector =
					// sectorDao.obtenerSectorPorCodigo(resultSet.getString("id_cod_sector"));

					turnoDto = new TurnoDto();
					turnoDto.setNumero_turno(resultSet.getString("numero_turno"));
					turnoDto.setId_cod_sector(resultSet.getString("id_cod_sector"));
					turnoDto.setNomSector(resultSet.getString("nom_sector"));
					turnoDto.setNombreSucursal(resultSet.getString("nombre_sucursal"));
					turnoDto.setCodSucursal(resultSet.getString("cod_sucursal"));

					///

					turnoDto.setNomUsuario(resultSet.getString("nom_usuario"));
					turnoDto.setAtendido(resultSet.getString("atendido"));
					turnoDto.setLlamado(resultSet.getString("llamado"));
					//turnoDto.setHoraTurno(sdf.format(resultSet.getDate("horaTurno")));
					//turnoDto.setHoraAtencion(sdf.format(resultSet.getDate("horaLlamado")));
					//turnoDto.setHoraFin(sdf.format(resultSet.getDate("horaFin")));
					if (resultSet.getString("atencionCasteada")==null){
						turnoDto.setHoraAtencion("-");
					}else{
						turnoDto.setHoraAtencion(resultSet.getString("horaLlamado"));
					}
					if (resultSet.getString("ticketCasteada")==null){
						turnoDto.setFecha_ticket("-");
						turnoDto.setHoraTurno("-");
					}else{
						turnoDto.setFecha_ticket(sdf.format(resultSet.getDate("fecha_ticket")));
						turnoDto.setHoraTurno(resultSet.getString("horaTurno"));

						//turnoDto.setHoraTurno(sdf.format(resultSet.getDate("horaTurno")));
					}
					if (resultSet.getString("finCasteada")==null){
						turnoDto.setHoraFin("-");

					}else{
						//turnoDto.setHoraFin(sdf.format(resultSet.getDate("horaFin")));
						turnoDto.setHoraFin(resultSet.getString("horaFin"));

						

					}
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
	public String getTiempo(int tiempo){
		int runtime =tiempo;
		int hours = runtime / 60;
		int minutes = runtime % 60;
		String horaFinal=String.format("%02d", hours);
		String minutesFinal=String.format("%02d", minutes);

		return horaFinal + ":" +minutesFinal;
		
	}
	}