package shop.wazard.util.calculator;

import java.util.List;

public class Calculator {

    public static double getAttitudeScore(int tardyCount, int absentCount, int workDayCount) {
        double result = calculateAttitudeScore(tardyCount, absentCount, workDayCount);
        if (result < 0) result = 0;
        return result;
    }

    private static double calculateAttitudeScore(
            int tardyCount, int absentCount, int workDayCount) {
        double penaltyPoint = calculatePenaltyPoint(tardyCount, absentCount, workDayCount);
        double rawResult = 10 * (1 - penaltyPoint);
        return roundTensPlaceScore(rawResult);
    }

    private static double calculatePenaltyPoint(int tardyCount, int absentCount, int workDayCount) {
        return (calculateTardyScore(tardyCount) + calculateAbsentScore(absentCount))
                / sumTotalWorkDay(workDayCount, absentCount);
    }

    private static int sumTotalWorkDay(int workDayCount, int absentCount) {
        return workDayCount + absentCount;
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
        double total = totalAttitudeScore.stream().reduce(0.0, Double::sum);
        double average = total / totalAttitudeScore.size();
        return roundTensPlaceScore(average);
    }
}
