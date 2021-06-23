package net.zyunx.trader.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.zyunx.trader.model.xgb.FetchXgbTopPlateCommand;
import net.zyunx.trader.model.xgb.XgbService;

@RestController
@AllArgsConstructor
@RequestMapping(value="/api/v1/fetch/xgb",
produces=MediaType.APPLICATION_JSON_VALUE)
public class XgbController {
    
    private XgbService xgbService;
   
    @PostMapping(value="/")
    public void crawlXgbPlate(@RequestBody FetchXgbTopPlateCommand command) throws Exception {
        xgbService.fetchTopPlate(command);
    }
}
