package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.SensorException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.fail;

public class SensorServiceImplTest {

    SensorServiceImpl sensorServiceImpl = new SensorServiceImpl();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(sensorServiceImpl, "numberOfAttemptsUntilSensorIsSuccessful", 2);
        sensorServiceImpl.setupService(new PlotOfLand());
    }

    @Test
    public void testSendRequest() throws SensorException {
        try {
            sensorServiceImpl.sendRequest();
            fail("Should have thrown exception");
        }
        catch (SensorException exception) {
            // Expected exception
        }

        sensorServiceImpl.sendRequest();
    }

}
