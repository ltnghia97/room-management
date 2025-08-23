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
@Table(name = "tenants")
public class TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserEntity user;

    @Column(name = "id_card", unique = true, nullable = false)
    private String idCard;

    @Column(name = "job_title")
    private String jobTitle;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "room_id", nullable = false)
//    private RoomEntity room;

    // Thêm mối quan hệ One-to-Many với AttachmentEntity
    // Một Tenant có thể có nhiều tệp đính kèm
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AttachmentEntity> attachments;
}