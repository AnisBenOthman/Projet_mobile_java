/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.esprit.entities.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import com.codename1.io.JSONParser;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * @author Anis
 */
public class ServiceUser {

    private boolean responseResult;
    private List<User> user;
    private List<Candidat> cd;
    private List<Entreprisedomaine> entreprise;
    private List<String> list;
    boolean loginSuccess = false;
    int id_domaine;
    String role = "not found";
    Candidat candidat;
    Entreprisedomaine entreprisedomaine;
    //private List<Domaine> domaine;

    private final String URI = Statics.BASE_URL + "/user/";

    public ServiceUser() {
        user = new ArrayList<>();
        cd = new ArrayList<>();
        entreprise = new ArrayList<>();
        list = new ArrayList<>();
    }

    public boolean ajouter(User t) throws MailException {

        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("POST");

        if (t instanceof Candidat) {
            request.addArgument("nom", t.getNom());
            request.addArgument("prenom", t.getPrenom());
            request.addArgument("mail", t.getMail());
            request.addArgument("motdepasse", t.getMotdepasse());
            request.addArgument("numero_telephone", String.valueOf(t.getNumero_telephone()));
            request.addArgument("description", t.getDescription());
            request.addArgument("role", t.getClass().getSimpleName());
            request.addArgument("education", ((Candidat) t).getEducation().name());
            request.addArgument("Github", ((Candidat) t).getGithub());
            request.addArgument("experience", ((Candidat) t).getExperience().name());

            request.addResponseListener((evt) -> {
                responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
            });
            NetworkManager.getInstance().addToQueueAndWait(request);

            return responseResult;

        } else if (t instanceof Entreprise) {

            request.addArgument("nom", t.getNom());
            request.addArgument("prenom", t.getPrenom());
            request.addArgument("mail", t.getMail());
            request.addArgument("numero_telephone", String.valueOf(t.getNumero_telephone()));
            request.addArgument("motdepasse", t.getMotdepasse());
            request.addArgument("description", t.getDescription());
            request.addArgument("role", t.getClass().getSimpleName());
            request.addArgument("NomEntreprise", ((Entreprise) t).getNomEntreprise());
            request.addArgument("TailleEntreprise", ((Entreprise) t).getTailleEntreprise().name());
            request.addArgument("SiteWeb", ((Entreprise) t).getSiteWeb());
            request.addArgument("Linkedin", ((Entreprise) t).getLinkedin());
            request.addArgument("id_domaine", String.valueOf(((Entreprise) t).getId_domaine()));

            request.addResponseListener((evt) -> {
                responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
            });
            NetworkManager.getInstance().addToQueueAndWait(request);

            return responseResult;

        }
        return false;
    }

    public boolean modifier(User t) throws MailException {
        if (t instanceof Candidat) {

            ConnectionRequest request = new ConnectionRequest();

            request.setUrl(URI + "modifiercandidat/" + t.getId());
            request.setHttpMethod("PUT");

            request.addArgument("nom", t.getNom());
            request.addArgument("prenom", t.getPrenom());
            request.addArgument("mail", t.getMail());

            request.addArgument("motdepasse", t.getMotdepasse());
            request.addArgument("numero_telephone", String.valueOf(t.getNumero_telephone()));

            request.addArgument("education", ((Candidat) t).getEducation().name());

            request.addArgument("experience", ((Candidat) t).getExperience().name());

            request.addResponseListener((evt) -> {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
            });
            NetworkManager.getInstance().addToQueueAndWait(request);

            return responseResult;

        } else if (t instanceof Entreprise) {

            ConnectionRequest request = new ConnectionRequest();

            request.setUrl(URI + "modifierentreprise/" + t.getId());
            request.setHttpMethod("PUT");

//            request.addArgument("nom", t.getNom());
//            request.addArgument("prenom", t.getPrenom());
            request.addArgument("mail", t.getMail());
            request.addArgument("motdepasse", t.getMotdepasse());
            request.addArgument("numero_telephone", String.valueOf(t.getNumero_telephone()));
//            request.addArgument("description", t.getDescription());
//            request.addArgument("role", t.getClass().getSimpleName());
            request.addArgument("NomEntreprise", ((Entreprise) t).getNomEntreprise());
            request.addArgument("TailleEntreprise", ((Entreprise) t).getTailleEntreprise().name());

//            request.addArgument("SiteWeb", ((Entreprise) t).getSiteWeb());
//            request.addArgument("Linkedin", ((Entreprise) t).getLinkedin());
            request.addArgument("id_domaine", String.valueOf(((Entreprise) t).getId_domaine()));

            request.addResponseListener((evt) -> {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
            });
            NetworkManager.getInstance().addToQueueAndWait(request);

            return responseResult;
        }
        return false;
    }

    public boolean supprimer(User t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + t.getId());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<User> afficher() throws MailException {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                user.clear();

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("nom").toString();
                    String prenom = obj.get("prenom").toString();
                    String mail = obj.get("mail").toString();
                    int numero_telephone = (int) Float.parseFloat(obj.get("numero_telephone").toString());
                    String description = obj.get("description").toString();
                    String NomEntreprise = obj.get("NomEntreprise").toString();
                    String Linkedin = obj.get("Linkedin").toString();
                    String SiteWeb = obj.get("SiteWeb").toString();
                    Taille TailleEntreprise = Taille.valueOf(obj.get("TailleEntreprise").toString());
                    Experience experience = Experience.valueOf(obj.get("experience").toString());
                    String Github = obj.get("Github").toString();
                    Diplome education = Diplome.valueOf(obj.get("education").toString());
                    int id_domaine = (int) Float.parseFloat(obj.get("id_domaine").toString());

                    String role = obj.get("role").toString();
                    if (role.equals("Candidat")) {
                        User candidat = new Candidat(id, nom, prenom, mail, numero_telephone, "", description, education, Github, experience);
                        user.add(candidat);
                    } else if (role.equals("Entreprise")) {
                        User entreprise = new Entreprise(id, nom, prenom, mail, numero_telephone, "", description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine);
                        user.add(entreprise);
                    }

                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MailException ex) {
                ex.getMessage();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return user;

    }

    public class Entreprisedomaine extends Entreprise {

        private String nom_domaine;

        public Entreprisedomaine(int id, String nom, String prenom, String mail, int numero_telephone, String motdepasse, String description, String NomEntreprise, Taille TailleEntreprise, String SiteWeb, String Linkedin, int id_domaine, String nom_domaine) throws MailException {
            super(id, nom, prenom, mail, numero_telephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine);
            this.nom_domaine = nom_domaine;
        }

        public String getNom_domaine() {
            return nom_domaine;
        }

        public void setNom_domaine(String nom_domaine) {
            this.nom_domaine = nom_domaine;
        }

        @Override
        public String toString() {
            return "entreprisedomaine{" + super.toString() + "nom_domaine=" + nom_domaine + '}';
        }

    }

    public List<Entreprisedomaine> afficherentreprise() throws MailException {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "entreprise-domaine");
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                this.entreprise.clear();

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("nom").toString();
                    String prenom = obj.get("prenom").toString();
                    String mail = obj.get("mail").toString();
                    int numero_telephone = (int) Float.parseFloat(obj.get("numero_telephone").toString());
                    String description = obj.get("description").toString();
                    String NomEntreprise = obj.get("NomEntreprise").toString();
                    String motdepasse = obj.get("motdepasse").toString();
                    String Linkedin = obj.get("Linkedin").toString();
                    String SiteWeb = obj.get("SiteWeb").toString();
                    Taille TailleEntreprise = Taille.valueOf(obj.get("TailleEntreprise").toString());
                    String nom_domaine = obj.get("nom_domaine").toString();

                    int id_domaine = (int) Float.parseFloat(obj.get("id_domaine").toString());

                    Entreprisedomaine ent = new Entreprisedomaine(id, nom, prenom, mail, numero_telephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine, nom_domaine);
                    this.entreprise.add(ent);

                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MailException ex) {

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return this.entreprise;

    }

    public List<String> afficherDomainebynom() {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "domaine");
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                this.list.clear();

                for (Map<String, Object> obj : list) {
                    String nom_domaine = obj.get("nom_domaine").toString();
                    this.list.add(nom_domaine);
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return list;
    }

    public List<Candidat> afficherCandidat() throws MailException {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "candidat");
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                cd.clear();

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("nom").toString();
                    String prenom = obj.get("prenom").toString();
                    String mail = obj.get("mail").toString();
                    int numero_telephone = (int) Float.parseFloat(obj.get("numero_telephone").toString());
//                  
                    String motdepasse = obj.get("motdepasse").toString();

                    Experience experience = Experience.valueOf(obj.get("experience").toString());

                    Diplome education = Diplome.valueOf(obj.get("education").toString());

                    Candidat candidat = new Candidat(nom, prenom, mail, numero_telephone, motdepasse, "", education, "", experience);

                    cd.add(candidat);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MailException ex) {

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return cd;
    }

    public Candidat afficherseulCandidat(String mailo) throws MailException {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "seulcandidat/" + mailo);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                candidat = null;

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("nom").toString();
                    String prenom = obj.get("prenom").toString();
                    String mail = obj.get("mail").toString();
                    int numero_telephone = (int) Float.parseFloat(obj.get("numero_telephone").toString());
//                  
                    String motdepasse = obj.get("motdepasse").toString();

                    Experience experience = Experience.valueOf(obj.get("experience").toString());

                    Diplome education = Diplome.valueOf(obj.get("education").toString());

                    candidat = new Candidat(id, nom, prenom, mail, numero_telephone, motdepasse, "", education, "", experience);

                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MailException ex) {

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return candidat;
    }

    public boolean login(String login, String password) {

        loginSuccess = false;
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI + "signin");
        request.setHttpMethod("POST");

        request.addArgument("login", login);
        request.addArgument("password", password);
        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                if (!list.isEmpty()) {
                    loginSuccess = true;
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return loginSuccess;
    }

    public boolean loginpasse(String login) {

        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI + "anis/" + login);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                if (!list.isEmpty()) {
                    loginSuccess = true;
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);

        return loginSuccess;
    }
//

    public List<Candidat> SearchCandidat(String login) throws MailException {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "searchcandidat/" + login);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                cd.clear();

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("nom").toString();
                    String prenom = obj.get("prenom").toString();
                    String mail = obj.get("mail").toString();
                    int numero_telephone = (int) Float.parseFloat(obj.get("numero_telephone").toString());
//                  
                    String motdepasse = obj.get("motdepasse").toString();

                    Experience experience = Experience.valueOf(obj.get("experience").toString());

                    Diplome education = Diplome.valueOf(obj.get("education").toString());

                    Candidat candidat = new Candidat(nom, prenom, mail, numero_telephone, motdepasse, "", education, "", experience);

                    cd.add(candidat);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MailException ex) {

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return cd;
    }

    public List<Entreprisedomaine> Searchentreprise(String Nom) throws MailException {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "searchentreprise/" + Nom);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                this.entreprise.clear();

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("nom").toString();
                    String prenom = obj.get("prenom").toString();
                    String mail = obj.get("mail").toString();
                    int numero_telephone = (int) Float.parseFloat(obj.get("numero_telephone").toString());
                    String description = obj.get("description").toString();
                    String NomEntreprise = obj.get("NomEntreprise").toString();
                    String motdepasse = obj.get("motdepasse").toString();
                    String Linkedin = obj.get("Linkedin").toString();
                    String SiteWeb = obj.get("SiteWeb").toString();
                    Taille TailleEntreprise = Taille.valueOf(obj.get("TailleEntreprise").toString());
                    String nom_domaine = obj.get("nom_domaine").toString();

                    int id_domaine = (int) Float.parseFloat(obj.get("id_domaine").toString());

                    Entreprisedomaine ent = new Entreprisedomaine(id, nom, prenom, mail, numero_telephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine, nom_domaine);
                    this.entreprise.add(ent);

                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MailException ex) {

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return this.entreprise;
    }

    public Entreprisedomaine Seulentreprise(String Nom) throws MailException {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "entreprise/" + Nom);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                entreprisedomaine = null;

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("nom").toString();
                    String prenom = obj.get("prenom").toString();
                    String mail = obj.get("mail").toString();
                    int numero_telephone = (int) Float.parseFloat(obj.get("numero_telephone").toString());
                    String description = obj.get("description").toString();
                    String NomEntreprise = obj.get("NomEntreprise").toString();
                    String motdepasse = obj.get("motdepasse").toString();
                    String Linkedin = obj.get("Linkedin").toString();
                    String SiteWeb = obj.get("SiteWeb").toString();
                    Taille TailleEntreprise = Taille.valueOf(obj.get("TailleEntreprise").toString());
                    String nom_domaine = obj.get("nom_domaine").toString();

                    int id_domaine = (int) Float.parseFloat(obj.get("id_domaine").toString());

                    entreprisedomaine = new Entreprisedomaine(id, nom, prenom, mail, numero_telephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine, nom_domaine);

                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MailException ex) {

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return entreprisedomaine;

    }

    public String idutilisateur(String login) {
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI + "anis/" + login);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                if (!list.isEmpty()) {
                    Map<String, Object> obj = list.get(0);
                    role = obj.get("role").toString();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);

        return role;
    }

    public boolean modifiermotdepasse(String motdepasse, String login) {

        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "reset-pwd");
        request.setHttpMethod("PUT");

        request.addArgument("motdepasse", String.valueOf(User.Codepasse(motdepasse)));
        request.addArgument("mail", login);
        request.addArgument("numero_telephone", login);

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public int getIdDomaineByName(String name) {

        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + "domain/" + name);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    id_domaine = (int) Float.parseFloat(obj.get("id_domaine").toString());

                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return id_domaine;
    }

}
