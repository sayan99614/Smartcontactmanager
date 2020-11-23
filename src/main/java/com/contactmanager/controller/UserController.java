package com.contactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.Otp;
import com.contactmanager.entities.User;
import com.contactmanager.healper.Message;
import com.contactmanager.repository.ContactRepository;
import com.contactmanager.repository.Otprepository;
import com.contactmanager.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	Otprepository otprepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute
	public void addcommondata(Model m, Principal principal) {
		String name = principal.getName();
		User user = userRepository.getUserByusername(name);
		m.addAttribute("user", user);
	}

	@GetMapping("/index")
	public String user_dahbord(Model m, Principal principal) {
		m.addAttribute("title", "UserDashbord");
		String name = principal.getName();
		System.out.println(name);
		// getting the username
		User user = userRepository.getUserByusername(name);
		System.out.println(user.getName());
		m.addAttribute("user", user);
		return "normal/user_dashbord";
	}

	@GetMapping("/add-contact")
	public String addcontactform(Model m) {
		m.addAttribute("title", "Smartcontacrmanager-addcontact");
		m.addAttribute("contact", new Contact());
		return "normal/add_contactform";
	}

	@PostMapping("/processcontact")
	public String Contactformprocess(@ModelAttribute Contact contact, @RequestParam("profile_image") MultipartFile file,
			Principal principal, HttpSession session) {
		try {
			String name = principal.getName();
			User user = userRepository.getUserByusername(name);
			user.getContact().add(contact);

			if (file.isEmpty()) {
				System.out.println("file is empty");

			} else {
				contact.setImage_url(file.getOriginalFilename());

				File file2 = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(file2.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("file uploded successfully");
			}

			contact.setUser(user);
			userRepository.save(user);
			System.out.println("contact saved");
			session.setAttribute("message", new Message("CONTACT ADDED SUCESSFULLY", "alert-success"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message", new Message("SOMETHING WENT WRONG TRY AGAIN LATER.....", "alert-danger"));
		}
		return "normal/add_contactform";
	}

	@GetMapping("/all-contact")
	public String allusercontact(Model m, Principal principal) {

		String name = principal.getName();
		User user = userRepository.getUserByusername(name);
		List<Contact> contact = user.getContact();
		m.addAttribute("user", user);
		m.addAttribute("contact", contact);
		return "Normal/showcontacts";
	}

	@GetMapping("edit/{id}")
	public String editcontactshow(Principal principal, Model m, @PathVariable int id) {
		String name = principal.getName();
		User user = userRepository.getUserByusername(name);
		Contact contact = contactRepository.getcontactById(id);
		m.addAttribute("contact", contact);
		return "Normal/editcontact";
	}

	@PostMapping("/editcontact")
	public String editcontact(@ModelAttribute Contact contact, @RequestParam("profile_image") MultipartFile file,
			Principal principle, Model m, HttpSession session) {

		try {
			String name = principle.getName();
			User user = userRepository.getUserByusername(name);
			user.getContact().add(contact);

			if (file.isEmpty()) {
				System.out.println("file is empty");

			} else {
				contact.setImage_url(file.getOriginalFilename());

				File file2 = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(file2.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("file uploded successfully");
			}

			contact.setUser(user);
			userRepository.save(user);
			m.addAttribute("contact", new Contact());
			session.setAttribute("message", new Message("CONTACT UPDATED SUCESSFULLY", "alert-success"));
			return "Normal/editcontact";
		} catch (Exception e) {
			e.getMessage();
			session.setAttribute("message", new Message("SOMETHING WENT WRONG TRY AGAIN LATER.....", "alert-danger"));
			return "Normal/editcontact";
		}

	}

	@GetMapping("delete/{id}")
	public String deletecontact(Principal principal, Model m, @PathVariable int id, HttpSession session) {
		try {
			Contact contact = contactRepository.getcontactById(id);
			contactRepository.delete(contact);
			session.setAttribute("message", new Message("CONTACT DELETED SUCCESSFULLY", "alert-danger"));
			return "redirect:/user/all-contact";
		} catch (Exception e) {
			session.setAttribute("message", new Message("SOMETHING WENT WRONG TRY AGAIN LATER.....", "alert-success"));
			return "redirect:/user/all-contact";
		}

	}

	@GetMapping("/userprofile/{id}")
	public String viewprofile(@PathVariable int id, Model m) {
		User user = userRepository.getUserByid(id);
		List<Contact> list = user.getContact();
		int size = list.size();
		m.addAttribute("user", user);
		m.addAttribute("size", size);
		return "Normal/userprofile";
	}

	@GetMapping("/singlecontact/{id}")
	public String singlecontact(@PathVariable int id, Model m, Principal principal) {
		String name = principal.getName();
		User user = userRepository.getUserByusername(name);
		Contact contact = contactRepository.getcontactById(id);
		if (user.getId() == contact.getUser().getId()) {
			m.addAttribute("contact", contact);
		}
		return "Normal/singlecontact";
	}

	@GetMapping("/changepass")
	public String sendotp(Principal principle) {

		String name = principle.getName();
		User user = userRepository.getUserByusername(name);

		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = 5;

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphaNumeric.length());
			char randomChar = alphaNumeric.charAt(index);
			sb.append(randomChar);
		}
		String otp = sb.toString();
		String subject = "otp for changing password";
		String message = "your otp for changing the password is :" + otp;
		String to = user.getEmail();
		String form = "dheemanpati1@gmail.com";

		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", true);

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("dheemanpati1@gmail.com", "sayan@1999");
			}

		});
		session.setDebug(true);

		MimeMessage m = new MimeMessage(session);

		try {
			m.setFrom(form);
			m.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			m.setSubject(subject);
			m.setText(message);

			Transport.send(m);

			Otp otpsave = new Otp();
			otpsave.setOtp(otp);
			user.setOtp(otpsave);
			otprepository.save(otpsave);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Normal/otpverify";
	}

	@PostMapping("/verifyotp")
	public String verifyotp(@RequestParam("otp") String otp, Principal principle, HttpSession session) {
		Otp checkotp = otprepository.getotpByotp(otp);
		if (checkotp == null) {
			session.setAttribute("message", new Message("INVALID OTP", "alert-danger"));
			return "Normal/otpverify";
		}
		return "Normal/changepassword";
	}

	@PostMapping("/passwordchange")
	public String passwordchange(Principal principle, @RequestParam("pass") String pass,
			@RequestParam("c_pass") String c_pass, HttpSession session) {
		String name = principle.getName();
		User user = userRepository.getUserByusername(name);
		System.out.println(pass + " " + c_pass);
		if (pass.equals(c_pass)) {
			
			user.setPassword(passwordEncoder.encode(c_pass));
			userRepository.save(user);
			return "redirect:/signin";
		} else {
			session.setAttribute("message",
					new Message("PASSWORD AND CONFIRM PASSWORD SHOULD BE SAME", "alert-danger"));
			return "Normal/changepassword";

		}
	}
}
