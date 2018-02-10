package org.codemaker.archimig.diagram;

public final class StructureCell {

  public static final StructureCell EMPTY_CELL = new StructureCell("(empty");

  private final String title;


  public StructureCell(String title) {
    this.title = title;
  }


  public String getTitle() {
    return title;
  }
}
