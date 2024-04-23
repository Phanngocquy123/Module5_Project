package com.ra.project5.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "addresses", schema = "project_module5", catalog = "")
public class AddressesEntity {
    private long addressId;
    private String fullAddress;
    private String phone;
    private String receiveName;
    private UsersEntity usersByUserId;

    @Id
    @Column(name = "address_id")
    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "full_address")
    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "receive_name")
    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressesEntity that = (AddressesEntity) o;
        return addressId == that.addressId && Objects.equals(fullAddress, that.fullAddress) && Objects.equals(phone, that.phone) && Objects.equals(receiveName, that.receiveName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, fullAddress, phone, receiveName);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
