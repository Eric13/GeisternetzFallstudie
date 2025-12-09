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
// Passwort für Registrierung //
    private String passwort;




    // GETTER und SETTER 

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public String getTelefon() {
        return telefon;
    }

   
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getPasswort() {
    return passwort;
}

public void setPasswort(String passwort) {
    this.passwort = passwort;
}
}
