package ar.com.osdepym.turnerorestapi.config;  
  
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
  
/**
 * Clase de Configuracion
 * - Inicializa el application context
 * 
 * @author Alejandro Masciotra
 *
 */
@Configuration 
@ComponentScan("ar.com.osdepym") 
@EnableWebMvc   
public class AppConfig {  

}  
