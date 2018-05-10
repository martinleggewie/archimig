package org.codemaker.archimig.model.migration;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all information about a migrations which our algorithms have collected at runtime, based on the information
 * collected from the descriptors. Analogously to MigDescriptor, there is only one and only one Migration instance at
 * runtime, and it contains the list of (calculated) migration steps.
 *
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public final class Migration {

  private final List<MigrationStep> migrationSteps = new ArrayList<MigrationStep>();


  public void addMigrationStep(MigrationStep migrationStep) {
    migrationSteps.add(migrationStep);
  }


  public List<MigrationStep> getMigrationSteps() {
    return migrationSteps;
  }


}
