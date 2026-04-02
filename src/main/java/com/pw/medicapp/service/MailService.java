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
    public void sendAppointmentConfirmation(Integer appointmentId, String toEmail, String patientName, String date, String time, String doctorName, String type) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Conferma Appuntamento - MedApp");

        String emailText = String.format(
                "Gentile %s,\n\n" +
                        "Ti confermiamo che l'appuntamento con id. %d è stato registrato con successo.\n\n" +
                        "DETTAGLI APPUNTAMENTO:\n" +
                        "- Data: %s\n" +
                        "- Orario: %s\n" +
                        "- Tipologia: %s\n" +
                        "- Medico: %s\n\n" +
                        "Ti ringraziamo per aver scelto MedApp.\n\n" +
                        "Cordiali saluti,\n" +
                        "Lo staff di MedApp",
                patientName, appointmentId, date, time, type, doctorName
        );

        message.setText(emailText);
        mailSender.send(message);
    }

    @Async
    public void sendAppointmentCancellation(Integer appointmentId, String toEmail, String patientName, String date, String time, String doctorName) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Annullamento Appuntamento - MedApp");

        String emailText = String.format(
                "Gentile %s,\n\n" +
                        "Ti informiamo che l'appuntamento con id. %d è stato ANNULLATO.\n\n" +
                        "Dettagli appuntamento rimosso:\n" +
                        "- Data: %s\n" +
                        "- Orario: %s\n" +
                        "- Medico: %s\n\n" +
                        "Per fissare un nuovo appuntamento, ti invitiamo a contattare lo studio.\n\n" +
                        "Cordiali saluti,\n" +
                        "Lo staff di MedApp",
                patientName, appointmentId, date, time, doctorName
        );

        message.setText(emailText);
        mailSender.send(message);
    }

    @Async
    public void sendAppointmentUpdate(Integer appointmentId, String toEmail, String patientName, String oldDate, String oldTime, String newDate, String newTime, String doctorName, String type) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Modifica Appuntamento - MedApp");

        // Costruiamo il testo in modo pulito
        String emailText = String.format(
                "Gentile %s,\n\n" +
                        "Ti informiamo che i dettagli dell'appuntamento con id. %d sono stati modificati.\n\n" +
                        "VECCHI DETTAGLI:\n" +
                        "- Data: %s alle ore %s\n\n" +
                        "NUOVI DETTAGLI:\n" +
                        "- Data: %s\n" +
                        "- Orario: %s\n" +
                        "- Tipologia: %s\n" +
                        "- Medico: %s\n\n" + // Rimosso "Dott." manuale perché è già nel parametro
                        "Ti aspettiamo in studio.\n\n" +
                        "Cordiali saluti,\n" +
                        "Lo staff di MedApp",
                patientName, appointmentId, oldDate, oldTime, newDate, newTime, type, doctorName
        );

        message.setText(emailText);
        mailSender.send(message);
    }
}