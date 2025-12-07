package com.Geisternetze.demo;

import jakarta.enterprise.context.RequestScoped;
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
@RequestScoped // existiert nur für eine Anfrage (bei Seitenaufruf)
public class GeisternetzBean implements Serializable {

    // @PersistenceContext sorgt dafür, dass JPA mir automatisch einen EntityManager gibt
    @PersistenceContext(unitName = "geisternetz-pu")
    private EntityManager em;

    // Geisternetz-Objekt für das Formular
    private Geisternetz netz = new Geisternetz();

    // meldende Person
    private Person melder = new Person();

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
    // Status-Filter aus Dropdown
private String statusFilter = "ALLE";

// Liefert entweder alle Netze oder nach Status gefiltert
public List<Geisternetz> getGefilterteNetze() {
    if ("ALLE".equals(statusFilter)) {
        return em.createQuery("SELECT g FROM Geisternetz g", Geisternetz.class).getResultList();
    }
    return em.createQuery(
            "SELECT g FROM Geisternetz g WHERE g.status = :status",
            Geisternetz.class)
            .setParameter("status", statusFilter)
            .getResultList();
}

// Status ändern (GEMELDET -> IN_BERGUNG -> GEBORGEN)
@Transactional
public void aendereStatus(Long id, String neuerStatus) {
    Geisternetz netzDb = em.find(Geisternetz.class, id);
    netzDb.setStatus(neuerStatus);
    netzDb.setStatusDatum(LocalDateTime.now());
    em.merge(netzDb);
}

// Google-Maps-Link erzeugen
public String googleMapsUrl(double lng, double lat) {
    return "https://www.google.com/maps?q=" + lat + "," + lng;
}

public String getStatusFilter() {
    return statusFilter;
}

public void setStatusFilter(String statusFilter) {
    this.statusFilter = statusFilter;
}


    // GETTER und SETTER damit JSF zugreifen kann

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
