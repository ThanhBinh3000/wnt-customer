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
@Table(name = "privilege")
public class Privilege extends BaseEntity {
    @Id
    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String icon;

    private Integer type;

    private Integer sort;

    private String meta;

    private String path;

    private Boolean enable;
}
