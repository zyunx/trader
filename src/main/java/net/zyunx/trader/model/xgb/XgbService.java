package net.zyunx.trader.model.xgb;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import net.zyunx.trader.model.Group;
import net.zyunx.trader.ui.TopPlatePanelModel;

public interface XgbService {
    void fetchTopPlate(FetchXgbTopPlateCommand command) throws Exception;
    Optional<Group> getGroup(Long id, LocalDate startDate, LocalDate endDate);
    List<TopPlatePanelModel> computeTopPlatePanelModelList(
            LocalDate startDate, LocalDate endDate, Integer rankAtLeast, List<TopPlate> plates);    
}
