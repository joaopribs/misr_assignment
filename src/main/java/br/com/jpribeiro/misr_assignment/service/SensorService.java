package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.SensorException;
import br.com.jpribeiro.misr_assignment.model.PlotOfLand;

public interface SensorService {

    void setupService(PlotOfLand plotOfLand);

    void sendRequest() throws SensorException;

}
