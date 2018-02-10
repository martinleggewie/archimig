package org.codemaker.archimig.model.architecture;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Martin Leggewie
 * @since 09.02.2018
 */
public class ComponentTest {
  @Test
  public void testAddDependencyTrivial() {

    // 1. Assign
    Component component1 = new Component("component1");
    Component component2 = new Component("component2");

    // 2. Act
    component1.addDependencyTo(component2);

    // 3. Assert
    assertTrue(component1.getDependees().contains(component2));
  }


  @Test
  public void testAddDependencySimpleChain() {

    // 1. Assign
    Component component1 = new Component("component1");
    Component component2 = new Component("component2");
    Component component3 = new Component("component3");

    // 2. Act
    component1.addDependencyTo(component2);
    component2.addDependencyTo(component3);

    // 3. Assert
    assertFalse(component1.getDependees().contains(component1));
    assertTrue(component1.getDependees().contains(component2));
    assertFalse(component1.getDependees().contains(component3));
    assertFalse(component2.getDependees().contains(component1));
    assertFalse(component2.getDependees().contains(component2));
    assertTrue(component2.getDependees().contains(component3));
    assertEquals(0, component3.getDependees().size());
  }


  @Test
  public void testAddDependencyWithSplitChain() {

    // 1. Assign
    Component component1 = new Component("component1");
    Component component2 = new Component("component2");
    Component component3 = new Component("component3");
    Component component4 = new Component("component4");

    // 2. Act
    component1.addDependencyTo(component2);
    component1.addDependencyTo(component3);
    component1.addDependencyTo(component4);
    component2.addDependencyTo(component3);
    component2.addDependencyTo(component4);
    component3.addDependencyTo(component4);

    // 3. Assert
    assertFalse(component1.getDependees().contains(component1));
    assertTrue(component1.getDependees().contains(component2));
    assertTrue(component1.getDependees().contains(component3));
    assertTrue(component1.getDependees().contains(component4));
    assertFalse(component2.getDependees().contains(component1));
    assertFalse(component2.getDependees().contains(component2));
    assertTrue(component2.getDependees().contains(component3));
    assertTrue(component2.getDependees().contains(component4));
    assertFalse(component3.getDependees().contains(component1));
    assertFalse(component3.getDependees().contains(component2));
    assertFalse(component3.getDependees().contains(component3));
    assertTrue(component3.getDependees().contains(component4));
    assertEquals(0, component4.getDependees().size());
  }


  @Test
  public void testAddDependencyCycleWithOneComponent() {

    // 1. Assign
    Component component1 = new Component("component1");

    // 2. Act + 3. Assert
    try {
      component1.addDependencyTo(component1);
      fail("Exception because of detected cycle should have been thrown");
    } catch (Exception e) {
      // expected exception
    }
  }


  @Test
  public void testAddDependencyCycleWithTwoComponents() {

    // 1. Assign
    Component component1 = new Component("component1");
    Component component2 = new Component("component2");
    component1.addDependencyTo(component2);

    // 2. Act + 3. Assert
    try {
      component2.addDependencyTo(component1);
      fail("Exception because of detected cycle should have been thrown");
    } catch (Exception e) {
      // expected exception
    }
  }


  @Test
  public void testAddDependencyCycleWithThreeComponents() {

    // 1. Assign
    Component component1 = new Component("component1");
    Component component2 = new Component("component2");
    Component component3 = new Component("component3");
    component1.addDependencyTo(component2);
    component2.addDependencyTo(component3);

    // 2. Act + 3. Assert
    try {
      component3.addDependencyTo(component1);
      fail("Exception because of detected cycle should have been thrown");
    } catch (Exception e) {
      // expected exception
    }
  }


  @Test
  public void testAddDependencyCycleWithManyManyManyComponents() {

    // 1. Assign
    Component firstComponent = new Component("TheComponent");
    Component nextComponent = createNextComponent(firstComponent);
    for (int i = 1; i < 1000; i++) {
      for (int j = 1; j < 10; j++) {
        nextComponent.addDependencyTo(new Component("AnotherComponent" + j));
      }
      nextComponent = createNextComponent(nextComponent);
    }

    // 2. Act + 3. Assert
    try {
      nextComponent.addDependencyTo(firstComponent);
      fail("Exception because of detected cycle should have been thrown");
    } catch (Exception e) {
      // expected exception
    }
  }


  private Component createNextComponent(Component component) {
    Component result = new Component(component.getName() + "*");
    component.addDependencyTo(result);
    return result;
  }

}
