package ar.com.osdepym.template.web.action;

import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.service.SectorDao;
import ar.com.osdepym.template.service.TurnoDAO;
import ar.com.osdepym.template.service.UsuarioDao;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by Alejandro on 12/19/14.
 */
public class AtendidoActionTest {

    private AtendidoAction atendidoAction;
    private final HashMap<String,Object> session = new HashMap<String, Object>();
    private final SectorDao sectorDAO = mock(SectorDao.class);
    private final UsuarioDao usuarioDao = mock(UsuarioDao.class);
    private final TurnoDAO turnoDao = mock(TurnoDAO.class);

    @Before
    public void setUp() {
    	ActionContext.setContext(mock(ActionContext.class));
    	
    	this.atendidoAction = new AtendidoAction();
        this.atendidoAction.setSession(this.session);
        this.atendidoAction.setSectorDAO(this.sectorDAO);
        this.atendidoAction.setUsuarioDao(this.usuarioDao);
        this.atendidoAction.setTurnoDAO(this.turnoDao);
    }

    @Test
    public void test() {
        // @Given
        List<Sector> sectors = new LinkedList<Sector>();
        this.session.put("sector", "theSectorName");
        this.session.put("nomUser", "jhonn");
        Sector sector = new Sector();
        sector.setCodSector("theSectorCode");
        when(this.sectorDAO.obtenerSectorPorNombre("theSectorName")).thenReturn(sector);
        when(this.usuarioDao.recuperarSectoresUsuario(anyInt())).thenReturn(sectors);
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        when(this.usuarioDao.obtenerUsuario(anyString())).thenReturn(usuario);
        // @When
        String value = this.atendidoAction.execute();
        // @Then
        assertThat(value).isEqualTo("error");
    }
    
    @Test
    public void test2() {
        // @Given
    	Sector sector = new Sector();
        sector.setCodSector("theSectorCode");
        sector.setNomSector("mostrador");
        List<Sector> sectors = new LinkedList<Sector>();
        sectors.add(sector);
        this.session.put("sector", "mostrador");
        this.session.put("nomUser", "jhonn");
        when(this.sectorDAO.obtenerSectorPorNombre(anyString())).thenReturn(sector);
        when(this.usuarioDao.recuperarSectoresUsuario(anyInt())).thenReturn(sectors);
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        when(this.usuarioDao.obtenerUsuario(anyString())).thenReturn(usuario);
        // @When
        String value = this.atendidoAction.execute();
        // @Then
        assertThat(sector.getCodSector()).isEqualTo("");
    }
    
    @Test
    public void test3() {
        // @Given
    	Sector sector = new Sector();
        sector.setCodSector("theSectorCode");
        sector.setNomSector("entrada");
        sector.setSector("1");
        List<Sector> sectors = new LinkedList<Sector>();
        sectors.add(sector);
        this.session.put("sector", "mostrador");
        this.session.put("nomUser", "jhonn");
        when(this.sectorDAO.obtenerSectorPorNombre(anyString())).thenReturn(sector);
        when(this.usuarioDao.recuperarSectoresUsuario(anyInt())).thenReturn(sectors);
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        when(this.usuarioDao.obtenerUsuario(anyString())).thenReturn(usuario);
        when(this.turnoDao.numerosEnEspera(anyInt())).thenReturn(45);
        // @When
        String value = this.atendidoAction.execute();
        // @Then
        assertThat(sector.getNrosEspera()).isEqualTo("45");
    }
}
