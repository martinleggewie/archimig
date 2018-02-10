package org.codemaker.archimig.diagram;

import java.util.ArrayList;
import java.util.List;

public final class StructureColumn {

  private String title;
  private List<StructureCell> cells = new ArrayList<StructureCell>();


  public StructureColumn(String title) {
    this.title = title;
  }


  public String getTitle() {
    return title;
  }


  public List<StructureCell> getCells() {
    return cells;
  }
}
