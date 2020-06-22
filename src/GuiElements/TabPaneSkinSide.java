/*
 * Copyright (c) 2011, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package GuiElements;

import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.VBox;

public class TabPaneSkinSide extends SkinBase<TabWindow> {

    /***************************************************************************
     *                                                                         *
     * Enums                                                                   *
     *                                                                         *
     **************************************************************************/
    public enum ContentResizing {
        OVERLAP, // Folded out menu overlaps tab content
        RESIZE  // Folded out menu resizes tab content
    }

    /***************************************************************************
     *                                                                         *
     * Private fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final TabPaneBehavior tabPaneBehavior;
    private Label lblTest;

    protected TabPaneSkinSide(TabWindow control) {
        super(control);
        tabPaneBehavior = new TabPaneBehavior(control);

        lblTest = new Label("Test");

        VBox vBox = new VBox();
        getChildren().clear();
        vBox.getChildren().addAll(lblTest, new Button("Click me"));
        vBox.getStyleClass().setAll("debug");
        vBox.setMinWidth(200);
        layoutInArea(vBox, 200, 200, 200, 200, 0, HPos.CENTER, VPos.CENTER);
        getChildren().setAll(vBox);
    }

    /** {@inheritDoc} */
    @Override public void dispose() {
        super.dispose();

        if (tabPaneBehavior != null) {
            tabPaneBehavior.dispose();
        }
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {

    }

}
