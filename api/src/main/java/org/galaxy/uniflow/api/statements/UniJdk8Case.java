package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.jetbrains.annotations.NotNull;

public interface UniJdk8Case extends UniCaseBase {

    void setLabel(@NotNull UniCaseLabel label);

    @NotNull UniCaseLabel getLabel();

    @NotNull UniList<@NotNull UniStatement> getStatements();

}
