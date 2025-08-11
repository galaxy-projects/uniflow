package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.jetbrains.annotations.NotNull;

public interface UniPackage extends UniAnnotationHolder {

    @NotNull
    String getPackageName();

}
