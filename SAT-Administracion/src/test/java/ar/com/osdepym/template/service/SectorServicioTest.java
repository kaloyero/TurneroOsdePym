package ar.com.osdepym.template.service;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ar.com.osdepym.template.dao.SectorDao;
import ar.com.osdepym.template.entity.Sector;

/**
 * Created by Alejandro on 12/19/14.
 */
public class SectorServicioTest {

    private static final int DUMMY_SECTOR_ID = 0;
    private final SectorServicio sectorServicio = new SectorServicio();
    private final SectorDao sectorDao = mock(SectorDao.class);
    private final Sector sector = new Sector();

    @Before
    public void setUp() {
        this.sectorServicio.setSectorDao(this.sectorDao);
    }

    @Test
    public void testGetSectorDaoReturnsDao_WhenCallsGetSectorDao() {
        // @Given
        // @When
        SectorDao sectorDao = this.sectorServicio.getSectorDao();
        // @Then
        assertThat(sectorDao).isEqualTo(this.sectorDao);
    }

    @Test
    public void testSetSectorDaoReturnsDao_WhenCallsSetSectorDao() {
        // @Given
        // @When
        // @Then
        assertThat(this.sectorServicio.getSectorDao()).isEqualTo(this.sectorDao);
    }

    @Test
    public void testListaSectoresCallsListaSectores_WhenCallsListaSectores() {
        // @Given
        // @When
        this.sectorServicio.listaSectores();
        // @Then
        verify(this.sectorDao).listaSectores();
    }

    @Test
    public void testInsertarSectorCallIsnsertarSector_WhenCallsInsertarSector() {
        // @Given
        // @When
        this.sectorServicio.insertarSector(this.sector);
        // @Then
        verify(this.sectorDao).insertarSector(this.sector);
    }

    @Test
    public void testEditarSectorCallsEditarSector_WhenCallsEditarSector() {
        // @Given
        // @When
        this.sectorServicio.editarSector(this.sector);
        // @Then
        verify(this.sectorDao).editarSector(this.sector);
    }

    @Test
    public void testEliminarSectorCallsliminarSector_WhenCallsEliminarSector() {
        // @Given
        // @When
        this.sectorServicio.eliminarSector(DUMMY_SECTOR_ID);
        // @Then
        verify(this.sectorDao).eliminarSector(DUMMY_SECTOR_ID);
    }
}
