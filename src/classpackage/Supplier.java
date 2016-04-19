package classpackage;

import javafx.beans.property.*;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Supplier {
    private IntegerProperty supplierId = new SimpleIntegerProperty();
    private IntegerProperty phoneNumber = new SimpleIntegerProperty();
    private ObjectProperty<Address> thisAddress = new SimpleObjectProperty<>();
    private StringProperty businessName = new SimpleStringProperty();

    // from database
    public Supplier(int supplierId, int phoneNumber, Address thisAddress, String businessName) {
        this.supplierId.set(supplierId);
        this.phoneNumber.set(phoneNumber);
        this.thisAddress.set(thisAddress);
        this.businessName.set(businessName);
    }

    // To database
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

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", phoneNumber=" + phoneNumber +
                ", thisAddress=" + thisAddress +
                ", businessName=" + getBusinessName() +
                '}';
    }
}
