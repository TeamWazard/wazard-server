package shop.wazard.util.calculator;

public class Calculator {

    public static double getAttitudeScore(int tardyCount, int absentCount, int totalWorkDay) {
        double result = calculateAttitudeScore(tardyCount, absentCount, totalWorkDay);
        if (result < 0)
            result = 0;
        return result;
    }

    private static double calculateAttitudeScore(int tardyCount, int absentCount, int totalWorkDay) {
        double rawResult = 10 - (10 * (calculateTardyScore(tardyCount) + calculateAbsentScore(absentCount)) / (double) totalWorkDay);
        return Math.round(rawResult * 10) / 10.0;
    }

    private static double calculateTardyScore(int tardyCount) {
        return tardyCount * 1.0;
    }

    private static double calculateAbsentScore(int absentCount) {
        return absentCount * 5.0;
    }

}
