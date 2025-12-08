package com.Geisternetze.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;



// @Entity sagt JPA, dass diese Klasse als Tabelle in der Datenbank verwendet wird
@Entity
// Tabellenname in der Datenbank
@Table(name = "GEISTERNETZ")
public class Geisternetz {

    public Geisternetz(){
        
    }

// Primärschlüssel
 @Id
// ID wird automatisch hochgezählt
@GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 // GPS-Koordinaten
 private double laengengrad;
private double breitengrad;

 // Größe des Netzes
private int groesse;

 // Status als String: z.B. "GEMELDET", "IN_BERGUNG", "GEBORGEN", "VERSCHOLLEN"
private String status;

// Zeitstempel, wann der Status gesetzt wurde
@Column(name = "StatusDatum")
    private LocalDateTime statusDatum;

/*
* Person, die das Netz gemeldet hat
* ManyToOne: Viele Netze können von einer Person gemeldet werden
*/
 @ManyToOne
 @JoinColumn(name = "melder_id")
private Person melder;

/*
* Person, die das Netz bergt, viele Netze können einer Person zugeteilt werden.
aber ein Netz hat nur eine Person.
*/
@ManyToOne
@JoinColumn(name = "berger_id")
private Person berger;

 // Getter & Setter 

public Long getId() {
return id;
 }

 public double getLaengengrad() { return laengengrad; }
public void setLaengengrad(double laengengrad) { this.laengengrad = laengengrad; }

public double getBreitengrad() { return breitengrad; }
public void setBreitengrad(double breitengrad) { this.breitengrad = breitengrad; }

 public int getGroesse() {
return groesse;
 }

public void setGroesse(int groesse) {
this.groesse = groesse; 
 }

public String getStatus() {
 return status;
}

public void setStatus(String status) {
 this.status = status;
/*  this.statusDatum = LocalDateTime.now(); // ENTFERNT, da dies nun von der Bean (zentral) gesteuert wird, 
 aktuell weiter belassen falls benötigt.
 */

}
    
    
    public void setStatusDatum(LocalDateTime statusDatum) {
        this.statusDatum = statusDatum;
    }

 public LocalDateTime getStatusDatum() {
return statusDatum;
}

public Person getMelder() {
 return melder;
}

public void setMelder(Person melder) {
 this.melder = melder;
}

public Person getBerger() {
return berger;
}

public void setBerger(Person berger) {
this.berger = berger;
 }
}