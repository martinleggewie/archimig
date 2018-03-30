package org.codemaker.archimig.util;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;

import java.util.List;

public final class Toolbox {
  private Toolbox() {
  }

  public static RunningSystem findSystemByName(List<RunningSystem> systems, String systemName) {
    for (RunningSystem system : systems) {
      if (system.getName().equals(systemName)) {
        return system;
      }
    }
    return null;
  }

  public static Component findComponentByName(List<RunningSystem> systems, String componentName) {
    for (RunningSystem system : systems) {
      Component component = findComponentByName(system, componentName);
      if (component != null) {
        return component;
      }
    }
    return null;
  }

  public static Component findComponentByName(RunningSystem system, String componentName) {
    for (Component component : system.getComponents()) {
      if (component.getName().equals(componentName)) {
        return component;
      }
    }
    return null;
  }
}
