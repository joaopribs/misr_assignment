package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.IrrigationException;
import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;

public interface IrrigationService {

    void changeStatusOfPlot(Integer plotId, String newStatus) throws NotFoundException, IrrigationException;

}
