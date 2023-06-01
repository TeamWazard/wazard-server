package shop.wazard.util.calculator;

import java.util.List;

public class Calculator {

    public static double getAttitudeScore(int tardyCount, int absentCount, int totalWorkDay) {
        double result = calculateAttitudeScore(tardyCount, absentCount, totalWorkDay);
        if (result < 0)
            result = 0;
        return result;
    }

    private static double calculateAttitudeScore(int tardyCount, int absentCount, int totalWorkDay) {
        double rawResult = 10 - (10 * (calculateTardyScore(tardyCount) + calculateAbsentScore(absentCount)) / (double) (totalWorkDay + absentCount));
        return roundTensPlaceScore(rawResult);
    }

    private static double calculateTardyScore(int tardyCount) {
        return tardyCount * 1.0;
    }

    private static double calculateAbsentScore(int absentCount) {
        return absentCount * 5.0;
    }

    private static double roundTensPlaceScore(double score) {
        return Math.round(score * 10) / 10.0;
    }

    public static double getAverageAttitudeScore(List<Double> totalAttitudeScore) {
        double total =  totalAttitudeScore.stream().reduce(0.0, Double::sum);
        double average = total / totalAttitudeScore.size();
        return roundTensPlaceScore(average);
    }
}
