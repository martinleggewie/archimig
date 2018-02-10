package org.codemaker.archimig.diagram;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StructureTest {

  @Test
  public void testTrivialStructure() {

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
  }
}
