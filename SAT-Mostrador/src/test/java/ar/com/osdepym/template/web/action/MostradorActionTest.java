package ar.com.osdepym.template.web.action;

import ar.com.osdepym.template.service.MostradorServicio;
import com.opensymphony.xwork2.Action;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alejandro on 12/19/14.
 */
public class MostradorActionTest {

    private final MostradorAction mostradorAction = new MostradorAction();
    private final MostradorServicio mostradorServicio = mock(MostradorServicio.class);

    @Before
    public void setUp() {
        this.mostradorAction.setMostradorServicio(this.mostradorServicio);
    }

    @Test
    public void test() {
        // @Given
        // @When
        this.mostradorAction.ultimosLlamados();
        // @Then
        verify(this.mostradorServicio).getUltimosTurnos();
    }

    @Test
    public void test2() {
        // @Given
        // @When
        String llamados = this.mostradorAction.ultimosLlamados();
        // @Then
        assertThat(llamados).isEqualTo(Action.SUCCESS);
    }
}
