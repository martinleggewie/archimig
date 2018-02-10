package org.codemaker.archimig.diagram;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;

import java.util.ArrayList;
import java.util.List;

public final class AsciiArtDiagramCreator implements DiagramCreator {


  public List<AsciiArtDiagram> create(Migration migration) {
    List<AsciiArtDiagram> asciiArtDiagrams = new ArrayList<AsciiArtDiagram>();

    for (MigrationStep migrationStep : migration.getMigrationSteps()) {
      asciiArtDiagrams.add(createAsciiArtDiagram(migrationStep));
    }

    return asciiArtDiagrams;
  }


  private AsciiArtDiagram createAsciiArtDiagram(MigrationStep migrationStep) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("    ").append(migrationStep.getName()).append("\n");
    for (RunningSystem system : migrationStep.getSystems()) {
      stringBuilder.append("        ").append(system.getName()).append("\n");
      for (Component component : system.getComponents()) {
        if (component.getDependees().size() > 0) {
          for (Component dependee : component.getDependees()) {
            stringBuilder.append("            ").append(component.getName()).append(" --> ").append(dependee.getName
                ()).append("\n");
          }
        } else {
          stringBuilder.append("            ").append(component.getName()).append("\n");
        }
      }
    }
    return new AsciiArtDiagram(stringBuilder.toString());
  }

}
