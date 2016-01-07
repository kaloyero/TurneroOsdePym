package ar.com.osdepym.template.web.action;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class CambiarSectorActionTest {

	private final HashMap<String,Object> session = new HashMap<String, Object>();
	private final CambiarSectorAction cambiarSectorAction = new CambiarSectorAction(); 
	
	@Before
	public void setUp() {
		this.cambiarSectorAction.setSession(this.session);
	}
	
	@Test
	public void test() {
		// @Given
		// @When
		// @Then
	}
}