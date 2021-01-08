package nl.conspect.drivedok.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum ParkingType {

    NORMAL, DISABLED, ELECTRIC;

    public static Set<ParkingType> possibleTypes() {
        return new HashSet<>(Arrays.asList(ParkingType.values()));
    }
}
