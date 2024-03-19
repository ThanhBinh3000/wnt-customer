package vn.com.gsoft.customer.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {
    @Id
    private Long id;

    @Size(min = 5, max = 150)
    private String username;

    private String fullName;

    private String password;

    private String email;

    private Long entityTypeId;

    private Integer status;
}
