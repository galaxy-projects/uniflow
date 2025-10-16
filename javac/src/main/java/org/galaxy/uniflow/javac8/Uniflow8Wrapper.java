package org.galaxy.uniflow.javac8;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.javac.VersionedWrapper;
import org.galaxy.uniflow.javac8.statements.Javac8Case;
import org.jetbrains.annotations.Nullable;

public class Uniflow8Wrapper implements VersionedWrapper {

    public static final Uniflow8Wrapper INSTANCE = new Uniflow8Wrapper();

    @Override
    public @Nullable UniElement wrap(JCTree element) {
        if (element instanceof JCTree.JCCase)
            return new Javac8Case((JCTree.JCCase) element);
        return null;
    }
}
