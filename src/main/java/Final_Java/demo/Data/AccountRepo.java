package Final_Java.demo.Data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends CrudRepository<Account,Integer> {
    Account findByEmail(String email);
}
