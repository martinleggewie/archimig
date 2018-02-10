package org.codemaker.archimig.model.migration;

import java.util.ArrayList;
import java.util.List;

/**
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
