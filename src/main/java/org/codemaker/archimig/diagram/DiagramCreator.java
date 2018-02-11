package org.codemaker.archimig.diagram;

import java.util.List;

public interface DiagramCreator {

  List<? extends Diagram> create(List<Structure> structures);
}
