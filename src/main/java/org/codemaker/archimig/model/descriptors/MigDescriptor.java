package org.codemaker.archimig.model.descriptors;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple container for all migration steps which describe the whole migration of a given system architecture. Any
 * following algorithms need to get hold on the one and only MigDescriptor instance.
 *
 * @author Martin Leggewie
 * @since 06.02.2018
 */
public class MigDescriptor {
  private final List<MigStepDescriptor> migStepDescriptors = new ArrayList<MigStepDescriptor>();

  public MigDescriptor addMigStepDescriptor(MigStepDescriptor migStepDescriptor) {
    migStepDescriptors.add(migStepDescriptor);
    return this;
  }

  public List<MigStepDescriptor> getMigStepDescriptors() {
    return migStepDescriptors;
  }
}
