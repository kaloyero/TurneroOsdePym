package ar.com.osdepym.turnerobatch.model.dao;

import java.util.List;

import ar.com.osdepym.turnerobatch.model.Sucursal;
import ar.com.osdepym.turnerobatch.model.TurnoEstadistica;


/**
 * Interfase de Acceso a datos 
 * 
 * @author Alejandro Masciotra
 *
 */
public interface ConsultaDao {

	static final String ESTADO_ACTUALIZACION_NOACTUALIZA = "N";
	static final String ESTADO_ACTUALIZACION_ACTUALIZANDO = "A";
	
	/**
	 * Lista todas las sucursales habilitadas
	 * 
	 * @return listado de sucursales
	 */
	public List<Sucursal> getSucursales();

	/**
	 * Actualiza en la base de datos la fecha de ultima vez que se obtuvieron datos de la sucursal 
	 * 
	 * @param idSucursal - Identificador de la Sucursal
	 */
	public void actualizarUltimaComprobacion(int idSucursal);
	
	/**
	 * Actualiza en la base de datos la fecha de ultima vez que se actualizaron los datos en la central.
	 * Tambien guarda el último id obtenido de la sucursal
	 * 
	 * @param idSucursal - Identificador de la Sucursal
	 */
	void actualizarUltimaActualizacion(int idSucursal, int idUltimoTurno);
	
	/**
	 * Devuelve el id del ultimo turno que se actualizo de la sucursal
	 * 
	 * @param idSucursal
	 * @return id ultimo turno 
	 */
	int getIdUltimoTurnoSucursalByIdSucursal(int idSucursal);

	/**
	 * Inserta los turnos en la base de datos central
	 * 
	 * @param turnos
	 */
	void insertTurnos(TurnoEstadistica[] turnos, int id_sat);

	/**
	 * Actualiza el estado de los turnos atendidos
	 * 
	 * @param turnos
	 * @param id_sat
	 */
	void actualizarTurnosAtendidos(TurnoEstadistica[] turnos, int id_sat);
	
	
	/** 
	 * Obtiene el estado de actualizaciòn del batch de Actualizacion
	 * 
	 * @return Si es A el batch se encuentra en proceso de actualización
	 * Si es N el batch no se encuentra en proceso de actualización
	 */
	String getEstadoActualizacionBatch();
	
	/** 
	 * Obtiene el estado de actualizaciòn de la sucursal
	 * 
	 * @param idSat id de sucursal
	 * @return Si es A la sucursal se encuentra en proceso de actualización
	 * Si es N la sucursal no se encuentra en proceso de actualización
	 */
	String getEstadoActualizacion(int idSat);
	
	void actualizarEstadoActualizacion(String estado,int idSucursal);
	
	void actualizarEstadoActualizacionBatch(String estado);
	
}
