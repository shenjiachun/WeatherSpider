package cn.zifangsky.model;

import com.alibaba.fastjson.JSONArray;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 代码信息
 *
 * @author chenguoxiang
 * @create 2018-10-30 15:15
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockCodeInfo {


    private Long id;

    //股票代码
    private String code;
    // 深市  沪市
    private String type;
    //股票名称
    private String name;
    //所在地域
    private String area;
    //所属行业
    private String industry;
    //股票全称
    private String fullname;
    //市场类型（主板/中小板/创业板）
    private String market;
    //上市状态L上市 D退市 P暂停上市
    private String list_status;
    //上市日期
    private Date list_date;


    /**
     * info 信息更新日
     */
    private Integer infoDate;
    /**
     * 持有人更新日
     */
    private Integer holdersDate;
    /**
     * 年报excel下载时间
     */
    private Integer yearReportDate;
    /**
     * xls下载错误数
     */
    private Integer xlsError;


    private String tsCode;

    /**
     * data 来源于 tushareSpilder
     *
     * @param data
     */
    public StockCodeInfo(JSONArray data) {
        this.code = data.getString(1);
        this.type = data.getString(0).replaceAll("\\d+\\.", "").toLowerCase();
        this.name = data.getString(2);
        this.area = data.getString(3);
        this.industry = data.getString(4);
        this.fullname = data.getString(5);
        this.market = data.getString(6);
        this.list_status = data.getString(7);
        String str = data.getString(8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date parse = sdf.parse(str);
            this.list_date = parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.infoDate = 0;
        this.holdersDate = 0;
        this.yearReportDate = 0;
        this.xlsError = 0;
        this.tsCode = data.getString(0);

    }

}
