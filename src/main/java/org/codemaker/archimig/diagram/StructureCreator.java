package org.codemaker.archimig.diagram;

import java.util.ArrayList;
import java.util.List;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;

public final class StructureCreator {


  private final Migration migration;


  public StructureCreator(Migration migration) {
    this.migration = migration;
  }


  public List<Structure> create() {
    List<Structure> result = new ArrayList<Structure>();

    AllStructure allStructure = createAllStructure();

    int numberOfCellsPerColumn = allStructure.determineNumberOfCellsPerColumn();
    int numberOfColumns = allStructure.determineNumberOfColumns();
    int numberOfCharactersPerCellTitle = allStructure.determineNumberOfCharactersPerCellTitle();


    for (MigrationStep migrationStep : migration.getMigrationSteps()) {

      Structure structure = new Structure(migrationStep.getName(), numberOfCharactersPerCellTitle);

      // 1. add one column per defined system
      for (RunningSystem system : migrationStep.getSystems()) {
        StructureColumn structureColumn = new StructureColumn(system.getName());

        // 1a. add one cell per defined component
        for (Component component : system.getComponents()) {
          StructureCell structureCell = new StructureCell(component.getName());
          structureColumn.getCells().add(structureCell);
        }

        // 1b. add empty cells until requested number of cells per column is reached
        while (structureColumn.getCells().size() < numberOfCellsPerColumn) {
          structureColumn.getCells().add(StructureCell.EMPTY_CELL);
        }
        structure.getColumns().add(structureColumn);
      }

      // 2. add empty columns until requested numbner of columns is reached
      while (structure.getColumns().size() < numberOfColumns) {
        structure.getColumns().add(StructureColumn.EMPTY_COLUMN);
      }

      result.add(structure);
    }

    return result;
  }


  /**
   * Collects all defined systems, components, and connections and stores them in the one AllStructure instance.
   */
  private AllStructure createAllStructure() {
    AllStructure allStructure = new AllStructure();

    for (MigrationStep migrationStep : migration.getMigrationSteps()) {
      for (RunningSystem system : migrationStep.getSystems()) {
        StructureColumn column = allStructure.findColumn(system.getName());
        if (column == null) {
          column = new StructureColumn(system.getName());
          allStructure.getColumns().add(column);
        }

        for (Component component : system.getComponents()) {
          StructureCell cell = allStructure.findCell(component.getName());
          if (cell == null) {
            cell = new StructureCell(component.getName());
            column.getCells().add(cell);
          }
        }
      }
    }
    return allStructure;
  }

}
