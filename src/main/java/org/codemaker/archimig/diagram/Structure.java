package org.codemaker.archimig.diagram;

import java.util.ArrayList;
import java.util.List;

public final class Structure {

  private final String title;
  private final int numberOfCharactersPerCellTitle;
  private final List<StructureColumn> columns = new ArrayList<StructureColumn>();


  public Structure(String title, int numberOfCharactersPerCellTitle) {
    this.title = title;
    this.numberOfCharactersPerCellTitle = numberOfCharactersPerCellTitle;
  }


  public String getTitle() {
    return title;
  }


  public List<StructureColumn> getColumns() {
    return columns;
  }


  public int getNumberOfCharactersPerCellTitle() {
    return numberOfCharactersPerCellTitle;
  }


  public int numberOfColumns() {
    return columns.size();
  }


  public int numberOfCellsPerColumn() {
    int result = 0;

    for (StructureColumn column : columns) {
      if (!column.equals(StructureColumn.EMPTY_COLUMN)) {
        if (result == 0) {
          // we have found the first non-empty column, and this defines the required number of cells per column
          result = columns.get(0).getCells().size();
        } else if (result != column.getCells().size()) {
          // Since we do not trust our programming skills, we do a small sanity check and verify that each columm
          // contains the same number of cells. Yes, this is smelly.
          throw new IllegalStateException("Number of cells is different in at least two columns of structure: " + this);
        }
      }
    }
    return result;
  }
}
