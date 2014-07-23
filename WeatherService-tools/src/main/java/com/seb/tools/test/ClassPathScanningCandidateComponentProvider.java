package com.seb.tools.test;


import org.apache.log4j.Logger;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ClassPathScanningCandidateComponentProvider implements ResourceLoaderAware {

    private static final Logger LOGGER = Logger.getLogger(ClassPathScanningCandidateComponentProvider.class);

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private String myResourcePattern = DEFAULT_RESOURCE_PATTERN;
    private final List<TypeFilter> myIncludeFilters = new LinkedList<TypeFilter>();
    private final List<TypeFilter> myExcludeFilters = new LinkedList<TypeFilter>();
    private ResourcePatternResolver myResourcePatternResolver = new PathMatchingResourcePatternResolver();
    private MetadataReaderFactory myMetadataReaderFactory = new CachingMetadataReaderFactory(myResourcePatternResolver);

    public ClassPathScanningCandidateComponentProvider() {
        this(true);
        setResourceLoader(new DefaultResourceLoader());
    }

    public ClassPathScanningCandidateComponentProvider(boolean anUseDefaultFilter) {
        if (anUseDefaultFilter) {
            TypeFilter theDefaultTypeFilter = new RegexPatternTypeFilter(Pattern.compile(".*"));
            addIncludeFilter(theDefaultTypeFilter);
        }
    }

    /**
     * Set the ResourceLoader to use for resource locations. This will typically be a ResourcePatternResolver implementation.
     * <p/>
     * Default is PathMatchingResourcePatternResolver, also capable of resource pattern resolving through the ResourcePatternResolver interface.
     *
     * @see org.springframework.core.io.support.ResourcePatternResolver
     * @see org.springframework.core.io.support.PathMatchingResourcePatternResolver
     */
    public void setResourceLoader(ResourceLoader aResourceLoader) {
        myResourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(aResourceLoader);
        myMetadataReaderFactory = new CachingMetadataReaderFactory(aResourceLoader);
    }

    /**
     * Set the resource pattern to use when scanning the classpath. This value will be appended to each base package name.
     *
     * @param aResourcePattern the resource pattern
     * @see #findCandidateComponents(String)
     */
    public void setResourcePattern(String aResourcePattern) {
        Assert.notNull(aResourcePattern, "'resourcePattern' must not be null");
        myResourcePattern = aResourcePattern;
    }

    /**
     * Add an include type filter to the <i>end</i> of the inclusion list.
     *
     * @param anIncludeFilter the include type filter
     */
    public void addIncludeFilter(TypeFilter anIncludeFilter) {
        myIncludeFilters.add(anIncludeFilter);
    }

    /**
     * Add an exclude type filter to the <i>front</i> of the exclusion list.
     *
     * @param anExcludeFilter the exclude filter
     */
    public void addExcludeFilter(TypeFilter anExcludeFilter) {
        myExcludeFilters.add(0, anExcludeFilter);
    }

    /**
     * Scan the class path for candidate components.
     *
     * @param aBasePackage the package to check for annotated classes
     * @return a corresponding Set of autodetected class name (only concrete class)
     */
    public Set<Class<?>> findCandidateComponents(String aBasePackage) {
        Set<Class<?>> theCandidates = new HashSet<Class<?>>();
        try {
            String thePackageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(aBasePackage) + "/"
                    + myResourcePattern;
            Resource[] theResources = myResourcePatternResolver.getResources(thePackageSearchPath);

            for (Resource theResource : theResources) {
                MetadataReader theMetadataReader = myMetadataReaderFactory.getMetadataReader(theResource);
                if (isCandidateComponent(theMetadataReader)) {
                    Class<?> theClass = null;
                    try {
                        theClass = Class.forName(theMetadataReader.getClassMetadata().getClassName());
                    } catch (ClassNotFoundException anException) {
                        LOGGER.error("ERROR", anException);
                    }
                    theCandidates.add(theClass);
                }
            }

        } catch (IOException anException) {
            throw new RuntimeException("I/O failure during classpath scanning", anException);
        }
        return theCandidates;
    }

    /**
     * Determine whether the given class does not match any exclude filter and does match at least one include filter.
     *
     * @param aMetadataReader the ASM ClassReader for the class
     * @return whether the class qualifies as a candidate component
     */
    protected boolean isCandidateComponent(MetadataReader aMetadataReader) throws IOException {
        for (TypeFilter theFilter : myExcludeFilters) {
            if (theFilter.match(aMetadataReader, myMetadataReaderFactory)) {
                return false;
            }
        }
        for (TypeFilter theFilter : myIncludeFilters) {
            if (theFilter.match(aMetadataReader, myMetadataReaderFactory)) {
                return true;
            }
        }
        return false;
    }

}
