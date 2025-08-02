package org.galaxy.uniflow.api.initializers;

import org.galaxy.uniflow.api.UniBlock;
import org.galaxy.uniflow.api.UniMember;
import org.jetbrains.annotations.NotNull;

public interface UniClassInitializer extends UniMember {

    @NotNull UniBlock getBody();

}
