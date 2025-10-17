package org.galaxy.uniflow.framework.javac;

import java.util.Map;

public class InMemoryClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes;

    public InMemoryClassLoader(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = classes.get(name);

        if (bytes != null) {
            return defineClass(name, bytes, 0, bytes.length);
        }
        return super.findClass(name);
    }
}
