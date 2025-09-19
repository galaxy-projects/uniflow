package org.galaxy.uniflow.api.files;

import org.galaxy.uniflow.api.elements.UniModifier;
import org.jetbrains.annotations.NotNull;

public interface UniJavaFile extends UniFile {

    @NotNull UniFileKind getKind();

    @NotNull UniModifier getAccessLevel();

}
