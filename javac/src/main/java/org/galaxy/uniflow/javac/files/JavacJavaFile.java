package org.galaxy.uniflow.javac.files;

import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.files.UniFileKind;
import org.galaxy.uniflow.api.files.UniJavaFile;
import org.galaxy.uniflow.common.EnumUtils;
import org.jetbrains.annotations.NotNull;

import javax.tools.JavaFileObject;

public class JavacJavaFile extends JavacFile<JavaFileObject> implements UniJavaFile {

    public JavacJavaFile(JavaFileObject file) {
        super(file);
    }

    @Override
    public @NotNull UniFileKind getKind() {
        switch (file.getKind()) {
            case CLASS:
                return UniFileKind.CLASS;
            case SOURCE:
                return UniFileKind.SOURCE;
            default:
                return UniFileKind.RESOURCE;
        }
    }

    @Override
    public @NotNull UniModifier getAccessLevel() {
        return EnumUtils.convert(UniModifier.class, file.getAccessLevel());
    }
}
