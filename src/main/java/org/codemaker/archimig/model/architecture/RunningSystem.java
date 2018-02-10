package org.codemaker.archimig.model.architecture;

import java.util.ArrayList;
import java.util.List;

/**
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
