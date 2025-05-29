public class HashingExperimentUtils {
    final private static int k = 16;

    public static double[] measureInsertionsProbing() {
        double[] times=new double[4];
        double[] alphas ={0.5, 0.75, 0.875, 0.9375};

        for (int i = 0; i < 4; i++) {
            ProbingHashTable<Long, Long> table = new ProbingHashTable<>(new MultiplicativeShiftingHash(),k, alphas[i]);
            int size= (int) ((1<<k)* alphas[i]);
            HashingUtils utils=new HashingUtils();
            Long[] nums=utils.genUniqueLong(size);
            long time=System.nanoTime();
            for(int j=0;j< size ;j++) {
                table.insert(nums[j], nums[j]);
            }
            times[i]= (double) (System.nanoTime() - time) /size;
        }
        return times;

    }

    public static double[] measureSearchesProbing() {
        double[] times=new double[4];
        double[] alphas ={0.5, 0.75, 0.875, 0.9375};

        for (int i = 0; i < 4; i++) {
            ProbingHashTable<Long, Long> table = new ProbingHashTable<>(new MultiplicativeShiftingHash(),k, alphas[i]);
            int size= (int) ((1<<k)* alphas[i]);
            HashingUtils utils=new HashingUtils();
            Long[] nums=utils.genUniqueLong(size*2);
            for(int j=0;j< size ;j++) {
                table.insert(nums[j], nums[j]);
            }
            long time=System.nanoTime();
            for(int j=size -10000;j<size + 10000;j++){
                long elem = table.search(nums[j]);
            }
            times[i]= (double) (System.nanoTime() - time) /size;
        }
        return times;

    }

    public static double[] measureInsertionsChaining() {
        throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
    }

    public static double[] measureSearchesChaining() {
        throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
    }
}
