package net.zyunx.trader.model.xgb;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TopPlateId implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private LocalDate date;
    private Long id;
}
