package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Properties;

@Service
@Transactional
public class ServicioEmailImpl implements ServicioEmail{

    @Value("${spring.mail.username}")
    private String remitente;
    @Value("${spring.mail.password}")
    private String contrasenia;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttls_enable;


    @Override
    public void enviarCodigoDescuento(String email, String codigoDescuento) {

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.port", port);
        propiedades.put("mail.smtp.auth", auth);
        propiedades.put("mail.smtp.starttls.enable", starttls_enable);

        Session sesion = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrasenia);
            }
        });

        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mensaje.setSubject("Código de Descuento para tu próxima compra");
            mensaje.setText("¡Gracias por tu compra! Usa este código de descuento para tu próxima compra: " + codigoDescuento + "\nTenés dos minutos para utilizarlo :)");

            Transport.send(mensaje);
            System.out.println("Correo enviado con éxito a " + email);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}
