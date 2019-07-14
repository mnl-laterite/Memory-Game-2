package MemoryGame;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {

  public ResizableCanvas(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
    super();

    this.setWidth(width.get());
    this.setHeight(height.get());

    this.widthProperty().bind(width);
    this.heightProperty().bind(height);
  }

  @Override
  public boolean isResizable() {
    return true;
  }
}
