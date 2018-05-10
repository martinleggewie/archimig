package org.codemaker.archimig.setup;

import org.codemaker.archimig.model.descriptors.AddComponentToComponentDependencyTrafoDescriptor;
import org.codemaker.archimig.model.descriptors.AddComponentToSystemTrafoDescriptor;
import org.codemaker.archimig.model.descriptors.AddSystemTrafoDescriptor;
import org.codemaker.archimig.model.descriptors.TrafoDescriptor;

/**
 * Builder which helps creating instances of the needed transformation descriptors. Goal is to be able to define such
 * descriptor in a fluent API.
 *
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class TrafoDescriptorBuilder {

  private String runningSystemName;
  private String componentSourceName;
  private String componentDestinationName;
  private String functionalityName;

  public TrafoDescriptorBuilder addSystem(String name) {
    if (runningSystemName == null) {
      runningSystemName = name;
      return this;
    } else {
      throw new IllegalStateException("systemName is already set: " + runningSystemName);
    }
  }

  public TrafoDescriptorBuilder addComponent(String name) {
    if (componentSourceName == null) {
      componentSourceName = name;
      return this;
    } else {
      throw new IllegalStateException("componentSourceName is already set: " + componentSourceName);
    }
  }

  public TrafoDescriptorBuilder toSystem(String name) {
    if (componentSourceName != null && runningSystemName == null) {
      runningSystemName = name;
      return this;
    } else {
      throw new IllegalStateException("componentSourceName is not set, using toSystem is not allowed.");
    }
  }

  public TrafoDescriptorBuilder addDependencyFromComponent(String name) {
    if (componentSourceName == null) {
      componentSourceName = name;
      return this;
    } else {
      throw new IllegalStateException("componentSourceName is already set: " + componentSourceName);
    }
  }

  public TrafoDescriptorBuilder toComponent(String name) {
    if (componentSourceName != null && componentDestinationName == null) {
      componentDestinationName = name;
      return this;
    } else {
      throw new IllegalStateException("componentSourceName is not set, using toComponent is not allowed.");
    }
  }

  public TrafoDescriptor build() {
    if (runningSystemName != null && componentSourceName == null && componentDestinationName == null && functionalityName == null) {
      // AddSystemTrafoDescriptor
      return new AddSystemTrafoDescriptor(runningSystemName);
    } else if (runningSystemName != null && componentSourceName != null && componentDestinationName == null && functionalityName == null) {
      // AddComponentToSystemTrafoDescriptor
      return new AddComponentToSystemTrafoDescriptor(componentSourceName, runningSystemName);
    } else if (runningSystemName == null && componentSourceName != null && componentDestinationName != null && functionalityName == null) {
      return new AddComponentToComponentDependencyTrafoDescriptor(componentSourceName, componentDestinationName);
    } else {
      throw new IllegalStateException("No fitting TrafoDescriptor found with current TrafoDescriptorBuilder: " + this);
    }
  }

}
