package org.galaxy.uniflow.intellij.psi.elements.resources;

import com.intellij.psi.PsiResourceListElement;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.elements.resources.UniResource;

public abstract class IJResource<T extends UniElement> implements UniResource {

    public abstract PsiResourceListElement getResourceElement();

}
