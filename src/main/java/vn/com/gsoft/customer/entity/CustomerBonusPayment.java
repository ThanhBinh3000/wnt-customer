package vn.com.gsoft.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CustomerBonusPayment")
public class CustomerBonusPayment {
    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "DrugStoreId")
    private String drugStoreId;
    @Column(name = "CustomerId")
    private Integer customerId;
    @Column(name = "PaymentDate")
    private Date paymentDate;
    @Column(name = "PaymentByUserId")
    private Integer paymentByUserId;
    @Column(name = "Score")
    private Double score;
    @Column(name = "Amount")
    private BigDecimal amount;
    @Column(name = "RecordStatusId")
    private Integer recordStatusId;
    @Column(name = "PreScore")
    private BigDecimal preScore;
    @Column(name = "StoreId")
    private Integer storeId;
    @Column(name = "Description")
    private String description;
    @Column(name = "NoteId")
    private Integer noteId;
}

