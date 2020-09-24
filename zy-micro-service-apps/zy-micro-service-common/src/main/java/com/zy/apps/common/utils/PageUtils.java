//package com.zy.apps.common.utils;
//
//
//
//import com.github.pagehelper.Page;
//import org.apache.commons.collections.CollectionUtils;
//
//import java.util.List;
//
///**
// * @author ZY
// * Copyright（C） 2010~2020 深圳市宏电技术股份有限公司
// * @date 2020/7/30 9:59
// * @Description:
// * @Version 1.0
// */
//public class PageUtils {
//
//    /**
//     * 获取页数
//     *
//     * @param pageSize
//     * @param count
//     * @return
//     */
//    public static int getPages(Integer pageSize, Integer count) {
//        if (pageSize == 0) {
//            return 0;
//        } else {
//            int pages = count / pageSize;
//            if (count % pageSize != 0) {
//                ++pages;
//            }
//            return pages;
//        }
//    }
//
//    public static <T> List<T> getRecords(List<T> arr, Integer pageNo, Integer pageSize) {
//        int fromIndex = (pageNo - 1) * pageSize;
//        int toIndex = pageNo * pageSize > arr.size() ? arr.size() : pageNo * pageSize;
//        if (fromIndex <= toIndex) {
//            return arr.subList(fromIndex, toIndex);
//        } else {
//            return null;
//        }
//    }
//
//    public static <T> Page<T> pageSet(Integer pageNo, Integer pageSize, List<T> subArr) {
//        Page<T> page = new Page<>(pageNo, pageSize);
//        int pages = getPages(pageSize, subArr.size());
//        page.setTotal(subArr.size());
//        page.setPages(pages);
//        List<T> records = PageUtils.getRecords(subArr, pageNo, pageSize);
//        if (!CollectionUtils.isEmpty(records)) {
//            page.setRecords(records);
//        }
//        return page;
//    }
//}
