package MemoryGame;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.canvas.Canvas;

/**
 * @author mnl-laterite
 */
public class ResizableCanvas extends Canvas {

  /**
   * Creates a resizable canvas which will change its size to always fit in a given container node.
   * @param width the container node width.
   * @param height the container node height.
   */
  ResizableCanvas(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
    super();

    this.setWidth(width.get());
    this.setHeight(height.get());

    this.widthProperty().bind(width);
    this.heightProperty().bind(height);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean isResizable() {
    return true;
  }
}
