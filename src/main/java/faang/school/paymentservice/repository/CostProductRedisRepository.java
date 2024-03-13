package faang.school.paymentservice.repository;

import faang.school.paymentservice.entity.CostProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostProductRedisRepository extends CrudRepository<CostProduct, Long> {
    boolean existsByName(String name);
}
