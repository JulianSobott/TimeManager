package WebView.Gui;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;

public class SplitPaneWeb extends SplitPane {

    private SplitPane leftChild;
    private SplitPane rightChild;
    private int level;


    public SplitPaneWeb() {

    }


    public SplitPaneWeb(SplitPane leftChild, SplitPane rightChild, int level) {

        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.level = level;
    }


    public SplitPane getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(SplitPane leftChild) {
        this.leftChild = leftChild;
    }

    public SplitPane getRightChild() {
        return rightChild;
    }

    public void setRightChild(SplitPane rightChild) {
        this.rightChild = rightChild;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
