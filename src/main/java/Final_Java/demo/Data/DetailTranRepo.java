package Final_Java.demo.Data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailTranRepo extends CrudRepository<DetailTran,Integer> {
    @Query("SELECT d FROM DetailTran d WHERE " +
            "d.Phone = :#{#detailTran.phone} " +
            "AND d.ProductID = :#{#detailTran.productID} " +
            "AND d.Quantity = :#{#detailTran.quantity} " +
            "AND d.UnitPrice = :#{#detailTran.unitPrice} " +
            "AND d.score = :#{#detailTran.score} " +
            "AND d.TotalPrice = :#{#detailTran.totalPrice} " +
            "AND d.status = :#{#detailTran.status} " +
            "AND d.typePayment = :#{#detailTran.typePayment} " +
            "AND d.DATETIME = :#{#detailTran.DATETIME}")
    DetailTran findByMatchingProperties(@Param("detailTran") DetailTran detailTran);
}
