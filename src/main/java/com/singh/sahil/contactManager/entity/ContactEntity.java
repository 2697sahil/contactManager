package com.singh.sahil.contactManager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contacts", indexes = {
        @Index(columnList = "firstName"),
        @Index(columnList = "lastName")
})
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
}
