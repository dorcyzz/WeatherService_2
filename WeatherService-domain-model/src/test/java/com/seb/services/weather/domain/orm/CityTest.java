package com.seb.services.weather.domain.orm;

import com.seb.tools.test.GetterSetterTest;


public class CityTest extends GetterSetterTest {

    @Override
    public Class<?>[] touchableClasses() {
        return new Class<?>[]{City.class};
    }


//    @Override
//    public String[] touchablePackages() {
//        return new String[]{"com.seb.services.weather.domain.orm"};
//    }

//    @Test
//    public void testEqualsHashCode() {
//        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
//        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*PK.*")));
//
//        for (Class<?> clazzz : touchableClasses()) {
//            for (Class<?> clazz : provider.findCandidateComponents(clazzz.getName())) {
//                assertMeetsEqualsContract(clazz);
//                assertMeetsHashCodeContract(clazz);
//            }
//        }
//    }

}