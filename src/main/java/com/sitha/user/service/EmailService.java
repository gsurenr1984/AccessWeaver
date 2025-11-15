package com.sitha.user.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
	this.mailSender= mailSender;	
	}
	
	private String loadTemplate(String templateName, String name) throws IOException {
	    ClassPathResource resource = new ClassPathResource("templates/"+templateName);
	    String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	    return content.replace("{{name}}", name);
	}
	
	public void sendOTPMail(String to, String otp) {
		String subject = "Your One-Time Password (OTP)";
		String templateName = "email-otp-template.html";
		try {
			sendEmail(to, subject, loadTemplate(templateName, otp));
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true = HTML

        ClassPathResource logo = new ClassPathResource("static/images/logo.png");
        helper.addInline("companyLogo", logo); // "companyLogo" matches cid in HTML


        
        mailSender.send(message);
    }


}
