package org.codemaker.archimig.model.migration;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.util.Toolbox;

import java.util.ArrayList;
import java.util.List;

/**
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
}