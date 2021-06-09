package net.zyunx.trader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Value Object
 * 
 * @author zyunx
 *
 */
@Entity
@Getter
@Setter
@Table(name="bankuai")
public class Bankuai {

    @Id
    @Column(name="title", length=60)
    private String title;

    public Bankuai() {}
    
    public Bankuai(String title) {
        this.title = title;
    }
}
