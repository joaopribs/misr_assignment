package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.AreaType;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import br.com.jpribeiro.misr_assignment.repository.AreaTypeRepository;
import br.com.jpribeiro.misr_assignment.repository.PlotOfLandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AreaTypeServiceImpl implements AreaTypeService {

    @Autowired
    private AreaTypeRepository areaTypeRepository;

    @Autowired
    private PlotOfLandRepository plotOfLandRepository;

    @Override
    public List<AreaType> listAll() {
        return areaTypeRepository.findAll();
    }

    @Override
    public List<PlotOfLand> listAllPlotsOfLand(Integer id) throws NotFoundException {
        checkOptionalById(id);
        return plotOfLandRepository.findPlotsOfLandByAreaType(id);
    }

    @Override
    public AreaType save(AreaType areaType) throws NotFoundException {
        if (areaType.getId() != null) {
            checkOptionalById(areaType.getId());
        }

        return areaTypeRepository.save(areaType);
    }

    @Override
    public AreaType get(Integer id) throws NotFoundException {
        Optional<AreaType> optional = checkOptionalById(id);
        return optional.get();
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        checkOptionalById(id);
        areaTypeRepository.deleteById(id);
    }

    @Override
    public double predictRatio(Integer id) throws NotFoundException {
        checkOptionalById(id);
        return plotOfLandRepository.predictRatio(id);
    }

    private Optional<AreaType> checkOptionalById(Integer id) throws NotFoundException {
        Optional<AreaType> optional = areaTypeRepository.findById(id);

        if (!optional.isPresent()) {
            throw new NotFoundException();
        }

        return optional;
    }

}
