package net.zyunx.trader.ui;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route
public class StockView extends VerticalLayout
    implements HasUrlParameter<String>, AfterNavigationObserver {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;
    
    public StockView() {
        
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        this.code = parameter;
        
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String url = "https://image.sinajs.cn/newchart/daily/n/%s.gif";

        if (code.endsWith(".SS")) {
            url = String.format(url, "sh" + StringUtils.left(code, code.length()-3));
        }
        
        add(new Image(url, code));
        
    }

}
