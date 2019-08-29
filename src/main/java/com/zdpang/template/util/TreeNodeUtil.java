package com.zdpang.template.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/3/12 16:49
 */
public class TreeNodeUtil<T, K extends Comparable> {
    private static final String DATA_FIELD = "data";
    private static final String CHILDREN_FIELD = "children";
    /**
     * 根据list集合生成树形结构返回
     * @param list 集合
     * @param idField 自身id字段名
     * @param parentIdField 父对象id字段名
     * @return 树型结构集合
     */
    public List<Map<String, Object>> generateTree(List<T> list, String idField, String parentIdField) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Map<String, Object>> resultList = new LinkedList<>();
        /**
         * 空集合直接返回结果
         */
        if(null == list || 0 == list.size())
            return resultList;

        int[] indexList = new TopologicalSortUtil<T, K>().ringCheck(list, idField, parentIdField);
        /**
         * 空结果或者成环 上方判断了空结果这里只可能是成环
         */
        if(0 == indexList.length){
            throw new RuntimeException("结果集成环，无法拼接成树形结构");
        }
        /**
         * 反射获取获得相应ID的方法
         */
        Class<?> clazz = list.get(0).getClass();
        Method getSelfIdMethod = clazz.getMethod("get" + idField.substring(0, 1).toUpperCase() + idField.substring(1));
        Method getParentIdMethod = clazz.getMethod("get" + parentIdField.substring(0, 1).toUpperCase() + parentIdField.substring(1));
        /**
         * 先将parent字段为空的节点放入最终树型集合
         */
        Map<K, Map<String, Object>> map = new HashMap<>();
        for (T node : list) {
            if (null == getParentIdMethod.invoke(node)) {
                Map<String, Object> nodeMap = new HashMap<>();
                nodeMap.put(DATA_FIELD, node);
                resultList.add(nodeMap);
                map.put((K) getSelfIdMethod.invoke(node), nodeMap);
            }
        }
        /**
         * 拓扑排序后的结果可一次遍历全部放入
         */
        for (int i = 0; i < indexList.length; ++i) {
            T node = list.get(indexList[i]);
            K parentId = (K)getParentIdMethod.invoke(node);
            if(null == parentId) {
                continue;
            }
            Map<String, Object> root = map.get(getParentIdMethod.invoke(node));
            if(null == root.get(CHILDREN_FIELD)){
                root.put(CHILDREN_FIELD, new LinkedList<>());
            }
            Map<String, Object> child = new HashMap<>();
            child.put(DATA_FIELD, node);
            List<Object> children = (List<Object>)root.get(CHILDREN_FIELD);
            children.add(child);
            map.put((K)getSelfIdMethod.invoke(node), child);
        }

        return resultList;
    }
}
