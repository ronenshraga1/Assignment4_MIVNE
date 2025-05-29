public class SkipListUtils {

    public static double calculateExpectedHeight(double p) {
        if (p <= 0.0 || p >= 1.0) {
            throw new IllegalArgumentException("p must be in (0,1)");
        }
        return p / (1.0 - p);
    }

}
