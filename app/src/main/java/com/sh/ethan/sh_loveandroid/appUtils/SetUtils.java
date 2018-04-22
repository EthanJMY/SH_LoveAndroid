package com.sh.ethan.sh_loveandroid.appUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Author：     ethan
 * CreatTime：  2017/4/11
 * ContactInfo：
 * Description: 处理Set集合的工具类
 */
public class SetUtils {
    private SetUtils() {
        throw new UnsupportedOperationException("SetUtils cannot be instantiated");
    }

    /**
     * 清空list 中元素
     * @param mSet
     * @param <T>
     */
    public static <T> void clear(Set<T> mSet){
        if(null != mSet){
            mSet.clear();
        }
    }

    /**
     * list是否是空
     * @param mSet
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(Set<T> mSet) {
        return (mSet == null || mSet.size() == 0);
    }

    /**
     * 获取元素个数
     * @param mSet
     * @param <T>
     * @return
     */
    public static <T> int getListSize(Set<T> mSet) {
        return mSet == null ? 0 : mSet.size();
    }

    /**
     * 添加元素到set中
     * @param mSet
     * @param t
     * @param <T>
     */
    public static <T> void addT(Set<T> mSet , T t){
        if(null == mSet){
            mSet = new HashSet<T>();
        }
        mSet.add(t);
    }

    /**
     * 移除指定元素
     * @param mSet
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean removeT(Set<T> mSet , T t){
        if(null != mSet && mSet.contains(t)){
            mSet.remove(t);
            return true;
        }
        return false;
    }


}
