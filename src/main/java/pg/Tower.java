package pg;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Tower.findAll",
                query = "SELECT t FROM Tower t"),
        @NamedQuery(name = "Tower.findSmallerThan",
                query = "SELECT t FROM Tower t WHERE t.height < :height")
})
@Entity
public class Tower {

    @Getter
    @Setter
    @Id
    private String name;

    @Getter
    @Setter
    private int height;

    @Getter
    @Setter
    @OneToMany(mappedBy = "tower")
    private List<Mage> mages;

    @Override
    public String toString() {
        return String.format("Tower: %s, Mages: %s", name, mages);
    }
}
