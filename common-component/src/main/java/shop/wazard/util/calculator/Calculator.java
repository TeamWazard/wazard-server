package shop.wazard.util.calculator;

import org.springframework.stereotype.Component;

@Component
public class Calculator {

    public static double calculateAttitudeScore(int tardyCount, int absentCount, int totalWorkDay) {
        double result = 10 - (10 * (tardyCount + absentCount * 5) / (double)totalWorkDay);
        return Math.round(result * 10 / 10.0);
    }

}
