package ar.com.osdepym.template.web.action;

import ar.com.osdepym.template.entity.UsuarioSector;
import ar.com.osdepym.template.service.UsuarioServicio;
import com.opensymphony.xwork2.Action;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;

import static com.google.common.collect.Maps.newHashMap;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Agustin on 12/19/14.
 */
public class UsuarioSectoresDatatableActionTest {

    private final UsuarioSectoresDatatableAction usuarioSectoresDatatableAction = new UsuarioSectoresDatatableAction();
    private final UsuarioServicio usuarioServicio = mock(UsuarioServicio.class);
    private final HashMap<Object,Object> parameters = newHashMap();

    @Test
    public void testListaUsuarioSectoresReturnsSucess_WhenCallsListaUsuarioSectores() {
        // @Given
        this.usuarioSectoresDatatableAction.setUsuarioServicio(this.usuarioServicio);
        // @When
        String usuarioSectores = this.usuarioSectoresDatatableAction.listaUsuarioSectores();
        // @Then
        assertThat(usuarioSectores).isEqualTo(Action.SUCCESS);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuarioSector_WhenCallsUsuarioSectoresOperacionesEditar() {
        // @Given
        this.parameters.put("action", new String[]{"edit"});
        this.parameters.put("data[sectorId]", new String[]{"1"});
        this.parameters.put("data[usuarioId]", new String[]{"2"});
        this.parameters.put("id", new String[]{"3"});

        this.usuarioSectoresDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioSectoresDatatableAction.setParameters(parameters);
        ArgumentCaptor<UsuarioSector> argumentCaptorUsuarioSector = ArgumentCaptor.forClass(UsuarioSector.class);
        // @When
        this.usuarioSectoresDatatableAction.usuarioSectoresOperaciones();
        // @Then
        verify(this.usuarioServicio).editarUsuarioSector(argumentCaptorUsuarioSector.capture());
        assertThat(argumentCaptorUsuarioSector.getValue().getSectorId()).isEqualTo(1);
        assertThat(argumentCaptorUsuarioSector.getValue().getUsuarioId()).isEqualTo(2);
        assertThat(argumentCaptorUsuarioSector.getValue().getDT_RowId()).isEqualTo(3);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadRow_WhenCallsUsuarioSectoresOperacionesEditar() {
        // @Given
        this.parameters.put("action", new String[]{"edit"});
        this.parameters.put("data[sectorId]", new String[]{"1"});
        this.parameters.put("data[usuarioId]", new String[]{"2"});
        this.parameters.put("id", new String[]{"3"});

        UsuarioSector expectedUsuarioSector = new UsuarioSector();
        when(this.usuarioServicio.editarUsuarioSector(any(UsuarioSector.class))).thenReturn(expectedUsuarioSector);
        this.usuarioSectoresDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioSectoresDatatableAction.setParameters(parameters);
        // @When
        this.usuarioSectoresDatatableAction.usuarioSectoresOperaciones();
        // @Then
        assertThat(this.usuarioSectoresDatatableAction.getRow()).isEqualTo(expectedUsuarioSector);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuarioSector_WhenCallsUsuarioSectoresOperacionesCreate() {
        // @Given
        this.parameters.put("action", new String[]{"create"});
        this.parameters.put("data[sectorId]", new String[]{"1"});
        this.parameters.put("data[usuarioId]", new String[]{"2"});

        this.usuarioSectoresDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioSectoresDatatableAction.setParameters(parameters);
        ArgumentCaptor<UsuarioSector> argumentCaptorUsuarioSector = ArgumentCaptor.forClass(UsuarioSector.class);
        // @When
        this.usuarioSectoresDatatableAction.usuarioSectoresOperaciones();
        // @Then
        verify(this.usuarioServicio).insertarUsuarioSector(argumentCaptorUsuarioSector.capture());
        assertThat(argumentCaptorUsuarioSector.getValue().getSectorId()).isEqualTo(1);
        assertThat(argumentCaptorUsuarioSector.getValue().getUsuarioId()).isEqualTo(2);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadRow_WhenCallsUsuarioSectoresOperacionesCreate() {
        // @Given
        this.parameters.put("action", new String[]{"create"});
        this.parameters.put("data[sectorId]", new String[]{"1"});
        this.parameters.put("data[usuarioId]", new String[]{"2"});

        UsuarioSector expectedUsuarioSector = new UsuarioSector();
        when(this.usuarioServicio.insertarUsuarioSector(any(UsuarioSector.class))).thenReturn(expectedUsuarioSector);
        this.usuarioSectoresDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioSectoresDatatableAction.setParameters(parameters);
        // @When
        this.usuarioSectoresDatatableAction.usuarioSectoresOperaciones();
        // @Then
        assertThat(this.usuarioSectoresDatatableAction.getRow()).isEqualTo(expectedUsuarioSector);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuarioSector_WhenCallsUsuarioSectoresOperacionesRemove() {
        // @Given
        this.parameters.put("action", new String[]{"remove"});
        this.parameters.put("id[]", new String[]{"1"});

        this.usuarioSectoresDatatableAction.setUsuarioServicio(this.usuarioServicio);
        this.usuarioSectoresDatatableAction.setParameters(parameters);
        // @When
        this.usuarioSectoresDatatableAction.usuarioSectoresOperaciones();
        // @Then
        verify(this.usuarioServicio).eliminarUsuario(1);
    }
}
