package uek335.project.stepcounter;

import org.junit.Test;

import static org.junit.Assert.*;

import kotlinx.coroutines.internal.FastServiceLoader;
import uek335.project.stepcounter.service.DistanceCalculationService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private DistanceCalculationService distanceCalculationService;
    @Test
    public void addition_isCorrect() {
    int countValue = 100;
    String stepLength = "100";
    String expectedDistance = "0.1";

    String actualFormattedDistance = DistanceCalculationService.calculateDistance(stepLength, countValue);

        assertEquals(expectedDistance, actualFormattedDistance);
    }
}