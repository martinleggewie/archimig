package org.codemaker.archimig.diagram;

import java.io.Reader;
import java.io.StringReader;

public final class AsciiArtDiagram implements Diagram {

  private String asciiArtString;


  public AsciiArtDiagram(String asciiArtString) {
    this.asciiArtString = asciiArtString;
  }


  public Reader getReader() {
    return new StringReader(asciiArtString);
  }


  @Override
  public String toString() {
    return "AsciiArtDiagram{" + "asciiArtString='" + asciiArtString + '\'' + '}';
  }
}
