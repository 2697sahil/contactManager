package com.singh.sahil.contactManager.repository;

import com.singh.sahil.contactManager.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactEntity, String> {

    // Custom query method for searching contacts by first name and last name
    List<ContactEntity> findByFirstNameAndLastName(String firstName, String lastName);

    // Custom query method for searching contacts by first name
    List<ContactEntity> findByFirstName(String firstName);

    // Custom query method for searching contacts by last name
    List<ContactEntity> findByLastName(String lastName);

}
