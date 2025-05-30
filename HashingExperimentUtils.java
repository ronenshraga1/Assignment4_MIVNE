public class HashingExperimentUtils {
    final private static int k = 16;

    public static double[] measureInsertionsProbing() {
        double[] times=new double[4];
        double[] alphas ={0.5, 0.75, 0.875, 0.9375};

        for (int i = 0; i < 4; i++) {
            ProbingHashTable<Long, Long> hashTable = new ProbingHashTable<>(new MultiplicativeShiftingHash(),k, alphas[i]);
            int size= (int) ((1<<k)* alphas[i]);
            HashingUtils hashUtils =new HashingUtils();
            Long[] numbers = hashUtils.genUniqueLong(size);
            long time=System.nanoTime();
            for(int j=0;j< size ;j++) {
                hashTable.insert(numbers[j], numbers[j]);
            }
            times[i]= (double) (System.nanoTime() - time) /size;
        }
        return times;

    }

    public static double[] measureSearchesProbing() {
        double[] times=new double[4];
        double[] alphas ={0.5, 0.75, 0.875, 0.9375};
        for (int i = 0; i < 4; i++) {
            ProbingHashTable<Long, Long> hashTable = new ProbingHashTable<>(new MultiplicativeShiftingHash(),k, alphas[i]);
            int size= (int) ((1<<k)* alphas[i]);
            HashingUtils hashUtils =new HashingUtils();
            Long[] numbers = hashUtils.genUniqueLong(size*2);
            for(int j=0;j< size ;j++) {
                hashTable.insert(numbers[j], numbers[j]);
            }
            long time=System.nanoTime();
            for(int k = size -10000; k <size + 10000; k++){
                Long elem = hashTable.search(numbers[k]);
            }
            times[i]= (double) (System.nanoTime() - time) /20000;
        }
        return times;
    }

    public static double[] measureInsertionsChaining() {
        double[] times=new double[5];
        double[] alphas ={0.5, 0.75, 1, 1.5,2};
        for (int i = 0; i < 5; i++) {
            ChainedHashTable<Long, Long> hashTable = new ChainedHashTable<>(new MultiplicativeShiftingHash(),k, alphas[i]);
            int size= (int) ((1<<k)* alphas[i]);
            HashingUtils hashUtils =new HashingUtils();
            Long[] numbers = hashUtils.genUniqueLong(size);
            long time=System.nanoTime();
            for(int j=0;j< size ;j++) {
                hashTable.insert(numbers[j], numbers[j]);
            }
            times[i]= (double) (System.nanoTime() - time) /size;
        }
        return times;
    }

    public static double[] measureSearchesChaining() {
        double[] times=new double[5];
        double[] alphas ={0.5, 0.75, 1, 1.5,2};
        for (int i = 0; i < 5; i++) {
            ChainedHashTable<Long, Long> hashTable = new ChainedHashTable<>(new MultiplicativeShiftingHash(),k, alphas[i]);
            int size= (int) ((1<<k)* alphas[i]);
            HashingUtils hashUtils=new HashingUtils();
            Long[] numbers=hashUtils.genUniqueLong(size*2);
            for(int k = 0; k < size ; k++) {
                hashTable.insert(numbers[k], numbers[k]);
            }
            long time=System.nanoTime();
            for(int j=size -10000;j<size + 10000;j++){
                Long elem = hashTable.search(numbers[j]);
            }
            times[i]= (double) (System.nanoTime() - time) /20000;
        }
        return times;
    }
}
