/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.ui.management;

import java.awt.CardLayout;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author victo
 */
public class LayoutManagerUI {

    private final CardLayout layout;
    private final JPanel container;
    private final Set<String> layouts;

    public LayoutManagerUI(JPanel container) {
        this.container = container;
        this.layout = new CardLayout();
        this.layouts = new HashSet<>();

        container.setLayout(layout);
    }

    public void addLayout(String name, JPanel panel) {
        if (layouts.contains(name)) {
            throw new IllegalArgumentException(
                    "Layout já registrado: " + name
            );
        }
        
        layouts.add(name);
        container.add(panel, name);
    }
    
    public void showLayout(String name) {
        if (!layouts.contains(name)) {
            throw new IllegalArgumentException(
                "Layout não encontrado: " + name
            );
        }
        
        layout.show(container, name);
        container.revalidate();
        container.repaint();
    }
}
