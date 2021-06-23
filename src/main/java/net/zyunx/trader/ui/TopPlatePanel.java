package net.zyunx.trader.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import net.zyunx.trader.model.xgb.TopPlate;
import net.zyunx.trader.model.xgb.TopStock;

public class TopPlatePanel extends Composite<Div> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Label idLabel = new Label();
    private Label nameLabel = new Label();
    private Label countLabel = new Label();
    

    public TopPlatePanel(Long id, String name, Integer count, List<TopPlate> plates) {
        
        idLabel.setText(String.valueOf(id) + " ");
        nameLabel.setText(name + " ");
        countLabel.setText(String.valueOf(count));
        
        VerticalLayout vl = new VerticalLayout();
        vl.add(new HorizontalLayout(idLabel, nameLabel, countLabel));
        
        List<TopPlate> descList = new ArrayList<>();
        String lastDesc = "";
        
        plates.sort((e1, e2) -> e1.getDate().isAfter(e2.getDate()) ? 1 : -1);
        
        for (TopPlate p : plates) {
            if (StringUtils.isBlank(p.getDescription())) {
                continue;
            }
            if (StringUtils.equals(lastDesc, p.getDescription())) {
                continue;
            }
            
            lastDesc = p.getDescription();
            
            descList.add(p);
            
            
        }
        
        if (CollectionUtils.isNotEmpty(descList)) {
            for (int i = descList.size() - 1; i >= 0; i--) {
                TopPlate p = descList.get(i);
                vl.add(new Label(p.getDate() + ": " + p.getDescription()));
            }
        }
        
        List<TopStock> stocks = new ArrayList<>();
        for (TopPlate p : plates) {
            System.out.println(p.getStocks());
            stocks.addAll(p.getStocks());
        }
        
        Map<String, List<TopStock>> mapOfStockByCode = stocks.stream().collect(Collectors.groupingBy(ee -> ee.getCode()));
        List<TopStock> uniqStocks = new ArrayList<>();
        for (Map.Entry<String, List<TopStock>> ent : mapOfStockByCode.entrySet()) {
            ent.getValue().sort((e1, e2) -> e1.getDate().isAfter(e2.getDate()) ? 1 : -1);
            uniqStocks.add(ent.getValue().get(0));
        }
        StringBuilder sb = new StringBuilder();
        for (TopStock s : uniqStocks) {
            sb.append(s.getProdName() + s.getCode() + ", ");
        }
        
        vl.add(new Label(sb.toString()));
        
        this.getContent().add(vl);
    }
}