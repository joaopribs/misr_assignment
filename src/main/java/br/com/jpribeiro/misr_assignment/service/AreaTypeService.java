package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.AreaType;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;

import java.util.List;

public interface AreaTypeService {

    List<AreaType> listAll();

    List<PlotOfLand> listAllPlotsOfLand(Integer id) throws NotFoundException;

    AreaType save(AreaType areaType) throws NotFoundException;

    AreaType get(Integer id) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

    double predictRatio(Integer id) throws NotFoundException;

}
