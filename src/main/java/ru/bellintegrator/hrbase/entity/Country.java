package ru.bellintegrator.hrbase.entity;

import javax.persistence.*;

/**
 * Государство
 */
@Entity
@Table(name = "country")
public class Country {
    /**
     * id страны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Код страны
     */
    @Column(nullable = false)
    private String code;

    /**
     * Название
     */
    @Column(nullable = false)
    private String name;

    public Country() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
