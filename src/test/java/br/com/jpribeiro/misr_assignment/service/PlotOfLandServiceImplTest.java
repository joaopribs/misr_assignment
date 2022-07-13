package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.repository.PlotOfLandRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlotOfLandServiceImplTest {

    @InjectMocks
    PlotOfLandServiceImpl plotOfLandServiceImpl = new PlotOfLandServiceImpl();

    @Mock
    PlotOfLandRepository plotOfLandRepository;

    @Test
    public void testListAll() {
        List<PlotOfLand> plotOfLandList = new ArrayList<>();
        plotOfLandList.add(new PlotOfLand());
        plotOfLandList.add(new PlotOfLand());

        when(plotOfLandRepository.findAll()).thenReturn(plotOfLandList);

        List<PlotOfLand> plotOfLandListFromService = plotOfLandServiceImpl.listAll();
        assertEquals(plotOfLandListFromService, plotOfLandList);
    }

    @Test
    public void testSave() throws NotFoundException {
        PlotOfLand plotOfLand1 = new PlotOfLand();
        plotOfLand1.setId(1);

        PlotOfLand plotOfLand2 = new PlotOfLand();
        plotOfLand2.setId(2);

        when(plotOfLandRepository.findPlotOfLand(1)).thenReturn(plotOfLand1);
        when(plotOfLandRepository.findPlotOfLand(2)).thenReturn(null);

        when(plotOfLandRepository.save(plotOfLand1)).thenReturn(plotOfLand1);

        PlotOfLand plotOfLand1FromService = plotOfLandServiceImpl.save(plotOfLand1);
        assertEquals(plotOfLand1FromService, plotOfLand1);

        try {
            plotOfLandServiceImpl.save(plotOfLand2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

    @Test
    public void testGet() throws NotFoundException {
        PlotOfLand plotOfLand1 = new PlotOfLand();
        plotOfLand1.setId(1);

        when(plotOfLandRepository.findPlotOfLand(1)).thenReturn(plotOfLand1);
        when(plotOfLandRepository.findPlotOfLand(2)).thenReturn(null);

        PlotOfLand plotOfLand1FromService = plotOfLandServiceImpl.get(1);
        assertEquals(plotOfLand1FromService, plotOfLand1);

        try {
            plotOfLandServiceImpl.get(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

    @Test
    public void testDelete() throws NotFoundException {
        PlotOfLand plotOfLand1 = new PlotOfLand();
        plotOfLand1.setId(1);

        when(plotOfLandRepository.findPlotOfLand(1)).thenReturn(plotOfLand1);
        when(plotOfLandRepository.findPlotOfLand(2)).thenReturn(null);

        doNothing().when(plotOfLandRepository).deleteById(1);

        plotOfLandServiceImpl.delete(1);

        try {
            plotOfLandServiceImpl.delete(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

}
