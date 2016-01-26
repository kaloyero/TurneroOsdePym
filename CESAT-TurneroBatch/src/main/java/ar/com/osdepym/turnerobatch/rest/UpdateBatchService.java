package ar.com.osdepym.turnerobatch.rest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.osdepym.turnerobatch.bean.PeriodBatch;
import ar.com.osdepym.turnerobatch.model.dao.ConsultaDao;
import ar.com.osdepym.turnerobatch.model.dao.impl.ConsultaDaoImpl;
/**
 * 
 * Esta clase contiene servicios batch
 * 
 * @author Alejandro MAsciotra
 *
 */
@RestController
@RequestMapping("/servicio")
public class UpdateBatchService {

	@RequestMapping("/test")
	public String getTest(HttpServletRequest req) {

		
		return "prueba OK";
	}
	
	/**
	 * Actualiza el tiempo de repeticion para la tarea de actualizaci[on de turnos
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/updatePeriodBatch")
	public String updatePeriodBatch(HttpServletRequest req) {
		
        ServletContext ctx = req.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
        Scheduler scheduler;
		try {
			scheduler = factory.getScheduler("MyScheduler");
			
			Trigger oldTrigger = scheduler.getTrigger(new TriggerKey("dummyTriggerName"));
			// Obtengo como fue configurado
			TriggerBuilder tb = oldTrigger.getTriggerBuilder();
			int periodTime = PeriodBatch.getPeriodValue();
			Trigger newTrigger = tb.withSchedule(SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInSeconds(periodTime ).repeatForever()).build();
			
			//reconfiguro el trabajo con el nuevo periodo
			scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR AL ACTUALIZAR EL PEriodo: " + e.getMessage());
			return "ERROR ";
		}    
		
		return "OK";
	}
	
	/**
	 * 
	 * Fuerza la ejecucucion y actualiza el tiempo de repeticion para la tarea de actualizaci[on de turnos
	 * @param req
	 * @return
	 * @throws InterruptedException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/ejecutarAhora")
	public String ejecutarAhora(HttpServletRequest req) throws InterruptedException {
        ServletContext ctx = req.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
        Scheduler scheduler;
		try {
			scheduler = factory.getScheduler("MyScheduler");
			
			Trigger oldTrigger = scheduler.getTrigger(new TriggerKey("dummyTriggerName"));
			// Obtengo como fue configurado
			TriggerBuilder tb = oldTrigger.getTriggerBuilder();
			int periodTime = PeriodBatch.getPeriodValue();
			Trigger newTrigger = tb.withSchedule(SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInSeconds(periodTime ).repeatForever()).startNow().build();
			
			//reconfiguro el trabajo con el nuevo periodo
			scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR AL ACTUALIZAR EL PEriodo: " + e.getMessage());
			return "ERROR";
		}    
		
		return "OK";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/obtenerEstadoBatch")
	public String p(HttpServletRequest req) throws InterruptedException {
        ConsultaDao dao = new ConsultaDaoImpl();
        
        return dao.getEstadoActualizacionBatch();
	}
}