package net.zyunx.trader.model.xgb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zyunx.trader.model.Group;
import net.zyunx.trader.model.Stock;

@Service
@Slf4j
@AllArgsConstructor
public class XgbServiceImpl implements XgbService {

    private RestTemplate restTemplate;
    
    private TopStockRepository stockRepository;
    
    private TopPlateRepository plateRepository;
    
    private ObjectMapper objectMapper;
    
    @Override
    @Transactional
    public void fetchTopPlate(FetchXgbTopPlateCommand command) throws JsonMappingException, JsonProcessingException {
        LocalDate date = command.getDate();
        
        log.info("date: " + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ", " + date.atStartOfDay().toEpochSecond(ZoneOffset.of("+8")));
        
        String url = "https://flash-api.xuangubao.cn/api/surge_stock/plates?date={date}";

        TopPlateResponse plateResponse = restTemplate.getForObject(url, TopPlateResponse.class, date.atStartOfDay().toEpochSecond(ZoneOffset.of("+8")));
        log.info(plateResponse.toString());

        List<TopPlate> plateList = new ArrayList<>();
        
        if (CollectionUtils.isEmpty(plateResponse.getData().getItems())) {
            return;
        }
        
        date = LocalDateTime.ofEpochSecond(plateResponse.getData().getTimestamp(), 0, ZoneOffset.of("+8")).toLocalDate();
        log.info("real date: " + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        
        int rank = 0;
        for (TopPlateResponse.DataItem i : plateResponse.getData().getItems()) {
            rank++;
            
            TopPlate p = new TopPlate();
            p.setDate(date);
            p.setId(i.getId());
            p.setName(i.getName());
            p.setDescription(i.getDescription());
            p.setRank(rank);
            
            plateList.add(p);
        }

        
        String stockUrl = "https://flash-api.xuangubao.cn/api/surge_stock/stocks?date={date}&normal=true&uplimit=true";
        
        String stockResponse = restTemplate.execute(stockUrl, HttpMethod.GET, null, new ResponseExtractor<String>() {

            @Override
            public String extractData(ClientHttpResponse response) throws IOException {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                response.getBody().transferTo(baos);                
                return baos.toString();
            }
            
        }, date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        
        log.info("stock: " + stockResponse);
        JsonNode node = objectMapper.readTree(stockResponse);
        
        JsonNode fields = node.get("data").get("fields");
        if (fields.isNull()) {
            return;
        }
        int codeFieldIndex = -1;
        int prodNameIndex = -1;
        int descriptionFieldIndex = -1;
        int platesFieldIndex = -1;
        int index = -1;
        for (Iterator<JsonNode> it = fields.elements(); it.hasNext(); ) {
            index++;
            String fn = it.next().asText();
            
            if (Objects.equals(fn, "code")) {
                codeFieldIndex = index;
            }
            if (Objects.equals(fn, "prod_name")) {
                prodNameIndex = index;
            }
            if (Objects.equals(fn, "description")) {
                descriptionFieldIndex = index;
            }
            if (Objects.equals(fn, "plates")) {
                platesFieldIndex = index;
            }
        }
        
        JsonNode items = node.get("data").get("items");
        if (items.isNull()) {
            return;
        }
        
        Map<Long, List<TopPlate>> mapPlateOfId = plateList.stream().collect(Collectors.groupingBy(ee -> ee.getId()));
        
        List<TopStock> stocks = new ArrayList<>();
        for (Iterator<JsonNode> it = items.elements(); it.hasNext(); ) {
            JsonNode n = it.next();
            TopStock s = new TopStock();
            s.setDate(date);
            s.setCode(n.get(codeFieldIndex).asText());
            s.setProdName(n.get(prodNameIndex).asText());
            s.setDescription(n.get(descriptionFieldIndex).asText());
            
            List<TopPlate> pl = new ArrayList<>();
            JsonNode plates = n.get(platesFieldIndex);
            for (Iterator<JsonNode> pit = plates.elements(); pit.hasNext(); ) {
//                TopPlateId p = new TopPlateId();
//                p.setDate(date);
//                p.setId(pit.next().get("id").asLong());
                // log.info("pid: " + p);
                
                List<TopPlate> pp = mapPlateOfId.getOrDefault(pit.next().get("id").asLong(), Collections.emptyList());
                
                if (!pp.isEmpty()) {
                    pl.add(pp.get(0));
                    pp.get(0).getStocks().add(s);
                    //pp.get().getStocks().add(s);
                }
                
            }
            
            //s.setPlates(pl);
            stocks.add(s);
        }
        log.info("stocks: " + stocks);
        
        stockRepository.saveAll(stocks);
        plateRepository.saveAll(plateList);
        
    }

    @Override
    public Optional<Group> getGroup(Long id, LocalDate startDate, LocalDate endDate) {
        List<TopPlate> plates = plateRepository.findByIdAndDateBetween(id, startDate, endDate);
        if (plates.isEmpty()) {
            return Optional.empty();
        }
        
        String name = plates.get(0).getName();
        Set<Stock> stocks = plates
                .stream()
                .flatMap(e -> e.getStocks().stream())
                .collect(Collectors.toList())
                .stream()
                .map(e -> Stock.create(e.getCode(), e.getProdName()))
                .collect(Collectors.toSet());
        
        List<Stock> orderedStocks = new ArrayList<>();
        orderedStocks.addAll(stocks);
        orderedStocks.sort((e1, e2) -> e1.getCode().compareTo(e2.getCode()));
        Group g = new Group();
        g.setId(id);
        g.setName(name);
        g.setStocks(orderedStocks);
        return Optional.of(g);
    }

}
