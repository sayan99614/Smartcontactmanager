package com.contactmanager.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String otp;
    
    @OneToOne
    private User user_otp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	
	public User getUser_otp() {
		return user_otp;
	}

	public void setUser_otp(User user_otp) {
		this.user_otp = user_otp;
	}

	
	public Otp(int id, String otp, User user_otp) {
		super();
		this.id = id;
		this.otp = otp;
		this.user_otp = user_otp;
	}

	public Otp() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
