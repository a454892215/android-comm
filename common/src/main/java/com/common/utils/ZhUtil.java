package com.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class ZhUtil {

    private HashSet<String> zhSet = new HashSet<>();
    private int count;

    /**
     * @param originList 源数组
     * @param count      选多少个组合
     */
    public ZhUtil(List<String> originList, int count) {
        this.count = count;
        computeAllZh(originList, null);
    }

    private String sep = ",";

    private void computeAllZh(List<String> originList, String lastString) {
        for (String name : originList) {
            String zh;
            if (null == lastString) {
                zh = name;
            } else {
                zh = lastString + sep + name;
            }
            if (isContainRepeatStr(zh)) continue; //如果包含重复字符则略过
            if (zh.split(sep).length == count) {

                String[] arr = zh.split(sep);
                List<String> list = Arrays.asList(arr);
                Collections.sort(list);
                //把字符串按照从小到大排序
                StringBuilder builder = new StringBuilder();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    builder.append(i == size - 1 ? list.get(i) : list.get(i) + sep);
                }
                zhSet.add(builder.toString());
            } else {
                computeAllZh(originList, zh);
            }
        }
    }


    public List<String> getZhSet() {
        ArrayList<String> list = new ArrayList<>(zhSet);
        Collections.sort(list);
        return list;
    }


    private boolean isContainRepeatStr(String str) {
        String[] split = str.split(sep);
        HashSet<String> hashSet = new HashSet<>(Arrays.asList(split));
        return hashSet.size() != split.length;
    }

    /**
     * @param selectedCount 选择的个数
     * @param count         多少个一组
     * @return 多少组组合
     */
    public static int computeZhs(int selectedCount, int count) {
        if (selectedCount < count) {
            return 0;
        }
        int bcs = 1; //被除数
        int cs = 1; //除数
        while (count > 0) {
            cs = cs * count;
            bcs = bcs * selectedCount;
            count--;
            selectedCount--;

        }
        return bcs / cs;
    }


    /**
     * @param selectedCount 选择的个数
     * @param count         多少个一组
     * @return 多少组排列
     */
    public static int computePL(int selectedCount, int count) {
        if (selectedCount < count) {
            return 0;
        }
        return selectedCount * (selectedCount - 1);
    }

    /**
     * @param list1 集合一（集合中不能有重复元素）
     * @param list2 集合二（集合中不能有重复元素）
     * @return 多少组（从每个集合中选一个元素， 组成一组，一组中不能出现重复的数）
     */
    public static int computePL2(List<String> list1, List<String> list2) {
        int count = 0;
        int size1 = list1.size();
        int size2 = list2.size();
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                if (!list1.get(i).equals(list2.get(j))) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @param list1 集合1（集合中不能有重复元素）
     * @param list2 集合2（集合中不能有重复元素）
     * @param list3 集合3（集合中不能有重复元素）
     * @return 多少组（从每个集合中选一个元素， 组成一组，一组中不能出现重复的数）
     */
    public static int computePL3(List<String> list1, List<String> list2, List<String> list3) {
        int count = 0;
        int size1 = list1.size();
        int size2 = list2.size();
        int size3 = list3.size();
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                for (int k = 0; k < size3; k++) {
                    if (!list1.get(i).equals(list2.get(j))
                            && !list1.get(i).equals(list3.get(k))
                            && !list2.get(j).equals(list3.get(k))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param list1 集合1（集合中不能有重复元素）
     * @param list2 集合2（集合中不能有重复元素）
     * @param list3 集合3（集合中不能有重复元素）
     * @param list4 集合4（集合中不能有重复元素）
     * @return 多少组（从每个集合中选一个元素， 组成一组，一组中不能出现重复的数）
     */
    public static int computePL4(List<String> list1, List<String> list2, List<String> list3, List<String> list4) {
        int count = 0;
        for (String name1 : list1) {
            for (String name2 : list2) {
                for (String name3 : list3) {
                    for (String name4 : list4) {
                        if (!name1.equals(name2)
                                && !name1.equals(name3)
                                && !name1.equals(name4)
                                && !name2.equals(name3)
                                && !name2.equals(name4)
                                && !name3.equals(name4)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param list1 集合1（集合中不能有重复元素）
     * @param list2 集合2（集合中不能有重复元素）
     * @param list3 集合3（集合中不能有重复元素）
     * @param list4 集合4（集合中不能有重复元素）
     * @param list5 集合4（集合中不能有重复元素）
     * @return 多少组（从每个集合中选一个元素， 组成一组，一组中不能出现重复的数）
     */
    public static int computePL5(List<String> list1, List<String> list2, List<String> list3, List<String> list4, List<String> list5) {
        int count = 0;
        for (String name1 : list1) {
            for (String name2 : list2) {
                for (String name3 : list3) {
                    for (String name4 : list4) {
                        for (String name5 : list5) {
                            if (!name1.equals(name2)
                                    && !name1.equals(name3)
                                    && !name1.equals(name4)
                                    && !name1.equals(name5)
                                    && !name2.equals(name3)
                                    && !name2.equals(name4)
                                    && !name2.equals(name5)
                                    && !name3.equals(name4)
                                    && !name3.equals(name5)
                                    && !name4.equals(name5)
                            ) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }


}
