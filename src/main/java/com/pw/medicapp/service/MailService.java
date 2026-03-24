package com.pw.medicapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

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
}