

# What is it about?

ArchiMig's goal is to create a sequence of diagrams which support you when you would like to discuss a plan by which you would
like to migrate an existing IT system architecture into another one.
The goal is that you can then use these diagrams to communicate the migration to your co-workers and your management.
As it is typical for such discussions, many iterations are needed, and each  iteration might and most like will reveal changes which need to be added to these diagrams.
ArchiMig allows you to have these changes flown back into the diagrams without you having to meticulously and most of the time
painstakingly change these diagrams manually.

To achieve this, the key concept is that you model the whole migration as a sequence of steps.
Each step defines which parts of your architecture are added, changed, deleted, or connected to each other.
These steps do not "know" anything about a diagram - instead, you describe the actual steps from the architecture's perspective.
After you have modeled the migration and its steps, ArchiMig will then take this model and creates a sequence of diagrams, one for
each step.
Changes to these diagrams are actually changes to the migration model, and after these model changes are done, you only need to
re-run ArchiMig so that it creates a new version of the diagrams.

# Hello World

To get a first impression of how this could look like, download the repo, open it in your favorite Maven-supporting IDE and run
the CaaMigrator class. It will (hopefully) output some AsciiArt like this:

```
+---------------------------------------------------------------------------+
|                                 Schritt 1                                 |
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
|                                 Schritt 2                                 |
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


# Basic concepts

ArchiMig's domain model let's you define your architecture as follows:

* system
* component

TO BE CONTINUED ...


