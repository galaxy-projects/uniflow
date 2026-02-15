package org.galaxy.uniflow.api.files;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniFileLocation {

    @NotNull Location getLocation();

    @Nullable CharSequence getModule();

    @NotNull CharSequence getPackage();

    @NotNull CharSequence getName();

    enum Location {
        SOURCE,
        CLASS,
        NATIVE_HEADER
    }
}
