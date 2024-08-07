package com;

import java.security.SecureRandom;
import java.util.*;

public class KeyedShuffle {
    public static void main(String[] args) {
        // 原始数组
        String[] originalArray = {"10", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        // 使用密钥打乱数组
        String key = "secret-key";
        ShuffleResult shuffledResult = shuffleArray(originalArray, key);

        // 打印打乱后的数组和索引映射
        System.out.println("Shuffled array: " + Arrays.toString(shuffledResult.getArray()));
        System.out.println("Index mapping: " + Arrays.toString(shuffledResult.getIndices()));

        // 使用相同的密钥还原数组
        String[] restoredArray = restoreArray(shuffledResult);
        System.out.println("Restored array: " + Arrays.toString(restoredArray));
    }

    // 使用密钥打乱数组，并记录索引映射
    private static ShuffleResult shuffleArray(String[] array, String key) {
        SecureRandom random = new SecureRandom(key.getBytes());
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);

        String[] shuffledArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            shuffledArray[i] = array[indices.get(i)];
        }

        return new ShuffleResult(shuffledArray, indices.toArray(new Integer[0]));
    }

    // 使用记录的索引还原数组
    private static String[] restoreArray(ShuffleResult result) {
        // 还原数组
        String[] shuffledArray = result.getArray();
        Integer[] indices = result.getIndices();

        String[] restoredArray = new String[shuffledArray.length];
        for (int i = 0; i < indices.length; i++) {
            restoredArray[indices[i]] = shuffledArray[i];
        }

        return restoredArray;
    }
}

// 用于保存打乱结果和索引的类
class ShuffleResult {
    private final String[] array;
    private final Integer[] indices;

    public ShuffleResult(String[] array, Integer[] indices) {
        this.array = array;
        this.indices = indices;
    }

    public String[] getArray() {
        return array;
    }

    public Integer[] getIndices() {
        return indices;
    }
}
