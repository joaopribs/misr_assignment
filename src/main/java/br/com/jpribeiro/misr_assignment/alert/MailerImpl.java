package br.com.jpribeiro.misr_assignment.alert;

import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MailerImpl implements Mailer {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${alert-recipient}")
    private String alertRecipient;

    @Value("${max-number-of-attempts}")
    private Integer maxNumberOfAttempts;

    @Override
    public void sendAlertEmail(PlotOfLand plotOfLand, String newStatus) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(alertRecipient);

        String action = PlotOfLand.IDLE.equals(newStatus) ? "stop" : "start";

        StringBuilder subjectBuilder = new StringBuilder();
        subjectBuilder.append("Alert - Error while trying to ");
        subjectBuilder.append(action);
        subjectBuilder.append(" irrigation of plot ");
        subjectBuilder.append(plotOfLand.getName());

        msg.setSubject(subjectBuilder.toString());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);

        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("At ");
        textBuilder.append(now);
        textBuilder.append(", the system tried to ");
        textBuilder.append(action);
        textBuilder.append(" irrigation of the below plot of land, but failed after ");
        textBuilder.append(maxNumberOfAttempts);
        textBuilder.append(" attempts.");
        textBuilder.append("\n\nPlot name: ");
        textBuilder.append(plotOfLand.getName());
        textBuilder.append("\nAmount of water: ");
        textBuilder.append(plotOfLand.getAmountOfWater());
        textBuilder.append("\nTime slots: ");
        textBuilder.append(plotOfLand.getTimeSlots());

        msg.setText(textBuilder.toString());

        javaMailSender.send(msg);
    }

}
