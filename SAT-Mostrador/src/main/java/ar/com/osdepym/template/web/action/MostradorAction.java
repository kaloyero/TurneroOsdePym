package ar.com.osdepym.template.web.action;
 
import java.util.ArrayList;

import ar.com.osdepym.template.entity.Turno;
import ar.com.osdepym.template.service.MostradorServicio;

import com.opensymphony.xwork2.ActionSupport;


 
public class MostradorAction extends ActionSupport{
	  private MostradorServicio mostradorServicio = new MostradorServicio();
	  private ArrayList<Turno> listaTurnos ;
	  /**
	   * Llena la lista de los ultimos turnos que se mostraran en pantalla.
	   * @return String
	   */
	  public String ultimosLlamados() {
		 // ServletContext servletContext = ServletActionContext.getServletContext();
		  //String content = "This is the content to write into file";
		 // HttpServletRequest request=(HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		   // String savePath =request.getRealPath("/test.txt");
		   // System.out.println("PAth "+savePath);
	       // File file = new File(savePath);
		  			System.out.println("Done");
		  listaTurnos=mostradorServicio.getUltimosTurnos();
			return SUCCESS;
		}

	public MostradorServicio getMostradorServicio() {
		return mostradorServicio;
	}

	public void setMostradorServicio(MostradorServicio mostradorServicio) {
		this.mostradorServicio = mostradorServicio;
	}

	public ArrayList<Turno> getListaTurnos() {
		return listaTurnos;
	}

	public void setListaTurnos(ArrayList<Turno> listaTurnos) {
		this.listaTurnos = listaTurnos;
	}
	  
}