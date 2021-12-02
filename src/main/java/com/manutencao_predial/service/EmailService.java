package com.manutencao_predial.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manutencao_predial.model.User;

@Service
public class EmailService {
	
	@Autowired
	UserService userService;
	String email = "";
	String password = "";
	
	
	public List<User> birthdayVerification(List<User> usersList) {
		LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();
				
		ArrayList<User> users = new ArrayList<User>();
		for(User user : usersList) {
			
			int userMonth = user.getDate().getMonthValue();
			int userDay =  user.getDate().getDayOfMonth();

			if(userDay == day && userMonth == month) {
				users.add(user);
			}
		}
		return users;
	}
	
	public boolean sendEmail(String email) {
		User user = userService.findByEmail(email);
		LocalDate userDate = user.getDate();
		int userYear = userDate.getYear();
		
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		
		int age = year - userYear;
		
		
		SimpleEmail simpleMail = new SimpleEmail();
		simpleMail.setHostName("smtp.gmail.com");
		simpleMail.setSmtpPort(465);
		simpleMail.setAuthenticator(new DefaultAuthenticator(this.email, password));
		simpleMail.setSSLOnConnect(true);
		
		try {
			simpleMail.setFrom(this.email);
			simpleMail.setSubject("Parabens");
			simpleMail.setMsg("Parabens pelos seus "+age+" anos de idade, tire um dia de folga");
			simpleMail.addTo(email);
			simpleMail.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
