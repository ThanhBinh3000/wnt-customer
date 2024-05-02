package vn.com.gsoft.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.customer.entity.CustomerBonusPayment;

import java.util.List;

@Repository
public interface CustomerBonusPaymentRepository extends CrudRepository<CustomerBonusPayment, Long> {

  List<CustomerBonusPayment> findByCustomerIdAndRecordStatusIdIn(Long customerId, List<Long> status);
}
