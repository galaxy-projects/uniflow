package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.expressions.UniIdentifier;

public final class UniConstants {

    public static final UniIdentifier THIS = Uniflow.getInstance().getElementFactory().createThis();

}
