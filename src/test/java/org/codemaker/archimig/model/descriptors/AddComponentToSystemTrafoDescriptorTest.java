package org.codemaker.archimig.model.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.codemaker.archimig.setup.TrafoDescriptorBuilder;
import org.junit.Test;

/**
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class AddComponentToSystemTrafoDescriptorTest {

  @Test
  public void testAddComponentToSystemHappy() {

    // 1. Assign
    String componentName = "Meine erste Komponente";
    String systemName = "Mein erstes System";

    // 2. Act
    TrafoDescriptor trafoDescriptor = new TrafoDescriptorBuilder().addComponent(componentName).toSystem(systemName).build();

    // 3. Assert
    assertEquals(AddComponentToSystemTrafoDescriptor.class, trafoDescriptor.getClass());
    AddComponentToSystemTrafoDescriptor addComponentToSystemTrafoDescriptor = (AddComponentToSystemTrafoDescriptor) trafoDescriptor;
    assertEquals(componentName, addComponentToSystemTrafoDescriptor.getComponentName());
    assertEquals(systemName, addComponentToSystemTrafoDescriptor.getSystemName());
  }


  @Test
  public void testAddComponentToSystemUnhappy() {

    // 1. Assign
    String componentName = "Meine erste Komponente";
    String systemName = "Mein erstes System";

    // 2. Act + 3. Assert: only component is added, no system is set
    try {
      new TrafoDescriptorBuilder().addComponent(componentName).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }

    // 2. Act + 3. Assert: two components are added
    try {
      new TrafoDescriptorBuilder().addComponent(componentName).addComponent(componentName).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }

    // 2. Act + 3. Assert: system and component are set in the wrong order
    try {
      new TrafoDescriptorBuilder().toSystem(systemName).addComponent(componentName).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }
  }

}
