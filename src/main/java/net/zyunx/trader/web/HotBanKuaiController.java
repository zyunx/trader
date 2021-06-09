package net.zyunx.trader.web;

import java.time.LocalDate;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.zyunx.trader.model.HotBankuaiAppService;
import net.zyunx.trader.model.HotBankuaiCommandRequest;
import net.zyunx.trader.model.HotBankuaiQueryRequest;
import net.zyunx.trader.model.HotBankuaiQueryResponse;
import net.zyunx.trader.model.RecentHotBankuaiQueryRequest;
import net.zyunx.trader.model.RecentHotBankuaiQueryResponse;

@RestController
@RequestMapping(value="/api/v1/hotBankuai",
    produces=MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class HotBanKuaiController {
    
    private HotBankuaiAppService hotBankuaiAppService;
    
    @PostMapping(value="/")
    public void hotBankuai(@RequestBody HotBankuaiCommandRequest request) {
        hotBankuaiAppService.processHotBankuaiCommand(request);
    }
    
    @GetMapping(value="/byTradeDate/{tradeDate}/")
    public ResponseEntity<HotBankuaiQueryResponse> hotBankuai(@PathVariable("tradeDate") LocalDate tradeDate) {
        HotBankuaiQueryRequest request = new HotBankuaiQueryRequest();
        request.setDate(tradeDate);
        HotBankuaiQueryResponse response = hotBankuaiAppService.processHotBankuaiQuery(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/byTradeDayCount/{tradeDayCount}/")
    public ResponseEntity<RecentHotBankuaiQueryResponse> hotBankuai(@PathVariable("tradeDayCount") Integer tradeDayCount) {
        RecentHotBankuaiQueryRequest request = new RecentHotBankuaiQueryRequest();
        request.setTradeDayCount(tradeDayCount);
        RecentHotBankuaiQueryResponse response = hotBankuaiAppService
                .processRecentHotBankuaiQuery(request);
        return ResponseEntity.ok(response);
    }
}
