package org.codemaker.archimig.model.migration;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public final class Migration {

  private final List<MigrationStep> migrationSteps = new ArrayList<MigrationStep>();


  public void addMigrationStep(MigrationStep migrationStep) {
    migrationSteps.add(migrationStep);
  }


  public List<MigrationStep> getMigrationSteps() {
    return migrationSteps;
  }


  public String generateReport() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Migration").append("\n");
    for (MigrationStep migrationStep : migrationSteps) {
      stringBuilder.append("    ").append(migrationStep.getName()).append("\n");
      for (RunningSystem system : migrationStep.getSystems()) {
        stringBuilder.append("        ").append(system.getName()).append("\n");
        for (Component component : system.getComponents()) {
          if (component.getDependees().size() > 0) {
            for (Component dependee : component.getDependees()) {
              stringBuilder.append("            ").append(component.getName()).append(" --> ").append(dependee
                  .getName()).append("\n");
            }
          } else {
            stringBuilder.append("            ").append(component.getName()).append("\n");
          }
        }
      }
    }

    return stringBuilder.toString();
  }
}
