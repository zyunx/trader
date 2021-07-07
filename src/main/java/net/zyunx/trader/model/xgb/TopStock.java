package net.zyunx.trader.model.xgb;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="xgb_top_stock")
@IdClass(TopStockId.class)
public class TopStock {
    @Id
    private LocalDate date;
    @Id
    private String code;
    
    private String prodName;
    private String description;
}
