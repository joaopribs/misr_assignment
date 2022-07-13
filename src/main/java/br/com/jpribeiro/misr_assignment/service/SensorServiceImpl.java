package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.SensorException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {

    @Value("${number-of-attempts-until-sensor-is-successful}")
    private Integer numberOfAttemptsUntilSensorIsSuccessful;

    private Integer numberOfAttempts = 0;

    private PlotOfLand plotOfLand;

    @Override
    public void setupService(PlotOfLand plotOfLand) {
        numberOfAttempts = 0;
        this.plotOfLand = plotOfLand;
    }

    @Override
    public void sendRequest() throws SensorException {
        if (numberOfAttempts < numberOfAttemptsUntilSensorIsSuccessful - 1) {
            numberOfAttempts++;
            throw new SensorException();
        }
    }

}
