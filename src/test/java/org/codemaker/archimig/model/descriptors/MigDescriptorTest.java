package org.codemaker.archimig.model.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.codemaker.archimig.setup.TrafoDescriptorBuilder;
import org.junit.Test;

/**
 * @author Martin Leggewie
 * @since 06.02.2018
 */
public class MigDescriptorTest {

  @Test
  public void testTrivialMigration() {

    // 1. Assign
    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Schritt 1");
    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Schritt 2");

    // 2. Act
    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor(migStepDescriptor2);

    // 3. Assert
    assertEquals(2, migDescriptor.getMigStepDescriptors().size());
    assertEquals(migStepDescriptor1, migDescriptor.getMigStepDescriptors().get(0));
    assertEquals(migStepDescriptor2, migDescriptor.getMigStepDescriptors().get(1));
  }


  @Test
  public void testSimpleMigration() {

    // 1. Assign
    TrafoDescriptor addServerSystemTrafoDescriptor = new TrafoDescriptorBuilder().addSystem("serverSystem").build();
    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Schritt 1").addTrafoDescriptor(addServerSystemTrafoDescriptor);

    TrafoDescriptor addClientSystemTrafoDescriptor = new TrafoDescriptorBuilder().addSystem("clientSystem").build();
    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Schritt 2").addTrafoDescriptor(addClientSystemTrafoDescriptor);

    // 2. Act
    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor(migStepDescriptor2);

    // 3. Assert
    assertNotNull(addServerSystemTrafoDescriptor);
    assertNotNull(addClientSystemTrafoDescriptor);
    assertEquals(2, migDescriptor.getMigStepDescriptors().size());
    assertEquals(migStepDescriptor1, migDescriptor.getMigStepDescriptors().get(0));
    assertEquals(migStepDescriptor2, migDescriptor.getMigStepDescriptors().get(1));
    assertEquals(1, migDescriptor.getMigStepDescriptors().get(0).getTrafoDescriptors().size());
    assertEquals(addServerSystemTrafoDescriptor, migDescriptor.getMigStepDescriptors().get(0).getTrafoDescriptors().get(0));
    assertEquals(1, migDescriptor.getMigStepDescriptors().get(1).getTrafoDescriptors().size());
    assertEquals(addClientSystemTrafoDescriptor, migDescriptor.getMigStepDescriptors().get(1).getTrafoDescriptors().get(0));
  }

}
