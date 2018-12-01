package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LongMapImplTest {


    @Test
    public void putTestAfterPutSizeMustBeEqualsOne() {
        //Given
        int expectedSize = 1;
        LongMap<String> longMap = new LongMapImpl<>();
        //When
        longMap.put(10L, "some String");
        //Then
        Assert.assertEquals(expectedSize, longMap.size());
    }

    @Test
    public void putLargeDataTestAfterPutSizeMustBeEquals100() {
        //Given
        Random r = new Random();
        int expectedSize = 100;
        LongMap<Long> longMap = new LongMapImpl<>();
        //When
        for (long i = 0; i < 100; i++) {
            longMap.put(i, r.nextLong());
        }
        //Then
        Assert.assertEquals(expectedSize, longMap.size());
    }
    @Test
    public void putTestAfterAddingTwoIdenticalObjectSizeShouldNotChange(){
        //Given
        int expectedSize = 1;
        LongMap<String> longMap = new LongMapImpl<>();
        //When
        longMap.put(10L, "some String");
        longMap.put(10L, "some String");
        //Then
        Assert.assertEquals(expectedSize, longMap.size());
    }

    @Test
    public void getTestValueFromMapMustBeEqualsToExpected() {
        //Given
        long key = 10L;
        String expectedString = "expected string";
        LongMap<String> longMap = new LongMapImpl<>();
        //When
        longMap.put(key, expectedString);
        //Then
        String resultString = longMap.get(key);
        Assert.assertEquals(expectedString, resultString);
    }
    @Test
    public void getTestGetFromEmptyMapMustReturnNull(){
        //Given
        LongMap longMap = new LongMapImpl();
        //When
        Object res = longMap.get(10L);
        //Then
        Assert.assertNull(res);

    }

    @Test
    public void removeTestAfterRemoveSizeMustBeEqualsToExpected() {
        //Given
        int expectedSize = 4;
        LongMap<String> longMap = new LongMapImpl<>();
        //When
        longMap.put(1L, "Value1");
        longMap.put(10L, "Value10");
        longMap.put(45L, "Value45");
        longMap.put(60L, "Value160");
        longMap.put(75L, "Value75");

        longMap.remove(1L);
        //Then
        Assert.assertEquals(expectedSize, longMap.size());
    }

    @Test
    public void removeTestRemoveMethodMustReturnRemovedValue() {
        //Given
        LongMap<Double> longMap = new LongMapImpl<>();
        double expectedValue = 25.0;
        //When
        double realValue = longMap.put(33L, expectedValue);
        //Then
        Assert.assertEquals(expectedValue, realValue, 0.0);
    }

    @Test
    public void isEmptyTestAfterMapCreationMethodMustReturnTrue() {
        //Given
        boolean expected = true;
        LongMap longMap = new LongMapImpl();
        //When
        boolean real = longMap.isEmpty();
        //Then
        Assert.assertEquals(real, expected);
    }

    @Test
    public void isEmptyTestAfterAddElementMethodMustReturnFalse() {
        //Given
        boolean expected = false;
        LongMap<String> longMap = new LongMapImpl<>();
        //When
        longMap.put(1L, "some string");
        boolean real = longMap.isEmpty();
        //Then
        Assert.assertEquals(real, expected);
    }

    @Test
    public void containsKeyTestMustReturnTrue() {
        //Given
        boolean expected = true;
        LongMap<Integer> longMap = new LongMapImpl<>();
        //When
        longMap.put(152L, 152);
        boolean real = longMap.containsKey(152L);
        //Then
        Assert.assertEquals(real, expected);
    }

    @Test
    public void containsKeyTestMustReturnFalse() {
        //Given
        boolean expected = false;
        LongMap<Integer> longMap = new LongMapImpl<>();
        //When
        longMap.put(999L, 999);
        boolean real = longMap.containsKey(100);
        //Then
        Assert.assertEquals(real, expected);
    }

    @Test
    public void containsValueTestMustReturnTrue() {
        //Given
        boolean expected = true;
        LongMap<Integer> longMap = new LongMapImpl<>();
        //When
        longMap.put(152L, 152);
        boolean real = longMap.containsValue(152);
        //Then
        Assert.assertEquals(real, expected);
    }

    @Test
    public void containsValueTestMustReturnFalse() {
        //Given
        boolean expected = false;
        LongMap<Integer> longMap = new LongMapImpl<>();
        //When
        longMap.put(999L, 999);
        boolean real = longMap.containsValue(100);
        //Then
        Assert.assertEquals(real, expected);
    }

    @Test
    public void keysTestMustReturnArrayOfKeysEqualsToExpected() {
        //Given
        List<Long> longList = Arrays.asList(10L, 20L, 152L);
        LongMap<Float> longMap = new LongMapImpl<>();
        //When
        longMap.put(10, 25.0f);
        longMap.put(20, 45.0f);
        longMap.put(152, 135.0f);

        long[] realArray = longMap.keys();
        //Then
        Assert.assertTrue(longList.contains(realArray[0]));
        Assert.assertTrue(longList.contains(realArray[1]));
        Assert.assertTrue(longList.contains(realArray[2]));
    }

    @Test
    public void valuesTestMustReturnArrayOfValuesEqualsToExpected() {
        //Given
        List<Float> longList = Arrays.asList(25.0f, 45.0f, 135.0f);
        LongMap<Float> longMap = new LongMapImpl<>();
        //When
        longMap.put(10, 25.0f);
        longMap.put(20, 45.0f);
        longMap.put(152, 135.0f);

        Float[] realArray = longMap.values();
        //Then
        Assert.assertTrue(longList.contains(realArray[0]));
        Assert.assertTrue(longList.contains(realArray[1]));
        Assert.assertTrue(longList.contains(realArray[2]));
    }

    @Test
    public void clearTestAfterClearSizeMustBeEqualZero() {
        //Given
        int expectedSize = 0;
        LongMap<String> longMap = new LongMapImpl<>();
        //When
        longMap.put(1L, "Value1");
        longMap.put(10L, "Value10");
        longMap.put(45L, "Value45");
        longMap.put(60L, "Value60");
        longMap.put(75L, "Value75");

        longMap.clear();
        //Then
        Assert.assertEquals(expectedSize, longMap.size());
    }

}
