package org.codemaker.archimig.structure;

import java.util.ArrayList;
import java.util.List;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;
import org.codemaker.archimig.structure.internal.AllStructure;
import org.codemaker.archimig.structure.internal.AllStructureCreator;

/**
 * As the name implies, this StructureCreateo creates a Structure. Well, in fact it creates a list of Structure instance
 * with each representing one source for the later visualization.
 *
 * @author Martin Leggewie
 * @since 30.03.2018
 */
public final class StructureCreator {


  private final Migration migration;


  public StructureCreator(Migration migration) {
    this.migration = migration;
  }


  public List<Structure> create() {
    List<Structure> result = new ArrayList<Structure>();

    AllStructure allStructure = new AllStructureCreator(migration).create();

    int numberOfCellsPerColumn = allStructure.determineNumberOfCellsPerColumn();
    int numberOfColumns = allStructure.determineNumberOfColumns();
    int numberOfCharactersPerCellTitle = allStructure.determineNumberOfCharactersPerCellTitle();


    for (MigrationStep migrationStep : migration.getMigrationSteps()) {

      Structure structure = new Structure(migrationStep.getName(), numberOfCharactersPerCellTitle);

      for (StructureColumn allColumn : allStructure.getColumns()) {

        if (migrationStep.containsSystem(allColumn.getTitle())) {
          StructureColumn structureColumn = new StructureColumn(allColumn.getTitle());
          structure.getColumns().add(structureColumn);

          for (StructureCell allCell : allColumn.getCells()) {
            if (migrationStep.containsComponentInSystem(allCell.getTitle(), allColumn.getTitle())) {
              structureColumn.getCells().add(new StructureCell(allCell.getTitle()));
            } else {
              structureColumn.getCells().add(StructureCell.EMPTY_CELL);
            }
          }
        } else {
          structure.getColumns().add(StructureColumn.EMPTY_COLUMN);
        }
      }
      result.add(structure);
    }

    return result;
  }

}
