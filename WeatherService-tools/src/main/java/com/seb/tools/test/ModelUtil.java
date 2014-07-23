package com.seb.tools.test;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.*;

public final class ModelUtil {

    private ModelUtil() {
    }

    /**
     * Retrieves a filed inside a class using a filed name.
     *
     * @param aClass     the class owning the field
     * @param aFieldName the filed name
     * @return the found field
     * @throws NoSuchFieldException if the field does not exist
     */
    public static Field findField(Class<?> aClass, String aFieldName) throws NoSuchFieldException {
        Field theField;
        if (aClass == null) {
            throw new NoSuchFieldException(aFieldName);
        }
        try {
            theField = aClass.getDeclaredField(aFieldName);
        } catch (NoSuchFieldException anException) {
            theField = findField(aClass.getSuperclass(), aFieldName);
        }
        return theField;
    }

    /**
     * Invoke a getter method using reflection and get the return value.
     *
     * @param anObject      Object to invoke the method on
     * @param aPropertyName Name of the property to invoke.
     * @return result of the method invocation.
     * @throws IllegalArgumentException Thrown in case method can't be found or invocation throws an Exception
     */
    public static Object getValue(Object anObject, String aPropertyName) {

        Field theField = getField(anObject.getClass(), anObject, aPropertyName);
        return getValue(anObject, theField);
    }

    /**
     * Invoke a getter method using reflection and get the return value.
     *
     * @param anObject Object to invoke the method on
     * @param aField   Reflection Field of the property to invoke
     * @return result of the method invocation, null if no getter could be invoked.
     */
    public static Object getValueLenient(Object anObject, Field aField) {
        try {
            return getValue(anObject, aField);
        } catch (IllegalArgumentException anException) {
            return null;
        }
    }

    /**
     * Invoke a getter using reflection and the field as the basis to look up the getter. Special case for booleans.
     *
     * @param anObject Object to invoke the method on
     * @param aField   Field to call the getter for
     * @return result of the method invocation.
     * @throws IllegalArgumentException Thrown in case method can't be found or invocation throws an Exception
     */
    public static Object getValue(Object anObject, Field aField) {
        // invoke the getter
        try {
            return getValue(anObject.getClass(), anObject, "get" + StringUtils.capitalize(aField.getName()));
        } catch (NullPointerException anException) {
            return null;
        }
    }

    /**
     * Method used to resurse for find a method on a class. Resursion will be done upwards in the inheritance tree, until Object is reached.
     *
     * @param aClass      Class to inspect
     * @param anObject    Object to invoke the method on
     * @param aMethodName Name of the method to invoke.
     * @return result of the method invocation.
     * @throws IllegalArgumentException Thrown in case method can't be found or invocation throws an Exception
     */
    public static Object getValue(Class<?> aClass, Object anObject, String aMethodName) {

        Method theGetterMethod = null;
        try {
            theGetterMethod = aClass.getDeclaredMethod(aMethodName);
        } catch (NoSuchMethodException anException) {
            theGetterMethod = null;
        }

        if (theGetterMethod != null) {
            // invoke the method
            try {
                return theGetterMethod.invoke(anObject);
            } catch (InvocationTargetException anException) {
                throw new IllegalArgumentException(anException);
            } catch (IllegalAccessException anException) {
                throw new IllegalArgumentException(anException);
            }
        }

        Class<?> theSuperClazz = aClass.getSuperclass();

        if (Object.class.equals(theSuperClazz)) {
            throw new IllegalArgumentException("Method " + aMethodName + " not found");
        }

        return getValue(theSuperClazz, anObject, aMethodName);
    }

    /**
     * Method used to invoke a setter.
     *
     * @param aclass      Class to inspect
     * @param anObject    Object to invoke the method on
     * @param aValue      Argument for the setter
     * @param aValueClass the class of the value
     * @param aMethodName Name of the method to invoke.
     * @throws IllegalArgumentException Thrown in case method can't be found or invocation throws an Exception
     */
    public static void setValue(Class<?> aclass, Object anObject, Object aValue, Class<?> aValueClass, String aMethodName) {

        Method theSetterMethod;
        try {
            theSetterMethod = aclass.getDeclaredMethod(aMethodName, aValueClass);
        } catch (SecurityException anException) {
            throw new IllegalArgumentException(anException);
        } catch (NoSuchMethodException anException) {
            throw new IllegalArgumentException(anException);
        }

        // invoke the method
        try {
            theSetterMethod.invoke(anObject, aValue);
        } catch (IllegalAccessException anException) {
            throw new IllegalArgumentException(anException);
        } catch (InvocationTargetException anException) {
            throw new IllegalArgumentException(anException);
        }
    }

    /**
     * Lenient version to call method on object. Catches and swallows the Reflective errors.
     *
     * @param aClass      Class to inspect
     * @param anObject    Object to invoke the method on
     * @param aMethodName Name of the method to invoke.
     * @return result of the method invocation.
     */
    public static Object getValueLenient(Class<?> aClass, Object anObject, String aMethodName) {

        try {
            return getValue(aClass, anObject, aMethodName);
        } catch (IllegalArgumentException anException) {
            return null;
        } catch (NullPointerException anException) {
            return null;
        }
    }

    private static Field getField(Class<?> aClass, Object anObject, String aPropertyName) {

        try {
            return aClass.getDeclaredField(aPropertyName);
        } catch (NoSuchFieldException anException) {
            aClass = aClass.getSuperclass();
        }

        if (Object.class.equals(aClass)) {
            throw new IllegalArgumentException("Property " + aPropertyName + " not found");
        }

        return getField(aClass, anObject, aPropertyName);
    }

    /**
     * Returns the Method within the given anOwnEntity that has argument type aPropertyEntity
     *
     * @param aPreviousClass The class in which we search the method.
     * @param aCurrentClass  The argument type of the method
     * @return The method with the given argument aPropertyEntity
     */
    public static Method findMethodWithParametrizedType(Class<?> aPreviousClass, Class<?> aCurrentClass) {
        Method[] theMethods = aPreviousClass.getMethods();
        for (Method theMethod : theMethods) {
            if (theMethod.getParameterTypes().length == 0) {
                Type theType = theMethod.getGenericReturnType();
                if (theType instanceof ParameterizedType) {
                    ParameterizedType theParameterizedType = (ParameterizedType) theType;
                    if (theParameterizedType.getActualTypeArguments()[0].equals(aCurrentClass)) {
                        return theMethod;
                    }
                    Class<?> theSuperClass = aCurrentClass.getSuperclass();
                    while (!Object.class.equals(theSuperClass)) {
                        if (theParameterizedType.getActualTypeArguments()[0].equals(theSuperClass)) {
                            return theMethod;
                        }
                        theSuperClass = theSuperClass.getSuperclass();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the Method within the given anOwnEntity that has return type aPropertyEntity
     *
     * @param aPreviousClass The class in which we search the method.
     * @param aCurrentClass  The return type of the method
     * @return The method with the given return type aPropertyEntity
     */
    public static Method findMethodWithType(Class<?> aPreviousClass, Class<?> aCurrentClass) {
        Method[] theMethods = aPreviousClass.getMethods();
        for (Method theMethod : theMethods) {

            if ("toString".equals(theMethod.getName()) || "hashCode".equals(theMethod.getName()) || "equals".equals(theMethod.getName())) {
                continue;
            }

            if (theMethod.getParameterTypes().length == 0) {
                Type theType = theMethod.getGenericReturnType();
                if (theType.equals(aCurrentClass)) {
                    return theMethod;
                }
                Class<?> theSuperClass = aCurrentClass.getSuperclass();
                while (!Object.class.equals(theSuperClass)) {
                    if (theType.equals(theSuperClass)) {
                        return theMethod;
                    }
                    theSuperClass = theSuperClass.getSuperclass();
                }
            }
        }
        return null;
    }

    /**
     * Returns the Field within the given anOwnEntity that has the type aPropertyEntity
     *
     * @param anOwnEntity     The class in which we search the field.
     * @param aPropertyEntity The type of the field
     * @return The field with the given aPropertyEntity
     */
    public static Field findFieldWithParametrizedType(Class<?> anOwnEntity, Class<?> aPropertyEntity) {
        Field[] theFields = anOwnEntity.getDeclaredFields();
        for (Field theField : theFields) {
            Type theType = theField.getGenericType();
            if (theType instanceof ParameterizedType) {
                ParameterizedType theParameterizedType = (ParameterizedType) theType;
                if (theParameterizedType.getActualTypeArguments()[0].equals(aPropertyEntity)) {
                    return theField;
                }
                Class<?> theSuperClass = aPropertyEntity.getSuperclass();
                while (!Object.class.equals(theSuperClass)) {
                    if (theParameterizedType.getActualTypeArguments()[0].equals(theSuperClass)) {
                        return theField;
                    }
                    theSuperClass = theSuperClass.getSuperclass();
                }
            }
        }
        return null;
    }

    /**
     * This method makes a clone of an Object using serialization.
     *
     * @param anObject to be cloned
     * @return the clone
     */
    @SuppressWarnings("unchecked")
    public static <T> T getClone(T anObject) {
        Object theClone = null;
        try {
            ByteArrayOutputStream theOutputSTream = new ByteArrayOutputStream();
            ObjectOutput theOutput = new ObjectOutputStream(theOutputSTream);
            theOutput.writeObject(anObject);
            theOutput.close();

            ByteArrayInputStream theInputStream = new ByteArrayInputStream(theOutputSTream.toByteArray());
            ObjectInput theInput = new ObjectInputStream(theInputStream);
            theClone = theInput.readObject();
            theInput.close();
        } catch (IOException anException) {
            return null;
        } catch (ClassNotFoundException anException) {
            return null;
        }
        return (T) theClone;
    }

}
