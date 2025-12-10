package com.Geisternetze.demo;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped; 
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;



/**
 * Diese Bean verbindet die XHTML-Seite mit der Datenbank.
 * Sie verarbeitet die Eingaben vom Formular und speichert sie ab.
 */
@Named("geisternetzBean") // Name, mit dem die Bean in JSF genutzt wird
@ViewScoped // Hält den Zustand, solange der Nutzer auf der Seite bleibt
public class GeisternetzBean implements Serializable {
    @Inject
    private LoginBean loginBean;

    // @PersistenceContext sorgt dafür, dass JPA mir automatisch einen EntityManager gibt
    @PersistenceContext(unitName = "geisternetz-pu")
    private EntityManager em;

    // Geisternetz-Objekt für das Formular
    private Geisternetz netz = new Geisternetz();

    // meldende Person
    private Person melder = new Person();

    // Status-Filter aus Dropdown
    private String statusFilter = "ALLE";

    // Lokale Liste, um Mehrfachabfragen in Gettern zu vermeiden
    private List<Geisternetz> gefilterteNetze;

    /**
     * Wird beim ersten Laden der Seite aufgerufen
     */
    @PostConstruct
    public void init() {
        datenLaden();
        
        // NEUE LOGIK: Daten der eingeloggten Person (falls Melder) übernehmen
        if (loginBean.isLoggedIn() && "MELDER".equals(loginBean.getEingeloggtePerson().getRolle())) {
            // Übernimmt die eingeloggte Person direkt als Melder.
            this.melder = loginBean.getEingeloggtePerson(); 
        } else {
        
            this.melder = new Person(); 
        }
    }

    /**
     * Zentrale Methode zum Laden der Daten basierend auf dem Filter
     */
    public void datenLaden() {
        if ("ALLE".equals(statusFilter)) {
            gefilterteNetze = em.createQuery("SELECT g FROM Geisternetz g", Geisternetz.class).getResultList();
        } else {
            gefilterteNetze = em.createQuery(
                "SELECT g FROM Geisternetz g WHERE g.status = :status",
                Geisternetz.class)
                .setParameter("status", statusFilter)
                .getResultList();
        }
    }

    /**
     * Wird vom AJAX-Event aufgerufen, wenn der Filter geändert wird
     */
   public void filterAnwenden() {

    if ("ALLE".equals(statusFilter)) {
        gefilterteNetze = em.createQuery("SELECT g FROM Geisternetz g", Geisternetz.class)
                            .getResultList();
    } else {
        gefilterteNetze = em.createQuery(
            "SELECT g FROM Geisternetz g WHERE g.status = :status", Geisternetz.class)
            .setParameter("status", statusFilter)
            .getResultList();
    }
}

    /**
     * Wird beim Klick auf „Speichern“ aufgerufen
     * Speichert zuerst die Person, dann das Geisternetz
     */
    @Transactional
    public String speichereNetz() {

        
        if (!loginBean.isLoggedIn()) {
             em.persist(melder);
        }
        // Wenn der Melder eingeloggt ist, ist 'melder' bereits ein Managed Entity
        // aus der Datenbank und muss nicht erneut persistiert werden.

        // 2. Netz mit Melder verknüpfen 
        netz.setMelder(melder);

        // 3. Status setzen
        netz.setStatus("GEMELDET");

        // 4. Zeitstempel setzen
        netz.setStatusDatum(LocalDateTime.now());

        // 5. Netz speichern
        em.persist(netz);

        // 6. Felder zurücksetzen für neue Eingabe
        netz = new Geisternetz();
        melder = new Person();

        // 7. Weiterleitung zur Startseite
        return "index.xhtml?faces-redirect=true";
    }

    // Status ändern und Berger zuweisen

@Transactional
public String aendereStatus(Long id, String neuerStatus) {
    FacesContext context = FacesContext.getCurrentInstance();
    Geisternetz netzDb = em.find(Geisternetz.class, id);
    
    boolean istBergerEingeloggt = loginBean.isLoggedIn() 
&& "BERGER".equals(loginBean.getEingeloggtePerson().getRolle());
Person eingeloggterBerger = loginBean.getEingeloggtePerson();
    
    // --- Allgemeine Prüfung: Netz existiert? ---
    if (netzDb == null) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Netz konnte nicht gefunden werden."));
        datenLaden();
        return null;
    }

    // *******************************************************************
    // 1. Zuweisung / Übernahme ("IN_BERGUNG")
    // *******************************************************************
    if ("IN_BERGUNG".equals(neuerStatus)) {
        
        // 1.1 Prüfung: Ist das Netz bereits einem ANDEREN Berger zugewiesen?
        if (netzDb.getBerger() != null && !netzDb.getBerger().getId().equals(eingeloggterBerger.getId())) {
            
            String zugewiesenerBergerName = netzDb.getBerger().getName(); 
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", 
                "Netz bereits vergeben! Es wurde an Berger **" + zugewiesenerBergerName + "** zugewiesen."));
            datenLaden(); // Liste aktualisieren, falls nötig
            return null; // Abbruch
        }
        
        // 1.2 Prüfung: Darf der aktuelle Berger es übernehmen? (Nur wenn Status GEMELDET oder noch kein Berger zugewiesen)
        if ("GEMELDET".equals(netzDb.getStatus()) || netzDb.getBerger() == null) {
             if (istBergerEingeloggt) {
                // Zuweisung, da Netz noch GEMELDET ist
                netzDb.setBerger(eingeloggterBerger); 
                netzDb.setStatus(neuerStatus);
                netzDb.setStatusDatum(LocalDateTime.now());
                em.merge(netzDb);
                
                context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", 
                    "Netz erfolgreich zur Bergung übernommen. **Button wird aktualisiert.**")); // NEUE Erfolgsmeldung
                
            } else {
                context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", 
                    "Sie müssen als Berger angemeldet sein, um die Bergung zu planen."));
            }
        } else {
             // Wenn es bereits IN_BERGUNG ist und der aktuelle Berger der Richtige ist, keine Aktion nötig.
             context.addMessage(null, 
                 new FacesMessage(FacesMessage.SEVERITY_WARN, "Hinweis", 
                 "Netz ist bereits in Ihrer Bergungsplanung."));
        }
    
    // *******************************************************************
    // 2. Abschluss ("GEBORGEN")
    // *******************************************************************
    } else if ("GEBORGEN".equals(neuerStatus)) {
        
        // **PRÜFUNG:** Nur der zugewiesene Berger darf den Status auf GEBORGEN setzen
        if (netzDb.getBerger() != null && eingeloggterBerger != null 
            && netzDb.getBerger().getId().equals(eingeloggterBerger.getId())) { 
            
            // Abschluss ist erlaubt
            netzDb.setStatus(neuerStatus);
            netzDb.setStatusDatum(LocalDateTime.now());
            em.merge(netzDb);
            
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", 
                "Netz erfolgreich als 'Geborgen' markiert."));
            
        } else if ("GEBORGEN".equals(netzDb.getStatus())) {
             // Ist bereits geborgen
             context.addMessage(null, 
                 new FacesMessage(FacesMessage.SEVERITY_WARN, "Hinweis", 
                 "Das Netz wurde bereits als 'Geborgen' markiert."));
        
        } else {
            // Fehler bei Versuch, ein zugewiesenes Netz zu bergen, obwohl falscher Berger
            String zugewiesenerBergerName = netzDb.getBerger() != null ? netzDb.getBerger().getName() : "keinem Berger";
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", 
                "Aktion nicht erlaubt! Nur der zugewiesene Berger (" + zugewiesenerBergerName + ") kann den Status auf 'Geborgen' setzen."));
        }
    
    }
    
    // Abschließend die Liste IMMER aktualisieren. 
    datenLaden(); 
    return null; 
}

    // Google-Maps-Link erzeugen
    public String googleMapsUrl(double lng, double lat) {
        return "https://www.google.com/maps?q=" + lat + "," + lng;
    }

    // Getter und Setter

    public List<Geisternetz> getGefilterteNetze() {
        return gefilterteNetze;
    }

    public String getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(String statusFilter) {
        this.statusFilter = statusFilter;
    }

    public Geisternetz getNetz() {
        return netz;
    }

    public void setNetz(Geisternetz netz) {
        this.netz = netz;
    }

    public Person getMelder() {
        return melder;
    }

    public void setMelder(Person melder) {
        this.melder = melder;
    }
}