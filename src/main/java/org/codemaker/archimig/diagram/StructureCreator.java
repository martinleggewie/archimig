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
    int numberOfCellsPerColumn = determineNumberOfCellsPerColumn();
    int numberOfColumns = determineNumberOfColumns();
    int numberOfCharactersPerCellTitle = determineNumberOfCharactersPerCellTitle();

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


  private int determineNumberOfColumns() {
    int maxNumberOfColumns = 0;

    for (MigrationStep migrationStep : migration.getMigrationSteps()) {
      if (migrationStep.getSystems().size() > maxNumberOfColumns) {
        maxNumberOfColumns = migrationStep.getSystems().size();
      }
    }
    return maxNumberOfColumns;
  }


  private int determineNumberOfCellsPerColumn() {
    int maxNumberOfCellsPerColumn = 0;

    for (MigrationStep migrationStep : migration.getMigrationSteps()) {
      for (RunningSystem system : migrationStep.getSystems()) {
        if (system.getComponents().size() > maxNumberOfCellsPerColumn) {
          maxNumberOfCellsPerColumn = system.getComponents().size();
        }
      }
    }
    return maxNumberOfCellsPerColumn;
  }


  private int determineNumberOfCharactersPerCellTitle() {
    int maxNumberOfCharactersPerCellTitle = 0;

    for (MigrationStep migrationStep : migration.getMigrationSteps()) {
      for (RunningSystem system : migrationStep.getSystems()) {
        for (Component component : system.getComponents()) {
          if (component.getName().length() > maxNumberOfCharactersPerCellTitle) {
            maxNumberOfCharactersPerCellTitle = component.getName().length();
          }
        }
      }
    }
    return maxNumberOfCharactersPerCellTitle;
  }

}
