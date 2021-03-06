package classpackage;

import javafx.beans.property.*;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Supplier {
    /**
     * Objectvariables for Supplier.java
     */
    private IntegerProperty supplierId = new SimpleIntegerProperty();
    private IntegerProperty phoneNumber = new SimpleIntegerProperty();
    private ObjectProperty<Address> thisAddress = new SimpleObjectProperty<>();
    private StringProperty businessName = new SimpleStringProperty();

    /**
     * Constructor used when reading from database
     * @param supplierId
     * @param phoneNumber
     * @param thisAddress
     * @param businessName
     */
    public Supplier(int supplierId, int phoneNumber, Address thisAddress, String businessName) {
        this.supplierId.set(supplierId);
        this.phoneNumber.set(phoneNumber);
        this.thisAddress.set(thisAddress);
        this.businessName.set(businessName);
    }

    /**
     * Constructor used when writing to the database
     * @param phoneNumber
     * @param thisAddress
     * @param businessName
     */
    public Supplier(int phoneNumber, Address thisAddress, String businessName) {
        this.phoneNumber.set(phoneNumber);
        this.thisAddress.set(thisAddress);
        this.businessName.set(businessName);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public IntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public Address getThisAddress() {
        return thisAddress.get();
    }

    public ObjectProperty<Address> thisAddressProperty() {
        return thisAddress;
    }

    public void setThisAddress(Address thisAddress) {
        this.thisAddress.set(thisAddress);
    }

    public String getBusinessName() {
        return businessName.get();
    }

    public StringProperty businessNameProperty() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName.set(businessName);
    }

    /**
     * toString method
     * @return toString
     */
    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", phoneNumber=" + phoneNumber +
                ", thisAddress=" + thisAddress +
                ", businessName=" + getBusinessName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        if (supplierId != null ? !supplierId.equals(supplier.supplierId) : supplier.supplierId != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(supplier.phoneNumber) : supplier.phoneNumber != null)
            return false;
        if (thisAddress != null ? !thisAddress.equals(supplier.thisAddress) : supplier.thisAddress != null)
            return false;
        if (businessName != null ? !businessName.equals(supplier.businessName) : supplier.businessName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = supplierId != null ? supplierId.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (thisAddress != null ? thisAddress.hashCode() : 0);
        result = 31 * result + (businessName != null ? businessName.hashCode() : 0);
        return result;
    }
}
