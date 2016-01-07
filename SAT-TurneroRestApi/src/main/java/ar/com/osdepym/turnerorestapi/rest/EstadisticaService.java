package ar.com.osdepym.turnerorestapi.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.osdepym.turnerorestapi.model.TurnoEstadistica;
import ar.com.osdepym.turnerorestapi.model.dao.ConsultaDao;

/**
 * Servicios
 * 
 * @author Alejandro Masciotra
 *
 */
@RestController
@RequestMapping("/servicio")
public class EstadisticaService {

	@Autowired
	private ConsultaDao consultaDao;
	
	/**
	 * Servicio que devuelve los turnos de la sucursal
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getEstadisitica")
	public synchronized List<TurnoEstadistica> getEstadisticasTurnero(@RequestParam(value = "id",required = false,
	                                                    defaultValue = "0") Integer id) {
		List<TurnoEstadistica> estadisticas = consultaDao.getEstadisticas(id);
		
		return estadisticas;
	}

	/**
	 * Servicio de prueba
	 * 
	 * @return mensaje de prueba
	 */
	@RequestMapping("/test")
	public String getTest() {
	
		System.out.println("Servicio de Prueba");
		
		return "Ok";
	}
}