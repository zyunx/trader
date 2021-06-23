package net.zyunx.trader.model.xgb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class XgbResponse<T> {
    private Integer code;
    private String message;
    private T data;
}
