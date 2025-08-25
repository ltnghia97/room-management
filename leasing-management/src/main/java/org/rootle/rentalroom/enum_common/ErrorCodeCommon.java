package org.rootle.rentalroom.enum_common;

import lombok.Getter;
import org.rootle.rentalroom.base.exception.ErrorException;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ErrorCodeCommon implements ErrorException {
    //    ACCESS_CARD_REACH_LIMITED(-43),
//    RESIDENT_PERMISSION_DENIED(-42),
//    RESIDENT_NAME_HAS_EXISTED(-41),
//    RESIDENT_EMAIL_HAS_EXISTED(-40),
//    RESIDENT_PHONE_NUMBER_HAS_EXISTED(-39),
//    RESIDENT_ID_CARD_NUMBER_HAS_EXISTED(-38),
//    RESIDENT_HAS_EXISTED(-37),
//    INVALID_FORMAT_PASSWORD(-36),
//    EMAIL_HAS_EXISTED(-35),
//    INVALID_EMAIL(-34),
//    INVITATION_IS_BELONG_TO_CUSTOMER_PROFILE(-33),
//    UPDATE_PHONE_NUMBER_IS_CURRENTLY_NOT_ALLOWED(-32),
//    INVITATION_HAS_EXISTED(-31),
//    INVITATION_NOT_FOUND(-30),
//    INVALID_INVITATION(-29),
//    INVALID_PASSWORD(-28),
//    INVALID_LEAD_CONTACT(-27),
//    PASSWORD_CHANGE_IS_CURRENTLY_NOT_ALLOWED(-26),
//    USER_REGISTRATION_IS_CURRENTLY_NOT_ALLOWED(-25),
//    CANT_NOT_SYNC_CRM_CONTACT(-24),
//    ID_CARD_NOT_FOUND(-23),
//    INVALID_VOUCHER(-22),
//    GUIDANCE_HAS_EXISTED(-21),
//    PERMISSION_DENIED(-20),
//    PASSWORD_DOES_NOT_MATCH(-19),
//    OTP_EXPIRED(-18),
//    INVALID_PHONE_NUMBER(-17),
//    BOOKING_LAUNCH_EVENT_EXCEPTION(-16),
//    BOOKING_DAILY_SALE_EXCEPTION(-15),
//    BOOKING_CODE_NOT_FOUND(-14),
//    WRONG_PASSWORD(-13),
//    INVALID_CUSTOMER_CODE(-12),
//    INVALID_JWT(-11),
//    LOGOUT_EXCEPTION(-10),
//    JWT_OVERLAP(-9),
//    INVALID_OTP(-8),
//    JWT_EXPIRED(-7),
//    MISSING_AUTHORIZATION(-6),
//    AUTHORIZATION_EXCEPTION(-5),
//    CUSTOMER_NOT_FOUND(-4),
//    LOGIN_EXCEPTION(-3),
//    HTTP_EXCEPTION(-2),
//    FAIL(-1),
//    SUCCESS(1),
//    UPDATE_SUCCESS(2),
    UN_DEFINED(0),
    PHONE_NUMBER_EXISTED(-99);


    private static final Map<String, ErrorCodeCommon> mapByName = new HashMap();
    private static final Map<Integer, ErrorCodeCommon> mapById = new HashMap();
    private int value;

    private ErrorCodeCommon(int value) {
        this.value = value;
    }

    public static ErrorCodeCommon getByValue(int value) {
        return (ErrorCodeCommon) mapById.get(value);
    }

    public static ErrorCodeCommon getByName(String name) {
        return (ErrorCodeCommon) mapByName.get(name);
    }

    public Integer getErrorCode() {
        return this.value;
    }

    public String getErrorName() {
        return ((ErrorCodeCommon) mapById.getOrDefault(this.value, UN_DEFINED)).name();
    }

    static {
        for (ErrorCodeCommon value : values()) {
            mapByName.put(value.name(), value);
            mapById.put(value.getValue(), value);
        }

    }
}