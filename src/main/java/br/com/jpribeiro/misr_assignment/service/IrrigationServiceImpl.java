package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.alert.Mailer;
import br.com.jpribeiro.misr_assignment.exceptions.IrrigationException;
import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.exceptions.SensorException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IrrigationServiceImpl implements IrrigationService {

    @Autowired
    private PlotOfLandService plotOfLandService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private Mailer mailer;

    @Value("${alert-recipient}")
    private String alertRecipient;

    @Value("${max-number-of-attempts}")
    private Integer maxNumberOfAttempts;

    private static final Logger LOGGER = LoggerFactory.getLogger(IrrigationServiceImpl.class);

    @Override
    public void changeStatusOfPlot(Integer plotId, String newStatus) throws NotFoundException, IrrigationException {
        PlotOfLand plotOfLand = plotOfLandService.get(plotId);

        if (plotOfLand.getStatus().equals(newStatus)) {
            return;
        }

        sensorService.setupService(plotOfLand);

        Integer numberOfAttempts = 1;
        while (numberOfAttempts <= maxNumberOfAttempts) {
            LOGGER.info("Sending request to sensor, attempt number " + numberOfAttempts);

            try {
                sensorService.sendRequest();
            }
            catch (SensorException exception) {
                numberOfAttempts++;
                continue;
            }

            LOGGER.info("Request to sensor was successful");

            plotOfLand.setStatus(newStatus);
            plotOfLandService.save(plotOfLand);
            return;
        }

        LOGGER.info("Request to sensor was not successful. Sending alert to recipient " + alertRecipient);
        mailer.sendAlertEmail(plotOfLand, newStatus);
        throw new IrrigationException();
    }

}
