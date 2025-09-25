package org.galaxy.uniflow.framework;

import org.galaxy.uniflow.api.processing.UniProcessor;
import org.galaxy.uniflow.framework.assertions.CompilationResult;

public interface CompilationHarness {

    CompilationResult compile(UniProcessor processor, Resource resource, Resource... resources);

}
