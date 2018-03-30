package org.codemaker.archimig.diagram.asciiart;

import org.codemaker.archimig.structure.Structure;

import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_CORNER;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_HORIZONTAL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_VERTICAL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_WHITESPACE;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.PADDING_TEXT_VERTICAL;

class AsciiArtDiagramTitleCreator {

  private final Structure structure;
  private final int width;


  AsciiArtDiagramTitleCreator(Structure structure, int width) {
    this.structure = structure;
    this.width = width;
  }


  public String create() {

    StringBuilder buffer = new StringBuilder();
    String title = structure.getTitle();

    // 1. top line + vertical space above title line
    buffer.append(createHorizontalEdgeLine(width));
    buffer.append(createHorizontalSpaceLine(width));

    // 2. title line
    int titlePaddingLeft = (width - 2 - title.length()) / 2;
    buffer.append(CHAR_VERTICAL);
    for (int i = 0; i < titlePaddingLeft; i++) {
      buffer.append(CHAR_WHITESPACE);
    }
    buffer.append(title);
    int titlePaddingRight = width - 2 - titlePaddingLeft - title.length();
    for (int i = 0; i < titlePaddingRight; i++) {
      buffer.append(CHAR_WHITESPACE);
    }
    buffer.append(CHAR_VERTICAL);
    buffer.append("\n");

    // 3. vertical space below title line + bottom line
    buffer.append(createHorizontalSpaceLine(width));
    buffer.append(createHorizontalEdgeLine(width));

    return buffer.toString();
  }


  private String createHorizontalEdgeLine(int titleWidth) {
    StringBuilder buffer = new StringBuilder();
    buffer.append(CHAR_CORNER);
    for (int i = 0; i < titleWidth - 2; i++) {
      buffer.append(CHAR_HORIZONTAL);
    }
    buffer.append(CHAR_CORNER);
    buffer.append("\n");
    return buffer.toString();
  }


  private String createHorizontalSpaceLine(int titleWidth) {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < PADDING_TEXT_VERTICAL; i++) {
      buffer.append(CHAR_VERTICAL);
      for (int j = 0; j < titleWidth - 2; j++) {
        buffer.append(CHAR_WHITESPACE);
      }
      buffer.append(CHAR_VERTICAL);
      buffer.append("\n");
    }
    return buffer.toString();
  }

}
