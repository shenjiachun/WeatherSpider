package cn.zifangsky.common;

/**
 * @author shenjiachun
 * @create 2019/5/15
 * @description
 */
public class GlobalConstant {


    public enum ZQEnum {

        ZT("1","涨停揭秘", "涨停揭秘"),
        YD("2","异动揭秘", "异动揭秘");

        private String code;

        private String name;

        private String desc;


        ZQEnum(String code, String name, String desc) {
            this.code = code;
            this.name = name;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }


        public static ZQEnum codeOf(String name) {
            for (ZQEnum zqEnum : values()) {
                if (zqEnum.getName().equals(name)) {
                    return zqEnum;
                }
            }
            return null;
        }
    }


}
