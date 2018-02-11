package org.codemaker.archimig.diagram.asciiart;

import org.codemaker.archimig.diagram.DiagramCreator;
import org.codemaker.archimig.diagram.Structure;

import java.util.ArrayList;
import java.util.List;

public final class AsciiArtDiagramCreator implements DiagramCreator {

  static final String CHAR_CORNER = "+";
  static final String CHAR_HORIZONTAL = "-";
  static final String CHAR_VERTICAL = "|";
  static final String CHAR_WHITESPACE = " ";

  static final int PADDING_TEXT_HORIZONTAL = 3;
  static final int PADDING_TEXT_VERTICAL = 0;
  static final int PADDING_CELL = 1;
  static final int PADDING_COLUMN = 4;


  public List<AsciiArtDiagram> create(List<Structure> structures) {
    List<AsciiArtDiagram> asciiArtDiagrams = new ArrayList<AsciiArtDiagram>();

    for (Structure structure : structures) {
      asciiArtDiagrams.add(createAsciiArtDiagram(structure));
    }

    return asciiArtDiagrams;
  }


  private AsciiArtDiagram createAsciiArtDiagram(Structure structure) {

    StringBuilder buffer = new StringBuilder();

    // 1. Create body (columns and cells and text contained in cells)
    String body = new AsciiArtDiagramBodyCreator(structure).create();

    // 2. Create title
    String title = new AsciiArtDiagramTitleCreator(structure).create();

    // 3. Construct the whole thing
    buffer.append(title);
    buffer.append(body);

    return new AsciiArtDiagram(buffer.toString());
  }

}
