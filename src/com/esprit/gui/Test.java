/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;

/**
 *
 * @author Anis
 */
public class Test extends Form {

    public Test() {
        super("test", BoxLayout.y());
        DefaultListModel model=new DefaultListModel();
        model.addItem("Thaïlande");
        
         model.addItem("Japon"); 
         model.addItem("République dominicaine");
        model.addItem("Nouvelle Zélande"); 
        List listePays=new List(model);
    listePays.setFixedSelection(List.FIXED_NONE_CYCLIC);
        add(listePays);
    }
    
    
    
}
