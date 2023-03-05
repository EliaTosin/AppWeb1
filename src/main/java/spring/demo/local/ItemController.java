package spring.demo.local;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.demo.local.dto.ItemDTO;
import spring.demo.local.dto.Success;
import spring.demo.local.item.Item;
import spring.demo.local.item.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {
    public final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ItemDTO> list(){
        return service.getAll().stream().map(item -> ItemDTO.item2DTO(item)).collect(Collectors.toList());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<ItemDTO> get(@PathVariable int id){
        if (!service.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = service.get(id);
        return item != null ? new ResponseEntity<>(ItemDTO.item2DTO(item), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="")
    public ResponseEntity<ItemDTO> post(@RequestBody Item item){
        item = service.post(item);
        return new ResponseEntity<>(ItemDTO.item2DTO(item), HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<ItemDTO> put(@PathVariable int id, @RequestBody Item item){
        if (!service.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        item.setId(id);
        service.put(item);
        return new ResponseEntity<>(ItemDTO.item2DTO(item), HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Success> delete(@PathVariable int id){
        if (!service.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        boolean remove = service.delete(id);
        if(remove) return new ResponseEntity<>(new Success(true), HttpStatus.OK);
        else return new ResponseEntity<>(new Success(false), HttpStatus.OK);
    }

}
