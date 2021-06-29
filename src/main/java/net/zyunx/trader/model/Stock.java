package net.zyunx.trader.model;

import lombok.Value;

@Value
public class Stock {
    private String code;
    private String name;
    
    public static Stock create(String code, String name) {
        return new Stock(code, name);
    }
    
    public String toString() {
        return name + " " + code;
    }
}
