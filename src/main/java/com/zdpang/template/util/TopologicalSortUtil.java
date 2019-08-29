package com.zdpang.template.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/3/12 16:29
 */
public class TopologicalSortUtil<T, K extends Comparable> {
    /**
     * 拓扑排序
     * @param list 集合
     * @param idField 自身id字段名
     * @param parentIdField 父对象id字段名
     * @return 可执行的下标数组
     */
    public int[] ringCheck(List<T> list, String idField, String parentIdField) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /**
         * 空集合当做无环处理
         */
        if(null == list || 0 == list.size())
            return new int[]{};

        /**
         * 反射获取获得相应ID的方法
         */
        Class<?> clazz = list.get(0).getClass();
        Method getSelfIdMethod = clazz.getMethod("get" + idField.substring(0, 1).toUpperCase() + idField.substring(1));
        Method getParentIdMethod = clazz.getMethod("get" + parentIdField.substring(0, 1).toUpperCase() + parentIdField.substring(1));
        Map<K, Integer> nodeIndexMap = new HashMap<>();
        int relationCount = 0;
        /**
         * 生成id与下标对应的map 用于下方转换成下标数组关系使用
         * 通常list为mybatis查询出的结合 是ArrayList get效率高
         */
        for (int index = 0; index < list.size(); index++) {
            K parentId = (K)getParentIdMethod.invoke(list.get(index));
            K id = (K)getSelfIdMethod.invoke(list.get(index));
            nodeIndexMap.put(id, index);
            if(null != parentId){
                relationCount++;
            }
        }
        /**
         * 将list中的关系翻译成[0,1](0为子，1为父）这样的数组 parentList
         */
        int [][] parentList = new int[relationCount][];
        int index = 0;
        for (Object node : list) {
            if(null != getParentIdMethod.invoke(node)){
                int[] parent = new int[2];
                K id = (K)getSelfIdMethod.invoke(node);
                K parentId = (K)getParentIdMethod.invoke(node);
                parent[0] = nodeIndexMap.get(id);
                if(null == nodeIndexMap.get(parentId))
                    throw new RuntimeException("无效的parentId:" + parentId);
                parent[1] = nodeIndexMap.get(parentId);
                parentList[index++] = parent;
            }
        }

        return findOrder(list.size(), parentList);
    }

    /**
     * 拓扑排序生成可执行顺序
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] res = new int[numCourses];
        int index = 0;
        int[] in = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : prerequisites) {
            graph.get(edge[1]).add(edge[0]);
            in[edge[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (in[i] == 0) {
                queue.add(i);
                res[index++] = i;
            }
        }

        while(!queue.isEmpty()) {
            int i = queue.poll();
            for (int a : graph.get(i)) {
                in[a]--;
                if (in[a] == 0) {
                    queue.add(a);
                    res[index++] = a;
                }
            }

        }

        return index == numCourses ? res : new int[]{};
    }
}
