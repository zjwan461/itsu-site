package com.itsu.core.util;

import java.util.regex.Pattern;

/**
 * @ClassName: TimeUtil
 * @Description: 时间换算
 * @Author: liqingquan
 * @Date: 2021/4/20 8:36
 */
public final class TimeUtil {

    /**
     * ToSecond
     *
     * @param time 1d | 1D | 2h | 2H | 30m | 30M | 20s | 20S
     * @return second(Long) | null
     */
    public static Long toSecond(String time){
        Long second = null;
        if (formatPass("^\\d+d|h|m|s$", time)){
            String unit = time.replaceAll("\\d", "");
            Long number = Long.valueOf(time.replace(unit, ""));
            second = transform(number, unit);
        }
        return second;
    }

    /**
     * ToMillis
     *
     * @param time 1d | 1D | 2h | 2H | 30m | 30M | 20s | 20S | 30ms | 30MS
     * @return millis(Long) | null
     */
    public static Long toMillis(String time){
        Long m = null;
        if (formatPass("^\\d+d|h|m|s|ms$", time)){
            String unit = time.replaceAll("\\d", "");
            Long number = Long.valueOf(time.replace(unit, ""));
            if ("ms".equalsIgnoreCase(unit)){
                m = number;
            }else{
                m = transform(number, unit);
                if (m != null){
                   m *= 1000L;
                }
            }
        }
        return m;
    }

    private static boolean formatPass(String regex, String time){
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return (time != null) && pattern.matcher(time).find();
    }

    private static Long transform(Long number, String unit){
        Long second = null;
        if ("d".equalsIgnoreCase(unit)){
            second = number * 24 * 3600L;
        } else if ("h".equalsIgnoreCase(unit)){
            second = number * 3600L;
        } else if ("m".equalsIgnoreCase(unit)){
            second = number * 60L;
        } else if ("s".equalsIgnoreCase(unit)){
            second = number;
        }
        return second;
    }

}
