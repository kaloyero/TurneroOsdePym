package ar.com.osdepym.turnerorestapi.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.com.osdepym.turnerorestapi.model.TurnoEstadistica;
import ar.com.osdepym.turnerorestapi.model.dao.ConsultaDao;

/**
 * Clase de acceso a datos
 * 
 * @author Alejandro Masciotra
 *
 */
@Component
public class ConsultaDaoImpl extends BaseDao implements ConsultaDao {

	private static final String CONSULTA = "select CAST(fecha_ticket AS CHAR) as fecha_ticket_test,CAST(fecha_atencion AS CHAR) as fecha_atencion,CAST(fecha_fin AS CHAR) as fecha_fin,id_sat,cod_sucursal,nom_sucursal,nom_sector,id_turno,id_cod_sector,numero_turno,fecha_ticket,llamado,nro_puesto,nom_usuario,atendido,nom_sucursal from vista_turnos_estadistica ";
	private Statement statement = null;
	private ResultSet resultSet = null;

	/**
	 * @see ar.com.osdepym.turnerorestapi.model.dao.ConsultaDao#getEstadisticas(java.lang.Integer)
	 */
	public synchronized List<TurnoEstadistica> getEstadisticas(Integer ultimoId) {
		List<TurnoEstadistica> lista = new ArrayList<TurnoEstadistica>();

		try {

			statement = getConn();
			String where = " WHERE id_turno > " + ultimoId + " " ;
			String orderBy = " order by id_turno ; ";
			resultSet = statement.executeQuery(CONSULTA + where + orderBy);
			
			while (resultSet.next()) {
				TurnoEstadistica dto = new TurnoEstadistica();
				dto.setId_sat(resultSet.getInt("id_sat"));
				dto.setCod_sucursal(resultSet.getString("cod_sucursal"));
				dto.setAtendido(resultSet.getString("atendido"));
				dto.setId_turno(resultSet.getInt("id_turno"));
				dto.setId_cod_sector(resultSet.getString("id_cod_sector"));
				dto.setNumero_turno(resultSet.getInt("numero_turno"));
				dto.setFecha_ticket(resultSet.getDate("fecha_ticket"));
				dto.setFecha_ticketTest(resultSet.getString("fecha_ticket_test"));
				dto.setLlamado(resultSet.getString("llamado"));
				dto.setFecha_atencionTest(resultSet.getString("fecha_atencion"));
				dto.setFecha_finTest(resultSet.getString("fecha_fin"));
				dto.setNom_sucursal(resultSet.getString("nom_sucursal"));

				dto.setNom_sector(resultSet.getString("nom_sector"));

				//dto.setFecha_atencion(resultSet.getDate("fecha_atencion"));
				dto.setNro_puesto(resultSet.getInt("nro_puesto"));
				dto.setNom_usuario(resultSet.getString("nom_usuario"));
				dto.setAtendido(resultSet.getString("atendido"));
				//dto.setFecha_fin(resultSet.getDate("fecha_fin"));
				lista.add(dto);
			}


		} catch (Exception e) {
			System.out.println("ERROR");
		} finally {

			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				statement = null;
			}
		}

		return lista;
	}
		
}