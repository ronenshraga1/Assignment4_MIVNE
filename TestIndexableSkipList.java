public class TestIndexableSkipList {
    public static void main(String[] args) {
        IndexableSkipList skiplist = new IndexableSkipList(0.5);

        System.out.println("=== STARTING INDEXABLE SKIPLIST TESTS ===\n");

        // Test 1: Insert 10, 20, 30 and check minimum and maximum
        System.out.println("Test 1: Insert 10, 20, 30 and check minimum and maximum");
        skiplist.insert(10);
        skiplist.insert(20);
        skiplist.insert(30);

        assertEquals(10, skiplist.minimum().key(), "Minimum after inserts");
        assertEquals(30, skiplist.maximum().key(), "Maximum after inserts");
        System.out.println();

        // Test 2: Search for existing (20) and non-existing (25) keys
        System.out.println("Test 2: Search for existing (20) and non-existing (25) keys");
        assertEquals(20, skiplist.search(20).key(), "Search existing key 20");
        assertNull(skiplist.search(25), "Search non-existing key 25");
        System.out.println();

        // Test 3: Select elements by index
        System.out.println("Test 3: Select elements by index");
        try {
            assertEquals(10, skiplist.select(0), "Select index 0");
            assertEquals(20, skiplist.select(1), "Select index 1");
            assertEquals(30, skiplist.select(2), "Select index 2");
        } catch (Exception e) {
            System.out.println("❌ Select test threw exception: " + e.getMessage());
        }
        System.out.println();

        // Test 4: Rank of existing and non-existing values
        System.out.println("Test 4: Rank of existing and non-existing values");
        assertEquals(0, skiplist.rank(10), "Rank of 10");
        assertEquals(1, skiplist.rank(20), "Rank of 20");
        assertEquals(2, skiplist.rank(30), "Rank of 30");
        assertEquals(3, skiplist.rank(40), "Rank of non-existing key > max (40)");
        System.out.println();

        // Test 5: Successor and Predecessor of node with key 20
        System.out.println("Test 5: Successor and Predecessor of node with key 20");
        IndexableSkipList.SkipListNode node20 = skiplist.search(20);
        assertEquals(30, skiplist.successor(node20).key(), "Successor of 20");
        assertEquals(10, skiplist.predecessor(node20).key(), "Predecessor of 20");
        System.out.println();

        // Test 6: Delete key 10 and verify removal
        System.out.println("Test 6: Delete key 10 and verify removal");
        IndexableSkipList.SkipListNode node10 = skiplist.search(10);
        skiplist.delete(node10);
        assertNull(skiplist.search(10), "Search after deleting 10");
        assertEquals(20, skiplist.minimum().key(), "Minimum after deleting 10");
        System.out.println();

        // Test 7: Select and Rank after deleting 10
        System.out.println("Test 7: Select and Rank after deleting 10");
        assertEquals(20, skiplist.select(0), "Select index 0 after delete");
        assertEquals(30, skiplist.select(1), "Select index 1 after delete");
        assertEquals(0, skiplist.rank(20), "Rank of 20 after delete");
        assertEquals(1, skiplist.rank(30), "Rank of 30 after delete");
        System.out.println();

        // Test 8: Select with out-of-bounds index (should throw exception)
//        System.out.println("Test 8: Select with out-of-bounds index (should throw exception)");
//        try {
//            skiplist.select(5);
//            System.out.println("❌ FAIL: Select with out-of-bounds index did not throw exception");
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("✅ PASS: Select with out-of-bounds index threw exception");
//        }
//        System.out.println();

            // Test 9: Bulk insert 1000 elements and check ranks/selects
            System.out.println("Test 9: Bulk insert 1000 elements and check ranks/selects");
            for (int i = 1; i <= 1000; i++) {
                skiplist.insert(i * 10);
            }
            assertEquals(1, skiplist.rank(20), "Rank of 20 after bulk insert");
            assertEquals(2, skiplist.rank(30), "Rank of 30 after bulk insert");
            assertEquals(999, skiplist.rank(10000), "Rank of 10000 after bulk insert");
            assertEquals(100, skiplist.select(9), "Select index 10 after bulk insert");
            System.out.println();

        // Test 10: toString output check
        System.out.println("Test 10: toString output check");
        String listStr = skiplist.toString();
        if (listStr == null || listStr.isEmpty()) {
            System.out.println("❌ FAIL: toString returned empty string");
        } else {
            System.out.println("✅ PASS: toString returned non-empty string");
            System.out.println("--- SkipList Contents ---");
            System.out.println(listStr);
            System.out.println("------------------------");
        }

        System.out.println("=== ALL TESTS COMPLETED ===");
    }

    private static void assertEquals(int expected, int actual, String testName) {
        if (expected == actual) {
            System.out.println("✅ PASS: " + testName + " (Expected = Actual = " + expected + ")");
        } else {
            System.out.printf("❌ FAIL: %s - Expected: %d, Got: %d%n", testName, expected, actual);
        }
    }

    private static void assertNull(Object obj, String testName) {
        if (obj == null) {
            System.out.println("✅ PASS: " + testName + " (Expected null, Got null)");
        } else {
            System.out.printf("❌ FAIL: %s - Expected: null, Got: %s%n", testName, obj.toString());
        }
    }
}
