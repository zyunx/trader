package net.zyunx.trader.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotBankuaiQueryResponse {
    boolean found;
    private LocalDate date;
    private List<String> bankuais;
}
