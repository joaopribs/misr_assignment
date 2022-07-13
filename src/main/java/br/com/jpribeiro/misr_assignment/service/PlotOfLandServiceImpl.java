package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.repository.PlotOfLandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlotOfLandServiceImpl implements PlotOfLandService {

    @Autowired
    private PlotOfLandRepository plotOfLandRepository;

    @Override
    public List<PlotOfLand> listAll() {
        return plotOfLandRepository.findAll();
    }

    @Override
    public PlotOfLand save(PlotOfLand plotOfLand) throws NotFoundException {
        if (plotOfLand.getId() != null) {
            getPlotOfLand(plotOfLand.getId());
        }

        return plotOfLandRepository.save(plotOfLand);
    }

    @Override
    public PlotOfLand get(Integer id) throws NotFoundException {
        return getPlotOfLand(id);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        getPlotOfLand(id);
        plotOfLandRepository.deleteById(id);
    }

    private PlotOfLand getPlotOfLand(Integer id) throws NotFoundException {
        PlotOfLand plotOfLand = plotOfLandRepository.findPlotOfLand(id);

        if (plotOfLand == null) {
            throw new NotFoundException();
        }

        return plotOfLand;
    }

}
