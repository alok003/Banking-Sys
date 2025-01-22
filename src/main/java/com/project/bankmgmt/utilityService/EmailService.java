package com.project.bankmgmt.utilityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.project.bankmgmt.bean.TransactionsBean;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	public void sendEmailOfTransaction(String to,String Subject,String method,TransactionsBean transaction) {
		Context context=new Context();
		context.setVariable("transaction", transaction);
		context.setVariable("method", method);
		String htmlContent=templateEngine.process("transaction", context);
		sendEmail(to, Subject, htmlContent);
	}
	public void sendEmailofUserTransactions(String to,String Subject,List<TransactionsBean> transaction) {
		Context context=new Context();
		context.setVariable("transaction", transaction);
		String htmlContent=templateEngine.process("statement", context);
		sendEmail(to, Subject, htmlContent);
	}
	private void sendEmail(String to,String subject,String htmlContent) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("statements@banking.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true);  // true means it's HTML content
        };
        mailSender.send(messagePreparator);
	}
}
