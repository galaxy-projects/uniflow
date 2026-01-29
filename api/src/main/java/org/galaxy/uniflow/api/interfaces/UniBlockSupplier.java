package org.galaxy.uniflow.api.interfaces;

import org.galaxy.uniflow.api.statements.UniBlock;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface UniBlockSupplier {

    @NotNull UniBlock get();

}
