package com.example.game.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "characters")
public class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "job_class")
    private String jobClass;

    @Column(columnDefinition = "int default 1")
    private Integer level = 1;

    @Column(columnDefinition = "bigint default 0")
    private Long exp = 0L;

    private Integer hp;

    @Column(name = "max_hp")
    private Integer maxHp;

    private Integer attack;

    private Integer defense;
    @Column(columnDefinition = "bigint default 0")
    private Long gold = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<Inventory> inventory;


}
