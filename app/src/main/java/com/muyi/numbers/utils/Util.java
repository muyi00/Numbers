package com.muyi.numbers.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by YJ on 2018/1/18.
 */

public class Util {

    /**
     * 生成不重复的随机数
     *
     * @param lowLimit   下限
     * @param upperLimit 上限
     * @param count      数量
     * @return
     */
    public static List<Integer> getRandomNumber(int lowLimit, int upperLimit, int count) {
        upperLimit++;//上限闭合
        HashSet<Integer> integerHashSet = new HashSet<>();
        boolean isRun = true;
        do {
            int randomInt = getRandomNumber(lowLimit, upperLimit);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
            }
            if (integerHashSet.size() == count) {
                isRun = false;
            }
        } while (isRun);

        List<Integer> numberList = new ArrayList<>();

        for (Integer i : integerHashSet) {
            if (i != null) {
                numberList.add(i);
            }
        }
        return numberList;
    }

    /**
     * 生成指定范围的随机数
     *
     * @param lowLimit
     * @param upperLimit
     * @return
     */
    public static int getRandomNumber(int lowLimit, int upperLimit) {
        Random random = new Random();
        int randomInt;
        boolean isRun = true;
        do {
            randomInt = random.nextInt(upperLimit);//生成0~10000之间的随机数
            if (randomInt > lowLimit) {
                isRun = false;
            }
        } while (isRun);
        return randomInt;
    }


}
