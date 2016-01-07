package ar.com.osdepym.template.web.action;

import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.service.SectorServicio;
import com.opensymphony.xwork2.Action;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;

import static com.google.common.collect.Maps.newHashMap;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 * Created by Agustin on 12/19/14.
 */
public class SectorDatatableActionTest {
    private final HashMap<Object,Object> parameters = newHashMap();
    private final SectorDatatableAction sectorDatatableAction = new SectorDatatableAction();
    private final SectorServicio sectorServicio = mock(SectorServicio.class);

    @Before
    public void setUp() {
        this.sectorDatatableAction.setParameters(this.parameters);
    }

    @Test
    public void testListaUsuarioSectoresReturnsSucess_WhenCallsListaUsuarioSectores() {
        // @Given
        this.sectorDatatableAction.setSectorServicio(this.sectorServicio);
        // @When
        String usuario = this.sectorDatatableAction.listaSectores();
        // @Then
        assertThat(usuario).isEqualTo(Action.SUCCESS);
    }

    @Test
    public void testSectoresOperacionesLoadUsuario_WhenCallsSectoresOperacionesEditar() {
        // @Given
        this.parameters.put("action", new String[]{"edit"});
        this.parameters.put("data[codigoSector]", new String[]{"1"});
        this.parameters.put("id", new String[]{"2"});
        this.parameters.put("data[habilitado]", new String[]{"3"});
        this.parameters.put("data[nombreSector]", new String[]{"4"});

        this.sectorDatatableAction.setSectorServicio(this.sectorServicio);
        this.sectorDatatableAction.setParameters(parameters);
        when(this.sectorServicio.editarSector(any(Sector.class))).thenReturn(new Sector());
        ArgumentCaptor<Sector> argumentCaptorUsuario = ArgumentCaptor.forClass(Sector.class);
        // @When
        this.sectorDatatableAction.sectoresOperaciones();
        // @Then
        verify(this.sectorServicio).editarSector(argumentCaptorUsuario.capture());
        assertThat(argumentCaptorUsuario.getValue().getCodigoSector()).isEqualTo("1");
        assertThat(argumentCaptorUsuario.getValue().getDT_RowId()).isEqualTo(2);
        assertThat(argumentCaptorUsuario.getValue().getHabilitado()).isEqualTo("3");
        assertThat(argumentCaptorUsuario.getValue().getNombreSector()).isEqualTo("4");
    }

    @Test
    public void testSectoresOperacionesLoadUsuario_WhenCallsSectoresOperacionesCreate() {
        // @Given
        this.parameters.put("action", new String[]{"create"});
        this.parameters.put("data[codigoSector]", new String[]{"1"});
        this.parameters.put("data[habilitado]", new String[]{"2"});
        this.parameters.put("data[nombreSector]", new String[]{"3"});

        this.sectorDatatableAction.setSectorServicio(this.sectorServicio);
        this.sectorDatatableAction.setParameters(parameters);
        when(this.sectorServicio.insertarSector(any(Sector.class))).thenReturn(new Sector());
        ArgumentCaptor<Sector> argumentCaptorUsuario = ArgumentCaptor.forClass(Sector.class);
        // @When
        this.sectorDatatableAction.sectoresOperaciones();
        // @Then
        verify(this.sectorServicio).insertarSector(argumentCaptorUsuario.capture());
        assertThat(argumentCaptorUsuario.getValue().getCodigoSector()).isEqualTo("1");
        assertThat(argumentCaptorUsuario.getValue().getHabilitado()).isEqualTo("2");
        assertThat(argumentCaptorUsuario.getValue().getNombreSector()).isEqualTo("3");
    }

    @Test
    public void testSectoresOperacionesLoadUsuario_WhenCallsSectoresOperacionesRemove() {
        // @Given
        this.parameters.put("action", new String[]{"remove"});
        this.parameters.put("id[]", new String[]{"1"});

        this.sectorDatatableAction.setSectorServicio(this.sectorServicio);
        this.sectorDatatableAction.setParameters(parameters);
        // @When
        this.sectorDatatableAction.sectoresOperaciones();
        // @Then
        verify(this.sectorServicio).eliminarSector(1);
    }
}
