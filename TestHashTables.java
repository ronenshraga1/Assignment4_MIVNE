public class TestHashTables {
    public static void main(String[] args) {
        double[] avg1 =HashingExperimentUtils.measureInsertionsProbing();
        for(double elem:avg1){
            System.out.println(elem);
        }
    }
}