package net.zyunx.trader.ui;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.zyunx.trader.model.xgb.TopPlate;

@Getter
@Setter
public class TopPlatePanelModel {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long plateId;
    private String plateName;
    private Integer countOfOnList;
    private List<TopPlate> plates;
}
