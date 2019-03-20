package uk.co.afe.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Utilities of object
 *
 * @author Sergey Teryoshin
 * 25.03.2018 12:44
 */
public final class ObjectUtils {

    /**
     * Return array of names of null value properties
     *
     * @param source Bean to lookup
     * @return Array of property names
     */
    public static String[] getNullPropertiesNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private ObjectUtils() {
    }
}
