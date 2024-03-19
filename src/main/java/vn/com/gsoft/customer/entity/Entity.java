package vn.com.gsoft.customer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@jakarta.persistence.Entity
@Table(name = "entity")
@EntityListeners(AuditingEntityListener.class)
public class Entity extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "CODE")
    private Long code;

    @Column(name = "NAME")
    private Long name;
}
