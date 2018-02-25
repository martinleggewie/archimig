


# What is it all about?

ArchiMig's lets you create a sequence of diagrams which show - like a flip-book - a sequence of steps for migrating an IT system architecture.

The goal is that you can then use these diagrams to communicate the migration to your co-workers and your management.
As it is typical for such discussions, many iterations are needed, and each iteration might and most like will reveal changes which need to be added to these diagrams.
Now, ArchiMig allows you to have these changes flown back into the diagrams without the need to meticulously and most of the time painstakingly change these diagrams manually.

The key concept is that you model the whole migration as a sequence of steps.
Each step defines which parts of your architecture are added, changed, deleted, or connected to each other.
These steps do not "know" anything about a diagram - instead, you describe the various transformations from the architecture's perspective.
After you have modeled the migration and its steps, ArchiMig will then take this model and create a sequence of diagrams, one for each modeled step.
Changes to these diagrams are actually changes to the migration model.
After you have modeled these changes, you only need to re-run ArchiMig which will create a new sequence of diagrams which represent the changed version of the migration steps.


# Hello World

To get a first impression of how this could look like, have Maven installed on your machine, download the repo, change to the repo's root folder and execute the following command:

```
mvn exec:java -Dexec.mainClass="org.codemaker.archimig.DemoMigrator"
```



It will (hopefully) output some AsciiArt like this:

```
+---------------------------------------------------------------------------+
|                             Migration Step 1                              |
+---------------------------------------------------------------------------+
+---------------------+    +---------------------+    +---------------------+
|                     |    |                     |    |                     |
| +-----------------+ |    | +-----------------+ |    |                     |
| |                 | |    | |                 | |    |                     |
| |       API       | |    | |     Tables      | |    |                     |
| |                 | |    | |                 | |    |                     |
| +-----------------+ |    | +-----------------+ |    |                     |
|                     |    |                     |    |                     |
| +-----------------+ |    |                     |    |                     |
| |                 | |    |                     |    |                     |
| |      Impl       | |    |                     |    |                     |
| |                 | |    |                     |    |                     |
| +-----------------+ |    |                     |    |                     |
|                     |    |                     |    |                     |
|                     |    |                     |    |                     |
|                     |    |                     |    |                     |
|                     |    |                     |    |                     |
|                     |    |                     |    |                     |
|                     |    |                     |    |                     |
|                     |    |                     |    |                     |
|       Server        |    |      Database       |    |                     |
+---------------------+    +---------------------+    +---------------------+

+---------------------------------------------------------------------------+
|                             Migration Step 2                              |
+---------------------------------------------------------------------------+
+---------------------+    +---------------------+    +---------------------+
|                     |    |                     |    |                     |
| +-----------------+ |    | +-----------------+ |    | +-----------------+ |
| |                 | |    | |                 | |    | |                 | |
| |       API       | |    | |     Tables      | |    | |       UI        | |
| |                 | |    | |                 | |    | |                 | |
| +-----------------+ |    | +-----------------+ |    | +-----------------+ |
|                     |    |                     |    |                     |
| +-----------------+ |    |                     |    | +-----------------+ |
| |                 | |    |                     |    | |                 | |
| |      Impl       | |    |                     |    | |   Controller    | |
| |                 | |    |                     |    | |                 | |
| +-----------------+ |    |                     |    | +-----------------+ |
|                     |    |                     |    |                     |
|                     |    |                     |    | +-----------------+ |
|                     |    |                     |    | |                 | |
|                     |    |                     |    | |   API-Adapter   | |
|                     |    |                     |    | |                 | |
|                     |    |                     |    | +-----------------+ |
|                     |    |                     |    |                     |
|       Server        |    |      Database       |    |       Client        |
+---------------------+    +---------------------+    +---------------------+
```


# The concept of how to model

ArchiMig's domain model let's you define your architecture as follows:

* **System**: represents a running application, e.g., a client, a server, a database.

* **Component**: part of exactly one system, e.g., WebShop UI, Storage API, Storage Impl, Storage Tables.

* **CallDependency**: directed connection between two components with the meaning that one  component depends on another component. Typically this means that one component calls another component, e.g., WebShop UI calls Storage API.

* **InheritDependency**: also a directed connection between two components, this time it means that one component implements another component. The typical example is that you have one component which is an API definition, and there is another component which contains the implementation of this API definition.

Based on these building blocks, you now can model the migration, its steps and the step's single transformations as follows:

* **Migration**: It all starts with the top-most entity: the migration itself. There must be one and only one migration per model. A migration has a title and a description, but most importantly it consists out of one to many migration steps.

* **MigrationStep**: Each migration step has a name and a list of one to many transformations. The following list of transformations are possible:

  - **AddSystem**: Adds a new system to the architecture. The system is identified by its name, and this name needs to be unique. Adding a system within a migration step means that you plan to add this system in this very migration step, meaning that it was not there before.

  - **AddComponentToSystem**: Adds a new component to an already existing system. Like the system, the component is identified by its unique name, and adding a component means that you plan to add this component to the system in this migration step.

  - **AddCall**: Adds a call dependency from one already existing component to another component. The two components don't need to be added to the same system. As the name implies, you want to model that the first component calls the second component.

  - **AddInheritance**: Adds a inheritence dependency from one already existing component to another component. Like with the call dependency, the two components don't need to be added to the same system. With this transformation, you model that the first component inherits or implements the second component.

  - **MoveComponent**: Moves an already existing component "as-is" from its original system to another system. This means that this component is not changed but really just moved. Example for such transformation is when you remove a library from one system and directly adds it to another system.

  - **TransformComponent**: Transforms an already existing component into a new component which can be located in the same or another system. Transforming means that you have to change (or maybe even completely implement it from scratch) it before it can be moved to its new location.

  - **RemoveSystem**: Guess what: with this you remove a system from the architecture.

  - **RemoveComponent**: Removes a component from its system.

  - **RemoveCall**: Removes a call dependency between two components.

  - **RemoveInheritance**: I guess you'll see the pattern here: with this transformation you remove the inheritance dependency between two components.


# The principles for creating diagrams

As said in the introduction, the created diagrams are to enable you to communicate the migration steps to other persons.
Since the amount of information modeled in these migration steps can be quite big, several rules need to be applied so that they are easier to be understood.
These rules enable humans's abilities to recognize patterns, and by this makes it easier to spot differences.
The main idea here is that the sequence of diagrams can be watched like a flip-book.
By comparing one diagram with following or previous diagrams, you and your co-humans can quite easily spot the differences.

To achieve this flip-book concept, ArchiMig applies the following layout rules:

* There is a hidden grid structure on all created diagrams, and the grid's dimension is the same on all diagrams.

* The canvas size is the same for all diagrams.

* Each system is shown as a rectangle with portrait orientation, and it is located in its own column in the hidden grid. There cannot be more than one system rectangle in one column. The system's name is located at the bottom of its rectangle.

* Each component is shown as a rectangle with landscape orientation, and it is located inside their system's rectangle. The component rectangles are also aligned to the hidden grid's rows. The size of all component rectangles of all systems are all the same. The component's name is placed in its rectangle's center.

* Call dependencies are shown as arrows with a "dot" start and a "vee" end (check [https://www.graphviz.org/doc/info/arrows.html](https://www.graphviz.org/doc/info/arrows.html) for the explanation of what "dot" and "vee" look like). These arrows are drawn as horizontal lines from left to right.

* Inheritance dependencies are shown as arrows with a "dot" start and an "onormal" end (again, check [https://www.graphviz.org/doc/info/arrows.html](https://www.graphviz.org/doc/info/arrows.html) for the explanation of what "dot" and "onormal" look like). These arrows are drawn as horizontal lines from right to left.

* If possible, crossing arrows have to be avoided. Since this most likely cannot be avoided when the migrated architecture gets bigger, the created diagrams will then have crossing arrows. To visualize that these arrows are not connected, the diagrams will indicate this by NOT having a "dot" at the crossing point. This is the reason why arrows start with a "dot" - this "dot" signals that arrows are really connected with its component.

* Arrows are drawn (if possible) as horizontal lines. If it cannot be avoided, the arrows's path will only turn in 45 or 90 degrees angles.

* Arrows are never ever drawn on top of each other. If there are many arrows located next to each other, this might even lead to a widening of the hidden grid.

* Once a system or a component is located at a given place in the hidden grid, it will not leave this place anymore throughout the whole migration. In addition, this location is not used by other components. Each system or component gets its space, it is reserved just for it, and nothing else will be located there.


The requirements defined in above lists have quite some effect on diagram creation.
The most important is that creating a diagram for a given step cannot be done without being aware of all other steps.
The algorithm which determines the dimensions of the hidden grid and locates all rectangles and arrows needs to first collect informationen from all steps before it can start with its layout work.






