package vn.com.gsoft.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {
    @Id
    private Long id;

    private String code;

    private Long departmentId;

    private String name;

    private Boolean enable;

    private String description;

    private String type;

    private Long userId;
}
