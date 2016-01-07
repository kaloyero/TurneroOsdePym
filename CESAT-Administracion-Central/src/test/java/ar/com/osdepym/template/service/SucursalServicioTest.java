package ar.com.osdepym.template.service;

import ar.com.osdepym.template.dao.SucursalDao;
import ar.com.osdepym.template.entity.Sucursal;
import org.junit.Before;
import org.junit.Test;

import static org.fest.reflect.core.Reflection.field;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alejandro on 12/19/14.
 */
public class SucursalServicioTest {

    private final SucursalServicio sucursalServicio = new SucursalServicio();
    private final SucursalDao sucursalDao = mock(SucursalDao.class);

    @Before
    public void setUp() {
        field("sucursalDao").ofType(SucursalDao.class).in(this.sucursalServicio).set(this.sucursalDao);
    }

    @Test
    public void testEditarSucursalCallEditarSucursal_WhenCallEditarSucursal() {
        // @Given
        Sucursal sucursal = new Sucursal();
        // @When
        this.sucursalServicio.editarSucursal(sucursal);
        // @Then
        verify(this.sucursalDao).editarSucursal(sucursal);
    }

    @Test
    public void testListaSucursalCallListaSucursal_WhenCallListaSucursal() {
        // @Given
        Sucursal sucursal = new Sucursal();
        // @When
        this.sucursalServicio.listaSucursal();
        // @Then
        verify(this.sucursalDao).listaSucursal();
    }
}
