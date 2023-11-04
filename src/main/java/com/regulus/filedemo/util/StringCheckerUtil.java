package com.regulus.filedemo.util;

import com.regulus.filedemo.exception.AppException;
import com.regulus.filedemo.exception.AppExceptionCodeMsg;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-11-02
 */
public class StringCheckerUtil {
    public static void notEmptyChecker(String string) {
        if(string.equals(""))
            throw new AppException(AppExceptionCodeMsg.REQUEST_NOT_EMPTY);
    }
    public static void isValidString(String input, int maxLength) {
        if (input == null || input.isEmpty()) {
            throw new AppException(AppExceptionCodeMsg.UNPROCESSABLE_ENTITY);
        }

        // 检查是否包含空格
        if (input.contains(" ")) {
            throw new AppException(AppExceptionCodeMsg.UNPROCESSABLE_ENTITY);
        }

        // 检查长度是否大于maxLength
        if (input.length() > maxLength) {
            throw new AppException(AppExceptionCodeMsg.UNPROCESSABLE_ENTITY);
        }


    }

}
