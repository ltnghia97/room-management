package org.rootle.rentalroom.util;

import java.util.regex.Pattern;

public class ValidUtil{
    // Regex cho email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Regex cho số điện thoại Việt Nam (ví dụ: 0912345678 hoặc +84912345678)
    private static final String PHONE_REGEX = "^(0|\\+84)(\\d{9,10})$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // Hàm kiểm tra một chuỗi có null, rỗng, hoặc chỉ chứa khoảng trắng hay không
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Hàm kiểm tra định dạng email
    public static boolean isValidEmail(String email) {
        if (isNullOrBlank(email)) {
            return false;
        }
        return !EMAIL_PATTERN.matcher(email).matches();
    }

    // Hàm kiểm tra định dạng số điện thoại
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrBlank(phoneNumber)) {
            return false;
        }
        return !PHONE_PATTERN.matcher(phoneNumber).matches();
    }

}
