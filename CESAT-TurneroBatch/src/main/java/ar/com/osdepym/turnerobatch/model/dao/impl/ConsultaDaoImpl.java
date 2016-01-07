package ar.com.osdepym.turnerobatch.model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.com.osdepym.turnerobatch.model.Sucursal;
import ar.com.osdepym.turnerobatch.model.TurnoEstadistica;
import ar.com.osdepym.turnerobatch.model.dao.BaseDao;
import ar.com.osdepym.turnerobatch.model.dao.ConsultaDao;

/**
 * 
 * Clase de Acceso a datos 
 * 
 * @author Alejandro Masciotra
 *
 */
@Component("consultaDao")
public class ConsultaDaoImpl extends BaseDao implements ConsultaDao {

	private static final String CONSULTA_SUCURSALES_ACTIVAS = "select * from sat_sucursales where habilitado like 'SI' ;";
	private static final String UPDATE_ULTIMA_COMPROBACION = "update sat_sucursales set fech_ult_conex = CURRENT_TIMESTAMP where id_sat = ";
	private static final String GET_SUCURSAL_ESTADO_ACTUALIZACION = "SELECT `estado_actualizacion` FROM `sat_sucursales` WHERE `id_sat`= ";
	
	
	private Statement statement = null;
	private ResultSet resultSet = null;

	
	/**
	 * Lista todas las sucursales habilitadas
	 * 
	 * @return listado de sucursales
	 */
	public List<Sucursal> getSucursales() {
		List<Sucursal> lista = new ArrayList<Sucursal>();

		try {

			statement = getConn();

			resultSet = statement.executeQuery(CONSULTA_SUCURSALES_ACTIVAS);
			
			while (resultSet.next()) {
				Sucursal dto = new Sucursal();
				
				dto.setId_sat(resultSet.getInt("id_sat"));
				dto.setCod_sucursal(resultSet.getString("cod_sucursal"));
				dto.setNom_sucursal(resultSet.getString("nom_sucursal"));
				dto.setVersionSAT(resultSet.getString("versionSAT"));
				dto.setFech_act(resultSet.getDate("fech_act"));
				dto.setIP(resultSet.getString("IP"));
				dto.setFech_ult_conex(resultSet.getDate("fech_ult_conex"));
				dto.setUlt_id_turno(resultSet.getInt("ult_id_turno"));
				dto.setHabilitado(resultSet.getString("habilitado"));
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

	/**
	 * Actualiza en la base de datos la fecha de ultima vez que se obtuvieron datos de la sucursal 
	 * 
	 * @param idSucursal - Identificador de la Sucursal
	 */
	public synchronized void actualizarUltimaComprobacion(int idSucursal){
		try {

			statement = getConn();

			statement.executeUpdate(UPDATE_ULTIMA_COMPROBACION+ "'"+idSucursal+"' ; ");


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
	}

	
	/** 
	 * Obtiene el estado de actualizaciòn de la sucursal
	 * 
	 * @param idSat id de sucursal
	 * @return Si es A la sucursal se encuentra en proceso de actualización
	 * Si es N la sucursal no se encuentra en proceso de actualización
	 */
	public String getEstadoActualizacion(int idSat) {
		List<Sucursal> lista = new ArrayList<Sucursal>();
		String estado= ESTADO_ACTUALIZACION_NOACTUALIZA;
		try {

			statement = getConn();

			resultSet = statement.executeQuery(GET_SUCURSAL_ESTADO_ACTUALIZACION + "'"+idSat+"' ;" );
			
			while (resultSet.next()) {
				estado=resultSet.getString("estado_actualizacion");
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

		return estado;
	}
	
	
	public synchronized void actualizarEstadoActualizacion(String estado,int idSucursal){
		try {
			String query = "update sat_sucursales set estado_actualizacion = '"+estado+"' where id_sat = "+idSucursal+" ;";

			statement = getConn();
			statement.executeUpdate(query);
			
			
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
	}	
	/**
	 * Actualiza en la base de datos la fecha de ultima vez que se actualizaron los datos en la central.
	 * Tambien guarda el último id obtenido de la sucursal
	 * 
	 * @param idSucursal - Identificador de la Sucursal
	 */
	public void actualizarUltimaActualizacion(int idSucursal, int idUltimoTurno){
		try {

			statement = getConn();

			String updateActualizacionCentral = getActualizarUltimaActualizacion(idSucursal, idUltimoTurno);
			statement.executeUpdate(updateActualizacionCentral);


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
	}
	
	public synchronized void reservaSucursalParaActualizar(int idSucursal){
		try {

			statement = getConn();

			statement.executeUpdate(UPDATE_ULTIMA_COMPROBACION+ "'"+idSucursal+"' ; ");


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
	}

	/**
	 * obtiene el query q actualiza la fecha hora de ultima actualizaci[on de la sucursal
	 * 
	 * @param idSucursal
	 * @param idUltimoTurno
	 * @return
	 */
	private String getActualizarUltimaActualizacion(int idSucursal, int idUltimoTurno){
		return "update sat_sucursales set fech_act = CURRENT_TIMESTAMP, ult_id_turno = "+ idUltimoTurno +" where id_sat = '"+ idSucursal +"' ; ";
	}

	
	/**
	 * Obtiene el query para insertar un turno
	 * 
	 * @param turno
	 * @param idSucursal
	 * @return
	 */
	private String getInsertTurno(TurnoEstadistica turno,int idSucursal){
		StringBuffer insert=new StringBuffer("");
		
		insert.append("INSERT INTO turnos_cesat ");
		insert.append("	(id_sucursal, id_turno, id_cod_sector, nom_sector, numero_turno, fecha_ticket, " );
		insert.append(" llamado, fecha_atencion, nom_usuario, nro_puesto, atendido, fecha_fin, nombre_sucursal,cod_sucursal) "); 
		insert.append(" VALUES ( ");
		insert.append("'"+ idSucursal+"', ");
		insert.append(""+ turno.getId_turno()+", " );
		insert.append("'"+ turno.getId_cod_sector()+"', ");
		insert.append(" '"+ turno.getNom_sector()+"', " );
		insert.append(" "+ turno.getNumero_turno()+", ");
		if (turno.getFecha_ticketTest() != null){
			insert.append(" '"+ turno.getFecha_ticketTest() +"', ");
		} else {
			insert.append(" null, ");
		}
		insert.append(" '"+ turno.getLlamado()+"', "); 
		if (turno.getFecha_atencionTest()  != "0000-00-00"){
			//insert.append(" '"+ new java.sql.Date(turno.getFecha_atencion().getTime()) +"', ");	
			insert.append(" '"+ turno.getFecha_atencionTest() +"', ");

		} else {
			insert.append(" null, ");
		}
		insert.append("'"+ turno.getNom_usuario()+"', ");
		insert.append(""+ turno.getNro_puesto()+", ");
		insert.append("'"+ turno.getAtendido()+"', ");
		if (turno.getFecha_finTest() != "0000-00-00"){
			
			insert.append(" '"+ turno.getFecha_finTest() +"', ");
		} else {
			insert.append(" null, ");
		}
		insert.append("'"+ turno.getNom_sucursal()+"', ");
		insert.append("'"+ turno.getCod_sucursal()+"' "); 
		insert.append(");");

		return insert.toString();
	}
	
	/**
	 * Devuelve el id del ultimo turno que se actualizo de la sucursal
	 * 
	 * @param idSucursal
	 * @return id ultimo turno 
	 */
	public int getIdUltimoTurnoSucursalByIdSucursal(int idSucursal) {
		Sucursal dto = new Sucursal();
		try {

			statement = getConn();
			String consulta = "select * from sat_sucursales where id_sat = '"+ idSucursal +"' ;";
			resultSet = statement.executeQuery(consulta);
			
			resultSet.next();
			
			dto.setId_sat(resultSet.getInt("id_sat"));
			dto.setCod_sucursal(resultSet.getString("cod_sucursal"));
			dto.setNom_sucursal(resultSet.getString("nom_sucursal"));
			dto.setVersionSAT(resultSet.getString("versionSAT"));
			dto.setFech_act(resultSet.getDate("fech_act"));
			dto.setIP(resultSet.getString("IP"));
			dto.setFech_ult_conex(resultSet.getDate("fech_ult_conex"));
			dto.setUlt_id_turno(resultSet.getInt("ult_id_turno"));
			dto.setHabilitado(resultSet.getString("habilitado"));
				

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

		return dto.getUlt_id_turno();
	}

	/**
	 * Inserta los turnos en la base de datos central
	 * 
	 * @param turnos
	 */
	public void insertTurnos(TurnoEstadistica[] turnos, int id_sat){
		try {
			statement = getConn();
			
			for (TurnoEstadistica turno : turnos) {
				//Inserta turno en la central
				String insert = getInsertTurno(turno,id_sat);
				statement.execute(insert);
				
				//Actualiza la Sucursal
				String updateActualizacionCentral = getActualizarUltimaActualizacion(id_sat, turno.getId_turno());
				statement.executeUpdate(updateActualizacionCentral);
			}
			
			//statement = getConn();



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
	}

	
}