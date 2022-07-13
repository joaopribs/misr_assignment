package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.alert.Mailer;
import br.com.jpribeiro.misr_assignment.exceptions.IrrigationException;
import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.exceptions.SensorException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IrrigationSerivceImplTest {

    @InjectMocks
    IrrigationServiceImpl irrigationServiceImpl = new IrrigationServiceImpl();

    @Mock
    PlotOfLandService plotOfLandService;

    @Mock
    private SensorService sensorService;

    @Mock
    private Mailer mailer;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(irrigationServiceImpl, "alertRecipient", "recipient@email.com");
        ReflectionTestUtils.setField(irrigationServiceImpl, "maxNumberOfAttempts", 10);
    }

    @Test
    public void changeStatusOfPlotAlreadyInExpectedStatus() throws NotFoundException, IrrigationException {
        PlotOfLand plotOfLand = new PlotOfLand();
        plotOfLand.setStatus(PlotOfLand.IDLE);

        when(plotOfLandService.get(1)).thenReturn(plotOfLand);

        irrigationServiceImpl.changeStatusOfPlot(1, PlotOfLand.IDLE);

        verify(sensorService, never()).setupService(plotOfLand);
    }

    @Test
    public void changeStatusOfPlotSuccess() throws NotFoundException, IrrigationException, SensorException {
        PlotOfLand plotOfLand = new PlotOfLand();
        plotOfLand.setStatus(PlotOfLand.IDLE);

        when(plotOfLandService.get(1)).thenReturn(plotOfLand);

        doThrow(new SensorException()).doNothing().when(sensorService).sendRequest();

        irrigationServiceImpl.changeStatusOfPlot(1, PlotOfLand.IRRIGATING);

        verify(sensorService, times(1)).setupService(plotOfLand);
    }

    @Test
    public void changeStatusOfPlotFailure() throws NotFoundException, IrrigationException, SensorException {
        PlotOfLand plotOfLand = new PlotOfLand();
        plotOfLand.setStatus(PlotOfLand.IDLE);

        when(plotOfLandService.get(1)).thenReturn(plotOfLand);

        doThrow(new SensorException()).when(sensorService).sendRequest();

        doNothing().when(mailer).sendAlertEmail(plotOfLand, PlotOfLand.IRRIGATING);

        try {
            irrigationServiceImpl.changeStatusOfPlot(1, PlotOfLand.IRRIGATING);
            fail("Should have thrown exception");
        }
        catch (IrrigationException exception) {
            // Expected exception
        }

        verify(sensorService, times(1)).setupService(plotOfLand);
        verify(mailer, times(1)).sendAlertEmail(plotOfLand, PlotOfLand.IRRIGATING);
    }

}
