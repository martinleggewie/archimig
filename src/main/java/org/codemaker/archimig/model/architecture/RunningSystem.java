package org.codemaker.archimig.model.architecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple container for one system which is part of the whole system environment. The system is to top-most element, and
 * it contains a list of components.
 * <p>
 * This class got its prefix "Running" to better separate it from the basic Java System class, to avoid confusion.
 *
 * @author Martin Leggewie
 * @since 06.02.2018
 */
public class RunningSystem {

  private final String name;
  private final List<Component> components;

  public RunningSystem(String name) {
    this.name = name;
    components = new ArrayList<Component>();
  }

  public String getName() {
    return name;
  }

  public void addComponent(Component component) {
    components.add(component);
  }

  public List<Component> getComponents() {
    return components;
  }

}
