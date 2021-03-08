package nl.conspect.drivedok.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum ParkingType {

    //KEEP ENUMS IN THIS ORDER FOR THE SAKE OF CORRECTLY ORDERING THE PARKINGSPOTS
    NORMAL, DISABLED, ELECTRIC, MOTOR;

    public static Set<ParkingType> possibleTypes() {
        return new HashSet<>(Arrays.asList(ParkingType.values()));
    }
}
