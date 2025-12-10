package com.Geisternetze.demo;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;

// ManagedBean und SessionScoped für JSF und die Verwaltung über die gesamte Sitzung
@Named
@SessionScoped 
public class RegistrierungBean implements Serializable {

    //Injection des EntityManager für die Datenbank-Operationen
    @PersistenceContext
    private EntityManager em; 
    
    // Das Formular bindet alle Eingaben (Name, Passwort, Rolle, Telefon) direkt an dieses Objekt
    private Person neuePerson = new Person(); 
    
    // Wird auf true gesetzt, wenn die Speicherung erfolgreich war
    private boolean istRegistriert = false;
    
    // KERNLOGIK: Regisitrierung (Speicherung in der MariaDB)//

    /**
     * Speichert das neue Person-Objekt in der Datenbank.
     * @return Navigations-String
     */
    // Stellt sicher, dass die Datenbank-Operation (em.persist) sicher ausgeführt wird
    @Transactional 
    public String registrieren() {
        
        // Prüft auf dem Server, ob kritische Felder befüllt sind
        if (!validateInput()) {
            return null; // Bleibt auf der aktuellen Seite
        }
        
        

        try {
            // Fügt die neue Person-Entität zur Datenbank hinzu
            em.persist(neuePerson); 
            
            
            this.istRegistriert = true; 
            
            // Erfolgsmeldung für den Nutzer
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                 "Erfolg!", 
                                 "Sie sind erfolgreich als " + neuePerson.getRolle() + " registriert und gespeichert."));

            // NEU: Weiterleitung zur Login-Seite
return "login.xhtml?faces-redirect=true"; 
 
} catch (Exception e) {
FacesContext.getCurrentInstance().addMessage(null, 
new FacesMessage(FacesMessage.SEVERITY_ERROR, "DB-Fehler", "Registrierung konnte nicht gespeichert werden: " + e.getMessage()));
return null;
}
    
            
       
    }
    
    
    // VALIDIERUNGSLOGIK// 
    
    // Interne serverseitige Prüfung der Pflichtfelder
    private boolean validateInput() {
        // Prüft, ob Name, Rolle und Passwort gesetzt sind
        if (neuePerson.getName() == null || neuePerson.getName().trim().isEmpty() ||
            neuePerson.getRolle() == null || neuePerson.getRolle().trim().isEmpty() ||
            neuePerson.getPasswort() == null || neuePerson.getPasswort().trim().isEmpty()) {
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Name, Rolle und Passwort sind Pflichtfelder."));
            return false;
        }
        // Korrigierte Pflichtfeldprüfung für die Rolle 'BERGER'
    // Stellt sicher, dass das Telefonfeld nicht NULL oder leer ist, WENN die Rolle BERGER ist.
    if ("BERGER".equals(neuePerson.getRolle()) && 
        (neuePerson.getTelefon() == null || neuePerson.getTelefon().trim().isEmpty())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Als Berger ist die Telefonnummer zwingend erforderlich."));
            return false;
    }
    
    // Wenn die Rolle MELDER ist, ist das Telefonfeld optional und die Prüfung wird hier nicht ausgelöst.
    
    return true;
}
    
    
    // Getter und Setter//
    
    
    public Person getNeuePerson() {
        return neuePerson;
    }

    public void setNeuePerson(Person neuePerson) {
        this.neuePerson = neuePerson;
    }
    
    
    public String getRolle() {
        return neuePerson.getRolle();
    }

    public void setRolle(String rolle) {
        neuePerson.setRolle(rolle);
    }

    public boolean isIstRegistriert() {
        return istRegistriert;
    }

    public void setIstRegistriert(boolean istRegistriert) {
        this.istRegistriert = istRegistriert;
    }
}