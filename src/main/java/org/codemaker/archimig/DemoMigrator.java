package org.codemaker.archimig;

import org.apache.commons.io.IOUtils;
import org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator;
import org.codemaker.archimig.diagram.Diagram;
import org.codemaker.archimig.structure.Structure;
import org.codemaker.archimig.structure.StructureCreator;
import org.codemaker.archimig.model.descriptors.MigDescriptor;
import org.codemaker.archimig.model.descriptors.MigStepDescriptor;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.setup.MigrationGenerator;
import org.codemaker.archimig.setup.TrafoDescriptorBuilder;

import java.io.IOException;
import java.util.List;

/**
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class DemoMigrator {

  public static void main(String[] args) {

    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Migration Step 1");
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Impl").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Database").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Tables").toSystem("Database")
        .build());
//    migStepDescriptor1.addTrafoDescriptor((new TrafoDescriptorBuilder().addDependencyFromComponent("Impl")
//        .toComponent("API")).build());
    migStepDescriptor1.addTrafoDescriptor((new TrafoDescriptorBuilder().addDependencyFromComponent("Impl")
        .toComponent("Tables")).build());

    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Migration Step 2");
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("UI").toSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Controller").toSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API-Adapter").toSystem("Client")
        .build());
//    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("UI").toComponent
//        ("Controller").build());
//    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("Controller").toComponent
//        ("API-Adapter").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("API-Adapter")
        .toComponent("API").build());

    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor
        (migStepDescriptor2);

    Migration migration = new MigrationGenerator(migDescriptor).generate();
    List<Structure> structures = new StructureCreator(migration).create();

    for (Diagram diagram : new AsciiArtDiagramCreator().create(structures)) {
      try {
        System.out.println(IOUtils.toString(diagram.getReader()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
