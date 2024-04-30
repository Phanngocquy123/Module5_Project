package com.ra.project5.model.entity;


import jakarta.persistence.*;

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

        if (addressId != that.addressId) return false;
        if (fullAddress != null ? !fullAddress.equals(that.fullAddress) : that.fullAddress != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (receiveName != null ? !receiveName.equals(that.receiveName) : that.receiveName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (fullAddress != null ? fullAddress.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (receiveName != null ? receiveName.hashCode() : 0);
        return result;
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
