package org.codemaker.archimig.setup;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.descriptors.AddComponentToComponentDependencyTrafoDescriptor;
import org.codemaker.archimig.model.descriptors.AddComponentToSystemTrafoDescriptor;
import org.codemaker.archimig.model.descriptors.AddSystemTrafoDescriptor;
import org.codemaker.archimig.model.descriptors.MigDescriptor;
import org.codemaker.archimig.model.descriptors.MigStepDescriptor;
import org.codemaker.archimig.model.descriptors.TrafoDescriptor;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;
import org.codemaker.archimig.util.Toolbox;

/**
 * Main implementation for creating a Migration out of its corresponding descriptors. The created Migration is destined
 * to be directly used by the following visualization algorithms.
 *
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class MigrationGenerator {

  private final MigDescriptor migDescriptor;

  public MigrationGenerator(MigDescriptor migDescriptor) {
    this.migDescriptor = migDescriptor;
  }

  public Migration generate() {

    Migration result = new Migration();
    MigrationStep previousMigrationStep = null;

    for (MigStepDescriptor migStepDescriptor : migDescriptor.getMigStepDescriptors()) {
      MigrationStep migrationStep = previousMigrationStep == null ? new MigrationStep(migStepDescriptor.getName()) : new MigrationStep(migStepDescriptor.getName(), previousMigrationStep);
      result.addMigrationStep(migrationStep);

      for (TrafoDescriptor trafoDescriptor : migStepDescriptor.getTrafoDescriptors()) {

        if (trafoDescriptor instanceof AddSystemTrafoDescriptor) {
          AddSystemTrafoDescriptor addSystemTrafoDescriptor = (AddSystemTrafoDescriptor) trafoDescriptor;
          migrationStep.addSystem(new RunningSystem(addSystemTrafoDescriptor.getSystemName()));
        }

        if (trafoDescriptor instanceof AddComponentToSystemTrafoDescriptor) {
          AddComponentToSystemTrafoDescriptor addComponentToSystemTrafoDescriptor = (AddComponentToSystemTrafoDescriptor) trafoDescriptor;
          String systemName = addComponentToSystemTrafoDescriptor.getSystemName();
          String componentName = addComponentToSystemTrafoDescriptor.getComponentName();
          RunningSystem system = Toolbox.findSystemByName(migrationStep.getSystems(), systemName);
          if (system != null) {
            system.addComponent(new Component(componentName));
          } else {
            throw new IllegalArgumentException("System with name " + systemName + " not found.");
          }
        }

        if (trafoDescriptor instanceof AddComponentToComponentDependencyTrafoDescriptor) {
          AddComponentToComponentDependencyTrafoDescriptor addComponentToComponentDependencyTrafoDescriptor = (AddComponentToComponentDependencyTrafoDescriptor) trafoDescriptor;
          String fromComponentName = addComponentToComponentDependencyTrafoDescriptor.getFromComponentName();
          String toComponentName = addComponentToComponentDependencyTrafoDescriptor.getToComponentName();
          Component fromComponent = Toolbox.findComponentByName(migrationStep.getSystems(), fromComponentName);
          Component toComponent = Toolbox.findComponentByName(migrationStep.getSystems(), toComponentName);
          if (fromComponent != null && toComponent != null) {
            fromComponent.addDependencyTo(toComponent);
          } else {
            throw new IllegalArgumentException("FromComponent " + fromComponentName + " and/or ToComponent " + toComponentName + " not found.");
          }
        }
      }

      previousMigrationStep = migrationStep;
    }

    return result;
  }

}
