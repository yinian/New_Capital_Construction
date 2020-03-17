package com.viagra.wechatordering.utils;

import com.viagra.wechatordering.enums.CodeEnum;

/**
 * @Auther: viagra
 * @Date: 2020/2/16 12:24
 * @Description:
 */

public class EnumUtils {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }

        return null;
    }


}
