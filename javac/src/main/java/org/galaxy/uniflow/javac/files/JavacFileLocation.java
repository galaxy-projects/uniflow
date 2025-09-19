package org.galaxy.uniflow.javac.files;

import lombok.Getter;
import org.galaxy.uniflow.api.files.UniFileLocation;
import org.jetbrains.annotations.NotNull;

@Getter
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
    public @NotNull CharSequence getPackage() {
        return packageName;
    }
}
