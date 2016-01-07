package ar.com.osdepym.template.web.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.ActionConfig;

public class LoginTest extends ConfigTest {

    public void FIXME_testLoginConfig() throws Exception {
        ActionConfig config = assertClass("example", "Sector_input", "inicio");
        assertResult(config, ActionSupport.SUCCESS, "/jsp/turnero.jsp");
        assertResult(config, ActionSupport.INPUT, "/jsp/turnero.jsp");
    }

    public void testLoginSubmit() throws Exception {
    	SectorAction sector = new SectorAction();
    	sector.setFechaTicket("09/06/1986");
    	sector.setLetra_sector("A");
    	sector.setTurnosDelante(2);
    	sector.setNro_ticket("2");
    	sector.setNro_sector(2);
    	sector.setNom_sector("asdad");
        String result = sector.execute();
        assertSuccess(result);
    }

//    // Needs access to an envinronment that includes validators
//    public void FIXME_testLoginSubmitInput() throws Exception {
//        Login login = new Login();
//        String result = login.execute();
//        assertInput(result);
//        Map errors = assertFieldErrors(login);
//        assertFieldError(errors,"username","Username is required.");
//        assertFieldError(errors,"password","Password is required.");
//    }

}