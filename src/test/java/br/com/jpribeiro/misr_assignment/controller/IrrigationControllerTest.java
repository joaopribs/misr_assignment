package br.com.jpribeiro.misr_assignment.controller;

import br.com.jpribeiro.misr_assignment.exceptions.IrrigationException;
import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.service.IrrigationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class IrrigationControllerTest {

    @InjectMocks
    IrrigationController irrigationController = new IrrigationController();

    @Mock
    IrrigationService irrigationService;

    @Test
    public void testStartIrrigation() throws NotFoundException, IrrigationException {
        doNothing().when(irrigationService).changeStatusOfPlot(1, PlotOfLand.IRRIGATING);
        doThrow(new NotFoundException()).when(irrigationService).changeStatusOfPlot(2, PlotOfLand.IRRIGATING);

        irrigationController.startIrrigation(1);

        try {
            irrigationController.startIrrigation(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

    @Test
    public void testStopIrrigation() throws NotFoundException, IrrigationException {
        doNothing().when(irrigationService).changeStatusOfPlot(1, PlotOfLand.IDLE);
        doThrow(new NotFoundException()).when(irrigationService).changeStatusOfPlot(2, PlotOfLand.IDLE);

        irrigationController.stopIrrigation(1);

        try {
            irrigationController.stopIrrigation(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }
}
