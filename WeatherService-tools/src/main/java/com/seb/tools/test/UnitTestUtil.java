package com.seb.tools.test;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.MissingResourceException;

public final class UnitTestUtil {

    private final static Logger LOGGER = Logger.getLogger(UnitTestUtil.class);

    private UnitTestUtil() {

    }

    public static Object invokePrivateStaticMethod(Class<?> clazz, String methodName, Object... args) throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Object result = null;

        while (result == null && !clazz.equals(Object.class)) {
            result = processClass(null, clazz, methodName, args);
            clazz = clazz.getSuperclass();
        }

        return result;
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object... args) throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Object result = null;
        Class<?> clazz = object.getClass();

        while (result == null && !clazz.equals(Object.class)) {
            result = processClass(object, clazz, methodName, args);
            clazz = clazz.getSuperclass();
        }

        return result;
    }

    public static Object invokeVoidPrivateMethod(Object object, String methodName, Object... args) throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        boolean result = false;
        Class<?> clazz = object.getClass();

        while (!result && !clazz.equals(Object.class)) {
            result = processVoidClass(object, clazz, methodName, args);
            clazz = clazz.getSuperclass();
        }

        return result;
    }

    private static Object processClass(Object object, Class<?> clazz, String methodName, Object... args) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        int size = 0;
        if (args != null) {
            size = args.length;
        }
        for (Method m : clazz.getDeclaredMethods()) {
            if (methodName.equals(m.getName()) && m.getParameterTypes().length == size) {
                m.setAccessible(true);
                return m.invoke(object, args);
            }
        }
        return null;
    }

    private static boolean processVoidClass(Object object, Class<?> clazz, String methodName, Object... args) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        int size = 0;
        if (args != null) {
            size = args.length;
        }
        for (Method m : clazz.getDeclaredMethods()) {
            if (methodName.equals(m.getName()) && m.getParameterTypes().length == size) {
                m.setAccessible(true);
                m.invoke(object, args);
                return true;
            }
        }
        return false;
    }

    public static void setPrivateVariable(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {

        Field field = findFieldInClassAndHierarchy(fieldName, obj.getClass());
        setValueOnField(obj, field, value);
    }

    public static void setPrivateStaticVariable(Class<?> clazz, String fieldName, Object value) throws NoSuchFieldException,
            IllegalAccessException {
        Field field = findFieldInClassAndHierarchy(fieldName, clazz);
        setValueOnField(null, field, value);
    }

    private static void setValueOnField(Object obj, Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, value);

    }

    private static Field findFieldInClassAndHierarchy(String fieldName, Class<?> clazz) throws NoSuchFieldException {
        Field field = null;
        while (field == null && !clazz.equals(Object.class)) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException exception) {
                // OK do nothing here in order to be able to traverse the hierarchy
            }
            clazz = clazz.getSuperclass();
        }

        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        }
        return field;
    }

    public static Object getPrivateVariable(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field field = null;

        while (field == null && !clazz.equals(Object.class)) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException exception) {
                // OK
            }

            clazz = clazz.getSuperclass();
        }

        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        } else {
            field.setAccessible(true);
            return field.get(obj);
        }
    }


    public static <A extends Annotation> A getAnnotation(TestContext testContext, Class<A> clazz) {
        A annotation = AnnotationUtils.findAnnotation(testContext.getTestMethod(), clazz);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(testContext.getTestClass(), clazz);
        }
        return annotation;
    }


    /**
     * Get the ressource file with a path like: "be/formatech/toolbox/utils/ucm9kb.png"
     *
     * @return File if found, MissingResourceException if not
     */
    public static File getFileFromRessource(Class<?> clazz, String path) {
        ClassLoader classLoader = clazz.getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource != null) {
            return new File(resource.getPath());
        } else {
            throw new MissingResourceException("File not found or access not allowed", clazz.toString(), path);
        }
    }

}
