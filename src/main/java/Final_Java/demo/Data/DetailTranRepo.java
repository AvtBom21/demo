package Final_Java.demo.Data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DetailTranRepo extends CrudRepository<DetailTran,Integer> {
}
