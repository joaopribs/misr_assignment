package br.com.jpribeiro.misr_assignment.controller;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.AreaType;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.service.AreaTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/area_types")
public class AreaTypeController {

    @Autowired
    private AreaTypeService areaTypeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaTypeController.class);

    @PostMapping
    public AreaType create(@Valid @RequestBody AreaType areaType) throws NotFoundException {
        LOGGER.info("Creating new area type");
        return areaTypeService.save(areaType);
    }

    @GetMapping("{id}")
    public AreaType get(@PathVariable Integer id) throws NotFoundException {
        LOGGER.info("Getting area type by ID " + id);
        return areaTypeService.get(id);
    }

    @GetMapping
    public List<AreaType> listAll() {
        LOGGER.info("Listing all area types");
        return areaTypeService.listAll();
    }

    @GetMapping("{id}/plots_of_land")
    public List<PlotOfLand> listAllPlotsOfLand(@PathVariable Integer id) throws NotFoundException {
        LOGGER.info("Listing all Plots of Land for Area Type");
        return areaTypeService.listAllPlotsOfLand(id);
    }

    @PutMapping("{id}")
    public AreaType edit(
            @PathVariable Integer id,
            @Valid @RequestBody AreaType areaType) throws NotFoundException {
        LOGGER.info("Editing area type with ID " + id);
        areaType.setId(id);
        return areaTypeService.save(areaType);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        LOGGER.info("Deleting area type with ID " + id);
        areaTypeService.delete(id);
    }

    @GetMapping("{id}/predict_ratio")
    public double predictRatio(@PathVariable Integer id) throws NotFoundException {
        LOGGER.info("Calculating ratio for area type with ID " + id);
        return areaTypeService.predictRatio(id);
    }

}
