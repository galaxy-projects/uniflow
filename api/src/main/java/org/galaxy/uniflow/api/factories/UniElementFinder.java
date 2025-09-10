package org.galaxy.uniflow.api.factories;

import org.jetbrains.annotations.NotNull;

public interface UniElementFinder {

    @NotNull UniFieldFinder fields();

    @NotNull UniMethodFinder methods();

}
