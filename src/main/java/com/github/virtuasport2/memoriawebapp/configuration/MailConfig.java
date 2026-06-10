package com.github.virtuasport2.memoriawebapp.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.libero.it");
	    mailSender.setPort(587);
	    mailSender.setUsername("saisei1099@libero.it");
	    mailSender.setPassword("u5h1r2zCX33hzsix");

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.ssl.enable", "false");
	    props.put("mail.smtp.connectiontimeout", "5000");
	    props.put("mail.smtp.timeout", "5000");
	    props.put("mail.smtp.writetimeout", "5000");

	    return mailSender;
	}

}
