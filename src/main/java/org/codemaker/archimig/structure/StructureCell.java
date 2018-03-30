package org.codemaker.archimig.structure;

import java.util.ArrayList;
import java.util.List;

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
