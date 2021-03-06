package org.codemaker.archimig.model.architecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines one group of functionalities which are so self-contained that it could be delivered as an artifact. A
 * component cannot live on its own, but needs to always be part of a running system (but this fact is hidden from the
 * component - only the whole Migration and its steps "know" about this constraint.
 *
 * @author Martin Leggewie
 * @since 06.02.2018
 */
public class Component {

  private final String name;
  private final List<Component> dependees = new ArrayList<Component>();

  public Component(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void addDependencyTo(Component dependee) {
    if (!this.equals(dependee) && !doesDependeeAlreadyExist(dependee) && !dependee.doesDependeeAlreadyExist(this)) {
      dependees.add(dependee);
    } else {
      throw new IllegalStateException("Dependency cycle detected: " + dependee);
    }
  }

  private boolean doesDependeeAlreadyExist(Component dependeeToSearch) {
    for (Component dependee : dependees) {
      if (dependee.equals(dependeeToSearch)) {
        return true;
      } else {
        if (dependee.doesDependeeAlreadyExist(dependeeToSearch)) {
          return true;
        }
      }
    }
    return false;
  }

  public void removeDependencyTo(Component dependee) {
    dependees.remove(dependee);
  }


  public List<Component> getDependees() {
    return dependees;
  }
}
