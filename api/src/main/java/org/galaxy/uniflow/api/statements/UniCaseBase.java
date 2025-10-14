package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.jetbrains.annotations.NotNull;

public interface UniCaseBase extends UniStatement {

    @NotNull UniList<@NotNull UniCaseLabel> getLabels();

}
