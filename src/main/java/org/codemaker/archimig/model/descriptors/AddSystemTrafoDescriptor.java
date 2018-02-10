package org.codemaker.archimig.model.descriptors;

/**
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class AddSystemTrafoDescriptor implements TrafoDescriptor {

  private final String systemName;

  public AddSystemTrafoDescriptor(String systemName) {
    this.systemName = systemName;
  }

  public String getSystemName() {
    return systemName;
  }
}
