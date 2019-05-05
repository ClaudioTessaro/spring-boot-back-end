package com.claudiotessaro.curso.services;

import org.springframework.mail.SimpleMailMessage;

import com.claudiotessaro.curso.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
