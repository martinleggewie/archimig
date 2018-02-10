package org.codemaker.archimig;

import org.codemaker.archimig.model.descriptors.MigDescriptor;
import org.codemaker.archimig.model.descriptors.MigStepDescriptor;
import org.codemaker.archimig.model.migration.Migration;
import org.codemaker.archimig.setup.MigrationGenerator;
import org.codemaker.archimig.setup.TrafoDescriptorBuilder;

/**
 * @author Martin Leggewie
 * @since 08.02.2018
 */
public class CaaMigrator {

  public static void main(String[] args) {

    MigStepDescriptor migStepDescriptor1 = new MigStepDescriptor("Schritt 1");
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Impl").toSystem("Server").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Database").build());
    migStepDescriptor1.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("Tables").toSystem("Database")
        .build());
    migStepDescriptor1.addTrafoDescriptor((new TrafoDescriptorBuilder().addDependencyFromComponent("Impl")
        .toComponent("API")).build());
    migStepDescriptor1.addTrafoDescriptor((new TrafoDescriptorBuilder().addDependencyFromComponent("Impl")
        .toComponent("Tables")).build());

    MigStepDescriptor migStepDescriptor2 = new MigStepDescriptor("Schritt 2");
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("UI").toSystem("Client").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addComponent("API-Adapter").toSystem("Client")
        .build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("UI").toComponent
        ("API-Adapter").build());
    migStepDescriptor2.addTrafoDescriptor(new TrafoDescriptorBuilder().addDependencyFromComponent("API-Adapter")
        .toComponent("API").build());

    MigDescriptor migDescriptor = new MigDescriptor().addMigStepDescriptor(migStepDescriptor1).addMigStepDescriptor
        (migStepDescriptor2);

    Migration migration = new MigrationGenerator(migDescriptor).generate();

    System.out.println(migration.generateReport());
  }

}