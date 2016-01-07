package ar.com.osdepym.template.web.action;

import ar.com.osdepym.template.entity.Sucursal;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.service.SucursalServicio;
import com.opensymphony.xwork2.Action;
import org.junit.Before;
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
public class SucursalDatatableActionTest {

    private final HashMap<Object,Object> parameters = newHashMap();
    private final SucursalDatatableAction sucursalDatatableAction = new SucursalDatatableAction();
    private final SucursalServicio sucursalServicio = mock(SucursalServicio.class);

    @Before
    public void setUp() {
        this.sucursalDatatableAction.setParameters(this.parameters);
    }

    @Test
    public void testListaUsuarioSectoresReturnsSucess_WhenCallsListaUsuarioSectores() {
        // @Given
        this.sucursalDatatableAction.setSucursalServicio(this.sucursalServicio);
        // @When
        String usuario = this.sucursalDatatableAction.listaSucursales();
        // @Then
        assertThat(usuario).isEqualTo(Action.SUCCESS);
    }

    @Test
    public void testUsuarioSectoresOperacionesLoadUsuario_WhenCallsUsuarioSectoresOperacionesEditar() {
        // @Given
        this.parameters.put("action", new String[]{"edit"});
        this.parameters.put("id", new String[]{"1"});
        this.parameters.put("data[codSucursal]", new String[]{"2"});
        this.parameters.put("data[nombreSucursal]", new String[]{"3"});
        this.parameters.put("data[ip]", new String[]{"4"});
        this.parameters.put("data[codigoTotem]", new String[]{"5"});

        this.sucursalDatatableAction.setSucursalServicio(this.sucursalServicio);
        this.sucursalDatatableAction.setParameters(parameters);
        when(this.sucursalServicio.editarSucursal(any(Sucursal.class))).thenReturn(new Sucursal());
        ArgumentCaptor<Sucursal> argumentCaptorUsuario = ArgumentCaptor.forClass(Sucursal.class);
        // @When
        this.sucursalDatatableAction.sucursalesOperaciones();
        // @Then
        verify(this.sucursalServicio).editarSucursal(argumentCaptorUsuario.capture());
        assertThat(argumentCaptorUsuario.getValue().getDT_RowId()).isEqualTo(1);
        assertThat(argumentCaptorUsuario.getValue().getCodSucursal()).isEqualTo("2");
        assertThat(argumentCaptorUsuario.getValue().getNombreSucursal()).isEqualTo("3");
        assertThat(argumentCaptorUsuario.getValue().getIp()).isEqualTo("4");
        assertThat(argumentCaptorUsuario.getValue().getCodigoTotem()).isEqualTo(5);
    }


}
