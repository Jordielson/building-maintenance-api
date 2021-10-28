package com.manutencao_predial.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	
	
	public List<User> birthdayVarification() {
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
				
		ArrayList<User> users = new ArrayList<User>();
		for(User user : userService.findProviders()) {
			
			Calendar userCalendar = Calendar.getInstance();
			userCalendar.setTime(user.getDate());
			int userMonth = userCalendar.get(Calendar.MONTH);
			int userDay = userCalendar.get(Calendar.DAY_OF_MONTH);

			if(userDay == day && userMonth == month) {
				users.add(user);
			}
		}
		return users;
	}
	
	public boolean sendEmail(String email) {
		User user = userService.findByEmail(email);
		Date userDate = user.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(userDate);
		int userYear = cal.get(Calendar.YEAR);
		
		Date date = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		int year = cal2.get(Calendar.YEAR);
		
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
