package com.seb.tools.test;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;

public class EqualsHashCodeTest {

    private static final Logger LOGGER = Logger.getLogger(EqualsHashCodeTest.class);

    private static final String TEST_STRING_VAL1 = "Some Value";
    private static final String TEST_STRING_VAL2 = "Some Other Value";

    public static void assertMeetsEqualsContract(Class<?> classUnderTest) {
        Object o1;
        Object o2;
        try {
            // Get Instances
            o1 = classUnderTest.newInstance();
            o2 = classUnderTest.newInstance();

            Assert.assertTrue("Instances with default constructor not equal (o1.equals(o2))", o1.equals(o2));
            Assert.assertTrue("Instances with default constructor not equal (o2.equals(o1))", o2.equals(o1));

            Field[] fields = classUnderTest.getDeclaredFields();
            for (Field field : fields) {

                // Reset the instances
                o1 = classUnderTest.newInstance();
                o2 = classUnderTest.newInstance();

                if (Modifier.isFinal(field.getModifiers()) || "$jacocoData".equals(field.getName())) {
                    continue;
                }

                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(o1, TEST_STRING_VAL1);
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(o1, true);
                } else if (field.getType().equals(Boolean.class)) {
                    field.set(o1, true);
                } else if (field.getType() == short.class) {
                    field.setShort(o1, (short) 1);
                } else if (field.getType() == long.class) {
                    field.setLong(o1, 1L);
                } else if (field.getType().equals(Long.class)) {
                    field.set(o1, 1L);
                } else if (field.getType().equals(BigDecimal.class)) {
                    field.set(o1, BigDecimal.ONE);
                } else if (field.getType() == float.class) {
                    field.setFloat(o1, 1);
                } else if (field.getType() == int.class) {
                    field.setInt(o1, 1);
                } else if (field.getType().equals(Integer.class)) {
                    field.set(o1, 1);
                } else if (field.getType() == byte.class) {
                    field.setByte(o1, (byte) 1);
                } else if (field.getType() == char.class) {
                    field.setChar(o1, (char) 1);
                } else if (field.getType() == double.class) {
                    field.setDouble(o1, 1);
                } else if (field.getType().isEnum()) {
                    field.set(o1, field.getType().getEnumConstants()[0]);
                } else if (Object.class.isAssignableFrom(field.getType())) {
                    field.set(o1, field.getType().newInstance());
                } else {
                    Assert.fail("Don't know how to set a " + field.getType().getName());
                }

                Assert.assertFalse("Instances with o1 having " + field.getName() + " set and o2 having it not set are equal", o1.equals(o2));

                field.set(o2, field.get(o1));

                Assert.assertTrue("After setting o2 with the value of the object in o1, the two objects in the field are not equal",
                        field.get(o1).equals(field.get(o2)));

                Assert.assertTrue("Instances with o1 having " + field.getName() + " set and o2 having it set to the same object of type "
                        + field.get(o2).getClass().getName() + " are not equal", o1.equals(o2));

                if (field.getType() == String.class) {
                    field.set(o2, TEST_STRING_VAL2);
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(o2, false);
                } else if (field.getType().equals(Boolean.class)) {
                    field.set(o2, false);
                } else if (field.getType() == short.class) {
                    field.setShort(o2, (short) 0);
                } else if (field.getType() == long.class) {
                    field.setLong(o2, 0L);
                } else if (field.getType().equals(Long.class)) {
                    field.set(o2, 0L);
                } else if (field.getType().equals(BigDecimal.class)) {
                    field.set(o1, BigDecimal.ZERO);
                } else if (field.getType() == float.class) {
                    field.setFloat(o2, 0);
                } else if (field.getType() == int.class) {
                    field.setInt(o2, 0);
                } else if (field.getType().equals(Integer.class)) {
                    field.set(o1, 0);
                } else if (field.getType() == byte.class) {
                    field.setByte(o2, (byte) 0);
                } else if (field.getType() == char.class) {
                    field.setChar(o2, (char) 0);
                } else if (field.getType() == double.class) {
                    field.setDouble(o2, 1);
                } else if (Date.class.isAssignableFrom(field.getType())) {
                    field.set(o2, new LocalDate().toDateTimeAtStartOfDay().toDate());
                } else if (field.getType().isEnum()) {
                    field.set(o2, field.getType().getEnumConstants()[1]);
                } else if (Object.class.isAssignableFrom(field.getType())) {
                    field.set(o2, field.getType().newInstance());
                } else {
                    Assert.fail("Don't know how to set a " + field.getType().getName());
                }
                if (field.get(o1).equals(field.get(o2))) {
                    // Even though we have different instances, they are equal. Let's walk one of them
                    // to see if we can find a field to set
                    Field[] paramFields = field.get(o1).getClass().getDeclaredFields();
                    for (Field paramField : paramFields) {
                        paramField.setAccessible(true);
                        if (paramField.getType() == String.class) {
                            paramField.set(field.get(o1), TEST_STRING_VAL1);
                        }
                    }
                }

                Assert.assertFalse("After setting o2 with a different object than what is in o1, the two objects in the field are equal. "
                        + "This is after an attempt to walk the fields to make them different", field.get(o1).equals(field.get(o2)));
                Assert.assertFalse("Instances with o1 having " + field.getName() + " set and o2 having it set to a different object are equal", o1.equals(o2));
            }

        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("ERROR", e);
            throw new AssertionError("Unable to construct an instance of the class under test");
        }
    }

    public static void assertMeetsHashCodeContract(Class<?> classUnderTest) {
        try {
            Field[] fields = classUnderTest.getDeclaredFields();
            for (Field field : fields) {
                Object o1 = classUnderTest.newInstance();
                int initialHashCode = o1.hashCode();

                if (Modifier.isFinal(field.getModifiers()) || "$jacocoData".equals(field.getName())) {
                    continue;
                }

                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(o1, TEST_STRING_VAL1);
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(o1, true);
                } else if (field.getType().equals(Boolean.class)) {
                    field.set(o1, true);
                } else if (field.getType() == short.class) {
                    field.setShort(o1, (short) 1);
                } else if (field.getType() == long.class) {
                    field.setLong(o1, 1L);
                } else if (field.getType().equals(Long.class)) {
                    field.set(o1, 1L);
                } else if (field.getType().equals(BigDecimal.class)) {
                    field.set(o1, BigDecimal.ONE);
                } else if (field.getType() == float.class) {
                    field.setFloat(o1, 1);
                } else if (field.getType() == int.class) {
                    field.setInt(o1, 1);
                } else if (field.getType().equals(Integer.class)) {
                    field.set(o1, 1);
                } else if (field.getType() == byte.class) {
                    field.setByte(o1, (byte) 1);
                } else if (field.getType() == char.class) {
                    field.setChar(o1, (char) 1);
                } else if (field.getType() == double.class) {
                    field.setDouble(o1, 1);
                } else if (field.getType().isEnum()) {
                    field.set(o1, field.getType().getEnumConstants()[0]);
                } else if (Object.class.isAssignableFrom(field.getType())) {
                    field.set(o1, field.getType().newInstance());
                } else {
                    Assert.fail("Don't know how to set a " + field.getType().getName());
                }
                int updatedHashCode = o1.hashCode();
                Assert.assertFalse("The field " + field.getName() + " was not taken into account for the hashCode contract ",
                        initialHashCode == updatedHashCode);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("ERROR", e);
            throw new AssertionError("Unable to construct an instance of the class under test");
        }
    }

}
