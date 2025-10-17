package org.galaxy.uniflow.javac.files;

import org.galaxy.uniflow.api.files.UniFileLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacFileLocation implements UniFileLocation {

    private final Location location;
    private final CharSequence module;
    private final CharSequence packageName;
    private final CharSequence name;

    public JavacFileLocation(Location location, CharSequence module, CharSequence packageName, CharSequence name) {
        this.location = location;
        this.module = module;
        this.packageName = packageName;
        this.name = name;
    }

    @Override
    public @NotNull Location getLocation() {
        return location;
    }

    @Override
    public @Nullable CharSequence getModule() {
        return module;
    }

    @Override
    public @NotNull CharSequence getPackage() {
        return packageName;
    }

    @Override
    public @NotNull CharSequence getName() {
        return name;
    }
}
