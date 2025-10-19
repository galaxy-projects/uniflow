package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.Nullable;

public interface VersionedWrapper {

    @Nullable UniElement wrap(JCTree element);

}
