package cn.zifangsky.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


@Data
@ToString
@Builder
public class StockTopHolders {




    private Long id;

    private String code;

    private Date annDate;

    private Date endDate;

    private String holderName;

    private BigDecimal holdAmount;

    private BigDecimal holdRatio;


}