package ch.supsi.webapp.web;

import ch.supsi.webapp.web.author.User;
import ch.supsi.webapp.web.author.UserRepository;
import ch.supsi.webapp.web.category.Category;
import ch.supsi.webapp.web.category.CategoryRepository;
import ch.supsi.webapp.web.dto.ItemDTO;
import ch.supsi.webapp.web.item.Item;
import ch.supsi.webapp.web.item.ItemRepository;
import ch.supsi.webapp.web.role.Role;
import ch.supsi.webapp.web.role.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
public class WebPagesController {
    private final ItemRepository ir;

    private final UserRepository ur;

    private final CategoryRepository cr;

    private final RoleRepository rr;

    public WebPagesController(ItemRepository ir, UserRepository ur, CategoryRepository cr, RoleRepository rr) {
        this.ir = ir;
        this.ur = ur;
        this.cr = cr;
        this.rr = rr;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("items",
                ir.findAll());
        model.addAttribute("item", new Item());
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String commitUser(String username, String password){
        if (!ur.findById(username).isPresent()) {
            User newUser = new User(username, password, new Role("ROLE_USER"));
            ur.save(newUser);
            rr.save(new Role("ROLE_USER"));
        }
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}")
    public String getItem(Model model, @PathVariable int id){
        if (ir.existsById(id)) {
            Item item = ir.findById(id).get();
            model.addAttribute("item", item);
            model.addAttribute("authorName", item.getAuthor().getUsername());
            model.addAttribute("categoryName", item.getCategory().getName());
            model.addAttribute("prezzo", item.getPrezzo());
            model.addAttribute("luogo", item.getLuogo());
            return "itemDetails";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/item/new")
    public String addItem(Model model){
        model.addAttribute("users", ur.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("categories", cr.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("buyOrSell", Item.Ad.values());
        model.addAttribute("tipoAnnuncio", new Item().getAd_type());
        model.addAttribute("prezzo", new Item().getPrezzo());
        model.addAttribute("luogo", new Item().getLuogo());
        model.addAttribute("item", new Item());
        return "createItemForm";
    }

    @PostMapping(value = "/item/new")
    public String addItemPost(Item item, Model model, @RequestParam("file") MultipartFile file){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ur.findById(user.getUsername()).isPresent())
            item.setAuthor(ur.findById(user.getUsername()).get());
        else
            return "redirect:/error";
        item.setDate(Date.from(Instant.now()));
        if (!file.isEmpty()) {
            try {
                item.setImage(file.getBytes());
            } catch (IOException e) {
                return "redirect:/error";
            }
        }
        model.addAttribute("item", item);
        ir.save(item);

        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable int id){
        Item item = ir.findById(id).get();
        return item.getImage();
    }

    @GetMapping(value = "/item/{id}/edit")
    public String modifyItem(Model model, @PathVariable int id){
        Item item = ir.findById(id).get();
        model.addAttribute("item", item);
        return "editItemForm";
    }

    @PostMapping(value = "/item/{id}/edit")
    public String modifyItemPost(Model model, Item item, @PathVariable int id) {
        Item newItem = ir.getById(id);
        newItem.setTitle(item.getTitle());
        newItem.setDescription(item.getDescription());
        newItem.setDate(Date.from(Instant.now()));
        newItem.setPrezzo(item.getPrezzo());
        newItem.setLuogo(item.getLuogo());
        model.addAttribute("item", newItem);

        ir.deleteById(id);
        ir.save(newItem);
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/delete")
    public String deleteItem(@PathVariable int id){
        if (ir.existsById(id))
            ir.delete(ir.getById(id));
        return "redirect:/";
    }

    @GetMapping(value = "/confronta/{username}")
    public String compareItems(Model model, @PathVariable String username){
        try {
            model.addAttribute("items",
                    ur.findById(username).get().getConfrontabili());
        } catch (NoSuchElementException e) {
            model.addAttribute("items", new ArrayList<Item>());
        }

        model.addAttribute("item", new Item());

//        List<String> categorie = new ArrayList<>();
//        for (Item item : ir.findAll()) {
//            categorie.add(item.getCategory().getName());
//        }
//        model.addAttribute("categories", categorie);
        return "confronta";
    }

    @PostMapping(value = "/confronta/")
    public ResponseEntity<Integer> addItemToCompare(@RequestParam("q") String find) {
        Item item;
        try {
            String usernameId = find.split("-")[0];
            int itemId = Integer.parseInt(find.split("-")[1]);

            item = ir.getById(itemId);
            item.getCompara().add(ur.getById(usernameId));

            User user = ur.getById(usernameId);
            user.getConfrontabili().add(ir.getById(itemId));

            ir.deleteById(itemId);

            ir.save(item);
        } catch (Exception exception) {
            return new ResponseEntity<Integer>(1000, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Integer>(item.getCompara().size(), HttpStatus.OK);
    }

    @GetMapping(value = "/favoriti/{username}")
    public List<Item> getFavourites(@PathVariable String username) {
        return ur.getById(username).getFavoriti();
    }

}
