package org.galaxy.uniflow.javac25;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.javac21.Uniflow21Wrapper;
import org.galaxy.uniflow.javac25.elements.imports.Javac25ModuleImport;
import org.jetbrains.annotations.Nullable;

public class Uniflow25Wrapper extends Uniflow21Wrapper {

    public static final Uniflow25Wrapper INSTANCE = new Uniflow25Wrapper();

    @Override
    public @Nullable UniElement wrap(JCTree element) {
        if (Reflection.MODULE_IMPORT.isInstance(element))
            return new Javac25ModuleImport(element);
        return super.wrap(element);
    }
}
