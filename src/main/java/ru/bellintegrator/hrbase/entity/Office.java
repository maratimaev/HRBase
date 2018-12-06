package ru.bellintegrator.hrbase.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Сущность, связанная с таблицей Office
 */
@Entity
@Table(name = "office")
public class Office {
    /**
     * id офиса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя офиса
     */
    @Column(nullable = false)
    private String name;

    /**
     * Адрес
     */
    @Column(nullable = false)
    private String address;

    /**
     * Телефон
     */
    private String phone;

    /**
     * Признак активности
     */
    @Column(name = "is_active")
    private boolean isActive = true;

    /**
     * Название организации
     * которой принадлежит офис
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    public Office() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
