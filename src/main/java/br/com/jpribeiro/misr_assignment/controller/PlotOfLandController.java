package br.com.jpribeiro.misr_assignment.controller;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.AreaType;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.service.AreaTypeService;
import br.com.jpribeiro.misr_assignment.service.PlotOfLandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PlotOfLandController {

    @Autowired
    private PlotOfLandService plotOfLandService;

    @Autowired
    private AreaTypeService areaTypeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlotOfLandController.class);

    @PostMapping("/area_types/{areaTypeId}/plots_of_land")
    public PlotOfLand create(
            @PathVariable Integer areaTypeId,
            @Valid @RequestBody PlotOfLand plotOfLand) throws NotFoundException {
        LOGGER.info("Creating new plot of land with area type " + areaTypeId);

        AreaType areaType = areaTypeService.get(areaTypeId);
        plotOfLand.setAreaType(areaType);

        return plotOfLandService.save(plotOfLand);
    }

    @GetMapping("/plots_of_land/{id}")
    public PlotOfLand get(@PathVariable Integer id) throws NotFoundException {
        LOGGER.info("Getting plot of land by ID " + id);
        return plotOfLandService.get(id);
    }

    @GetMapping("/plots_of_land")
    public List<PlotOfLand> listAll() {
        LOGGER.info("Listing all plots of land");
        return plotOfLandService.listAll();
    }

    @PutMapping("/plots_of_land/{id}")
    public PlotOfLand edit(
            @PathVariable Integer id,
            @Valid @RequestBody PlotOfLand plotOfLand) throws NotFoundException {
        LOGGER.info("Editing plot of land with ID " + id);
        plotOfLand.setId(id);
        return plotOfLandService.save(plotOfLand);
    }

    @DeleteMapping("/plots_of_land/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        LOGGER.info("Deleting plot of land with ID " + id);
        plotOfLandService.delete(id);
    }

}
