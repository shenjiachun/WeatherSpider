package cn.zifangsky.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@ToString
@Builder
@Data
public class StockInfo {

    private Long id;

    private String code;

    private String ztText;

    private String ztType;

    private Date zqDate;

    private String ztdesc;

}