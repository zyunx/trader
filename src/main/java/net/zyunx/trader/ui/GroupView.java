package net.zyunx.trader.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import lombok.extern.slf4j.Slf4j;
import net.zyunx.trader.model.Group;
import net.zyunx.trader.model.Stock;
import net.zyunx.trader.model.xgb.XgbService;

@Route("group")
@Slf4j
public class GroupView extends VerticalLayout
    implements HasUrlParameter<String>, AfterNavigationObserver {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    XgbService xgbService;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private Long id;
    
    
    private Div stockImagePanel;
    
    public GroupView() {
        
    }
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters
                .getParameters();
        
        id = Long.parseLong(parametersMap.get("id").get(0));
        startDate = LocalDate.parse(parametersMap.get("startDate").get(0));
        endDate = LocalDate.parse(parametersMap.get("endDate").get(0));
        
    }
    
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        
        Optional<Group> group = xgbService.getGroup(id, startDate, endDate);
        log.info(group.get().toString());
        if (group.isEmpty()) {
            return;
        }
        
        GroupViewModel vm = new GroupViewModel(group.get().getName(), group.get().getStocks());
        
        HorizontalLayout titleLayout = new HorizontalLayout(
                new H2(vm.getName()),
                new Paragraph(startDate + " ~ " + endDate)
                );
        titleLayout.setAlignItems(Alignment.BASELINE);
        add(titleLayout);
        
        if (vm.isEmpty()) {
            return;
        }
        
        stockImagePanel = new Div();
        vm.addStockChangeListener(e -> {
            stockImagePanel.removeAll();
            Image stockImage = new Image();
            stockImage.setSrc(stockImageUrl(e.getStock().getCode()));
            stockImage.setAlt(e.getStock().getCode());
            stockImage.setSizeUndefined();
            stockImagePanel.add(stockImage);
        });
        stockImagePanel.getStyle().set("position", "fixed");
        stockImagePanel.getStyle().set("right", "0");
        stockImagePanel.getStyle().set("bottom", "0");
        
        RadioButtonGroup<Stock> radioGroup = new RadioButtonGroup<>();
        radioGroup.setItems(vm.getStocks());
        radioGroup.addValueChangeListener(e -> {
            vm.setCurrentIndex(radioGroup.getItemPosition(e.getValue()));
        });
        radioGroup.setValue(vm.getStocks().get(0));
        
        Div radioGroupLayout = new Div(radioGroup);
        radioGroupLayout.setWidthFull();

        add(radioGroupLayout, stockImagePanel);
        
        
    }
    
    
    
    
    String stockImageUrl(String code) {
        String url = "https://image.sinajs.cn/newchart/daily/n/%s.gif";

        if (code.endsWith(".SS")) {
            url = String.format(url, "sh" + StringUtils.left(code, code.length()-3));
        } else if (code.endsWith(".SZ")) {
            url = String.format(url, "sz" + StringUtils.left(code, code.length()-3));
        } else {
            url = "";
        }
        
        return url;
    }
    

}
