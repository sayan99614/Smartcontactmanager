package com.contactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contactmanager.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>  {
	
	@Query("select c from Contact c where c.cId= :id")
	public Contact getcontactById(@Param("id") int id);
}
