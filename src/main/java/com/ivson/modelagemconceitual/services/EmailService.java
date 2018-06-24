package com.ivson.modelagemconceitual.services;

import org.springframework.mail.SimpleMailMessage;

import com.ivson.modelagemconceitual.model.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}