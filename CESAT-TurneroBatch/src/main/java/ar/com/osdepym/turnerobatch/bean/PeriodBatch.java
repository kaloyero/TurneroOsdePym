package ar.com.osdepym.turnerobatch.bean;

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

    /**
     * Constructor
     */
    private PeriodBatch() {
    	//Obtengo de la base de datos la configuracion
    	getQryPeriodBatch();
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
