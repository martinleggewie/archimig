package org.codemaker.archimig.model.descriptors;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple container for all transformation descriptions which shall happen in one so-called migration step.
 *
 * @author Martin Leggewie
 * @since 06.02.2018
 */
public class MigStepDescriptor {
  private final String name;
  private final List<TrafoDescriptor> trafoDescriptors = new ArrayList<TrafoDescriptor>();

  public MigStepDescriptor(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public MigStepDescriptor addTrafoDescriptor(TrafoDescriptor trafoDescriptor) {
    trafoDescriptors.add(trafoDescriptor);
    return this;
  }

  public List<TrafoDescriptor> getTrafoDescriptors() {
    return trafoDescriptors;
  }

}
