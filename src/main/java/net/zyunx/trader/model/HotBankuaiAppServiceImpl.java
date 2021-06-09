package net.zyunx.trader.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class HotBankuaiAppServiceImpl implements HotBankuaiAppService {

    private HotBankuaiRepository hotBankuaiRepository;
    
    private BankuaiRepository bankuaiRepository;
    
    @Override
    public void processHotBankuaiCommand(HotBankuaiCommandRequest request) {
        log.info("request: " + request.getBankuais().get(0));
        
        Optional<HotBankuai> old = getOldHotBankuaiListOfTradeDate(request.getDate());
        if (old.isPresent()) {
            hotBankuaiRepository.delete(old.get());
        }
        
        HotBankuai hbk = makeHotBankuaiListFromRequest(request);

        bankuaiRepository.saveAll(hbk.getBankuais());
        hotBankuaiRepository.save(hbk);

    }
    
    Optional<HotBankuai> getOldHotBankuaiListOfTradeDate(LocalDate tradeDate) {
        return hotBankuaiRepository.findOneByTradeDate(tradeDate);
    }
    
    HotBankuai makeHotBankuaiListFromRequest(HotBankuaiCommandRequest request) {
        HotBankuai result = new HotBankuai();
        result.setTradeDate(request.getDate());
        Set<Bankuai> bks = new HashSet<>();
        result.setBankuais(bks);
        
        if (CollectionUtils.isNotEmpty(request.getBankuais())) {

            for (String bk : request.getBankuais()) {
                Bankuai b = new Bankuai(bk);
                bks.add(b);
            }
        }
        
        return result;
    }

    @Override
    public RecentHotBankuaiQueryResponse processRecentHotBankuaiQuery(RecentHotBankuaiQueryRequest request) {
        
        List<HotBankuai> hbks = hotBankuaiRepository.findByOrderByTradeDateDesc(
                PageRequest.of(0, request.getTradeDayCount()));
        
        
        Map<String, Integer> mapCountOfBankuai = new HashMap<>();
        for (HotBankuai h : hbks) {
            for (Bankuai b : h.getBankuais()) {
                Integer c = mapCountOfBankuai.getOrDefault(b.getTitle(), 0);
                c++;
                mapCountOfBankuai.put(b.getTitle(), c);
            }
        }
        
        List<RecentHotBankuaiQueryResponse.BankuiCount> bc = new ArrayList<>();
        for (Map.Entry<String, Integer> e : mapCountOfBankuai.entrySet()) {
            bc.add(new RecentHotBankuaiQueryResponse.BankuiCount(e.getKey(), e.getValue()));
        }
        bc.sort((e1, e2) -> e2.getCount() - e1.getCount());

        RecentHotBankuaiQueryResponse response = new RecentHotBankuaiQueryResponse();
        response.setBankuais(bc);
        return response;

    }

    @Override
    public HotBankuaiQueryResponse processHotBankuaiQuery(HotBankuaiQueryRequest request) {
        Optional<HotBankuai> hbk = hotBankuaiRepository.findOneByTradeDate(request.getDate());
        
        if (hbk.isEmpty()) {
            HotBankuaiQueryResponse response = new HotBankuaiQueryResponse();
            response.setFound(false);
            return response;
        }
        
        HotBankuai theHbk = hbk.get();
        
        List<String> bks = theHbk.getBankuais()
                .stream()
                .map(Bankuai::getTitle)
                .collect(Collectors.toList());
        bks.sort((str1, str2) -> StringUtils.compare(str1, str2));
        
        HotBankuaiQueryResponse response = new HotBankuaiQueryResponse();
        response.setFound(true);
        response.setDate(request.getDate());
        response.setBankuais(bks);
        
        return response;
    }

}
