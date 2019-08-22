package cn.train.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StringUtils {
    public static String getString(Object o){
        if (o == null){
            return "";
        }
        if (o instanceof String){
            return o.toString();
        }else{
            return String.valueOf(o);
        }
    }

    public static boolean isNullOrEmpty(String str){
        if (str == null){
            return true;
        }else if ("".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

    public static Date getBirthDay(String icNumber){
        Date birth = null;
        if(isNullOrEmpty(icNumber))
            return null;
        if (icNumber.length()==18){
            String birthDay = icNumber.substring(6,14);
            try{
                TimeZone cdtTime = TimeZone.getTimeZone("CDT");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                sdf.setTimeZone(cdtTime);
                birth = sdf.parse(birthDay);
            }catch (ParseException e){
                return null;
            }
        }
        return birth;
    }
}
