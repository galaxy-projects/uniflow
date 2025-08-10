package org.galaxy.uniflow.api;

import org.jetbrains.annotations.Nullable;

public interface UniWildcard extends UniElement {

    void setBound(@Nullable UniElement bound);

    @Nullable UniElement getBound();

}
