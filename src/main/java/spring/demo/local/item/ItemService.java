package spring.demo.local.item;


import ch.supsi.webapp.web.role.Role;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;
import spring.demo.local.author.User;
import spring.demo.local.author.UserRepository;
import spring.demo.local.category.Category;
import spring.demo.local.category.CategoryRepository;
import spring.demo.local.role.RoleRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class ItemService {
        public static final ItemRepository ir = new ItemRepository();

        public static final UserRepository ur = new UserRepository();

        public static final CategoryRepository cr = new CategoryRepository();

        public static final RoleRepository rr = new RoleRepository();

        @PostConstruct
        public void init() throws IOException {
            if(ur.getSize() == 0) {
                User admin = new User("admin", "admin", new Role("ROLE_ADMIN"));
                ur.save(admin);
                rr.save(new Role("ROLE_ADMIN"));
            }

            if(cr.findAll().size() == 0) {
                cr.save(new Category("Veicoli"));
                cr.save(new Category("Immobili"));
                cr.save(new Category("Lavoro"));
            }

            if(ir.findAll().size() == 0) {
                Item blogPost = new Item();
                blogPost.setAuthor(ur.findById("admin").get());
                blogPost.setCategory(cr.findById("Veicoli").get());
                blogPost.setTitle("Audi R8");
                blogPost.setDescription("Bellissima auto,divertente nel misto.Perfette condizioni.Airscarf,Parktronic,pelle...");
                blogPost.setDate(new Date());
                blogPost.setLuogo("Lugano");
                blogPost.setPrezzo(150000);
                blogPost.setAd_type(Item.Ad.Offerta);
                blogPost.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/audi-r8-v10-plus-ge67534769_640.jpeg")));
                post(blogPost);
            }

//            System.err.println("Trovati dal filtro: " + getAllFiltered("auto"));
        }

        public List<Item> getAll(){
            return ir.findAll();
        }

        public Item get(int id){
            return ir.findById(id).get();
        }

        public Item post(Item p){
            return ir.save(p, true);
        }

        public Item put(Item p) {
            return ir.save(p, false);
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
