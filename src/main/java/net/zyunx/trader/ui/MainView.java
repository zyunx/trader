package net.zyunx.trader.ui;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import net.zyunx.trader.model.xgb.FetchXgbTopPlateCommand;
import net.zyunx.trader.model.xgb.XgbService;

@Route
public class MainView extends VerticalLayout {

    @Autowired
    private XgbService xgbService;
    
        /**
     * 
     */
    private static final long serialVersionUID = 1L;

        public MainView() {
            LocalDate now = LocalDate.now();
            
            DatePicker valueDatePicker = new DatePicker();
            valueDatePicker.setValue(now);
            
            Button fetchXgbButton = new Button("获取选股宝风口板块", e -> {
                try {
                    FetchXgbTopPlateCommand command = new FetchXgbTopPlateCommand();
                    command.setDate(valueDatePicker.getValue());
                    xgbService.fetchTopPlate(command);
                    Notification.show("获取选股宝风口板块成功!");
                } catch (Exception ex) {
                    Notification.show("获取选股宝风口板块失败: " + ex.getMessage());
                }
                
            });
            
            HorizontalLayout fetchXgbLayout = new HorizontalLayout();
            fetchXgbLayout.add(valueDatePicker, fetchXgbButton);
            
            add(fetchXgbLayout, new Button("Click me", e -> Notification.show("Hello, Spring+Vaadin user!")));
        }
}