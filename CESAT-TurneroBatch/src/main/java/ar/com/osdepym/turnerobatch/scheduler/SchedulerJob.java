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
	private static final String SERVICIO_ESTADISTICAS = "/servicio/getEstadisitica";
	private static final String SERVICIO_ACTUALIZA_LLAMADOS = "/servicio/getActualizaTurnosLlamados";
	private static final String SERVICIO_BATCH_ACTUALIZAR_PERIODO = "http://localhost/CESAT-TurneroBatch/servicio/updatePeriodBatch";
	
	@Autowired
	ConsultaDao consultaDao = new ConsultaDaoImpl();
	
	/** 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public synchronized void execute(JobExecutionContext context)
			throws JobExecutionException {
		/*Actualizo el tiempo de repetición del batch */
		actualizarPeriodoBatch (context);
		
		/* Consulto estado de actualizaciòn del batch */
		String estadoActualizacionBatch = consultaDao.getEstadoActualizacionBatch();
		if (estadoActualizacionBatch.equalsIgnoreCase(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA)){

			/*Actualizo en configuración BATCH ejecutandose:
			  Bloqueo el batch para que no se ejecute si entra nuevamente */
			consultaDao.actualizarEstadoActualizacionBatch(ConsultaDao.ESTADO_ACTUALIZACION_ACTUALIZANDO);
			/*Recorro las sucursales para actualizar*/
			for (Sucursal suc : consultaDao.getSucursales()) {
				int idSuc = suc.getId_sat();
				try {
					preSucursalActualizacion(suc);
				} catch (Exception e) {
					System.out.println("ERROR: No se puede establecer comunicación con la central: "+ suc.getNom_sucursal());
				} finally {
					/*Desbloqueo la Sucursal para que pueda Actualizarce*/
					consultaDao.actualizarEstadoActualizacion(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA, idSuc);
				}
			}
		}
		/*Desbloqueo la ejecución del batch para liberarlo y que pueda Actualizarse*/
		consultaDao.actualizarEstadoActualizacionBatch(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA);
		
	}

	private void preSucursalActualizacion(Sucursal suc) throws Exception {
		int idSuc = suc.getId_sat();
		/*Verifico si la sucursal no se esta actualizando en este momento*/
		String estadoActualizacionSucursal = consultaDao.getEstadoActualizacion(idSuc);
		if (estadoActualizacionSucursal.equals(ConsultaDao.ESTADO_ACTUALIZACION_NOACTUALIZA)){
			/*Bloqueo la Sucursal para que se actualice*/
			consultaDao.actualizarEstadoActualizacion(ConsultaDao.ESTADO_ACTUALIZACION_ACTUALIZANDO, idSuc);
			/* Actualizo en la central, los turnos NO llamados en la Sucursal*/
			actualizoEstadoTurnosCentral(suc);
			/*Actualizo en la central la información de nuevos turnos ingresados en la sucursal*/
			actualizoCentral(suc);
			
			
		} else {
			System.out.println("WARNING: Sucursal en proceso de actualización");
		}
	}
	
	
	/**
	 * Actualiza nuevos datos de la central
	 * 
	 * @param suc
	 * @throws Exception
	 */
	private synchronized void actualizoCentral(Sucursal suc) throws Exception{
		int idUltimoTurnoSuc  =consultaDao.getIdUltimoTurnoSucursalByIdSucursal(suc.getId_sat());

		//Consulto las estadisticas
		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.getForEntity("http://192.168.2.103/SAT-TurneroRestApi/servicio/test",String.class);
		TurnoEstadistica[] turnos = restTemplate.getForObject(PROTOCOLO+suc.getIP()+PROYECTO+SERVICIO_ESTADISTICAS+"?id="+idUltimoTurnoSuc, TurnoEstadistica[].class);

		//Actualizo la fecha de ultima comprobacion
		consultaDao.actualizarUltimaComprobacion(suc.getId_sat());
		
		//Inserto los turnos y actualizo fecha de actualizaciòn 
		consultaDao.insertTurnos(turnos,suc.getId_sat());


	}
	
	/**
	 * Actualiza Turnos llamados de la central
	 * 
	 * @param suc
	 * @throws Exception
	 */
	private synchronized void actualizoEstadoTurnosCentral(Sucursal suc) throws Exception{
		int idUltimoTurnoSuc  =consultaDao.getIdUltimoTurnoSucursalByIdSucursal(suc.getId_sat());

		//Consulto las estadisticas
		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.getForEntity("http://192.168.2.103/SAT-TurneroRestApi/servicio/test",String.class);
		TurnoEstadistica[] turnos = restTemplate.getForObject(PROTOCOLO+suc.getIP()+PROYECTO+SERVICIO_ACTUALIZA_LLAMADOS+"?id="+idUltimoTurnoSuc, TurnoEstadistica[].class);

		//Actualizo los turnos atendidos 
		consultaDao.actualizarTurnosAtendidos(turnos,suc.getId_sat());


	}

	/**
	 * Este metodo reconfigura el periodo de ejecución de con que se consultará a los quioscos
	 * 
	 * @param context
	 */
	private void actualizarPeriodoBatch (JobExecutionContext context){
    	RestTemplate restTemplate = new RestTemplate();
    	restTemplate.getForEntity(SERVICIO_BATCH_ACTUALIZAR_PERIODO,String.class);

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
