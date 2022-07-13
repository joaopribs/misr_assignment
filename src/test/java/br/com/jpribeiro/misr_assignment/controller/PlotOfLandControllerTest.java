package br.com.jpribeiro.misr_assignment.controller;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.service.AreaTypeService;
import br.com.jpribeiro.misr_assignment.service.PlotOfLandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlotOfLandControllerTest {

    @InjectMocks
    PlotOfLandController plotOfLandController = new PlotOfLandController();

    @Mock
    PlotOfLandService plotOfLandService;

    @Mock
    AreaTypeService areaTypeService;

    @Test
    public void testCreate() throws NotFoundException {
        PlotOfLand plotOfLandToCreate = new PlotOfLand();
        PlotOfLand plotOfLandSaved = new PlotOfLand();

        when(plotOfLandService.save(plotOfLandToCreate)).thenReturn(plotOfLandSaved);

        PlotOfLand plotOfLandThatWasCreated = plotOfLandController.create(1, plotOfLandToCreate);
        assertEquals(plotOfLandThatWasCreated, plotOfLandSaved);
    }

    @Test
    public void testGetById() throws NotFoundException {
        PlotOfLand plotOfLand1 = new PlotOfLand();
        PlotOfLand plotOfLand2 = new PlotOfLand();

        when(plotOfLandService.get(1)).thenReturn(plotOfLand1);
        when(plotOfLandService.get(2)).thenReturn(plotOfLand2);

        PlotOfLand plotOfLandFromController1 = plotOfLandController.get(1);
        assertEquals(plotOfLandFromController1, plotOfLand1);

        PlotOfLand plotOfLandFromController2 = plotOfLandController.get(2);
        assertEquals(plotOfLandFromController2, plotOfLand2);
    }

    @Test
    public void testListAll() {
        List<PlotOfLand> plotOfLandList = new ArrayList<>();
        plotOfLandList.add(new PlotOfLand());
        plotOfLandList.add(new PlotOfLand());

        when(plotOfLandService.listAll()).thenReturn(plotOfLandList);

        List<PlotOfLand> plotOfLandListFromController = plotOfLandController.listAll();
        assertEquals(plotOfLandListFromController, plotOfLandList);
    }

    @Test
    public void testEdit() throws NotFoundException {
        PlotOfLand plotOfLandToEdit = new PlotOfLand();

        when(plotOfLandService.save(plotOfLandToEdit)).thenReturn(plotOfLandToEdit);

        PlotOfLand plotOfLandFromController = plotOfLandController.edit(1, plotOfLandToEdit);
        assertEquals(plotOfLandFromController, plotOfLandToEdit);
    }

    @Test
    public void testDelete() throws NotFoundException {
        doNothing().when(plotOfLandService).delete(1);
        doThrow(new NotFoundException()).when(plotOfLandService).delete(2);

        plotOfLandController.delete(1);

        try {
            plotOfLandController.delete(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException e) {
            // Expected exception
        }
    }
}
