package com.Geisternetze.demo;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.io.IOException;
import java.io.Serializable;

@Named("loginBean")
@SessionScoped 
public class LoginBean implements Serializable {

    @PersistenceContext(unitName = "geisternetz-pu")
    private EntityManager em;

    // Felder für die Eingabe im Login-Formular
    private String name;
    private String passwort;
    
    // Hält die eingeloggte Person für die gesamte Session
    private Person eingeloggtePerson; 

    /**
     * Prüft die Anmeldedaten und setzt die eingeloggte Person in die Session.
     *HINWEIS: Logik ist so aufgebaut, dass ein Melder der sich versucht bei netzBergen einzuloggen automatisch zu 
*Netz melden weitergeleitet wird und umgekehrt.
*/
   public String login() {
    FacesContext context = FacesContext.getCurrentInstance();
    
    try {
        // 1. Person anhand des Namens suchen
        Person p = em.createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class)
                     .setParameter("name", name)
                     .getSingleResult();

        // 2. Passwort-Check (Unverschlüsselt für Fallstudie)
        if (p.getPasswort() != null && p.getPasswort().equals(passwort)) {
            this.eingeloggtePerson = p;
            
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", "Anmeldung erfolgreich."));
            
            // NEU: Rollenbasierte Weiterleitung
            String rolle = p.getRolle();
            
            if ("MELDER".equals(rolle)) {
                // Melder wird zu netzmelden.xhtml weitergeleitet
                return "/netzmelden.xhtml?faces-redirect=true";
            } else if ("BERGER".equals(rolle)) {
                // Berger wird zu netzbergen.xhtml weitergeleitet
                return "/netzbergen.xhtml?faces-redirect=true";
            } else {
                // Fallback 
                return "/index.xhtml?faces-redirect=true"; 
            }
        } else {
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Falsches Passwort."));
        }
    } catch (NoResultException e) {
        context.addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Benutzer nicht gefunden."));
    }
    
    this.eingeloggtePerson = null; // Stellt sicher, dass bei Fehlern niemand eingeloggt ist
    return null; // Bleibt auf der Login-Seite
}

    /**
     * Beendet die Session (loggt den Benutzer aus).
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.eingeloggtePerson = null;
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * Zugrifsskontrolle: Erzwingt einen Redirect, wenn der Nutzer nicht angemeldet ist.
     * Wird von den geschützten Seiten vor dem Rendern aufgerufen.
     */
    public void checkLoggedIn() throws java.io.IOException {
        if (!isLoggedIn()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }

    // Hilfsmethode zur Statusprüfung
    public boolean isLoggedIn() {
        return eingeloggtePerson != null;
    }
    //Prüft, ob der Nutzer eingeloggt UND ein MELDER ist
public void checkMelderAccess() throws IOException {
    if (!isLoggedIn() || !"MELDER".equals(eingeloggtePerson.getRolle())) {
        // Umleitung zur Login-Seite
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }
}

// Prüft, ob der Nutzer eingeloggt UND ein BERGER ist
public void checkBergerAccess() throws IOException {
    if (!isLoggedIn() || !"BERGER".equals(eingeloggtePerson.getRolle())) {
        // Umleitung zur Login-Seite
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }
}


    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPasswort() { return passwort; }
    public void setPasswort(String passwort) { this.passwort = passwort; }
    public Person getEingeloggtePerson() { return eingeloggtePerson; }
}