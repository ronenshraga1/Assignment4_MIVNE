public class TestHashTables {
    public static void main(String[] args) {
        double[] avg1 =HashingExperimentUtils.measureInsertionsProbing();
        for(double elem:avg1){
            System.out.println(elem);
        }
        double[] avg2 =HashingExperimentUtils.measureInsertionsChaining();
        for(double elem:avg2){
            System.out.println(elem);
        }

    }
}