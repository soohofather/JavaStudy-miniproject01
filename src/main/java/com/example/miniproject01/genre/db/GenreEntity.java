package com.example.miniproject01.genre.db;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "genre")
public class GenreEntity {

    @Id
    private Long id;
    private String name;

}
