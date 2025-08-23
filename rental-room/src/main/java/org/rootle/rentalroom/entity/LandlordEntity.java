package org.rootle.rentalroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rootle.rentalroom.enum_common.LandlordType;

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

    @Column(name = "business_name")
    private String businessName;

    // Thêm mối quan hệ One-to-Many với AttachmentEntity
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AttachmentEntity> attachments;

    // Sử dụng enum để phân biệt loại chủ thuê
    @Enumerated(EnumType.STRING) // Lưu tên của enum (INDIVIDUAL, BUSINESS) dưới dạng chuỗi
    @Column(name = "landlord_type", nullable = false)
    private LandlordType landlordType;

//    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<RoomEntity> rooms;

    public static String getTypeName(LandlordType type) {
        return type.name();
    }
}