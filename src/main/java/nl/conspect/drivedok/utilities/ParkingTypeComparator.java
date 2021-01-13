package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.ParkingSpot;

import java.util.Comparator;

public class ParkingTypeComparator implements Comparator<ParkingSpot> {

    @Override
    public int compare(ParkingSpot o1, ParkingSpot o2) {

        return o1.getParkingType().compareTo(o2.getParkingType());
    }
}
