package com.zy.common.utils;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2017/12/25 9:46.
 */
public class MapperUtil {
    public static <T> T getFirstOrNull(List<T> list) {
        return Optional.ofNullable(list).map(list1 -> list1.isEmpty() ? null : list1.get(0)).orElse(null);
    }
}
