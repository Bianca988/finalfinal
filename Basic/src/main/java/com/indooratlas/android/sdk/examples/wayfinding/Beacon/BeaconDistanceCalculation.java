package com.indooratlas.android.sdk.examples.wayfinding.Beacon;

import com.nexenio.bleindoorpositioning.ble.beacon.Beacon;
import com.nexenio.bleindoorpositioning.ble.beacon.IBeacon;
import com.nexenio.bleindoorpositioning.location.distance.BeaconDistanceCalculator;

import static com.nexenio.bleindoorpositioning.location.distance.BeaconDistanceCalculator.calculateDistance;

public class BeaconDistanceCalculation {

    public static final float PATH_LOSS_PARAMETER_OPEN_SPACE = 2;
    public static final float PATH_LOSS_PARAMETER_INDOOR = 1.7f;
    public static final float PATH_LOSS_PARAMETER_OFFICE_HARD_PARTITION = 3f;
    public static final int CALIBRATED_RSSI_AT_ONE_METER = -62;
    public static final int SIGNAL_LOSS_AT_ONE_METER = -41;
    private static float pathLossParameter = PATH_LOSS_PARAMETER_OFFICE_HARD_PARTITION;

    public static float calculateDistanceTo(Beacon beacon) {
        return calculateDistanceTo(beacon);
    }

    public static float calculateDist(Beacon beacon, float rssi, double device) {
        float distance = calculateDistanceTo(beacon, rssi);
        if (beacon.hasLocation() && beacon.getLocation().hasElevation()) {
            double elevD = Math.abs(beacon.getLocation().getElevation() - device);
            if (elevD > 0 && distance > (elevD * 2)) {
                double delta = Math.pow(distance, 2) - Math.pow(elevD, 2);
                return (float) Math.sqrt(delta);
            } else {
                return distance;
            }
        } else {
            return distance;
        }
    }
    public static float calculateDistanceTo(Beacon beacon , float rssi)
    {
        return calculateDistance(rssi,beacon.getCalibratedRssi(), beacon.getCalibratedDistance());

    }

    public static float getCalibratedRssiAtOneMeter(float calibratedRssi , float calibratedDistance)
    {
        float calibratedRssiAtOneMeter = 0;
        if(calibratedDistance == IBeacon.CALIBRATION_DISTANCE_DEFAULT)
        {
            calibratedRssiAtOneMeter = calibratedRssi;
        }
        return calibratedRssiAtOneMeter;
    }
    public static float calculateDistance(float rssi, float calibratedRssi , float pathLossParam)
    {
        return (float) Math.pow(10,(calibratedRssi - rssi ) / (10* pathLossParam));
    }

    public static void setPathLossParameter(float pathLossParameter)
    {
        BeaconDistanceCalculator.setPathLossParameter(pathLossParameter);
    }

    public static float getPathLossParameter ()
    {
        return BeaconDistanceCalculator.getPathLossParameter();
    }
}

