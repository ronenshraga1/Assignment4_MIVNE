import java.util.List;

public class TestMyDataStructure {
    public static void main(String[] args) {
        int failed = 0;

        // initialize with capacity N = 100
        MyDataStructure ds = new MyDataStructure(100);

        // Test 1: contains on empty
        if (!ds.contains(10)) {
            System.out.println("✅ PASS: contains(10) on empty → false");
        } else {
            System.out.println("❌ FAIL: contains(10) on empty should be false");
            failed++;
        }

        // Test 2: insert & contains & duplicate
        if (ds.insert(10)) {
            System.out.println("✅ PASS: insert(10) first time → true");
        } else {
            System.out.println("❌ FAIL: insert(10) first time should be true");
            failed++;
        }
        if (ds.contains(10)) {
            System.out.println("✅ PASS: contains(10) after insert → true");
        } else {
            System.out.println("❌ FAIL: contains(10) after insert should be true");
            failed++;
        }
        if (!ds.insert(10)) {
            System.out.println("✅ PASS: insert(10) duplicate → false");
        } else {
            System.out.println("❌ FAIL: insert(10) duplicate should be false");
            failed++;
        }

        // Test 3: delete non-existent
        if (!ds.delete(5)) {
            System.out.println("✅ PASS: delete(5) on empty → false");
        } else {
            System.out.println("❌ FAIL: delete(5) on empty should be false");
            failed++;
        }

        // Test 4: delete existing then contains
        ds.insert(20);
        if (ds.delete(20)) {
            System.out.println("✅ PASS: delete(20) after insert → true");
        } else {
            System.out.println("❌ FAIL: delete(20) after insert should be true");
            failed++;
        }
        if (!ds.contains(20)) {
            System.out.println("✅ PASS: contains(20) after delete → false");
        } else {
            System.out.println("❌ FAIL: contains(20) after delete should be false");
            failed++;
        }
        if (!ds.delete(20)) {
            System.out.println("✅ PASS: delete(20) again → false");
        } else {
            System.out.println("❌ FAIL: delete(20) again should be false");
            failed++;
        }

        // Test 5: rank
        ds = new MyDataStructure(100);
        int[] vals1 = {5, 1, 9, 3};
        for (int v : vals1) ds.insert(v);
        // sorted: [1,3,5,9]
        if (ds.rank(1) == 0) {
            System.out.println("✅ PASS: rank(1)=0");
        } else {
            System.out.println("❌ FAIL: rank(1) should be 0 but was " + ds.rank(1));
            failed++;
        }
        if (ds.rank(3) == 1) {
            System.out.println("✅ PASS: rank(3)=1");
        } else {
            System.out.println("❌ FAIL: rank(3) should be 1 but was " + ds.rank(3));
            failed++;
        }
        if (ds.rank(4) == 2) {
            System.out.println("✅ PASS: rank(4)=2");
        } else {
            System.out.println("❌ FAIL: rank(4) should be 2 but was " + ds.rank(4));
            failed++;
        }
        if (ds.rank(9) == 3) {
            System.out.println("✅ PASS: rank(9)=3");
        } else {
            System.out.println("❌ FAIL: rank(9) should be 3 but was " + ds.rank(9));
            failed++;
        }
        if (ds.rank(100) == 4) {
            System.out.println("✅ PASS: rank(100)=4");
        } else {
            System.out.println("❌ FAIL: rank(100) should be 4 but was " + ds.rank(100));
            failed++;
        }

        // Test 6: select (assume all indexes are valid)
        ds = new MyDataStructure(100);
        int[] vals2 = {7, 2, 8, 4};
        for (int v : vals2) ds.insert(v);
        // sorted: [2,4,7,8]
        if (ds.select(0) == 2) {
            System.out.println("✅ PASS: select(0)=2");
        } else {
            System.out.println("❌ FAIL: select(0) should be 2 but was " + ds.select(0));
            failed++;
        }
        if (ds.select(1) == 4) {
            System.out.println("✅ PASS: select(1)=4");
        } else {
            System.out.println("❌ FAIL: select(1) should be 4 but was " + ds.select(1));
            failed++;
        }
        if (ds.select(2) == 7) {
            System.out.println("✅ PASS: select(2)=7");
        } else {
            System.out.println("❌ FAIL: select(2) should be 7 but was " + ds.select(2));
            failed++;
        }
        if (ds.select(3) == 8) {
            System.out.println("✅ PASS: select(3)=8");
        } else {
            System.out.println("❌ FAIL: select(3) should be 8 but was " + ds.select(3));
            failed++;
        }

        // Test 7: range
        ds = new MyDataStructure(100);
        int[] vals3 = {10, 5, 20, 15};
        for (int v : vals3) ds.insert(v);
        // set = [5,10,15,20]
        List<Integer> r1 = ds.range(10, 20);
        if (r1 != null && r1.equals(List.of(10,15,20))) {
            System.out.println("✅ PASS: range(10,20) → [10,15,20]");
        } else {
            System.out.println("❌ FAIL: range(10,20) should be [10,15,20] but was " + r1);
            failed++;
        }
        List<Integer> r2 = ds.range(5,5);
        if (r2 != null && r2.equals(List.of(5))) {
            System.out.println("✅ PASS: range(5,5) → [5]");
        } else {
            System.out.println("❌ FAIL: range(5,5) should be [5] but was " + r2);
            failed++;
        }
        if (ds.range(12,20) == null) {
            System.out.println("✅ PASS: range(12,20) → null when low not present");
        } else {
            System.out.println("❌ FAIL: range(12,20) low-absent should be null");
            failed++;
        }
        List<Integer> r3 = ds.range(15,12);
        if (r3 != null && r3.isEmpty()) {
            System.out.println("✅ PASS: range(15,12) low-present, high<low → []");
        } else {
            System.out.println("❌ FAIL: range(15,12) should be [] but was " + r3);
            failed++;
        }
        List<Integer> r4 = ds.range(5,20);
        if (r4 != null && r4.equals(List.of(5,10,15,20))) {
            System.out.println("✅ PASS: range(5,20) → [5,10,15,20]");
        } else {
            System.out.println("❌ FAIL: range(5,20) should be [5,10,15,20] but was " + r4);
            failed++;
        }

        // Summary
        System.out.println();
        if (failed == 0) {
            System.out.println("ALL TESTS PASSED ✅");
        } else {
            System.out.printf("%d TEST(S) FAILED ❌%n", failed);
        }
    }
}
