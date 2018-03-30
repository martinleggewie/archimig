package org.codemaker.archimig.model.migration;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MigrationStepTest {

  private MigrationStep migrationStep;

  @Before
  public void assignMigrationStep() {
    RunningSystem system1 = new RunningSystem("System 1");
    Component component11 = new Component("Component 1.1");
    Component component12 = new Component("Component 1.2");
    system1.addComponent(component11);
    system1.addComponent(component12);

    RunningSystem system2 = new RunningSystem("System 2");
    Component component21 = new Component("Component 2.1");
    Component component22 = new Component("Component 2.2");
    Component component23 = new Component("Component 2.3");
    Component component24 = new Component("Component 2.4");
    system2.addComponent(component21);
    system2.addComponent(component22);
    system2.addComponent(component23);
    system2.addComponent(component24);

    migrationStep = new MigrationStep("To-be-tested migration step");
    migrationStep.addSystem(system1);
    migrationStep.addSystem(system2);
  }

  @Test
  public void testContainsSystem() {

    // 1. Assign - nothing aditional needed

    // 2. Act and 3. Assert
    assertTrue(migrationStep.containsSystem("System 1"));
    assertTrue(migrationStep.containsSystem("System 2"));
    assertFalse(migrationStep.containsSystem("System 3"));
  }

  @Test
  public void testContainsComponentInSystem() {

    // 1. Assign - nothing additional needed

    // 2. Act and 3. Assert
    assertTrue(migrationStep.containsComponentInSystem("Component 1.1", "System 1"));
    assertTrue(migrationStep.containsComponentInSystem("Component 1.2", "System 1"));
    assertTrue(migrationStep.containsComponentInSystem("Component 2.1", "System 2"));
    assertTrue(migrationStep.containsComponentInSystem("Component 2.2", "System 2"));
    assertTrue(migrationStep.containsComponentInSystem("Component 2.3", "System 2"));
    assertTrue(migrationStep.containsComponentInSystem("Component 2.4", "System 2"));

    assertFalse(migrationStep.containsComponentInSystem("Component 1.1", "System 2"));
    assertFalse(migrationStep.containsComponentInSystem("Component 1.2", "System 2"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.1", "System 1"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.2", "System 1"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.3", "System 1"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.4", "System 1"));

    assertFalse(migrationStep.containsComponentInSystem("Component 1.1", "System 3"));
    assertFalse(migrationStep.containsComponentInSystem("Component 1.2", "System 3"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.1", "System 3"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.2", "System 3"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.3", "System 3"));
    assertFalse(migrationStep.containsComponentInSystem("Component 2.4", "System 3"));

    assertFalse(migrationStep.containsComponentInSystem("YetAnotherComponent", "System 1"));
    assertFalse(migrationStep.containsComponentInSystem("YetAnotherComponent", "System 2"));
    assertFalse(migrationStep.containsComponentInSystem("YetAnotherComponent", "System 3"));

  }


}
