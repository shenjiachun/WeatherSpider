package cn.zifangsky.model;

import lombok.*;

import java.util.Date;


@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StockInfo {

    private Long id;

    private String code;

    private String ztText;

    private String ztType;

    private Date zqDate;

    private String ztdesc;

    private Integer isDeal;



}