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
import java.util.List;
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


    private Session configurarSesionCorreo() {
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.port", port);
        propiedades.put("mail.smtp.auth", auth);
        propiedades.put("mail.smtp.starttls.enable", starttls_enable);

        return Session.getInstance(propiedades, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrasenia);
            }
        });
    }

    private void enviarCorreo(String email, String asunto, String mensajeTexto) {
        Session sesion = configurarSesionCorreo();

        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mensaje.setSubject(asunto);
            mensaje.setText(mensajeTexto);

            Transport.send(mensaje);
            System.out.println("Correo enviado con éxito a " + email);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    private void enviarCorreoHTML(String email, String asunto, String contenidoHTML) {
        Session sesion = configurarSesionCorreo();

        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mensaje.setSubject(asunto);
            mensaje.setContent(contenidoHTML, "text/html; charset=utf-8");

            Transport.send(mensaje);
            System.out.println("Correo enviado con éxito a " + email);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarEntradasConQR(String emailUsuario, List<EntradaUsuario> entradas) {
        String asunto = "Tenemos tus entradas :)";
        StringBuilder mensajeHTML = new StringBuilder();

        mensajeHTML.append("<div style='font-family: Arial, sans-serif; color: #333; line-height: 1.5;'>");
        mensajeHTML.append("<h1 style='color: #4CAF50;'>¡Gracias por tu compra!</h1>");
        mensajeHTML.append("<p style='font-size: 16px;'>Aquí están tus entradas:</p>");

        for (EntradaUsuario entrada : entradas) {
            mensajeHTML.append("<div style='border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; border-radius: 10px;'>")
                    .append("<div style='display: inline-flex; align-items: center; gap: 10px;'>")
                    .append("<img src=\"")
                    .append(entrada.getEntrada().getEvento().getImagenUrl()) // URL de la foto del evento
                    .append("\" alt=\"Foto del evento\" style='width: 50px; height: 50px; border-radius: 5px; margin-right: 15px;'/>")
                    .append("<h2 style='color: #333; margin: 0;'>Entrada ")
                    .append(entrada.getEntrada().getNombre())
                    .append(" - ")
                    .append(entrada.getEntrada().getEvento().getNombre())
                    .append("</h2>")
                    .append("</div>")
                    .append("<p><strong>Fecha:</strong> ")
                    .append(entrada.getEntrada().getEvento().getFecha())
                    .append("</p>")
                    .append("<p><strong>Lugar:</strong> ")
                    .append(entrada.getEntrada().getEvento().getLugar())
                    .append("</p>")
                    .append("<p><strong>Código QR:</strong></p>")
                    .append("<div style='text-align: center;'>")
                    .append("<img src=\"data:image/png;base64,")
                    .append(entrada.getQrCode())
                    .append("\" alt=\"Código QR\" style='max-width: 150px; height: auto;'/>")
                    .append("</div>")
                    .append("</div>");
        }

        mensajeHTML.append("<p style='font-size: 16px;'>¡Disfruta del evento!</p>");
        mensajeHTML.append("</div>");

        enviarCorreoHTML(emailUsuario, asunto, mensajeHTML.toString());
    }

    @Override
    public void enviarCodigoDescuento(String email, String codigoDescuento) {
        String asunto = "Código de Descuento para tu próxima compra";
        String mensajeTexto = "¡Gracias por tu compra! Usa este código de descuento para tu próxima compra: " + codigoDescuento + "\nTenés dos minutos para utilizarlo :)";
        enviarCorreo(email, asunto, mensajeTexto);
    }

    @Override
    public void enviarContraseniaAUsuarios(String email, String contrasenia) {
        String asunto = "¡Bienvenido a MokitoPass!";
        String mensajeTexto = "Hola,\n\nTu contraseña es: " + contrasenia + "\nPor favor, cámbiala después de iniciar sesión.";
        enviarCorreo(email, asunto, mensajeTexto);
    }

    @Override
    public void enviarMensajeDeContacto(String nombre, String apellido, String email, String asunto, String mensajeUsuario) {
        String asuntoCompleto = "Consulta de " + nombre + " " + apellido + ": " + asunto;
        String mensajeTexto = "Mensaje enviado por: " + nombre + " " + apellido + " (" + email + ")\n\n" + mensajeUsuario;

        String nuestroCorreo = remitente;
        enviarCorreo(nuestroCorreo, asuntoCompleto, mensajeTexto);
    }

}
