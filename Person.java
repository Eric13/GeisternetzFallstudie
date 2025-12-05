import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;


// @Entity sagt JPA, dass diese Klasse als Tabelle in der Datenbank verwendet wird
@Entity
// Tabellenname in der Datenbank
@Table(name = "PERSON")
public class Person {

    // Primärschlüssel
    @Id
    // ID wird automatisch hochgezählt (1,2,3,…)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name der Person
    private String name;

    // Telefonnummer (optional, z.B. bei anonymen Meldungen leer)
    private String telefon;

    // Rolle (z.B. "MELDEND", "BERGEND", "BELIEBIG")
    private String rolle;

    // Getter und Setter

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
}