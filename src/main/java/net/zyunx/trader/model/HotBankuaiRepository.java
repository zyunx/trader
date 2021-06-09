package net.zyunx.trader.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotBankuaiRepository extends JpaRepository<HotBankuai, Long> {
    List<HotBankuai> findByOrderByTradeDateDesc(Pageable page);
    List<HotBankuai> findByTradeDateIn(List<LocalDate> tradeDates);
    Optional<HotBankuai> findOneByTradeDate(LocalDate tradeDate);
}
