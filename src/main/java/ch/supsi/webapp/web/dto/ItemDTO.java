package ch.supsi.webapp.web.dto;

import ch.supsi.webapp.web.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

