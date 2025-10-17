package org.galaxy.uniflow.api;

import org.jetbrains.annotations.Nullable;

public interface UniMember extends UniElement, UniModifiersHolder {

    @Nullable UniClass getEnclosingClass();

}
