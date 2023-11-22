package com.crm.repository;

import com.crm.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact,String> {

    Optional<Contact> findByEmail(String email);
}
