package net.zyunx.trader.ui;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;

@Route
public class TestView extends VerticalLayout {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public TestView() {
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add("发发发发 888888.SZ" + i);
        }
        radioGroup.setItems(items);
        
        Div div = new Div(radioGroup);
        add(div);
        
        
        Div divF = new Div();
        divF.getStyle().set("position", "fixed");
        divF.getStyle().set("right", "0");
        divF.getStyle().set("bottom", "0");
        divF.add("Hello, world");
        add(divF);
        
    }

}
