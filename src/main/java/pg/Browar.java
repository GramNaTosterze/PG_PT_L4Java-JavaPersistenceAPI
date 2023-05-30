package pg;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Browar.findAll",
                query = "SELECT b FROM Browar b"),
        @NamedQuery(name = "Browar.findWithCheapBeer",
                query = "SELECT p.browar FROM Piwo p " +
                        "WHERE p.cena <= :cena")
})
@Entity
public class Browar {

    @Getter
    @Setter
    @Id
    private String name;

    @Getter
    @Setter
    private long wartość;

    @Getter
    @Setter
    @OneToMany(mappedBy = "browar", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Piwo> piwa;

    @Override
    public String toString() {
        return String.format("Browar: %s, Piwa: %s", name, piwa);
    }
}
