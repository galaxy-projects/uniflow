package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniJdk9ElementFactory extends UniElementFactory {

    @NotNull UniModule createModule(@NotNull UniModifiers modifiers,
                                    @NotNull UniModule.ModuleKind kind,
                                    @NotNull String name,
                                    @NotNull List<@NotNull UniDirective> directives);

    @NotNull UniExports createExports(@NotNull String name,
                                      @NotNull List<@NotNull String> moduleNames);

    @NotNull UniOpens createOpens(@NotNull String name,
                                  @NotNull List<@NotNull String> moduleNames);

    @NotNull UniProvides createProvides(@NotNull String serviceName,
                                        @NotNull List<@NotNull String> implementationNames);

    @NotNull UniRequires createRequires(boolean isTransitive, boolean isStatic, @NotNull String name);

    @NotNull UniUses createUses(@NotNull String serviceName);

}
