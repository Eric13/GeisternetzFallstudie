package com.Geisternetze.demo;

import jakarta.persistence.*;

/**
 * Speichert Informationen über eine Person im System:
 * - meldende Person
 * - bergende Person
 */
@Entity
@Table(name = "PERSON")
public class Person {

    // Primärschlüssel
    @Id
    // Wird automatisch von der Datenbank erzeugt
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name der Person
    private String name;

    // Telefonnummer (bei meldender Person optional)
    private String telefon;

    // Rolle: MELDEND oder BERGEND
    private String rolle;

    // ---- GETTER UND SETTER -------

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // setzt den Namen der Person
    public void setName(String name) {
        this.name = name;
    }

    public String getTelefon() {
        return telefon;
    }

    // setzt die Telefonnummer
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getRolle() {
        return rolle;
    }

    // setzt die Rolle (z.B. MELDEND, BERGEND)
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
}
