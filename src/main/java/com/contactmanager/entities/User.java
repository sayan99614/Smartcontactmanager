package com.contactmanager.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "user_details")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	@NotBlank(message = "email should not be blank")
	@Email(message = "email should be in proper format eg.example@gmail.com")
	@Column(unique = true)
	private String email;
	@NotBlank(message = "username can't be blank")
	@Size(min = 5,max = 15,message = "user name must be size between 5 to 15")
	private String name;
	@Column(name = "user_role")
	private String role;
	
	@NotBlank(message = "password should not be blank")
	private String password;
	private boolean enabled;
	private String image_url;
	@Column(length = 500)
	@Size(min = 5, max=100 ,message = "description should be between 5 to 100 characters")
	private String about;
	
	/* mapping */
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<Contact> contact; 
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user_otp")
	private Otp otp;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	
	public List<Contact> getContact() {
		return contact;
	}
	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}
	
	public Otp getOtp() {
		return otp;
	}
	public void setOtp(Otp otp) {
		this.otp = otp;
	}
	public User(int id, String email, String name, String role, String password, boolean enabled, String image_url,
			String about) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.role = role;
		this.password = password;
		this.enabled = enabled;
		this.image_url = image_url;
		this.about = about;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", role=" + role + ", password=" + password
				+ ", enabled=" + enabled + ", image_url=" + image_url + ", about=" + about + ", contact=" + contact
				+ "]";
	}
	

	
	
}
