package net.zyunx.trader.model.xgb;

import java.time.LocalDate;
import java.util.Optional;

import net.zyunx.trader.model.Group;

public interface XgbService {
    void fetchTopPlate(FetchXgbTopPlateCommand command) throws Exception;
    Optional<Group> getGroup(Long id, LocalDate startDate, LocalDate endDate);
}
