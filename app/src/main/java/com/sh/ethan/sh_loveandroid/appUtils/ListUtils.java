package com.sh.ethan.sh_loveandroid.appUtils;

import java.util.List;
import java.util.Set;

/**
 * Author：     ethan
 * CreatTime：  2017/2/16
 * ContactInfo：
 * Description: List操作
 */
public class ListUtils {
    private ListUtils() {
        throw new UnsupportedOperationException("ListUtils cannot be instantiated");
    }

    /**
     * 清空list 中元素
     * @param mList
     * @param <T>
     */
    public static <T> void clear(List<T> mList){
        if(null != mList){
            mList.clear();
        }
    }

    /**
     * 清空list 中元素
     * @param <T>
     * @param mSet
     */
    public static <T> void clear(Set<String> mSet){
        if(null != mSet && mSet.size() >0){
            mSet.clear();
        }
    }

    /**
     * list是否是空
     * @param mList
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> mList) {
        return (mList == null || mList.size() == 0);
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
     * list中所有元素是否为空
     * @param mList
     * @return
     */
    public static  boolean stringListIsEmpty(List<String> mList) {
        boolean empty = true;
        if(mList == null || mList.size() == 0){
            return empty;
        }else {
            for(String s: mList){
                if( !StringUtils.isEmpty(s)){
                    empty = false;
                }
            }
        }
        return empty;
    }

    /**
     * 获取元素个数
     * @param mList
     * @param <T>
     * @return
     */
    public static <T> int getListSize(List<T> mList) {
        return mList == null ? 0 : mList.size();
    }

}
