package org.codemaker.archimig.diagram.asciiart;

import org.codemaker.archimig.structure.Structure;
import org.codemaker.archimig.structure.StructureCell;
import org.codemaker.archimig.structure.StructureColumn;

import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_CORNER;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_HORIZONTAL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_VERTICAL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_WHITESPACE;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.PADDING_CELL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.PADDING_COLUMN;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.PADDING_TEXT_HORIZONTAL;

class AsciiArtDiagramBodyCreator {

  private final Structure structure;
  private final int cellWidth;
  private final int columnWidth;


  AsciiArtDiagramBodyCreator(Structure structure) {
    this.structure = structure;
    this.cellWidth = structure.getNumberOfCharactersPerCellTitle() + (2 * PADDING_TEXT_HORIZONTAL);
    this.columnWidth = cellWidth + (2 * PADDING_CELL) + 2;
  }


  public String create() {
    StringBuilder buffer = new StringBuilder();

    // 1. Create top line
    buffer.append(createColumnEdge());

    // 2. For each row create free space above the cells and then the cells themselves
    for (int row = 0; row < structure.numberOfCellsPerColumn(); row++) {
      buffer.append(createRow(row));
    }

    // 3. Create column title line and bottom line
    buffer.append(createColumnTitle());
    buffer.append(createColumnEdge());

    return buffer.toString();
  }


  public int createdWidth() {
    return createColumnEdge().length() - 1;
  }


  private String createColumnEdge() {
    StringBuilder buffer = new StringBuilder();

    for (int i = 1; i <= structure.numberOfColumns(); i++) {
      buffer.append(CHAR_CORNER);
      for (int j = 0; j < columnWidth; j++) {
        buffer.append(CHAR_HORIZONTAL);
      }
      buffer.append(CHAR_CORNER);
      if (i < structure.numberOfColumns()) {
        for (int j = 0; j < PADDING_COLUMN; j++) {
          buffer.append(CHAR_WHITESPACE);
        }
      }
    }
    buffer.append("\n");
    return buffer.toString();
  }


  private String createColumnTitle() {
    StringBuilder buffer = new StringBuilder();

    buffer.append(createFreeSpaceOutsideCell());
    for (int col = 1; col <= structure.numberOfColumns(); col++) {
      buffer.append(CHAR_VERTICAL);

      StructureColumn column = structure.getColumns().get(col - 1);
      if (column.equals(StructureColumn.EMPTY_COLUMN)) {
        for (int i = 0; i < columnWidth; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      } else {
        String columnTitle = structure.getColumns().get(col - 1).getTitle();
        int titlePaddingLeft = (columnWidth - columnTitle.length()) / 2;
        for (int i = 0; i < titlePaddingLeft; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
        buffer.append(columnTitle);
        int titlePaddingRight = columnWidth - titlePaddingLeft - columnTitle.length();
        for (int i = 0; i < titlePaddingRight; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      }
      buffer.append(CHAR_VERTICAL);
      if (col < structure.numberOfColumns()) {
        for (int i = 0; i < PADDING_COLUMN; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      }
    }

    buffer.append("\n");
    return buffer.toString();
  }


  private String createRow(int rowindex) {
    StringBuilder buffer = new StringBuilder();

    // 1. Create free space above the row
    buffer.append(createFreeSpaceOutsideCell());

    // 2. Create top edge of the row
    buffer.append(createCellEdge(rowindex));

    // 3. Create free space below the top edge but above the cell titles
    buffer.append(createCellSpace(rowindex));

    // 4. Create cell titles
    buffer.append(createCellTitle(rowindex));

    // 5. Create free space below the cell titles but above the bottom edge
    buffer.append(createCellSpace(rowindex));

    // 6. Create bottom edge of the row
    buffer.append(createCellEdge(rowindex));

    return buffer.toString();
  }


  private String createFreeSpaceOutsideCell() {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < PADDING_CELL; i++) {
      for (int col = 1; col <= structure.numberOfColumns(); col++) {
        buffer.append(CHAR_VERTICAL);
        for (int j = 0; j < columnWidth; j++) {
          buffer.append(CHAR_WHITESPACE);
        }
        buffer.append(CHAR_VERTICAL);
        if (col < structure.numberOfColumns()) {
          for (int j = 0; j < PADDING_COLUMN; j++) {
            buffer.append(CHAR_WHITESPACE);
          }
        }
      }
      buffer.append("\n");
    }
    return buffer.toString();
  }


  private String createCellEdge(int rowindex) {
    StringBuilder buffer = new StringBuilder();

    for (int col = 1; col <= structure.numberOfColumns(); col++) {
      buffer.append(CHAR_VERTICAL);
      for (int i = 0; i < PADDING_CELL; i++) {
        buffer.append(CHAR_WHITESPACE);
      }

      StructureColumn column = structure.getColumns().get(col - 1);
      StructureCell cell = StructureCell.EMPTY_CELL;
      if (!column.equals(StructureColumn.EMPTY_COLUMN)) {
        cell = column.getCells().get(rowindex);
      }
      if (column.equals(StructureColumn.EMPTY_COLUMN) || cell.equals(StructureCell.EMPTY_CELL)) {
        for (int i = 0; i < cellWidth + 2; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      } else {
        buffer.append(CHAR_CORNER);
        for (int i = 0; i < cellWidth; i++) {
          buffer.append(CHAR_HORIZONTAL);
        }
        buffer.append(CHAR_CORNER);
      }

      for (int i = 0; i < PADDING_CELL; i++) {
        buffer.append(CHAR_WHITESPACE);
      }
      buffer.append(CHAR_VERTICAL);
      if (col < structure.numberOfColumns()) {
        for (int i = 0; i < PADDING_COLUMN; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      }
    }

    buffer.append("\n");
    return buffer.toString();
  }


  private String createCellSpace(int rowindex) {
    StringBuilder buffer = new StringBuilder();

    for (int col = 1; col <= structure.numberOfColumns(); col++) {
      buffer.append(CHAR_VERTICAL);
      for (int i = 0; i < PADDING_CELL; i++) {
        buffer.append(CHAR_WHITESPACE);
      }

      StructureColumn column = structure.getColumns().get(col - 1);
      StructureCell cell = StructureCell.EMPTY_CELL;
      if (!column.equals(StructureColumn.EMPTY_COLUMN)) {
        cell = column.getCells().get(rowindex);
      }
      if (column.equals(StructureColumn.EMPTY_COLUMN) || cell.equals(StructureCell.EMPTY_CELL)) {
        for (int i = 0; i < cellWidth + 2; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      } else {
        buffer.append(CHAR_VERTICAL);
        for (int i = 0; i < cellWidth; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
        buffer.append(CHAR_VERTICAL);
      }
      for (int i = 0; i < PADDING_CELL; i++) {
        buffer.append(CHAR_WHITESPACE);
      }
      buffer.append(CHAR_VERTICAL);
      if (col < structure.numberOfColumns()) {
        for (int i = 0; i < PADDING_COLUMN; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      }
    }

    buffer.append("\n");
    return buffer.toString();
  }


  private String createCellTitle(int rowindex) {
    StringBuilder buffer = new StringBuilder();

    for (int col = 1; col <= structure.numberOfColumns(); col++) {
      buffer.append(CHAR_VERTICAL);
      for (int i = 0; i < PADDING_CELL; i++) {
        buffer.append(CHAR_WHITESPACE);
      }

      StructureColumn column = structure.getColumns().get(col - 1);
      StructureCell cell = StructureCell.EMPTY_CELL;
      if (!column.equals(StructureColumn.EMPTY_COLUMN)) {
        cell = column.getCells().get(rowindex);
      }
      if (column.equals(StructureColumn.EMPTY_COLUMN) || cell.equals(StructureCell.EMPTY_CELL)) {
        for (int i = 0; i < cellWidth + 2; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      } else {
        buffer.append(CHAR_VERTICAL);
        String cellTitle = structure.getColumns().get(col - 1).getCells().get(rowindex).getTitle();
        int titlePaddingLeft = (cellWidth - cellTitle.length()) / 2;
        for (int i = 0; i < titlePaddingLeft; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
        buffer.append(cellTitle);
        int titlePaddingRight = cellWidth - titlePaddingLeft - cellTitle.length();
        for (int i = 0; i < titlePaddingRight; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
        buffer.append(CHAR_VERTICAL);
      }
      for (int i = 0; i < PADDING_CELL; i++) {
        buffer.append(CHAR_WHITESPACE);
      }
      buffer.append(CHAR_VERTICAL);
      if (col < structure.numberOfColumns()) {
        for (int i = 0; i < PADDING_COLUMN; i++) {
          buffer.append(CHAR_WHITESPACE);
        }
      }
    }

    buffer.append("\n");
    return buffer.toString();
  }

}
