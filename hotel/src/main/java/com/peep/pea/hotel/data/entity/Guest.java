package com.peep.pea.hotel.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="guests")
@Data
@ToString
public class Guest {

    @Id
    @Column(name="guest_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="first_name")
    private String FirstName;

    @Column(name="last_name")
    private String LastName;

    @Column(name="email_address")
    private String email_address;

    @Column(name="address")
    private String address;

    @Column(name="country")
    private String country;

    @Column(name="state")
    private String state;

    
    @Column(name="phone_number")
    private String phone_number;
}
