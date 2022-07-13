package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;

import java.util.List;

public interface PlotOfLandService {

    List<PlotOfLand> listAll();

    PlotOfLand save(PlotOfLand plotOfLand) throws NotFoundException;

    PlotOfLand get(Integer id) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

}
