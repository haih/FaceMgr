package com.iflytek.aio.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * {该处说明该类型的含义及作用}
 * 
 * @author ycli5
 * @lastModified
 * @history
 */
public class ReflectionUtils {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory
            .getLogger(ReflectionUtils.class);

    /**
     * 
     *  {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     *  @param object
     *  @param fieldName
     *  @return
     *  @author yhsu
     *  @created 2014-5-12 下午04:15:50
     *  @lastModified       
     *  @history
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param object
     * @param fieldName
     * @param value
     * @author yhsu
     * @created 2014-4-25 下午11:01:00
     * @lastModified
     * @history
     */
    public static void setFieldValue(Object object, String fieldName,
            Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param object
     * @param methodName
     * @param parameterTypes
     * @param parameters
     * @return
     * @throws InvocationTargetException
     * @author yhsu
     * @created 2014-4-25 下午11:01:08
     * @lastModified
     * @history
     */
    public static Object invokeMethod(Object object, String methodName,
            Class<?>[] parameterTypes, Object[] parameters)
            throws InvocationTargetException {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + object + "]");
        }

        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        return null;
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param object
     * @param fieldName
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:01:17
     * @lastModified
     * @history
     */
    @SuppressWarnings("unchecked")
    protected static Field getDeclaredField(Object object, String fieldName) {
        Assert.notNull(object, "object不能为空");
        Assert.hasText(fieldName, "fieldName");
        for (Class superClass = object.getClass(); superClass != Object.class;) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();

                superClass = superClass.getSuperclass();
            }

        }

        return null;
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param field
     * @author yhsu
     * @created 2014-4-25 下午11:01:25
     * @lastModified
     * @history
     */
    protected static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()))
                || (!Modifier
                        .isPublic(field.getDeclaringClass().getModifiers())))
            field.setAccessible(true);
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param object
     * @param methodName
     * @param parameterTypes
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:01:32
     * @lastModified
     * @history
     */
    @SuppressWarnings("unchecked")
    protected static Method getDeclaredMethod(Object object, String methodName,
            Class<?>[] parameterTypes) {
        Assert.notNull(object, "object不能为空");

        for (Class superClass = object.getClass(); superClass != Object.class;) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();

                superClass = superClass.getSuperclass();
            }

        }

        return null;
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param <T>
     * @param clazz
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:01:41
     * @lastModified
     * @history
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param clazz
     * @param index
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:01:47
     * @lastModified
     * @history
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName()
                    + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if ((index >= params.length) || (index < 0)) {
            logger.warn("Index: " + index + ", Size of "
                    + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger
                    .warn(clazz.getSimpleName()
                            + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param collection
     * @param propertyName
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:01:58
     * @lastModified
     * @history
     */
    @SuppressWarnings("unchecked")
    public static List fetchElementPropertyToList(Collection collection,
            String propertyName) {
        List list = new ArrayList();
        try {
            for (Iterator localIterator = collection.iterator(); localIterator
                    .hasNext();) {
                Object obj = localIterator.next();
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (IllegalAccessException e) {
            convertToUncheckedException(e);
        } catch (InvocationTargetException e) {
            convertToUncheckedException(e);
        } catch (NoSuchMethodException e) {
            convertToUncheckedException(e);
        }

        return list;
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param collection
     * @param propertyName
     * @param separator
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:02:07
     * @lastModified
     * @history
     */
    @SuppressWarnings("unchecked")
    public static String fetchElementPropertyToString(Collection collection,
            String propertyName, String separator) {
        List list = fetchElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param value
     * @param toType
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:02:13
     * @lastModified
     * @history
     */
    public static Object convertValue(Object value, Class<?> toType) {
        DateConverter dc = new DateConverter();
        dc.setUseLocaleFormat(true);
        dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
        ConvertUtils.register(dc, Date.class);
        return ConvertUtils.convert(value, toType);
    }

    /**
     * 
     * {说明该函数的含义和作用，如果函数较为复杂，请详细说明}
     * 
     * @param e
     * @return
     * @author yhsu
     * @created 2014-4-25 下午11:02:20
     * @lastModified
     * @history
     */
    public static IllegalArgumentException convertToUncheckedException(
            Exception e) {
        if (((e instanceof IllegalAccessException))
                || ((e instanceof IllegalArgumentException))
                || ((e instanceof NoSuchMethodException))) {
            return new IllegalArgumentException("Refelction Exception.", e);
        }
        return new IllegalArgumentException(e);
    }

}
