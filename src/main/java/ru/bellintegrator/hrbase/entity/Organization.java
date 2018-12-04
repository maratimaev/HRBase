package ru.bellintegrator.hrbase.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.OutputProfile.OrganizationProfile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 01.12.2018
 */
@Entity
@Table(name = "organization")
public class Organization {
    @JsonView(OrganizationProfile.Short.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonView(OrganizationProfile.Short.class)
    @Column(nullable = false)
    private String name;

    @JsonView(OrganizationProfile.Full.class)
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @JsonView(OrganizationProfile.Full.class)
    @Column(nullable = false)
    private String inn;

    @JsonView(OrganizationProfile.Full.class)
    @Column(nullable = false)
    private String kpp;

    @JsonView(OrganizationProfile.Full.class)
    @Column(nullable = false)
    private String address;

    @JsonView(OrganizationProfile.Full.class)
    private String phone;

    @JsonView(OrganizationProfile.Short.class)
    @Column(name = "is_active")
    private boolean isActive = false;

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
