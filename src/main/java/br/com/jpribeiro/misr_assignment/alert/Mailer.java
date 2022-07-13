package br.com.jpribeiro.misr_assignment.alert;

import br.com.jpribeiro.misr_assignment.model.PlotOfLand;

public interface Mailer {

    void sendAlertEmail(PlotOfLand plotOfLand, String newStatus);

}
