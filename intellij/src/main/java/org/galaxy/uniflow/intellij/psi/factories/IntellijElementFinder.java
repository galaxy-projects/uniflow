package org.galaxy.uniflow.intellij.psi.factories;

import org.galaxy.uniflow.api.factories.UniElementFinder;
import org.galaxy.uniflow.api.factories.UniFieldFinder;
import org.galaxy.uniflow.api.factories.UniMethodFinder;
import org.jetbrains.annotations.NotNull;

public class IntellijElementFinder implements UniElementFinder {

    private final UniFieldFinder fieldFinder;
    private final UniMethodFinder methodFinder;

    public IntellijElementFinder() {
        fieldFinder = new IntellijFieldFinder();
        methodFinder = new IntellijMethodFinder();
    }

    @Override
    public @NotNull UniFieldFinder fields() {
        return fieldFinder;
    }

    @Override
    public @NotNull UniMethodFinder methods() {
        return methodFinder;
    }
}
