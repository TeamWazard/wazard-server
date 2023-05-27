package shop.wazard.util.calculator;

public class Calculator {

    public static double calculateAttitudeScore(int tardyCount, int absentCount, int totalWorkDay) {
        double result = Math.round((10 - (10 * (calculateTardyScore(tardyCount) + calculateAbsentScore(absentCount)) / (double)totalWorkDay)) * 10) / 10.0;
        if (result < 0)
            result = 0;
        return result;
    }

    private static double calculateTardyScore(int tardyCount) {
        return tardyCount;
    }

    private static double calculateAbsentScore(int absentCount) {
        return absentCount * 5;
    }

}
