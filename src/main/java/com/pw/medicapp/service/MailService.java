package com.pw.medicapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendAppointmentConfirmation(String toEmail, String patientName, String date, String time, String doctorName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Conferma Appuntamento - MedApp");
        message.setText("Gentile " + patientName + ",\n\n" +
                "Ti confermiamo che il tuo appuntamento è stato registrato con successo.\n\n" +
                "Dettagli:\n" +
                "- Data: " + date + "\n" +
                "- Data: " + date + "\n" +
                "- Orario: " + time + "\n" +
                "- Medico: Dott. " + doctorName + "\n\n" +
                "Cordiali saluti,\nLo staff di MedApp");

        mailSender.send(message);
    }

    @Async
    public void sendAppointmentCancellation(String toEmail, String patientName, String date, String time, String doctorName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Annullamento Appuntamento - MedApp");
        message.setText("Gentile " + patientName + ",\n\n" +
                "Ti informiamo che il tuo appuntamento è stato annullato.\n\n" +
                "Dettagli dell'appuntamento rimosso:\n" +
                "- Data: " + date + "\n" +
                "- Orario: " + time + "\n" +
                "- Medico: Dott. " + doctorName + "\n\n" +
                "Puoi prenotare un nuovo appuntamento chiamando lo studio.\n" +
                "Ci scusiamo per l'inconveniente.\n\n" +
                "Cordiali saluti,\nLo staff di MedApp");

        mailSender.send(message);
    }

    @Async
    public void sendAppointmentUpdate(String toEmail, String patientName, String oldDate, String oldTime, String newDate, String newTime, String doctorName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Modifica Appuntamento - MedApp");
        message.setText("Gentile " + patientName + ",\n\n" +
                "Ti informiamo che i dettagli del tuo appuntamento sono stati modificati.\n\n" +
                "Vecchi dettagli:\n" +
                "- Data: " + oldDate + " alle " + oldTime + "\n\n" +
                "Nuovi dettagli:\n" +
                "- Data: " + newDate + "\n" +
                "- Orario: " + newTime + "\n" +
                "- Medico: Dott. " + doctorName + "\n\n" +
                "Ti aspettiamo in studio.\n\n" +
                "Cordiali saluti,\nLo staff di MedApp");

        mailSender.send(message);
    }
}