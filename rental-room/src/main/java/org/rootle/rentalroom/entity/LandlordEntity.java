package org.rootle.rentalroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "landlords")
public class LandlordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long landlordId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserEntity user;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    // Thêm mối quan hệ One-to-Many với AttachmentEntity
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AttachmentEntity> attachments;

//    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<RoomEntity> rooms;


}