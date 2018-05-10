package org.codemaker.archimig.model.migration;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.util.Toolbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all information about one migration step which our algorithms have collected at runtime, based on the
 * information collected from the descriptors. For each defined MigStepDescriptor there is one instance of
 * MigrationStep. But in contrast to its descriptor, MigrationStep does not care about transformations any more, but
 * about the current state of systems, components, and functionalities.
 *
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public final class MigrationStep {
  private final String name;
  private final List<RunningSystem> systems;


  public MigrationStep(String name) {
    this.name = name;
    systems = new ArrayList<RunningSystem>();
  }


  public MigrationStep(String name, MigrationStep otherMigrationStep) {
    this(name);

    // 1. Create copy of other systems and their corresponding components, but do not yet care about the dependency
    // connections
    List<RunningSystem> otherSystems = otherMigrationStep.getSystems();
    for (RunningSystem otherSystem : otherSystems) {
      RunningSystem system = new RunningSystem(otherSystem.getName());
      for (Component otherComponent : otherSystem.getComponents()) {
        system.addComponent(new Component(otherComponent.getName()));
      }
      systems.add(system);
    }

    // 2. Now that we have copies of all systems and all their components, we can connect the copied components
    // according to their dependency relations
    for (RunningSystem otherSystem : otherSystems) {
      for (Component otherComponent : otherSystem.getComponents()) {
        for (Component otherDependee : otherComponent.getDependees()) {
          Component component = Toolbox.findComponentByName(systems, otherComponent.getName());
          Component dependee = Toolbox.findComponentByName(systems, otherDependee.getName());
          if (component != null && dependee != null) {
            component.addDependencyTo(dependee);
          } else {
            throw new IllegalStateException("Component and/or its dependee could not be found.");
          }
        }
      }
    }
  }


  public String getName() {
    return name;
  }


  public void addSystem(RunningSystem system) {
    systems.add(system);
  }


  public List<RunningSystem> getSystems() {
    return systems;
  }

  public boolean containsSystem(String systemName) {
    for (RunningSystem system : systems) {
      if (system.getName().equals(systemName)) {
        return true;
      }
    }
    return false;
  }

  public boolean containsComponentInSystem(String componentName, String systemName) {
    RunningSystem system = Toolbox.findSystemByName(systems, systemName);
    if (system == null) {
      return false;
    }
    return Toolbox.findComponentByName(system, componentName) != null;
  }
}
