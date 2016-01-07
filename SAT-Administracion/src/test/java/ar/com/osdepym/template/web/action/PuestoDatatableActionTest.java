package ar.com.osdepym.template.web.action;

import ar.com.osdepym.template.entity.Puesto;
import ar.com.osdepym.template.service.PuestoServicio;

import com.opensymphony.xwork2.Action;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.collect.Maps.newHashMap;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 * Created by Agustin on 12/19/14.
 */
public class PuestoDatatableActionTest {

    private final HashMap<Object,Object> parameters = newHashMap();
    private final PuestoDatatableAction puestoDatatableAction = new PuestoDatatableAction();
    private final PuestoServicio puestoServicio = mock(PuestoServicio.class);
    private final List<Date> usuariosLoqueados = new LinkedList<Date>();

    @Before
    public void setUp() {
        this.puestoDatatableAction.setParameters(this.parameters);
        this.puestoDatatableAction.setPuestoServicio(this.puestoServicio);
        
        this.usuariosLoqueados.add(new Date());
        
        LoginAction.setUsuariosLogueadosTest(this.usuariosLoqueados);
    }

    @Test
    public void testListaUsuarioSectoresReturnsSucess_WhenCallsListaUsuarioSectores() {
        // @Given
        // @When
        String usuario = this.puestoDatatableAction.listaPuestos();
        // @Then
        assertThat(usuario).isEqualTo(Action.SUCCESS);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuario_WhenCallsUsuarioSectoresOperacionesEditar() {
        // @Given
        this.parameters.put("action", new String[]{"edit"});
        this.parameters.put("id", new String[]{"1"});
        this.parameters.put("data[puesto]", new String[]{"2"});
        this.parameters.put("data[habilitado]", new String[]{"3"});
        this.parameters.put("data[ip]", new String[]{"4"});

        when(this.puestoServicio.editarPuesto(any(Puesto.class))).thenReturn(new Puesto());
        ArgumentCaptor<Puesto> argumentCaptorPuesto = ArgumentCaptor.forClass(Puesto.class);
        // @When
        this.puestoDatatableAction.puestosOperaciones();
        // @Then
        verify(this.puestoServicio).editarPuesto(argumentCaptorPuesto.capture());
        assertThat(argumentCaptorPuesto.getValue().getDT_RowId()).isEqualTo(1);
        assertThat(argumentCaptorPuesto.getValue().getPuesto()).isEqualTo(2);
        assertThat(argumentCaptorPuesto.getValue().getHabilitado()).isEqualTo("3");
        assertThat(argumentCaptorPuesto.getValue().getIp()).isEqualTo("4");
    }

    @Test
    public void testPuestoSectoresOperacionesLoadPuesto_WhenCallsPuestoSectoresOperacionesCreate() {
        // @Given
        this.parameters.put("action", new String[]{"create"});
        this.parameters.put("data[puesto]", new String[]{"1"});
        this.parameters.put("data[habilitado]", new String[]{"2"});
        this.parameters.put("data[ip]", new String[]{"3"});

        when(this.puestoServicio.insertarPuesto(any(Puesto.class))).thenReturn(new Puesto());
        ArgumentCaptor<Puesto> argumentCaptorPuesto = ArgumentCaptor.forClass(Puesto.class);
        // @When
        this.puestoDatatableAction.puestosOperaciones();
        // @Then
        verify(this.puestoServicio).insertarPuesto(argumentCaptorPuesto.capture());
        assertThat(argumentCaptorPuesto.getValue().getPuesto()).isEqualTo(1);
        assertThat(argumentCaptorPuesto.getValue().getHabilitado()).isEqualTo("2");
        assertThat(argumentCaptorPuesto.getValue().getIp()).isEqualTo("3");
    }

    @Test
    public void testPuestoSectoresOperacionesLoadPuesto_WhenCallsPuestoSectoresOperacionesRemove() {
        // @Given
        this.parameters.put("action", new String[]{"remove"});
        this.parameters.put("id[]", new String[]{"0"});
        // @When
        this.puestoDatatableAction.puestosOperaciones();
        // @Then
        verify(this.puestoServicio).eliminarPuesto(0);
    }
}
