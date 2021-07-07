package net.zyunx.trader.model.xgb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="xgb_top_plate")
@IdClass(TopPlateId.class)
@ToString(exclude = "stocks")
public class TopPlate {
    @Id
    private LocalDate date;
    
    @Id
    private Long id;
    
    private String name;
    
    private String description;
    
    private Integer rank;
    
    @ManyToMany(fetch=FetchType.EAGER)
    private List<TopStock> stocks = new ArrayList<>();
}
