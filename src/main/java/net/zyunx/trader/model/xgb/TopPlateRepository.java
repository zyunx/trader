package net.zyunx.trader.model.xgb;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopPlateRepository extends JpaRepository<TopPlate, TopPlateId> {
    List<TopPlate> findByDateBetweenAndRankLessThanEqualAndIdNotIn(
            LocalDate startDate, LocalDate endDate, 
            Integer rank,
            List<Long> idList);
    
    List<TopPlate> findByIdAndDateBetween(Long id, LocalDate startDate, LocalDate endDate);
}
