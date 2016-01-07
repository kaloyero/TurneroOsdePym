package ar.com.osdepym.template.web.action;

// Login

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.common.validation.ValidacionUsuario;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Usuario;

public class PantallaAction extends AbstractLoginAction implements
		ParameterAware {
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + PantallaAction.class);

	Map parameters;
	HttpServletRequest request = ServletActionContext.getRequest();
	private String username;
	private String password;
	private List<Sector> sectoresEspera;

	/**
	 * Llama al metodo para validar usuario
	 * Setea puesto segun la IP
	 */
	public String execute() {
		String retorno = SUCCESS;
		Usuario usuario = new Usuario();
		usuario.setCod_error(0);
		ValidacionUsuario vu = new ValidacionUsuario();
		usuario = vu.execute(username, password, usuario);
		int nriEsp = 0;
		try {
			if (usuario.getCod_error() > 0) {
				this.setErrorMessage(usuario.getMensaje_error());
				return "error";
			} else {
				session.put(TURNO, "");
				this.setPerfil(usuario.getPerfil());
				this.setNomSector(usuario.getSectores().get(0).getNomSector());
				this.setId_user(usuario.getId_usuario().toString());
				this.setLetra_sector(usuario.getSectores().get(0)
						.getCodSector());
				this.setNro_sector(usuario.getSectores().get(0).getSector());
				this.setNomUsuario(usuario.getNomUsuario());
				session.put(USER, this.getNomUsuario());
				session.put("IDUSER", this.getId_user());
				session.put(SECTOR, getNomSector());
				session.put(PERFIL, getPerfil());
				nriEsp = getTurnoDAO().numerosEnEspera(
						new Integer(this.getNro_sector()));
				this.setNrosEspera(nriEsp);
				this.setNroLlamado(this.getLetra_sector().toUpperCase() + "--");
				setSectores(getUsuarioDao().recuperarSectoresUsuario(
						usuario.getId_usuario()));
				for (Sector sector : getSectores()) {
					if (!sector.getNomSector().equals(
							session.get(SECTOR).toString())) {
						sector.setNrosEspera(getTurnoDAO().numerosEnEspera(
								new Integer(sector.getSector())).toString());
					} else {
						sector.setCodSector("");
					}
				}
				String direccionIP = request.getRemoteAddr();
				if (direccionIP.equalsIgnoreCase("0:0:0:0:0:0:0:1") || direccionIP.equalsIgnoreCase("127.0.0.1")){
					InetAddress address = InetAddress.getLocalHost();
					direccionIP = address.getHostAddress();
				}
				System.out.println(direccionIP);
				this.setIpv4(direccionIP);
				setPuestoP(getPuestoDao().obtenerPuestoPorIp(this.getIpv4()));
				if (getPuestoP().getIdPuesto().equals("0")) {
					this.setErrorMessage("La IP no tiene puesto asignado");
					retorno = ERROR;
				} else {
					this.setPuesto(getPuestoP().getPuesto());
					this.session.put(PUESTO, this.getPuesto());
					this.session.put(ID_PUESTO, getPuestoP().getIdPuesto());
					session.put(DIR_IP, this.getIpv4());
					if (usuario.getPerfil().equalsIgnoreCase("3")) {
						retorno = SUCCESS + "estad";
					} else {
						retorno = SUCCESS;
					}
				}
				return retorno;
			}
		} catch (Exception e) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			System.out.println(e.getMessage());
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			return "error";
		}
	}

	public String enEspera() {
		// String[] usuario= (String[]) parameters.get("usuario");
		String idUsuario = (String) session.get("IDUSER");
		setSectoresEspera(getUsuarioDao().recuperarSectoresUsuario(
				Integer.parseInt(idUsuario)));
		for (Sector sector : getSectoresEspera()) {

			sector.setNrosEspera(getTurnoDAO().numerosEnEspera(
					new Integer(sector.getSector())).toString());

		}
		System.out.println("SEs" + this.getSession().get(USER));
		return SUCCESS;
	}

	public String inicio() {
		try {
			if (verificarLogueado()) {
				setProperties();
				String u = session.get(PERFIL).toString();
				if (u.equalsIgnoreCase("3")) {
					return "logueadoEstadis";
				} else {
					if (verifyExistance()) {
						this.setNroLlamado(getSectorDAO()
								.obtenerSectorPorNombre(
										session.get(SECTOR).toString())
								.getCodSector()
								+ getTurnoDAO().obtenerTurnoPorId(
										new Integer(session.get(TURNO)
												.toString())).getNumero_turno());
					} else {
						this.setNroLlamado(getSectorDAO()
								.obtenerSectorPorNombre(
										session.get(SECTOR).toString())
								.getCodSector()
								+ "--");
					}
					return "logueado";
				}
			} else {

				return SUCCESS;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());
			return ERROR + "null";
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public List<Sector> getSectoresEspera() {
		return sectoresEspera;
	}

	public void setSectoresEspera(List<Sector> sectoresEspera) {
		this.sectoresEspera = sectoresEspera;
	}
}