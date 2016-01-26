package ar.com.osdepym.turnerobatch.bean;

import ar.com.osdepym.turnerobatch.model.Sucursal;
import ar.com.osdepym.turnerobatch.model.dao.ConsultaDao;
import ar.com.osdepym.turnerobatch.model.dao.impl.ConsultaDaoImpl;
import ar.com.osdepym.turnerobatch.model.query.ConfigurationQuery;

/**
 * Se consulta el tiempo, frecuencia, con que se ejecutara el batch 
 * para actualizar la información de las centrales
 * 
 * @author Alejandro Masciotra
 */
public class PeriodBatch {
    private static PeriodBatch period = null;
    private int value;

    ConsultaDao consultaDao = new ConsultaDaoImpl();
    
    /**
     * Constructor
     */
    private PeriodBatch() {
    	//Obtengo de la base de datos la configuracion
    	getQryPeriodBatch();
		/* REINICIO DE ESTADOS DE ACTUALIZACION */
    	reinicioEstadosActualizacion();
    }

    
    /**
     * devuelve la instancia de Period batch
     * 
     * @return period
     */
    public static PeriodBatch getPeriodBatch() {
        if(period == null) {
        	period = new PeriodBatch();
        }

        return period;
    }

    public void setValue(int periodTime) {
        this.value = periodTime;
    }

    /**
     * Devuelve el valor de la variable PeriodBatch
     * 
     * @return period batch
     */
    public static int getPeriodValue() {
    	//TODO remover esto
    	getPeriodBatch().getQryPeriodBatch();
    	
    	
        return getPeriodBatch().value;
    }
    
    private  void reinicioEstadosActualizacion (){
		//Desbloqueo la Sucursal para que pueda Actualizarce
    	for (Sucursal suc : consultaDao.getSucursales()) {
    		consultaDao.actualizarEstadoActualizacion(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA, suc.getId_sat());	
    	}
		//Desbloqueo la ejecución del batch para liberarlo y que pueda Actualizarse
		consultaDao.actualizarEstadoActualizacionBatch(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA);
		
		
    }
    
    /**
     * Asigna valor a la variable periodBatch
     * 
     * @param periodTime
     */
    public static void setPeriodTime(int periodTime) {
        getPeriodBatch().setValue(periodTime);
    }    
    
    /**
     * Obtengo la configuraci[on de la base de datos
     * 
     */
    private void getQryPeriodBatch(){
    	ConfigurationQuery qry = new ConfigurationQuery();
    	this.value  =qry.getBatchPeriod();
    }
    
}
