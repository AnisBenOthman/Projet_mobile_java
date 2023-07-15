package com.esprit.entities;

import java.util.Objects;

public class Document {
    private int id_document;
    private String titre_document;
    private String description_document;
    private String type;
    private String lien;
    private int id_user;

    public Document() {
    }

    public Document(int id_document, String titre_document, String description_document, String type, String lien, int id_user) {
        this.id_document = id_document;
        this.titre_document = titre_document;
        this.description_document = description_document;
        this.type = type;
        this.lien = lien;
        this.id_user = id_user;
    }

    public int getId_document() {
        return id_document;
    }

    public void setId_document(int id_document) {
        this.id_document = id_document;
    }

    public String getTitre_document() {
        return titre_document;
    }

    public void setTitre_document(String titre_document) {
        this.titre_document = titre_document;
    }

    public String getDescription_document() {
        return description_document;
    }

    public void setDescription_document(String description_document) {
        this.description_document = description_document;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id_document == document.id_document &&
                id_user == document.id_user &&
                Objects.equals(titre_document, document.titre_document) &&
                Objects.equals(description_document, document.description_document) &&
                Objects.equals(type, document.type) &&
                Objects.equals(lien, document.lien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_document, titre_document, description_document, type, lien, id_user);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id_document=" + id_document +
                ", titre_document='" + titre_document + '\'' +
                ", description_document='" + description_document + '\'' +
                ", type='" + type + '\'' +
                ", lien='" + lien + '\'' +
                ", id_user=" + id_user +
                '}';
    }
}
