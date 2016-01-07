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
	
	public List<Sucursal> getSucursales();

	public void actualizarUltimaComprobacion(int idSucursal);
	
	void actualizarUltimaActualizacion(int idSucursal, int idUltimoTurno);
	
	int getIdUltimoTurnoSucursalByIdSucursal(int idSucursal);

	void insertTurnos(TurnoEstadistica[] turnos, int id_sat);
		
	String getEstadoActualizacion(int idSat);
	
	void actualizarEstadoActualizacion(String estado,int idSucursal);
}
