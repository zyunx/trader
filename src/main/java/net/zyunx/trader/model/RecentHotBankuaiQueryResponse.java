package net.zyunx.trader.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecentHotBankuaiQueryResponse {
    private List<BankuiCount> bankuais;
    
    
    @Setter
    @Getter
    @AllArgsConstructor
    public static class BankuiCount {
        
        String title;
        Integer count;
    }
}
