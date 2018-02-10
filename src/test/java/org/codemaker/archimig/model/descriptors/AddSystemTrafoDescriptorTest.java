package org.codemaker.archimig.model.descriptors;

import org.codemaker.archimig.setup.TrafoDescriptorBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AddSystemTrafoDescriptorTest {

  @Test
  public void testAddSystemHappy() {

    // 1. Assign
    String systemName = "Mein erstes System";

    // 2. Act
    TrafoDescriptor trafoDescriptor = new TrafoDescriptorBuilder().addSystem(systemName).build();

    // 3. Assert
    assertEquals(AddSystemTrafoDescriptor.class, trafoDescriptor.getClass());
    AddSystemTrafoDescriptor addSystemTrafo = (AddSystemTrafoDescriptor) trafoDescriptor;
    assertEquals(systemName, addSystemTrafo.getSystemName());
  }


  @Test
  public void testAddComponentToSystemUnhappy() {

    // 1. Assign
    String systemName = "Mein erstes System";

    // 2. Act + 3. Assert: two systems are added
    try {
      new TrafoDescriptorBuilder().addSystem(systemName).addSystem(systemName).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }

    // 2. Act + 3. Assert: one system is added to another system
    try {
      new TrafoDescriptorBuilder().addSystem(systemName).toSystem(systemName).build();
      fail("Exception should have been thrown.");
    } catch (Exception e) {
      // expected exception
    }
  }

}


