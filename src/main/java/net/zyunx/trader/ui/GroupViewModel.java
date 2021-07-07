package net.zyunx.trader.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Value;
import net.zyunx.trader.model.Stock;

public class GroupViewModel {
    private String name;
    private List<Stock> stocks = new ArrayList<>();
    private int index = -1;
    
    private List<StockChangeListener> stockChangeListeners = new ArrayList<>();
    
    public GroupViewModel(String name, List<Stock> stocks) {
        this.name = name;
        this.stocks.addAll(stocks);
    }
    
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(stocks);
    }
    
    public String getName() {
        return name;
    }
    
    public List<Stock> getStocks() {
        return Collections.unmodifiableList(stocks);
    }
    
    public void next() {
        if (CollectionUtils.size(stocks) > index + 1 && index >= 0) {
            index++;
            callStockChangeListeners();
        } else {
            throw new IllegalStateException();
        }
    }
    public void prev() {
        if (CollectionUtils.size(stocks) > index - 1 && index >= 1) {
            index--;
            callStockChangeListeners();
        } else {
            throw new IllegalStateException();
        }
    }
    
    public void setCurrentIndex(int i) {
        if (CollectionUtils.size(stocks) > i && i >= 0) {
            this.index = i;
            callStockChangeListeners();
        } else {
            throw new IllegalArgumentException();
        }
        
    }
    public int getCurrentIndex() {
        return this.index;
    }
    
    public Stock getCurrentStock() {
        if (CollectionUtils.size(stocks) > index && index > 0) {
            return this.stocks.get(index);
        } else {
            throw new IllegalStateException();
        }
    }
    
    public void addStockChangeListener(StockChangeListener l) {
        stockChangeListeners.add(l);
    }
    
    public void removeStockChangeListener(StockChangeListener l) {
        stockChangeListeners.remove(l);
    }
    
    public void callStockChangeListeners() {
        for (StockChangeListener l : stockChangeListeners) {
            l.on(new StockChangeEvent(index, stocks.get(index)));
        }
    }
    
    @Value
    public static class StockChangeEvent {
        private int index;
        private Stock stock;
    }
    
    public static interface StockChangeListener {
        void on(StockChangeEvent e);
    }
}
