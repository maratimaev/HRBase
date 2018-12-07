package ru.bellintegrator.hrbase.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.OrganizationProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @JsonView(OrganizationProfile.Short.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя организации
     */
    @JsonView(OrganizationProfile.Short.class)
    @Column(length = 50, nullable = false)
    private String name;

    /**
     * Полное имя организации
     */
    @JsonView(OrganizationProfile.Full.class)
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    /**
     * Инн организации
     */
    @JsonView(OrganizationProfile.Full.class)
    @Column(length = 12, nullable = false)
    private String inn;

    /**
     * КПП организации
     */
    @JsonView(OrganizationProfile.Full.class)
    @Column(length = 9, nullable = false)
    private String kpp;

    /**
     * Адрес
     */
    @JsonView(OrganizationProfile.Full.class)
    @Column(length = 200, nullable = false)
    private String address;

    /**
     * Телефон
     */
    @JsonView(OrganizationProfile.Full.class)
    @Column(length = 20)
    private String phone;

    /**
     * Признак активности
     */
    @JsonView(OrganizationProfile.Short.class)
    @Column(name = "is_active")
    private boolean isActive = false;

    /**
     * Список офисов организации
     */
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private Set<Office> offices = new HashSet<>();

    public Organization() {
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
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public Set<Office> getOffices() {
        return offices;
    }
    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }
}
