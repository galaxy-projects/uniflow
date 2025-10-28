package org.galaxy.uniflow.javac.elements.resources;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.resources.UniResource;

public abstract class JavacResource<T extends UniElement> implements UniResource {

    public abstract T getElement();

}
