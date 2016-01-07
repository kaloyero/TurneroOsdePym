package ar.com.osdepym.turnerobatch.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ar.com.osdepym.turnerobatch.bean.PeriodBatch;
import ar.com.osdepym.turnerobatch.model.Sucursal;
import ar.com.osdepym.turnerobatch.model.TurnoEstadistica;
import ar.com.osdepym.turnerobatch.model.dao.ConsultaDao;
import ar.com.osdepym.turnerobatch.model.dao.impl.ConsultaDaoImpl;


/**
 * Clase que gestiona la tarea de actualizacion de turnos
 * 
 * @author Alejandro Masciotra
 *
 */
public class SchedulerJob implements Job {


	private static final String PROTOCOLO = "http://";
	private static final String PROYECTO = "/SAT-TurneroRestApi";
	private static final String SERVICIO = "/servicio/getEstadisitica";

	
	@Autowired
	ConsultaDao consultaDao = new ConsultaDaoImpl();
	
	/** 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public synchronized void execute(JobExecutionContext context)
			throws JobExecutionException {

		for (Sucursal suc : consultaDao.getSucursales()) {
			int idSuc = suc.getId_sat();
			try {
				//Verifico si la sucursal no se esta actualizando en este momento
//				String estadoSuc = consultaDao.getEstadoActualizacion(idSuc);
//				if (estadoSuc.equals(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA)){
					//Bloqueo la Sucursal para que se actualice
//					consultaDao.actualizarEstadoActualizacion(ConsultaDao.ESTADO_ACTUALIZACION_ACTUALIZANDO, idSuc);
					//Actualizo la información de turnos en la central
					actualizoCentral(suc);
//				} else {
//					System.out.println("SUCURSAL EN PROCESO DE ACTUALIZACION");
//				}
				
			} catch (Exception e) {
				System.out.println("Error al comunicarse con la central: "+ suc.getNom_sucursal());
			} finally {
				//Desbloqueo la Sucursal para que pueda Actualizarce
//				consultaDao.actualizarEstadoActualizacion(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA, idSuc);
			}
		}
		
		actualizarPeriodoBatch (context);
	}
	
	/**
	 * Actualiza los datos de la central
	 * 
	 * @param suc
	 * @throws Exception
	 */
	private synchronized void actualizoCentral(Sucursal suc) throws Exception{
		int idUltimoTurnoSuc  =consultaDao.getIdUltimoTurnoSucursalByIdSucursal(suc.getId_sat());

		//Consulto las estadisticas
		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.getForEntity("http://192.168.2.103/SAT-TurneroRestApi/servicio/test",String.class);
		TurnoEstadistica[] turnos = restTemplate.getForObject(PROTOCOLO+suc.getIP()+PROYECTO+SERVICIO+"?id="+idUltimoTurnoSuc, TurnoEstadistica[].class);

		//Actualizo la fecha de ultima comprobacion
		consultaDao.actualizarUltimaComprobacion(suc.getId_sat());
		
		//Inserto los turnos y actualizo fecha de actualizaciòn 
		consultaDao.insertTurnos(turnos,suc.getId_sat());


	}
	
	

	/**
	 * Este metodo reconfigura el periodo de ejecución de con que se consultará a los quioscos
	 * 
	 * @param context
	 */
	private void actualizarPeriodoBatch (JobExecutionContext context){
    	RestTemplate restTemplate = new RestTemplate();
    	restTemplate.getForEntity("http://localhost:8080/CESAT-TurneroBatch/servicio/updatePeriodBatch",String.class);

	}
	
	/**
	 * Este metodo reconfigura el periodo de ejecución de con que se consultará a los quioscos
	 * 
	 * @param context
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Deprecated
	private void validarRepeticionTrabajo (JobExecutionContext context){

		// Obtengo el triguer viejo
		Trigger oldTrigger =  context.getTrigger();

		// Obtengo como fue configurado
		TriggerBuilder tb = oldTrigger.getTriggerBuilder();

		// Actualizo el periodo
		int periodTime = PeriodBatch.getPeriodValue();
		Trigger newTrigger = tb.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(periodTime ).repeatForever()).build();

		Scheduler scheduler = context.getScheduler();

		
		try {
			
			//reconfiguro el trabajo con el nuevo periodo
			scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
		} catch (SchedulerException e) {
			//Si no se actualiza imprime una excepciòn
			System.out.println("ERROR AL ACTUALIZAR EL PEriodo: " + e.getMessage());
		}
	
		//lo inyecto en el contexto
		context.put("scheduler", scheduler);	
	}

}
