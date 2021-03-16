import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class MathFunctionTest {
    private MathFunction mathFunction = new MathFunction();

    @ParameterizedTest
    @CsvFileSource(resources = "/rightData.csv", numLinesToSkip = 1)
    public void testArcTgRightData(double input, double expected){
        Assertions.assertEquals(expected, Math.round(mathFunction.arctgInTow(input)*1000.0)/1000.0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/badData.csv", numLinesToSkip = 1)
    public void testArcTgBadData(double input, double expected){
        Assertions.assertEquals(expected, mathFunction.arctgInTow(input));
    }
}
