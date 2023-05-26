package shop.wazard.util.calculator;

import org.springframework.stereotype.Component;

@Component
public class Calculator {

    public static double calculateAttitudeScore(int tardyCount, int absentCount, int totalWorkDay) {
        double result = Math.round((10 - (10 * (tardyCount + absentCount * 5) / (double)totalWorkDay)) * 10 / 10.0);
        if (result < 0)
            result = 0;
        return result;
    }

}
