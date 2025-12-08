package com.Geisternetze.demo;

import jakarta.annotation.PostConstruct;
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

        // 1. Melder speichern
        em.persist(melder);

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

    // Status ändern (GEMELDET -> IN_BERGUNG -> GEBORGEN)
    @Transactional
    public void aendereStatus(Long id, String neuerStatus) {
        Geisternetz netzDb = em.find(Geisternetz.class, id);
        netzDb.setStatus(neuerStatus);
        netzDb.setStatusDatum(LocalDateTime.now());
        em.merge(netzDb);
        datenLaden(); // Liste nach Änderung aktualisieren
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