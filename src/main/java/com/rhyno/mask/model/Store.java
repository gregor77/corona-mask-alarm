package com.rhyno.mask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private String addr;
    private String code;
    private Float lat;
    private Float lng;
    private String name;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "stock_at")
    private String stockAt;

    @JsonProperty(value = "remain_stat")
    private String remainStat;

    private String type;

    private RemainStat status;

    public void setRemainStat(String remainStat) {
        this.remainStat = remainStat;
        this.status = RemainStat.fromValue(remainStat);
    }

    @Override
    public String toString() {
        return "{" +
                " 주소='" + addr + '\'' +
                ", 약국명='" + name + '\'' +
                ", 입고시간='" + stockAt + '\'' +
                ", 재고상태=" + status +
                ", 데이터생성일자='" + createdAt + '\'' +
                '}';
    }
}
