package pg;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

import java.util.List;
import java.util.Scanner;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Piwo.findAll",
                query = "SELECT p FROM Piwo p"),
        @NamedQuery(name = "Piwo.findCheaper",
                query = "SELECT p FROM Piwo p WHERE p.cena < :cena"),
        @NamedQuery(name = "Piwo.findCheaperInBrowar",
                query = "SELECT p FROM Piwo p WHERE p.cena < :cena AND p.browar.name = :browar"),
})
@Entity
public class Piwo {
    @Getter
    @Setter
    @Id
    private String name;

    @Getter
    @Setter
    private long cena;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Browar browar;

    @Override
    public String toString() {
        return String.format("%s - %d, Browar: %s", name, cena, browar != null ? browar.getName() : "Brak");
    }
}
