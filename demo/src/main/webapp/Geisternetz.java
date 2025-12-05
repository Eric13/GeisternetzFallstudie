package demo.src.main.webapp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// @Entity sagt JPA, dass diese Klasse als Tabelle in der Datenbank verwendet wird
@Entity
// Tabellenname in der Datenbank
@Table(name = "GEISTERNETZ")
public class Geisternetz {

    // Primärschlüssel
    @Id
    // ID wird automatisch hochgezählt
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // GPS-Koordinaten
    private double koordinaten;

    // Größe des Netzes
    private int groeße;

    // Status als String: z.B. "GEMELDET", "IN_BERGUNG", "GEBORGEN", "VERSCHOLLEN"
    private String status;

    // Zeitstempel, wann der Status gesetzt wurde
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
     */
    @ManyToOne
    @JoinColumn(name = "berger_id")
    private Person berger;

    // Getter & Setter 

    public Long getId() {
        return id;
    }

    public double getKoordinaten() {
        return koordinaten;
    }

    public void setKoordinaten(double koordinaten) {
        this.koordinaten = koordinaten;
    }

    public int getGroeße() {
        return groeße;
    }

    public void setGröße(int groeße) {
        this.groeße = groeße; 
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.statusDatum = LocalDateTime.now(); // automatisch Zeitstempel setzen
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