package com.seb.tools.test;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertNotNull;
import static org.apache.commons.lang3.StringUtils.capitalize;

public abstract class GetterSetterTest {

    private Set<Class<?>> classes = new HashSet<>();

    protected void touch(Class<?> clazz) throws Exception {
        Class<?> testedClass = clazz;
        while (!Object.class.equals(testedClass) && !testedClass.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
            processFields(constructObject(clazz), testedClass);
            testedClass = testedClass.getSuperclass();
        }
    }

    @SuppressWarnings("unchecked")
    protected void processFields(Object object, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!"serialVersionUID".equals(field.getName()) && !clazz.isEnum()) {
                try {
                    Object o = ModelUtil.getValue(object, field);
                    if (List.class.isAssignableFrom(field.getType())) {
                        assertNotNull("Class :" + clazz + " - " + "Field : " + field.getName(), o);
                    }
                } catch (Exception ex) {
                }
                try {
                    ModelUtil.getValue(clazz, object, "is" + capitalize(field.getName()));
                } catch (Exception ex) {
                }
                try {
                    if (Float.class.isAssignableFrom(field.getType()) || Float.TYPE.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, (float) 1, field.getType(), "set" + capitalize(field.getName()));
                    } else if (Integer.class.isAssignableFrom(field.getType()) || Integer.TYPE.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, 1, field.getType(), "set" + capitalize(field.getName()));
                    } else if (Double.class.isAssignableFrom(field.getType()) || Double.TYPE.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, (double) 1, field.getType(), "set" + capitalize(field.getName()));
                    } else if (BigDecimal.class.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, new BigDecimal(0), field.getType(), "set" + capitalize(field.getName()));
                    } else if (Date.class.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, new Date(), field.getType(), "set" + capitalize(field.getName()));
                    } else if (Boolean.class.isAssignableFrom(field.getType()) || Boolean.TYPE.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, true, field.getType(), "set" + capitalize(field.getName()));
                    } else if (List.class.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, new ArrayList<>(), field.getType(), "set" + capitalize(field.getName()));
                    } else if (Set.class.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, new HashSet<>(), field.getType(), "set" + capitalize(field.getName()));
                    } else if (Map.class.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, new HashMap<>(), field.getType(), "set" + capitalize(field.getName()));
                    } else if (field.getType().isEnum()) {
                        ModelUtil.setValue(clazz, object, ((Class<? extends Enum<?>>) field.getType()).getEnumConstants()[0], field.getType(), "set"
                                + capitalize(field.getName()));
                    } else if (byte[].class.isAssignableFrom(field.getType())) {
                        ModelUtil.setValue(clazz, object, new byte[0], field.getType(), "set" + capitalize(field.getName()));
                    } else if (!field.getType().isInterface()) {
                        ModelUtil.setValue(clazz, object, constructObject(field.getType()), field.getType(), "set" + capitalize(field.getName()));
                        ModelUtil.getValue(clazz, object, field.getName());
                    }
                } catch (Exception ex) {
                }
            }

            // Test toString()
            assertNotNull(object.toString());
        }
        if (clazz.getGenericSuperclass() != null) {
            Type superClassType = clazz.getGenericSuperclass();
            if (superClassType instanceof Class<?>) {
                processFields(object, (Class<?>) superClassType);
            } else if (superClassType instanceof ParameterizedType) {
                ParameterizedType superClassParametrizedType = (ParameterizedType) superClassType;
                processFields(object, (Class<?>) superClassParametrizedType.getRawType());
            }
        }
    }

    protected boolean testExtraType(Object object, Class<?> clazz, Field field) {
        return false;
    }

    public Class<?>[] touchableClasses() {
        return new Class[0];
    }

    public String[] touchablePackages() {
        return new String[0];
    }

    @Before
    public void before() {
        Collections.addAll(this.classes, touchableClasses());
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider();
        // provider.addExcludeFilter(new NotInstanciatableFilter());
        // provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*[U|u]til.*")));
        provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*Test*.*")));
        provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*\\$.*")));
        // provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*Mapper.*")));
        // provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".[A|F|B|E].*")));
        // provider.addExcludeFilter(new AssignableTypeFilter(Enum.class));
        for (String packageName : touchablePackages()) {
            this.classes.addAll(provider.findCandidateComponents(packageName));
        }
    }

    @Test
    public void testTouchClasses() throws Exception {
        for (Class<?> clazz : this.classes) {
            if (!TestCase.class.isAssignableFrom(clazz)) {
                touch(clazz);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected Object constructObject(Class<?> clazz) throws Exception {
        if ((clazz == Integer.class) || (clazz == Integer.TYPE)) {
            return 1;
        }
        if ((clazz == Double.class) || (clazz == Double.TYPE)) {
            return 1.0;
        }
        if ((clazz == Long.class) || (clazz == Long.TYPE)) {
            return 1L;
        }
        if ((clazz == Boolean.class) || (clazz == Boolean.TYPE)) {
            return false;
        }
        if (clazz == BigDecimal.class) {
            return BigDecimal.ONE;
        }
        if (clazz == List.class) {
            return new ArrayList<>();
        }
        if (clazz == String.class) {
            return "";
        }
        if (clazz.isAssignableFrom(Throwable.class)) {
            return new RuntimeException();
        }
        if (clazz.isEnum()) {
            return ((Class<? extends Enum<?>>) clazz).getEnumConstants()[0];
        }
        if (byte[].class.isAssignableFrom(clazz)) {
            return new byte[0];
        }
        Constructor<?> constructor = null;
        Constructor<?> constructors[] = clazz.getDeclaredConstructors();
        for (Constructor<?> temp : constructors) {
            if ((constructor == null) || (temp.getParameterTypes().length > constructor.getParameterTypes().length)) {
                constructor = temp;
            }
        }
        if (constructor == null) {
            return clazz.newInstance();
        }
        Object[] parameters = new Object[constructor.getParameterTypes().length];
        for (int i = 0; i < constructor.getParameterTypes().length; i++) {
            Class<?> clazz2 = constructor.getParameterTypes()[i];
            parameters[i] = constructObject(clazz2);
        }
        constructor.setAccessible(true);
        return constructor.newInstance(parameters);
    }

    @SuppressWarnings("unused")
    private class NotInstanciatableFilter implements TypeFilter {

        /**
         * @see org.springframework.core.type.filter.TypeFilter#match(org.springframework.core.type.classreading.MetadataReader,
         * org.springframework.core.type.classreading.MetadataReaderFactory)
         */
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
            if (metadataReader.getClassMetadata().isAbstract() || metadataReader.getClassMetadata().isInterface()) {
                return true;
            }

            // check if there is a default constructor
            try {
                Class.forName(metadataReader.getClassMetadata().getClassName()).getConstructor();
            } catch (Exception e) {
                // no default constructor could be found
                return true;
            }
            return false;
        }
    }
}
