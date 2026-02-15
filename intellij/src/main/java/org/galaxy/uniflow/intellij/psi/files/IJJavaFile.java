package org.galaxy.uniflow.intellij.psi.files;

import com.intellij.openapi.vfs.VirtualFile;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.files.UniFileKind;
import org.galaxy.uniflow.api.files.UniJavaFile;
import org.jetbrains.annotations.NotNull;

public class IJJavaFile extends IJFile implements UniJavaFile {

    public IJJavaFile(VirtualFile file) {
        super(file);
    }

    @Override
    public @NotNull UniFileKind getKind() {
        return UniFileKind.SOURCE;
    }

    @Override
    public @NotNull UniModifier getAccessLevel() {
        return UniModifier.PUBLIC;
    }
}
