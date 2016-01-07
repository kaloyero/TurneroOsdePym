package ar.com.osdepym.template.web.action;

import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.service.UsuarioServicio;
import com.opensymphony.xwork2.Action;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;

import static com.google.common.collect.Maps.newHashMap;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Agustin on 12/19/14.
 */
public class UsuarioDatatableActionTest {

    private final UsuarioDatatableAction usuarioDatatableAction = new UsuarioDatatableAction();
    private final UsuarioServicio usuarioServicio = mock(UsuarioServicio.class);
    private final HashMap<Object,Object> parameters = newHashMap();

    @Test
    public void testListaUsuarioSectoresReturnsSucess_WhenCallsListaUsuarioSectores() {
        // @Given
        this.usuarioDatatableAction.setUsuarioServicio(this.usuarioServicio);
        // @When
        String usuario = this.usuarioDatatableAction.listaUsuarios();
        // @Then
        assertThat(usuario).isEqualTo(Action.SUCCESS);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuario_WhenCallsUsuarioSectoresOperacionesEditar() {
        // @Given
        this.parameters.put("action", new String[]{"edit"});
        this.parameters.put("data[nombreUsuario]", new String[]{"1"});
        this.parameters.put("data[password]", new String[]{"2"});
        this.parameters.put("data[habilitado]", new String[]{"3"});
        this.parameters.put("id", new String[]{"4"});
        this.parameters.put("data[perfilId]", new String[]{"5"});

        this.usuarioDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioDatatableAction.setParameters(parameters);
        when(this.usuarioServicio.editarUsuario(any(Usuario.class))).thenReturn(new Usuario());
        ArgumentCaptor<Usuario> argumentCaptorUsuario = ArgumentCaptor.forClass(Usuario.class);
        // @When
        this.usuarioDatatableAction.usuariosOperaciones();
        // @Then
        verify(this.usuarioServicio).editarUsuario(argumentCaptorUsuario.capture());
        assertThat(argumentCaptorUsuario.getValue().getNombreUsuario()).isEqualTo("1");
        assertThat(argumentCaptorUsuario.getValue().getPassword()).isEqualTo("2");
        assertThat(argumentCaptorUsuario.getValue().getHabilitado()).isEqualTo("3");
        assertThat(argumentCaptorUsuario.getValue().getDT_RowId()).isEqualTo(4);
        assertThat(argumentCaptorUsuario.getValue().getPerfilId()).isEqualTo(5);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuario_WhenCallsUsuarioSectoresOperacionesCreate() {
        // @Given
        this.parameters.put("action", new String[]{"create"});
        this.parameters.put("data[nombreUsuario]", new String[]{"1"});
        this.parameters.put("data[password]", new String[]{"2"});
        this.parameters.put("data[habilitado]", new String[]{"3"});
        this.parameters.put("data[perfilId]", new String[]{"4"});

        this.usuarioDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioDatatableAction.setParameters(parameters);
        when(this.usuarioServicio.insertarUsuario(any(Usuario.class))).thenReturn(new Usuario());
        ArgumentCaptor<Usuario> argumentCaptorUsuario = ArgumentCaptor.forClass(Usuario.class);
        // @When
        this.usuarioDatatableAction.usuariosOperaciones();
        // @Then
        verify(this.usuarioServicio).insertarUsuario(argumentCaptorUsuario.capture());
        assertThat(argumentCaptorUsuario.getValue().getNombreUsuario()).isEqualTo("1");
        assertThat(argumentCaptorUsuario.getValue().getPassword()).isEqualTo("2");
        assertThat(argumentCaptorUsuario.getValue().getHabilitado()).isEqualTo("3");
        assertThat(argumentCaptorUsuario.getValue().getPerfilId()).isEqualTo(4);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuario_WhenCallsUsuarioSectoresOperacionesRemove() {
        // @Given
        this.parameters.put("action", new String[]{"remove"});
        this.parameters.put("id[]", new String[]{"0"});

        this.usuarioDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioDatatableAction.setParameters(parameters);
        // @When
        this.usuarioDatatableAction.usuariosOperaciones();
        // @Then
        verify(this.usuarioServicio).eliminarUsuario(0);
    }
}
