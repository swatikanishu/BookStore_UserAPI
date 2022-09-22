package com.example.bookstore_user.model;



import com.example.bookstore_user.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "User")
public @Data class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    Long userid;
    String firstName;
    String lastName;
    String address;
    @Column(name = "email")
    String email_address;
    LocalDate DOB;
    String password;
    // parameterized constructor
    public User(UserDto userdto) {
        this.firstName = userdto.getFirstName();
        this.lastName = userdto.getLastName();
        this.address = userdto.getAddress();
        this.email_address = userdto.getEmail_address();
        this.DOB = userdto.getDOB();
        this.password = userdto.getPassword();
    }

}


