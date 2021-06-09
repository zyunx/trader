package net.zyunx.trader.model;

public interface HotBankuaiAppService {
    void processHotBankuaiCommand(HotBankuaiCommandRequest request);
    HotBankuaiQueryResponse processHotBankuaiQuery(HotBankuaiQueryRequest request);
    
    RecentHotBankuaiQueryResponse processRecentHotBankuaiQuery(RecentHotBankuaiQueryRequest request);
}
