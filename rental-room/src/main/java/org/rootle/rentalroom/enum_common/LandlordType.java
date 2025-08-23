package org.rootle.rentalroom.enum_common;

import java.util.stream.Stream;

public enum LandlordType {
    INDIVIDUAL,
    BUSINESS;

    public static boolean isValid(String type) {
        if (type == null) {
            return false;
        }
        return Stream.of(LandlordType.values())
                .anyMatch(landlordType -> landlordType.name().equals(type.toUpperCase()));
    }
}