package net.zyunx.trader.model.xgb;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="xgb_top_stock")
@IdClass(TopStockId.class)
@ToString
public class TopStock {
    @Id
    private LocalDate date;
    @Id
    private String code;
    
    private String prodName;
    private String description;
    
    @ManyToMany
    private List<TopPlate> plates;
}
