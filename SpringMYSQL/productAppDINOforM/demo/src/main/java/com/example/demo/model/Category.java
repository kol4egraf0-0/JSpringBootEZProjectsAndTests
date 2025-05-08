package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL, //удалении категории => удалятся все её продукты
            fetch = FetchType.LAZY)//не загрузятся до явного обращения
    //@JsonIgnore //ошибка цикличности
    private Set<Product> products;
}
