package org.galaxy.uniflow.javac9;

import org.galaxy.uniflow.api.factories.UniConstants;

public class Reflection {

    public static final Class<?> MODULE_TYPE;
    public static final Class<?> DIRECTIVE_TYPE;
    public static final Class<?> EXPORTS_TYPE;
    public static final Class<?> OPENS_TYPE;
    public static final Class<?> PROVIDES_TYPE;
    public static final Class<?> REQUIRES_TYPE;
    public static final Class<?> USES_TYPE;
    public static final Class<?> COMPILATION_UNIT;

    static {
        try {
            MODULE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCModuleDecl");
            DIRECTIVE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCDirective");
            EXPORTS_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCExports");
            OPENS_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCOpens");
            PROVIDES_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCProvides");
            REQUIRES_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCRequires");
            USES_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCUses");
            COMPILATION_UNIT = Class.forName("com.sun.tools.javac.tree.JCTree$JCCompilationUnit");
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
