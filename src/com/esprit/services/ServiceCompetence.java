///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.esprit.services;
//
//import com.esprit.entities.*;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author Anis
// */
//public class ServiceCompetence {
//    
//    public void ajouter(Competence competence) {
//        try {
//            String req = "INSERT INTO competences(id_c, nom, description) VALUES (?, ?, ?);";
//            PreparedStatement pst = cnx.prepareStatement(req);
//            pst.setInt(1, competence.getId_c());
//            pst.setString(2, competence.getNom());
//            pst.setString(3, competence.getDescription());
//            
//            pst.executeUpdate();
//            System.out.println("Compétence ajoutée !");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    public void modifier(Competence competence) {
//        try {
//            String req = "UPDATE competences SET nom=?, description=? WHERE id_c=?";
//            PreparedStatement pst = cnx.prepareStatement(req);
//            pst.setString(1, competence.getNom());
//            pst.setString(2, competence.getDescription());
//           
//            pst.setInt(3, competence.getId_c());
//            pst.executeUpdate();
//            System.out.println("Compétence modifiée !");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    public void supprimer(Competence competence) {
//        try {
//            String req = "DELETE FROM competences WHERE id_c=?";
//            PreparedStatement pst = cnx.prepareStatement(req);
//            pst.setInt(1, competence.getId_c());
//            pst.executeUpdate();
//            System.out.println("Compétence supprimée !");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    public List<Competence> afficher() {
//    List<Competence> list = new ArrayList<>();
//
//    String req = "SELECT id_c, nom, description FROM competences";
//    try {
//        PreparedStatement pst = cnx.prepareStatement(req);
//        ResultSet rs = pst.executeQuery();
//        while (rs.next()) {
//            int id_c = rs.getInt("id_c");
//            String nom = rs.getString("nom");
//            String description = rs.getString("description");
//            Competence competence = new Competence(id_c, nom, description);
//            list.add(competence);
//        }
//    } catch (SQLException ex) {
//        System.out.println(ex.getMessage());
//    }
//
//    return list;
//}
//    public List<String> affichercompetencebynom() {
//    List<String> list = new ArrayList<>();
//
//    String req = "SELECT nom FROM competences";
//    try {
//        PreparedStatement pst = cnx.prepareStatement(req);
//        ResultSet rs = pst.executeQuery();
//        while (rs.next()) {
//            
//            String nom = rs.getString("nom");
//            
//           
//            list.add(nom);
//        }
//    } catch (SQLException ex) {
//        System.out.println(ex.getMessage());
//    }
//
//    return list;
//}
//
//}
