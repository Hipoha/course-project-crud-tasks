package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import sun.java2d.pipe.SpanShapeRenderer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        // Given
        Mail mail = new Mail("test@test.com", "test2@test.com", "Test", "Test Message");

        MimeMessagePreparator mimeMessage = message -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setTo(mail.getMailTo());
            if (!("".equals(mail.getCcTo())) && mail.getCcTo()!=null) {
                messageHelper.setCc(mail.getCcTo());
            }
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage());
        };

        // When
        try {
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Then
        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}