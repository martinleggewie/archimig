package org.codemaker.archimig.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one column (= a rectangle-like shape). A column is different from just a rectangle because it "knows"
 * about its internal cells.
 *
 * @author Martin Leggewie
 * @since 11.02.2018
 */
public final class StructureColumn {

  public static final StructureColumn EMPTY_COLUMN = new StructureColumn("(empty)");

  private final String title;
  private final List<StructureCell> cells = new ArrayList<StructureCell>();


  public StructureColumn(String title) {
    this.title = title;
  }


  public String getTitle() {
    return title;
  }


  public List<StructureCell> getCells() {
    return cells;
  }

  public int numberOfOutgoingCells() {
    int result = 0;

    for (StructureCell cell : cells) {
      result += cell.getOutgoingCells().size();
    }
    return result;
  }

  public int numberOfIncomingCells() {
    int result = 0;

    for (StructureCell cell : cells) {
      result += cell.getIncomingCells().size();
    }
    return result;
  }
}
