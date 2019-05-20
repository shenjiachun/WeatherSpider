package cn.zifangsky.model;

import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ZqInfo {
    private Long id;

    private String code;

    private String name;

    private Date zqDate;

    private String result;

    private Date createdAt;

    private String ztType;


}