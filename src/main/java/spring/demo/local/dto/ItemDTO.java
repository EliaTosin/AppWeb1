package spring.demo.local.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import spring.demo.local.item.Item;

import java.util.Date;

@Setter @Getter @Builder
public class ItemDTO {
    private int id;
    private String title;
    private String description;
    private Date date;
    private String category;
    private String author;

    public static ItemDTO item2DTO(Item item){
        return ItemDTO.builder().id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .date(item.getDate())
                .author(item.getAuthor().getUsername())
                .category(item.getCategory().getName())
                .build();
    }
}

