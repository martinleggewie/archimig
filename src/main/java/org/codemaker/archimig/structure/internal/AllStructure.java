package org.codemaker.archimig.structure.internal;

import org.codemaker.archimig.structure.StructureCell;
import org.codemaker.archimig.structure.StructureColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * Before we can decide where systems and their components should be located, we have to collect everything from all migration
 * steps into one and only one structure, the "AllStructure". If we have all this information in one place, we can apply our
 * structuring algorithms.
 *
 * @author Martin Leggewie
 * @since 12.03.2018
 */
public class AllStructure {

  private final List<StructureColumn> columns = new ArrayList<StructureColumn>();

  public List<StructureColumn> getColumns() {
    return columns;
  }

  public StructureColumn findColumn(String columnTitle) {
    for (StructureColumn column : columns) {
      if (column.getTitle().equals(columnTitle)) {
        return column;
      }
    }
    return null;
  }

  public StructureCell findCell(String cellTitle) {
    for (StructureColumn column : columns) {
      for (StructureCell cell : column.getCells()) {
        if (cell.getTitle().equals(cellTitle)) {
          return cell;
        }
      }
    }
    return null;
  }


  public int determineNumberOfColumns() {
    return columns.size();
  }


  public int determineNumberOfCellsPerColumn() {
    int maxNumberOfCellsPerColumn = 0;
    for (StructureColumn column : columns) {
      if (column.getCells().size() > maxNumberOfCellsPerColumn) {
        maxNumberOfCellsPerColumn = column.getCells().size();
      }
    }
    return maxNumberOfCellsPerColumn;
  }


  public int determineNumberOfCharactersPerCellTitle() {
    int maxNumberOfCharactersPerCellTitle = 0;
    for (StructureColumn column : columns) {
      for (StructureCell cell : column.getCells()) {
        if (cell.getTitle().length() > maxNumberOfCharactersPerCellTitle) {
          maxNumberOfCharactersPerCellTitle = cell.getTitle().length();
        }
      }
    }
    return maxNumberOfCharactersPerCellTitle;
  }

}
