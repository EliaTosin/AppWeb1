package ch.supsi.webapp.web.item;



import ch.supsi.webapp.web.author.User;
import ch.supsi.webapp.web.author.UserRepository;
import ch.supsi.webapp.web.category.Category;
import ch.supsi.webapp.web.category.CategoryRepository;
import ch.supsi.webapp.web.role.Role;
import ch.supsi.webapp.web.role.RoleRepository;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class ItemService {
        private final ItemRepository ir;

        private final UserRepository ar;

        private final CategoryRepository cr;

        private final RoleRepository rr;

        public ItemService(ItemRepository ir, UserRepository ur, CategoryRepository cr, RoleRepository rr) {
            this.ir = ir;
            this.ar = ur;
            this.cr = cr;
            this.rr = rr;
        }

        @PostConstruct
        public void init() throws IOException {
            if(ar.findAll().size() == 0) {
                User admin = new User("admin", "admin", new Role("ROLE_ADMIN"));
                ar.save(admin);
                rr.save(new Role("ROLE_ADMIN"));
            }

            if(cr.findAll().size() == 0) {
                cr.save(new Category("Veicoli"));
                cr.save(new Category("Immobili"));
                cr.save(new Category("Lavoro"));
            }

            if(ir.findAll().size() == 0) {
                Item blogPost = new Item();
                blogPost.setAuthor(ar.findById("admin").get());
                blogPost.setCategory(cr.findById("Veicoli").get());
                blogPost.setTitle("\"SLC 43 Amg\"");
                blogPost.setDescription("Bellissima auto,divertente nel misto.Perfette condizioni.Airscarf,Parktronic,pelle...");
                blogPost.setDate(new Date());
                blogPost.setLuogo("Lugano");
                blogPost.setPrezzo(50000);
                blogPost.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/newLogo.png")));
                post(blogPost);
            }
        }

        public List<Item> getAll(){
            return ir.findAll();
        }

        public Item get(int id){
            return ir.findById(id).get();
        }

        public Item post(Item p){
            return ir.save(p);
        }

        public Item put(Item p) {
            return ir.save(p);
        }

        public List<Item> getAllFiltered(String filter) {
            return ir.findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(filter, filter);
        }

        public boolean exists(int id) {
            return ir.existsById(id);
        }

        public boolean delete(int id){
            ir.deleteById(id);
            if(ir.existsById(id)){
                return false;
            }
            return true;
        }
}
