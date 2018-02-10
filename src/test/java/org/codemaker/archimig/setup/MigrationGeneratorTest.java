package org.codemaker.archimig.setup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.descriptors.MigDescriptor;
import org.codemaker.archimig.model.descriptors.MigStepDescriptor;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.model.migration.MigrationStep;
import org.junit.Test;

/**
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class MigrationGeneratorTest {

  @Test
  public void testGenerateTrivial() {

    // 1. Assign
    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Schritt 1");
    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Schritt 2");
    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor(migStepDescriptor2);

    // 2. Act
    Migration migration = new MigrationGenerator(migDescriptor).generate();

    // 3. Assert
    assertEquals(2, migration.getMigrationSteps().size());
    assertEquals("Schritt 1", migration.getMigrationSteps().get(0).getName());
    assertEquals("Schritt 2", migration.getMigrationSteps().get(1).getName());
  }


  @Test
  public void testGenerateSystemsOnly() {

    // 1. Assign
    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Schritt 1");
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Database").build());

    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Schritt 2");
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Client").build());

    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor(migStepDescriptor2);

    // 2. Act
    Migration migration = new MigrationGenerator(migDescriptor).generate();

    // 3. Assert
    assertEquals("Server", migration.getMigrationSteps().get(0).getSystems().get(0).getName());
    assertEquals("Database", migration.getMigrationSteps().get(0).getSystems().get(1).getName());
    assertEquals("Server", migration.getMigrationSteps().get(1).getSystems().get(0).getName());
    assertEquals("Database", migration.getMigrationSteps().get(1).getSystems().get(1).getName());
    assertEquals("Client", migration.getMigrationSteps().get(1).getSystems().get(2).getName());
  }


  @Test
  public void testGenerateSystemsWithComponents() {

    // 1. Assign
    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Schritt 1");
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Impl").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Database").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Tables").toSystem("Database").build());

    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Schritt 2");
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("UI").toSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API-Adapter").toSystem("Client").build());

    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor(migStepDescriptor2);

    // 2. Act
    Migration migration = new MigrationGenerator(migDescriptor).generate();

    // 3. Assert
    MigrationStep migrationStep1 = migration.getMigrationSteps().get(0);
    assertEquals("API", migrationStep1.getSystems().get(0).getComponents().get(0).getName());
    assertEquals("Impl", migrationStep1.getSystems().get(0).getComponents().get(1).getName());
    assertEquals("Tables", migrationStep1.getSystems().get(1).getComponents().get(0).getName());

    MigrationStep migrationStep2 = migration.getMigrationSteps().get(1);
    assertEquals("API", migrationStep2.getSystems().get(0).getComponents().get(0).getName());
    assertEquals("Impl", migrationStep2.getSystems().get(0).getComponents().get(1).getName());
    assertEquals("Tables", migrationStep2.getSystems().get(1).getComponents().get(0).getName());

    assertEquals("UI", migrationStep2.getSystems().get(2).getComponents().get(0).getName());
    assertEquals("API-Adapter", migrationStep2.getSystems().get(2).getComponents().get(1).getName());
  }


  @Test
  public void testGenerateSystemsWithComponentsAndDependees() {

    // 1. Assign
    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Schritt 1");
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Impl").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("Impl").toComponent
        ("API").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Database").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Tables").toSystem("Database").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("Impl").toComponent
        ("Tables").build());

    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Schritt 2");
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("UI").toSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API-Adapter").toSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("UI").toComponent
        ("API-Adapter").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("API-Adapter").toComponent
        ("API").build());

    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor(migStepDescriptor2);

    // 2. Act
    Migration migration = new MigrationGenerator(migDescriptor).generate();


    // 3. Assert

    // migration step 1
    RunningSystem server1 = migration.getMigrationSteps().get(0).getSystems().get(0);
    Component api1 = server1.getComponents().get(0);
    Component impl1 = server1.getComponents().get(1);
    RunningSystem database1 = migration.getMigrationSteps().get(0).getSystems().get(1);
    Component tables1 = database1.getComponents().get(0);

    assertEquals(2, impl1.getDependees().size());
    assertEquals(api1, impl1.getDependees().get(0));
    assertEquals(tables1, impl1.getDependees().get(1));
    assertEquals(0, api1.getDependees().size());
    assertEquals(0, tables1.getDependees().size());

    // migration step 2
    RunningSystem server2 = migration.getMigrationSteps().get(1).getSystems().get(0);
    Component api2 = server2.getComponents().get(0);
    Component impl2 = server2.getComponents().get(1);
    RunningSystem database2 = migration.getMigrationSteps().get(1).getSystems().get(1);
    Component tables2 = database2.getComponents().get(0);
    RunningSystem client2 = migration.getMigrationSteps().get(1).getSystems().get(2);
    Component ui2 = client2.getComponents().get(0);
    Component apiAdapter2 = client2.getComponents().get(1);

    assertEquals(2, impl2.getDependees().size());
    assertEquals(api2, impl2.getDependees().get(0));
    assertEquals(tables2, impl2.getDependees().get(1));
    assertEquals(0, api2.getDependees().size());
    assertEquals(0, tables2.getDependees().size());

    assertEquals(1, ui2.getDependees().size());
    assertEquals(apiAdapter2, ui2.getDependees().get(0));
    assertEquals(1, apiAdapter2.getDependees().size());
    assertEquals(api2, apiAdapter2.getDependees().get(0));

    // check that objects in step 1 and 2 are really different from each other, despite having same names
    assertNotEquals(server2, server1);
    assertNotEquals(api2, api1);
    assertNotEquals(impl2, impl1);
    assertNotEquals(database2, database1);
    assertNotEquals(tables2, tables1);
  }
}
