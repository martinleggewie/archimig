package org.codemaker.archimig.model.descriptors;

/**
 * Concrete transformation description which describes that one component shall be added to a system.
 *
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class AddComponentToSystemTrafoDescriptor implements TrafoDescriptor {
  private final String componentName;
  private final String systemName;

  public AddComponentToSystemTrafoDescriptor(String componentName, String systemName) {
    this.componentName = componentName;
    this.systemName = systemName;
  }

  public String getComponentName() {
    return componentName;
  }

  public String getSystemName() {
    return systemName;
  }
}
