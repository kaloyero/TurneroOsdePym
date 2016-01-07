package ar.com.osdepym.template.web.action;
 
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.template.entity.Configuracion;
import ar.com.osdepym.template.service.ConfiguracionServicio;

import com.opensymphony.xwork2.ActionSupport;
 
public class ConfiguracionDatatableAction extends ActionSupport implements ParameterAware{
	  Map parameters;
	  private ArrayList<Configuracion> aaData = new ArrayList();
	  private ConfiguracionServicio configuracionServicio = new ConfiguracionServicio();
	  private Configuracion row ;
	  private String error;
	
	/**
	 * Lista Sucursales
	 * @return
	 */
	public String listaConfiguraciones() {
		this.setAaData(this.getConfiguracionServicio().listaConfiguracion());
		return SUCCESS;
	}
	
	/**
	 * Configuraciones Operaiones
	 * @return
	 */
	public String configuracionesOperaciones() {
		//LoginAction.getUsuariosLogueadosTest().remove(0);
		//LoginAction.getUsuariosLogueadosTest().add(new Date());
		String[] accion= (String[]) parameters.get("action");
	

		if (accion[0].equalsIgnoreCase("edit") ){
			Configuracion configuracionlEditar= new Configuracion();


			configuracionlEditar.setDT_RowId(Integer.parseInt(((String[]) parameters.get("id"))[0]));
			int tiempoTotal=Integer.parseInt(((String[]) parameters.get("data[tiempo]"))[0]);
			// Valido que el tiempo ingresado sea mayor o igual a un minuto
			if (tiempoTotal >= 1 && tiempoTotal <= 473600){
				//Multiplico por segundos
				tiempoTotal= tiempoTotal * 60;
				
				configuracionlEditar.setTiempo(tiempoTotal);

				//sucursalEditar.setCodigoTotem(Integer.parseInt(((String[]) parameters.get("data[codigoTotem]"))[0]));

				this.setRow(this.getConfiguracionServicio().editarConfiguracion(configuracionlEditar));
				this.setError(this.getRow().getError());
				
				//Llamo al servicio rest que actualiza el tiempo de la tarea de actualización
				actualizarTiempoDeTarea();
//		    	RestTemplate restTemplate = new RestTemplate();
//		    	restTemplate.getForEntity("http://localhost/CESAT-TurneroBatch/servicio/updatePeriodBatch",String.class);
				
			} else  {
				this.setError("El tiempo ingresado no puede ser menor a 1 minuto, ni mayor a 473.600 minutos.");
			}
				
				

		}
	
		
		return SUCCESS;
	}

	/**
	 * Este metodo invoca al servicio rest "updatePeriodBatch" 
	 * para actualizar el tiempo de repetición de la tarea
	 * 
	 */
	public void actualizarTiempoDeTarea() {
		
		try {

	        URL url = new URL("http://localhost/CESAT-TurneroBatch/servicio/updatePeriodBatch");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");

	        if (conn.getResponseCode() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                    + conn.getResponseCode());
	        }

	        conn.disconnect();

	    } catch (MalformedURLException e) {

	        e.printStackTrace();

	    } catch (IOException e) {

	        e.printStackTrace();

	    }

	}
	
	public ArrayList<Configuracion> getAaData() {
		return aaData;
	}
	public void setAaData(ArrayList<Configuracion> aaData) {
		this.aaData = aaData;
	}
	public ConfiguracionServicio getConfiguracionServicio() {
		return configuracionServicio;
	}
	public void setConfiguracionServicio(ConfiguracionServicio configuracionServicio) {
		this.configuracionServicio = configuracionServicio;
	}
	public Map getParameters() {
	    return parameters;
	  }

	  public void setParameters(Map parameters) {
	    this.parameters = parameters;
	  }


	public Configuracion getRow() {
		return row;
	}
	public void setRow(Configuracion row) {
		this.row = row;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

 

}