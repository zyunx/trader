package net.zyunx.trader.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Group {
    private Long id;
    private String name;
    List<Stock> stocks;    
}
