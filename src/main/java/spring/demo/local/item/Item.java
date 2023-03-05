package spring.demo.local.item;

import lombok.*;
import spring.demo.local.author.User;
import spring.demo.local.category.Category;

import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"})
public class Item {
    private int id;

    private String title;

    private String description;

    private Date date;

    private Ad ad_type;

    private byte[] image;

    public enum Ad {
        Offerta, Richiesta
    }

    public boolean isOfferta() {
        if (ad_type==Ad.Offerta)
            return true;
        return false;
    }

    private Category category;

    private User author;

    private List<User> compara;

    private List<User> favoriti;

    private String luogo;

    private int prezzo;
}
