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
@Table(name = "userrole")
@EntityListeners(AuditingEntityListener.class)
public class UserRole extends BaseEntity{
    @Id
    private Long id;
    private Long roleId;
    private Long userId;
}
