package org.codemaker.archimig.diagram;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StructureCreatorTest {

  @Test
  public void testCreateStructureOneStepOneSystem() {

    // 1. Assign
    Migration migration = new Migration();
    MigrationStep migrationStep = new MigrationStep("Schritt 1");
    RunningSystem system = new RunningSystem("The System");
    system.addComponent(new Component("Component 1"));
    system.addComponent(new Component("Component 2"));
    migrationStep.addSystem(system);
    migration.addMigrationStep(migrationStep);

    // 2. Act
    List<Structure> structures = new StructureCreator(migration).create();

    // 3. Assert
    assertEquals(1, structures.size());
    Structure structure = structures.get(0);
    assertEquals(1, structure.getColumns().size());
    StructureColumn structureColumn = structure.getColumns().get(0);
    assertEquals("The System", structureColumn.getTitle());
    List<StructureCell> structureCells = structureColumn.getCells();
    assertEquals(2, structureCells.size());
    assertEquals("Component 1", structureCells.get(0).getTitle());
    assertEquals("Component 2", structureCells.get(1).getTitle());

    assertEquals(1, structure.numberOfColumns());
    assertEquals(2, structure.numberOfCellsPerColumn());

    assertEquals("Component 1".length(), structure.getNumberOfCharactersPerCellTitle());
  }


  @Test
  public void testCreateStructureOneStepThreeSystems() {

    // 1. Assign
    Migration migration = new Migration();
    MigrationStep migrationStep1 = new MigrationStep("Step 1");
    RunningSystem system1 = new RunningSystem("System 1");
    system1.addComponent(new Component("Component 1.1"));
    system1.addComponent(new Component("Component 1.2"));
    migrationStep1.addSystem(system1);
    RunningSystem system2 = new RunningSystem("System 2");
    system2.addComponent(new Component("Component 2.1"));
    system2.addComponent(new Component("Component 2.2"));
    system2.addComponent(new Component("Component 2.3"));
    system2.addComponent(new Component("Component 2.4"));
    migrationStep1.addSystem(system2);
    RunningSystem system3 = new RunningSystem("System 3");
    system3.addComponent(new Component("Component 3.1"));
    system3.addComponent(new Component("Component 3.2"));
    system3.addComponent(new Component("Component 3.3"));
    migrationStep1.addSystem(system3);
    migration.addMigrationStep(migrationStep1);

    // 2. Act
    List<Structure> structures = new StructureCreator(migration).create();

    // 3. Assert
    assertEquals(1, structures.size());
    Structure structure = structures.get(0);
    assertEquals(3, structure.getColumns().size());

    StructureColumn structureColumn1 = structure.getColumns().get(0);
    assertEquals("System 1", structureColumn1.getTitle());
    List<StructureCell> structureCells1 = structureColumn1.getCells();
    assertEquals(4, structureCells1.size());
    assertEquals("Component 1.1", structureCells1.get(0).getTitle());
    assertEquals("Component 1.2", structureCells1.get(1).getTitle());
    assertEquals(StructureCell.EMPTY_CELL, structureCells1.get(2));
    assertEquals(StructureCell.EMPTY_CELL, structureCells1.get(3));

    StructureColumn structureColumn2 = structure.getColumns().get(1);
    assertEquals("System 2", structureColumn2.getTitle());
    List<StructureCell> structureCells2 = structureColumn2.getCells();
    assertEquals(4, structureCells2.size());
    assertEquals("Component 2.1", structureCells2.get(0).getTitle());
    assertEquals("Component 2.2", structureCells2.get(1).getTitle());
    assertEquals("Component 2.3", structureCells2.get(2).getTitle());
    assertEquals("Component 2.4", structureCells2.get(3).getTitle());

    StructureColumn structureColumn3 = structure.getColumns().get(2);
    assertEquals("System 3", structureColumn3.getTitle());
    List<StructureCell> structureCells3 = structureColumn3.getCells();
    assertEquals(4, structureCells3.size());
    assertEquals("Component 3.1", structureCells3.get(0).getTitle());
    assertEquals("Component 3.2", structureCells3.get(1).getTitle());
    assertEquals("Component 3.3", structureCells3.get(2).getTitle());
    assertEquals(StructureCell.EMPTY_CELL, structureCells1.get(3));

    assertEquals(3, structure.numberOfColumns());
    assertEquals(4, structure.numberOfCellsPerColumn());

    assertEquals("Component 1.1".length(), structure.getNumberOfCharactersPerCellTitle());

  }


  @Test
  public void testCreateStructureTwoStepsThreeSystems() {


    // 1. Assign
    Migration migration = new Migration();

    {
      MigrationStep migrationStep1 = new MigrationStep("Step 1");
      RunningSystem system1 = new RunningSystem("System 1");
      system1.addComponent(new Component("Component 1.1"));
      system1.addComponent(new Component("Component 1.2"));
      migrationStep1.addSystem(system1);
      RunningSystem system2 = new RunningSystem("System 2");
      system2.addComponent(new Component("Component 2.1"));
      system2.addComponent(new Component("Component 2.2"));
      system2.addComponent(new Component("Component 2.3"));
      migrationStep1.addSystem(system2);
      migration.addMigrationStep(migrationStep1);
    }

    {
      MigrationStep migrationStep2 = new MigrationStep("Step 2");
      RunningSystem system1 = new RunningSystem("System 1");
      system1.addComponent(new Component("Component 1.1"));
      system1.addComponent(new Component("Component 1.2"));
      migrationStep2.addSystem(system1);
      RunningSystem system2 = new RunningSystem("System 2");
      system2.addComponent(new Component("Component 2.1"));
      system2.addComponent(new Component("Component 2.2"));
      system2.addComponent(new Component("Component 2.3"));
      migrationStep2.addSystem(system2);
      RunningSystem system3 = new RunningSystem("System 3");
      system3.addComponent(new Component("Component 3.1"));
      system3.addComponent(new Component("Component 3.2"));
      system3.addComponent(new Component("Component 3.3"));
      system3.addComponent(new Component("Component 3.4"));
      migrationStep2.addSystem(system3);
      migration.addMigrationStep(migrationStep2);
    }

    // 2. Act
    List<Structure> structures = new StructureCreator(migration).create();

    // 3. Assert
    assertEquals(2, structures.size());

    {
      Structure structure1 = structures.get(0);
      assertEquals(3, structure1.getColumns().size());

      StructureColumn structureColumn1 = structure1.getColumns().get(0);
      assertEquals("System 1", structureColumn1.getTitle());
      List<StructureCell> structureCells1 = structureColumn1.getCells();
      assertEquals(4, structureCells1.size());
      assertEquals("Component 1.1", structureCells1.get(0).getTitle());
      assertEquals("Component 1.2", structureCells1.get(1).getTitle());
      assertEquals(StructureCell.EMPTY_CELL, structureCells1.get(2));
      assertEquals(StructureCell.EMPTY_CELL, structureCells1.get(3));

      StructureColumn structureColumn2 = structure1.getColumns().get(1);
      assertEquals("System 2", structureColumn2.getTitle());
      List<StructureCell> structureCells2 = structureColumn2.getCells();
      assertEquals(4, structureCells2.size());
      assertEquals("Component 2.1", structureCells2.get(0).getTitle());
      assertEquals("Component 2.2", structureCells2.get(1).getTitle());
      assertEquals("Component 2.3", structureCells2.get(2).getTitle());
      assertEquals(StructureCell.EMPTY_CELL, structureCells2.get(3));

      StructureColumn structureColumn3 = structure1.getColumns().get(2);
      assertEquals(StructureColumn.EMPTY_COLUMN, structureColumn3);

      assertEquals(3, structure1.numberOfColumns());
      assertEquals(4, structure1.numberOfCellsPerColumn());

      assertEquals("Component 1.1".length(), structure1.getNumberOfCharactersPerCellTitle());
    }

    {
      Structure structure2 = structures.get(1);
      assertEquals(3, structure2.getColumns().size());

      StructureColumn structureColumn1 = structure2.getColumns().get(0);
      assertEquals("System 1", structureColumn1.getTitle());
      List<StructureCell> structureCells1 = structureColumn1.getCells();
      assertEquals(4, structureCells1.size());
      assertEquals("Component 1.1", structureCells1.get(0).getTitle());
      assertEquals("Component 1.2", structureCells1.get(1).getTitle());
      assertEquals(StructureCell.EMPTY_CELL, structureCells1.get(2));
      assertEquals(StructureCell.EMPTY_CELL, structureCells1.get(3));

      StructureColumn structureColumn2 = structure2.getColumns().get(1);
      assertEquals("System 2", structureColumn2.getTitle());
      List<StructureCell> structureCells2 = structureColumn2.getCells();
      assertEquals(4, structureCells2.size());
      assertEquals("Component 2.1", structureCells2.get(0).getTitle());
      assertEquals("Component 2.2", structureCells2.get(1).getTitle());
      assertEquals("Component 2.3", structureCells2.get(2).getTitle());
      assertEquals(StructureCell.EMPTY_CELL, structureCells2.get(3));

      StructureColumn structureColumn3 = structure2.getColumns().get(2);
      assertEquals("System 3", structureColumn3.getTitle());
      List<StructureCell> structureCells3 = structureColumn3.getCells();
      assertEquals(4, structureCells3.size());
      assertEquals("Component 3.1", structureCells3.get(0).getTitle());
      assertEquals("Component 3.2", structureCells3.get(1).getTitle());
      assertEquals("Component 3.3", structureCells3.get(2).getTitle());
      assertEquals("Component 3.4", structureCells3.get(3).getTitle());

      assertEquals(3, structure2.numberOfColumns());
      assertEquals(4, structure2.numberOfCellsPerColumn());

      assertEquals("Component 1.1".length(), structure2.getNumberOfCharactersPerCellTitle());
    }
  }

}
