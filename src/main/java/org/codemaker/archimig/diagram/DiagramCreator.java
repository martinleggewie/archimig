package org.codemaker.archimig.diagram;

import org.codemaker.archimig.structure.Structure;

import java.util.List;

public interface DiagramCreator {

  List<? extends Diagram> create(List<Structure> structures);
}
