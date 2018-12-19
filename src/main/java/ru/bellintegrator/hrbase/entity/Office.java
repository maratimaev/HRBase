package ru.bellintegrator.hrbase.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;


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
    @Column(length = 50, nullable = false)
    private String name;

    /**
     * Адрес
     */
    @Column(length = 200, nullable = false)
    private String address;

    /**
     * Телефон
     */
    @Column(length = 20)
    private String phone;

    /**
     * Признак активности
     */
    @Column(name = "is_active")
    private boolean isActive = true;

    /**
     * Служебное полк JPA
     */
    @Version
    private Integer version;

    /**
     * Название организации
     * которой принадлежит офис
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    public int getId() {
        return id;
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
