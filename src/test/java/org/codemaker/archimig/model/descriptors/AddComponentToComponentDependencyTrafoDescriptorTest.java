package org.codemaker.archimig.model.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.codemaker.archimig.setup.TrafoDescriptorBuilder;
import org.junit.Test;

/**
 * @author Martin Leggewie
 * @since 09.02.2018
 */
public class AddComponentToComponentDependencyTrafoDescriptorTest {

  @Test
  public void testAddDependencyToComponentHappy() {

    // 1. Assign
    String componentName1 = "Meine erste Komponente";
    String componentName2 = "Meine zweite Komponente";

    // 2. Act
    TrafoDescriptor trafoDescriptor = new TrafoDescriptorBuilder().addDependencyFromComponent(componentName1)
        .toComponent(componentName2).build();

    // 3. Assert
    assertEquals(AddComponentToComponentDependencyTrafoDescriptor.class, trafoDescriptor.getClass());
    AddComponentToComponentDependencyTrafoDescriptor addComponentToComponentDependencyTrafoDescriptor = (AddComponentToComponentDependencyTrafoDescriptor) trafoDescriptor;
    assertEquals(componentName1, addComponentToComponentDependencyTrafoDescriptor.getFromComponentName());
    assertEquals(componentName2, addComponentToComponentDependencyTrafoDescriptor.getToComponentName());
  }

  @Test
  public void testAddDependencyToComponentUnhappy() {

    // 1. Assign
    String componentName1 = "Meine erste Komponente";
    String componentName2 = "Meine zweite Komponente";

    // 2. Act + 3. Assert: only fromComponent is set, toComponent is not set
    try {
      new TrafoDescriptorBuilder().addDependencyFromComponent(componentName1).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }

    // 2. Act + 3. Assert: only toComponent is set, fromComponent is not set
    try {
      new TrafoDescriptorBuilder().toComponent(componentName1).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }

    // 2. Act + 3. Assert: toComponent is set before fromComponent
    try {
      new TrafoDescriptorBuilder().toComponent(componentName1).addDependencyFromComponent(componentName2).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }
  }

}
