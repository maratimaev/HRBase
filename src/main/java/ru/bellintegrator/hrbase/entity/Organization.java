package ru.bellintegrator.hrbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, связанная с таблицей Organization
 */
@Entity
@Table(name = "organization")
public class Organization {
    /**
     * id организации
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя организации
     */
    @Column(length = 50, nullable = false)
    private String name;

    /**
     * Полное имя организации
     */
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    /**
     * Инн организации
     */
    @Column(length = 12, nullable = false)
    private String inn;

    /**
     * КПП организации
     */
    @Column(length = 9, nullable = false)
    private String kpp;

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
    private Boolean isActive;

    /**
     * Служебное полк JPA
     */
    @Version
    private Integer version;

    /**
     * Список офисов организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private Set<Office> offices = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Office> getOffices() {
        return offices;
    }

    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }
}
