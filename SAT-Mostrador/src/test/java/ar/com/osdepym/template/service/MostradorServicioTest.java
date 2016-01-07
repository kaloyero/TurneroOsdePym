package ar.com.osdepym.template.service;

import ar.com.osdepym.template.dao.MostradorDao;
import ar.com.osdepym.template.entity.Turno;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Alejandro on 12/19/14.
 */
public class MostradorServicioTest {

    private final MostradorServicio mostradorServicio = new MostradorServicio();
    private final MostradorDao mostradorDao = mock(MostradorDao.class);

    @Before
    public void setUp() {
        field("mostradorDao").ofType(MostradorDao.class).in(this.mostradorServicio).set(mostradorDao);
    }

    @Test
    public void test() {
        // @Given
        ArrayList<Turno> turnos = new ArrayList<Turno>();
        when(this.mostradorDao.getUltimosTurnos()).thenReturn(turnos);
        // @When
        List<Turno> ultimosTurnos = this.mostradorServicio.getUltimosTurnos();
        // @Then
        assertThat(ultimosTurnos).isEqualTo(turnos);
    }
}
