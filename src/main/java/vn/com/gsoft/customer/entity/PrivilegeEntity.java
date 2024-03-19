package vn.com.gsoft.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "privilegeentity")
public class PrivilegeEntity extends BaseEntity {
    @Id
    private Long id;
    private Long privilegeId;
    private Long entityId;
}
