package com.contactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contactmanager.entities.Otp;

public interface Otprepository extends JpaRepository<Otp, Integer> {
	
	@Query("select o from Otp o where o.otp= :otp")
	public Otp getotpByotp(@Param("otp") String otp);
}
