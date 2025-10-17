package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.elements.labels.UniDefaultCaseLabel;
import org.galaxy.uniflow.api.expressions.UniIdentifier;

public final class UniConstants {

    public static final String JAVA_VERSION_ERROR_MESSAGE = "Not supported in this version";

    public static final UniIdentifier THIS = Uniflow.getInstance().getElementFactory().createThis();
    public static final UniDefaultCaseLabel DEFAULT = Uniflow.getInstance().getElementFactory().createDefaultCase();

}
