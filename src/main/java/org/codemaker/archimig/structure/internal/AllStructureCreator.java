package org.codemaker.archimig.structure.internal;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;
import org.codemaker.archimig.structure.StructureCell;
import org.codemaker.archimig.structure.StructureColumn;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * As the name implies: creates an AllStructure, based on the information found in the give Migration instance.
 *
 * @author Martin Leggewie
 * @since 30.03.2018
 */
public class AllStructureCreator {

  private Migration migration;

  public AllStructureCreator(Migration migration) {
    this.migration = migration;
  }

  /**
   * Collects all defined systems, components, and connections and stores them in the one AllStructure instance. After
   * that it moves all collected systems and components to their destined positions so that diagrams can be created
   * using this position information.
   */
  public AllStructure create() {

    // step 1: collect all systems and their components from all migration steps
    AllStructure unsortedAllStructure = collect();

    // step 2: change order of systems and their components to get the final AllStructure
    return sort(unsortedAllStructure);
  }


  private AllStructure collect() {
    AllStructure allStructure = new AllStructure();

    // collect all systems and their components from all migration steps ...
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

    // ... connect all cells with each other in both directions ...
    for (MigrationStep migrationStep : migration.getMigrationSteps()) {
      for (RunningSystem system : migrationStep.getSystems()) {
        for (Component component : system.getComponents()) {
          StructureCell cell = allStructure.findCell(component.getName());
          for (Component dependeeComponent : component.getDependees()) {
            StructureCell dependeeCell = allStructure.findCell(dependeeComponent.getName());
            if (!dependeeCell.getIncomingCells().contains(cell)) {
              dependeeCell.getIncomingCells().add(cell);
            }
            if (!cell.getOutgoingCells().contains(dependeeCell)) {
              cell.getOutgoingCells().add(dependeeCell);
            }
          }
        }
      }
    }

    // ... and fill systems up with empty cells until all systems contain the same number of cells
    int numberOfCellsPerColumn = allStructure.determineNumberOfCellsPerColumn();
    for (StructureColumn column : allStructure.getColumns()) {
      for (int i = column.getCells().size(); i < numberOfCellsPerColumn; i++) {
        column.getCells().add(StructureCell.EMPTY_CELL);
      }
    }

    return allStructure;
  }


  private AllStructure sort(AllStructure allStructure) {
    List<StructureColumn> columns = allStructure.getColumns();
    Collections.sort(columns, new Comparator<StructureColumn>() {
      public int compare(StructureColumn column1, StructureColumn column2) {
        int weightColumn1 = column1.numberOfIncomingCells() - column1.numberOfOutgoingCells();
        int weightColumn2 = column2.numberOfIncomingCells() - column2.numberOfOutgoingCells();
        return weightColumn1 - weightColumn2;
      }
    });

    return allStructure;
  }
}
