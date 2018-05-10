package org.codemaker.archimig.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one cell (= a rectangle-like shape). What separates a cell from just a rectangle is the fact that a cell
 * also "knows" about so-called incoming and outgoing other cells. These incoming and outgoing cells will later be
 * represented by - for example - lines with arrow-heads on one side.
 *
 * @author Martin Leggewie
 * @since 11.02.2018
 */
public final class StructureCell {

  public static final StructureCell EMPTY_CELL = new StructureCell("(empty)");

  private final String title;
  private final List<StructureCell> incomingCells = new ArrayList<StructureCell>();
  private final List<StructureCell> outgoingCells = new ArrayList<StructureCell>();


  public StructureCell(String title) {
    this.title = title;
  }


  public String getTitle() {
    return title;
  }

  public List<StructureCell> getIncomingCells() {
    return incomingCells;
  }

  public List<StructureCell> getOutgoingCells() {
    return outgoingCells;
  }
}
