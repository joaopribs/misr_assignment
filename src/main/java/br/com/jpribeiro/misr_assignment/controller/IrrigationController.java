package br.com.jpribeiro.misr_assignment.controller;

import br.com.jpribeiro.misr_assignment.exceptions.IrrigationException;
import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.service.IrrigationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/irrigation")
public class IrrigationController {

    @Autowired
    private IrrigationService irrigationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(IrrigationController.class);

    @PostMapping("{plotId}/start")
    public void startIrrigation(@PathVariable Integer plotId) throws NotFoundException, IrrigationException {
        LOGGER.info("Starting irrigation on plot ID " + plotId);
        irrigationService.changeStatusOfPlot(plotId, PlotOfLand.IRRIGATING);
    }

    @PostMapping("{plotId}/stop")
    public void stopIrrigation(@PathVariable Integer plotId) throws NotFoundException, IrrigationException {
        LOGGER.info("Stopping irrigation on plot ID " + plotId);
        irrigationService.changeStatusOfPlot(plotId, PlotOfLand.IDLE);
    }

}
