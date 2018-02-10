package org.codemaker.archimig.diagram;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;

import java.util.ArrayList;
import java.util.List;

public final class StructureCreator {


  private final Migration migration;


  public StructureCreator(Migration migration) {
    this.migration = migration;
  }


  public List<Structure> create() {
    List<Structure> result = new ArrayList<Structure>();

    for (MigrationStep migrationStep : migration.getMigrationSteps()) {

      Structure structure = new Structure();

      for (RunningSystem system : migrationStep.getSystems()) {
        StructureColumn structureColumn = new StructureColumn(system.getName());

        for (Component component : system.getComponents()) {
          StructureCell structureCell = new StructureCell(component.getName());
          structureColumn.getCells().add(structureCell);
        }

        structure.getColumns().add(structureColumn);
      }


      result.add(structure);
    }

    return result;
  }
}
