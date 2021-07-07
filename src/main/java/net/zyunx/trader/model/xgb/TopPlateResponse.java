package net.zyunx.trader.model.xgb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopPlateResponse {
/*
 * {"code":20000,"message":"OK","data":{"items":[
 * {"id":18443089,
 * "name":"浙江国企改革",
 * "description": "中共中央、国务院发布关于支持浙江高质量发展建设共同富裕示范区的意见"},
 * {"id":-1,"name":"公告"},
 * {"id":-1,"name":"其他"}],
 * "manual_updated_at":1624376400,
 * "timestamp":1623340800}
 * }
 */
    private Integer code;
    private String message;
    private Data data;
    
    @Getter
    @Setter
    @ToString
    public static class Data {
        private List<DataItem> items;
        
        private Long timestamp;
        
        @JsonProperty("manual_updated_at")
        private Long manualUpdatedAt;
    }
    
    @Getter
    @Setter
    @ToString
    public static class DataItem {
        private Long id;
        private String name;
        private String description;
    }
}
