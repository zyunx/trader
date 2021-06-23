package net.zyunx.trader.model.xgb;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FetchXgbTopPlateCommand {
    private LocalDate date;
}
