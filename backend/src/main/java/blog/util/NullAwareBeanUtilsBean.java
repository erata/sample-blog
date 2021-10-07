package blog.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

//Thanks https://stackoverflow.com/a/19739041
public class NullAwareBeanUtilsBean {

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // then use Spring BeanUtils to copy and ignore null
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void copyNonNullAndIgnoredProperties(Object src, Object target, String... ignoredProperties) {
        //exclude null properties und additional properties
        String[] allIgnoredProperties = ArrayUtils.addAll(getNullPropertyNames(src), ignoredProperties);
        BeanUtils.copyProperties(src, target, allIgnoredProperties);
    }

}