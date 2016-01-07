package ar.com.osdepym.template.service;

import ar.com.osdepym.template.dao.UsuarioDao;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.entity.UsuarioSector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alejandro on 12/19/14.
 */
public class UsuarioServicioTest {

    private static final int DUMMY_ID = 0;
    private final UsuarioServicio usuarioServicio = new UsuarioServicio();
    private final UsuarioDao usuarioDao = mock(UsuarioDao.class);
    private final Usuario usuario = new Usuario();
    private final UsuarioSector usuarioSector = new UsuarioSector();

    @Before
    public void setUp() {
        this.usuarioServicio.setUsuarioDao(this.usuarioDao);
    }

    @Test
    public void testGetUsuarioDaoCallsGetUsuarioDao_WhenCallsGetUsuarioDao() {
        // @Given
        // @When
        this.usuarioServicio.setUsuarioDao(this.usuarioDao);
        // @Then
        assertThat(this.usuarioServicio.getUsuarioDao()).isEqualTo(this.usuarioDao);
    }

    @Test
    public void testBorrarSectorReturnsNull_WhenCallsBorrarSector() {
        // @Given
        // @When
        ArrayList<UsuarioServicio> usuarioServicios = this.usuarioServicio.borrarSector();
        // @Then
        assertThat(usuarioServicios).isNull();
    }

    @Test
    public void testEditarUsuarioCallsEditarUsuario_WhenCallsEditarUsuario() {
        // @Given
        // @When
        this.usuarioServicio.editarUsuario(this.usuario);
        // @Then
        verify(this.usuarioDao).editarUsuario(this.usuario);
    }

    @Test
    public void testEditarUsuarioSectorCallsEditarUsuarioSector_WhenCallsEditarUsuarioSector() {
        // @Given
        // @When
        this.usuarioServicio.editarUsuarioSector(this.usuarioSector);
        // @Then
        verify(this.usuarioDao).editarUsuarioSector(this.usuarioSector);
    }

    @Test
    public void testEliminarUsuarioCallsEliminarUsuario_WhenCallsEliminarUsuario() {
        // @Given
        // @When
        this.usuarioServicio.eliminarUsuario(DUMMY_ID);
        // @Then
        verify(this.usuarioDao).eliminarUsuario(DUMMY_ID);
    }

    @Test
    public void testEliminarUsuarioSectorCallsEliminarUsuarioSector_WhenCallsEliminarUsuarioSector() {
        // @Given
        // @When
        this.usuarioServicio.eliminarUsuarioSector(DUMMY_ID);
        // @Then
        verify(this.usuarioDao).eliminarUsuarioSector(DUMMY_ID);
    }

    @Test
    public void testInsertarUsuarioCallsInsertarUsuario_WhenCallsInsertarUsuario() {
        // @Given
        // @When
        this.usuarioServicio.insertarUsuario(this.usuario);
        // @Then
        verify(this.usuarioDao).insertarUsuario(this.usuario);
    }

    @Test
    public void testInsertarUsuarioSectorCallsInsertarUsuarioSector_WhenCallsInsertarUsuarioSector() {
        // @Given
        // @When
        this.usuarioServicio.insertarUsuarioSector(this.usuarioSector);
        // @Then
        verify(this.usuarioDao).insertarUsuarioSector(this.usuarioSector);
    }

    @Test
    public void testListaUsuariosCallsListaUsuarios_WhenCallsListaUsuarios() {
        // @Given
        // @When
        this.usuarioServicio.listaUsuarios();
        // @Then
        verify(this.usuarioDao).listaUsuarios();
    }

    @Test
    public void testListaUsuariosSectoresCallsListaUsuariosSectores_WhenCallsListaUsuariosSectores() {
        // @Given
        // @When
        this.usuarioServicio.listaUsuariosSectores();
        // @Then
        verify(this.usuarioDao).listaUsuarioSectores();
    }
}
