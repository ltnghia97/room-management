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
@Table(name = "attachments")
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath; // Đường dẫn tới tệp tin trên máy chủ hoặc dịch vụ lưu trữ đám mây

    @Column(name = "file_type", nullable = false)
    private String fileType; // Ví dụ: "image/jpeg", "application/pdf"

    @Column(name = "description")
    private String description; // Mô tả tệp đính kèm (ví dụ: "Giấy chứng nhận quyền sở hữu nhà")

    @Column(name = "created_at", updatable = false)
    @Temporal(value = TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    // Mối quan hệ Many-to-One với TenantEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenant;

    // Mối quan hệ Many-to-One với LandlordEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id") // Khóa ngoại mới cho Landlord
    private LandlordEntity landlord;
}