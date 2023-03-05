package spring.demo.local;

import ch.supsi.webapp.web.role.Role;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.demo.local.author.User;
import spring.demo.local.author.UserRepository;
import spring.demo.local.category.Category;
import spring.demo.local.category.CategoryRepository;
import spring.demo.local.item.Item;
import spring.demo.local.item.ItemRepository;
import spring.demo.local.role.RoleRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static spring.demo.local.item.ItemService.*;

@Controller
public class WebPagesController {

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
        if (ur.findById(username).isEmpty()) {
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
        ir.save(item, true);

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
        Item newItem = ir.findById(id).get();
        newItem.setTitle(item.getTitle());
        newItem.setDescription(item.getDescription());
        newItem.setDate(Date.from(Instant.now()));
        newItem.setPrezzo(item.getPrezzo());
        newItem.setLuogo(item.getLuogo());
        model.addAttribute("item", newItem);

        ir.deleteById(id);
        ir.save(newItem, false);
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/delete")
    public String deleteItem(@PathVariable int id){
        if (ir.existsById(id))
            ir.deleteById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/search/{str}")
    @ResponseBody
    public List<Item> searchItems(@PathVariable String str){
        return ir.findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(str, str);
    }

}
