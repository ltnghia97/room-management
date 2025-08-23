package org.rootle.rentalroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "usercode", nullable = false, unique = true)
    private String userCode;

    @Column(name = "role", nullable = false)
    private String role; // Ví dụ: "LANDLORD" hoặc "TENANT"

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at", updatable = false)
    @Temporal(value = TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;


//    // Một người dùng có nhiều phòng trọ
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Room> rooms;
//
//    // Getters and Setters
}