package org.codemaker.archimig.util;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;

import java.util.List;

public final class Toolbox {
  private Toolbox() {
  }

  public static RunningSystem findSystemByName(List<RunningSystem> systems, String name) {
    for (RunningSystem system : systems) {
      if (system.getName().equals(name)) {
        return system;
      }
    }
    return null;
  }

  public static Component findComponentByName(List<RunningSystem> systems, String name) {
    for (RunningSystem system : systems) {
      for (Component component : system.getComponents()) {
        if (component.getName().equals(name)) {
          return component;
        }
      }
    }
    return null;
  }
}
