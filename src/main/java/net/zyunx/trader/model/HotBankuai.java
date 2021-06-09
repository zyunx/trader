package net.zyunx.trader.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="hot_bankuai")
public class HotBankuai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="trade_date")
    private LocalDate tradeDate;
    
    @ManyToMany(targetEntity=Bankuai.class)
    @JoinTable(
            name = "hot_bankuai_set", 
            joinColumns = @JoinColumn(name = "hot_bankuai_id"), 
            inverseJoinColumns = @JoinColumn(name = "bankuai"))
    Set<Bankuai> bankuais;
}
