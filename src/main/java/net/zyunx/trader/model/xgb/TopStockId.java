package net.zyunx.trader.model.xgb;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopStockId implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private LocalDate date;
    private String code;
}
