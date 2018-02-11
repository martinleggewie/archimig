package org.codemaker.archimig.diagram.asciiart;

import org.codemaker.archimig.diagram.Structure;
import org.codemaker.archimig.model.architecture.Component;
import org.codemaker.archimig.model.architecture.RunningSystem;
import org.codemaker.archimig.model.migration.MigrationStep;

import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_CORNER;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_HORIZONTAL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_VERTICAL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.CHAR_WHITESPACE;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.PADDING_TEXT_HORIZONTAL;
import static org.codemaker.archimig.diagram.asciiart.AsciiArtDiagramCreator.PADDING_TEXT_VERTICAL;

class AsciiArtDiagramTitleCreator {

  private final Structure structure;


  AsciiArtDiagramTitleCreator(Structure structure) {
    this.structure = structure;
  }


  public String create() {

    StringBuilder buffer = new StringBuilder();
    String title = structure.getTitle();
    int titleWidth = PADDING_TEXT_HORIZONTAL + title.length() + PADDING_TEXT_HORIZONTAL;

    // 1. top line + vertical space above title line
    buffer.append(createHorizontalEdgeLine(titleWidth));
    buffer.append(createHorizontalSpaceLine(titleWidth));

    // 2. title line
    buffer.append(CHAR_VERTICAL);
    for (int i = 0; i < PADDING_TEXT_HORIZONTAL; i++) {
      buffer.append(CHAR_WHITESPACE);
    }
    buffer.append(title);
    for (int i = 0; i < PADDING_TEXT_HORIZONTAL; i++) {
      buffer.append(CHAR_WHITESPACE);
    }
    buffer.append(CHAR_VERTICAL);
    buffer.append("\n");

    // 3. vertical space below title line + bottom line
    buffer.append(createHorizontalSpaceLine(titleWidth));
    buffer.append(createHorizontalEdgeLine(titleWidth));

    return buffer.toString();
  }


  private String createHorizontalEdgeLine(int titleWidth) {
    StringBuilder buffer = new StringBuilder();
    buffer.append(CHAR_CORNER);
    for (int i = 0; i < titleWidth; i++) {
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
      for (int j = 0; j < titleWidth; j++) {
        buffer.append(CHAR_WHITESPACE);
      }
      buffer.append(CHAR_VERTICAL);
      buffer.append("\n");
    }
    return buffer.toString();
  }

}
