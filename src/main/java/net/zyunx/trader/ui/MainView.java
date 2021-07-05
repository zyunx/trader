package net.zyunx.trader.ui;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

import lombok.extern.slf4j.Slf4j;
import net.zyunx.trader.model.xgb.FetchXgbTopPlateCommand;
import net.zyunx.trader.model.xgb.TopPlate;
import net.zyunx.trader.model.xgb.TopPlateRepository;
import net.zyunx.trader.model.xgb.XgbService;

@Route
@Slf4j
public class MainView extends VerticalLayout {

    @Autowired
    private XgbService xgbService;
    
    @Autowired
    private TopPlateRepository topPlateRepository;
    
    private static final List<Long> IGNORE_PLATE_ID_LIST = Arrays.asList(17864537L, 17290881L, 24898553L, -1L);
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
            
            // top gainer
            DatePicker topGainerStartDatePicker = new DatePicker("开始日期");
            topGainerStartDatePicker.setValue(now);
            
            DatePicker topGainerEndDatePicker = new DatePicker("结束日期");
            topGainerEndDatePicker.setValue(now);
            
            NumberField rankNumberField = new NumberField("排名");
            rankNumberField.setValue(5.0);
            
            VerticalLayout topGainersContentPanel = new VerticalLayout();
            Button showTopGainerButton = new Button("显示风口板块", e -> {
                try {
                    topGainersContentPanel.removeAll();
                    
                    LocalDate startDate = topGainerStartDatePicker.getValue();
                    LocalDate endDate = topGainerEndDatePicker.getValue();
                    Integer rankAtLeast = rankNumberField.getValue().intValue();
                    
                    List<TopPlate> plates = topPlateRepository.findByDateBetweenAndRankLessThanEqualAndIdNotIn(
                            startDate,
                            endDate,
                            rankAtLeast,
                            IGNORE_PLATE_ID_LIST);
                    log.info("plates: " + plates);
                    
                    List<TopPlatePanelModel> models = xgbService.computeTopPlatePanelModelList(startDate, endDate, rankAtLeast, plates);
                    
                    for (TopPlatePanelModel m : models) {
                        TopPlatePanel pp = new TopPlatePanel(m);
                        topGainersContentPanel.add(pp);
                    }
                } catch (Exception ex) {
                    log.error("显示风口板块异常: ", ex);
                    Notification.show("显示风口板块异常: " + ex.getMessage());
                }
            });
            
            HorizontalLayout topGainersControlLayout = new HorizontalLayout(
                    topGainerStartDatePicker, topGainerEndDatePicker, 
                    rankNumberField, showTopGainerButton);
            topGainersControlLayout.setAlignItems(Alignment.BASELINE);
            VerticalLayout topGainersLayout = new VerticalLayout(
                    topGainersControlLayout,
                    topGainersContentPanel
                    );
            
            add(fetchXgbLayout, topGainersLayout);
        }
}