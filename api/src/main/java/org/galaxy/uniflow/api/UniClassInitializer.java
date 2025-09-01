package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.statements.UniBlock;
import org.jetbrains.annotations.NotNull;

public interface UniClassInitializer extends UniMember {

    boolean isStatic();

    @NotNull UniBlock getBody();

}
