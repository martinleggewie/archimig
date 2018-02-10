package org.codemaker.archimig.model.descriptors;

/**
 * @author Martin Leggewie
 * @since 09.02.2018
 */
public class AddComponentToComponentDependencyTrafoDescriptor implements TrafoDescriptor {
  private final String fromComponentName;
  private final String toComponentName;

  public AddComponentToComponentDependencyTrafoDescriptor(String fromComponentName, String toComponentName) {
    this.fromComponentName = fromComponentName;
    this.toComponentName = toComponentName;
  }

  public String getFromComponentName() {
    return fromComponentName;
  }

  public String getToComponentName() {
    return toComponentName;
  }
}
