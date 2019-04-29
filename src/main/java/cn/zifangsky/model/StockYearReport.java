package cn.zifangsky.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Builder
public class StockYearReport {

    private Long id;

    private BigDecimal mgsy;

    private String code;

    private String type;

    private Date reportDate;

    private String content;

    private Date createdAt;

    private String desc;

    private BigDecimal jlr;

    private BigDecimal zzl;


}