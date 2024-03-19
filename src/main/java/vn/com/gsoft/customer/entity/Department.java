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
@Table(name = "department")
public class Department extends BaseEntity {
    @Id
    private Long id;

    private String code;

    private Long userId;
}
