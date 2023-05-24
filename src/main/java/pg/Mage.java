package pg;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Mage.findAll",
                query = "SELECT m FROM Mage m"),
        @NamedQuery(name = "Mage.findWithHigherLevel",
                query = "SELECT m FROM Mage m WHERE m.level > :level")
})
@Entity
public class Mage {
    @Getter
    @Setter
    @Id
    private String name;

    @Getter
    @Setter
    private int level;

    @Getter
    @Setter
    @ManyToOne
    private Tower tower;


    @Override
    public String toString() {
        return String.format("Mage: %s - %d, Tower: %s", name, level, tower != null ? tower.getName() : "None");
    }
}
