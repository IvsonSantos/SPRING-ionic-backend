package com.ivson.modelagemconceitual.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.ivson.modelagemconceitual.model.Cliente;
import com.ivson.modelagemconceitual.model.Pedido;

public interface EmailService {

	// enviar email normal
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	// enviar email com HTML
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	// para enviar email HTML MimeMessage
	void sendHtmlEmail(MimeMessage msg);	 
	
	void sendNewpasswordEmail(Cliente cliente, String newPass);

}