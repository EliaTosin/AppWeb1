package ch.supsi.webapp.web.item;

import ch.supsi.webapp.web.author.User;
import ch.supsi.webapp.web.category.Category;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"})
@Entity
public class Item {
    @Id
    @GeneratedValue
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date date;

    private Ad ad_type;

    @Lob
    private byte[] image;

    public enum Ad {
        Offerta, Richiesta
    }

    public boolean isOfferta() {
        if (ad_type==Ad.Offerta)
            return true;
        return false;
    }

    @ManyToOne
    private Category category;

    @ManyToOne
    private User author;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> compara;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> favoriti;

    private String luogo;

    private int prezzo;
}
