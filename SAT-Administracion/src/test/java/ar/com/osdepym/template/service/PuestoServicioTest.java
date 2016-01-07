package ar.com.osdepym.template.service;

import ar.com.osdepym.template.dao.PuestoDao;
import ar.com.osdepym.template.entity.Puesto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alejandro on 12/19/14.
 */
public class PuestoServicioTest {

    private static final int DUMMY_INT = 0;
    private final PuestoDao puestoDao = mock(PuestoDao.class);
    private final PuestoServicio puestoServicio = new PuestoServicio();
    private final Puesto anyPuesto = new Puesto();

    @Before
    public void setUp() {
        field("puestoDao").ofType(PuestoDao.class).in(this.puestoServicio).set(this.puestoDao);
    }

    @Test
    public void testListaPuestosCallsListaPuestos_WhenCallsListaPuestos() {
        // @Given
        // @When
        this.puestoServicio.listaPuestos();
        // @Then
        verify(this.puestoDao).listaPuestos();
    }

    @Test
    public void testInsertarPuestoCallsInsertarPuesto_WhenCallsInsertarPuesto() {
        // @Given
        // @When
        this.puestoServicio.insertarPuesto(this.anyPuesto);
        // @Then
        verify(this.puestoDao).insertarPuesto(this.anyPuesto);
    }

    @Test
    public void testEditarPuestoCallsEditarPuesto_WhenCallsEditarPuesto() {
        // @Given
        // @When
        this.puestoServicio.editarPuesto(this.anyPuesto);
        // @Then
        verify(this.puestoDao).editarPuesto(this.anyPuesto);
    }

    @Test
    public void testBorrarSectorCallsBorrarSector_WhenCallsBorrarSector() {
        // @Given
        // @When
        ArrayList<PuestoServicio> sector = puestoServicio.borrarSector();
        // @Then
        assertThat(sector).isNull();
    }

    @Test
    public void testEliminarPuestoCallsEliminarPuesto_WhenCallsEliminarPuesto() {
        // @Given
        // @When
        this.puestoServicio.eliminarPuesto(DUMMY_INT);
        // @Then
        verify(this.puestoDao).eliminarPuesto(DUMMY_INT);
    }
}
