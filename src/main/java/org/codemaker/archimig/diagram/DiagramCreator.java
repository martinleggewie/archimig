package org.codemaker.archimig.diagram;

import org.codemaker.archimig.model.migration.Migration;

import java.util.List;

public interface DiagramCreator {

  List<? extends Diagram> create(Migration migration);
}
