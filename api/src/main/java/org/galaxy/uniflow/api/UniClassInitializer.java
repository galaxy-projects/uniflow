package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniClassInitializer extends UniMember {

    @NotNull UniBlock getBody();
}
