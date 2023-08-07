package me.adbi.javastolos.domain.models;

import me.adbi.javastolos.domain.enums.DriversLicense;
import me.adbi.javastolos.domain.exceptions.DomainException;

import java.time.LocalDate;
import java.util.List;

public class Driver {
    //region attrib
    private Integer id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private String natRegNumber;
    private String address;
    private List<DriversLicense> licenses;
    private String VIN;
    private String fuelcardNum;
    //endregion

    //region prop
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) throws DomainException {
        if (id != null && id < 1) {
            throw new DomainException("Driver: set-Id: Invalid value (val < 1)");
        }
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) throws DomainException {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new DomainException("Driver: set-LastName: NULL value;");
        }
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) throws DomainException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new DomainException("Driver: set-FirstName: NULL value");
        }
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) throws DomainException {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new DomainException("Driver: set-BirthDate: Incorrect value");
        }
        this.birthDate = birthDate;
    }

    public String getNatRegNumber() {
        return natRegNumber;
    }
    public void setNatRegNumber(String natRegNumber) throws DomainException {
        if (!simpleNatRegNumCheck(natRegNumber)) {
            throw new DomainException("Driver: Set-NatRegNumber: Not Valid Control Num");
        }
        this.natRegNumber = natRegNumber;
    }

    public List<DriversLicense> getLicenses() {
        return this.licenses;
    }
    public void setLicenses(List<DriversLicense> licenses) {
        this.licenses = licenses;
    }

    public String getVIN() {
        return this.VIN;
    }
    public void setVIN(String vin) throws DomainException {
        this.VIN = vin;
    }

    public String getFuelcardNum() {
        return this.fuelcardNum;
    }
    public void setFuelcardNum(String fuelcardNum) throws DomainException {
        this.fuelcardNum = fuelcardNum;
    }
    //endregion

    //region ctor
    public Driver(Integer id, String lastName, String firstName, String natRegNumber, List<DriversLicense> licenses, LocalDate birthDate, String address, String VIN, String fuelcardNum) throws DomainException {
        setId(id);
        setLastName(lastName);
        setFirstName(firstName);
        if (!simpleNatRegNumCheck(natRegNumber)) {
            throw new DomainException("Invalid RRN");
        }
        setNatRegNumber(natRegNumber);
        setLicenses(licenses);
        setBirthDate(birthDate);
        if (!isMatchingBirthDateAndRRN(getNatRegNumber())) {
            throw new DomainException("BirthDate and RRN do not match!");
        }
        setAddress(address);
        setVIN(VIN);
        setFuelcardNum(fuelcardNum);
    }
    //endregion

    //region methods
    private LocalDate getDateFromRRN(String natRegNum) throws DomainException {
        String[] parts = natRegNum.split("-");
        String fullDate = parts[0];
        String idAndControl = parts[1];
        String[] dateParts = fullDate.split("\\.");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        String[] idControlParts = idAndControl.split("\\.");
        String id = idControlParts[0];
        String control = idControlParts[1];

        int yearNum = Integer.parseInt(year);
        int monthNum = Integer.parseInt(month);
        int dayNum = Integer.parseInt(day);
        int controlNum = Integer.parseInt(control);

        if (monthNum < 1 || monthNum > 12) {
            throw new DomainException("Driver: GetDateFromRRN: Invalid month");
        }
        if (dayNum < 1 || dayNum > 31) {
            throw new DomainException("Driver: GetDateFromRRN: Invalid day");
        }

        boolean valid = false;
        String allButControl = year + month + day + id;
        int res = 97 - (Integer.parseInt(allButControl) % 97);

        if (controlNum == res) {
            valid = true;
        }

        String yearBeginning = "19";
        if (!valid) {
            final int magicNum = 2000000000; // gebruikt voor mensen die in/na 2000 zijn geboren
            res = 97 - ((magicNum + Integer.parseInt(allButControl)) % 97);
            if (controlNum == res) {
                valid = true;
                yearBeginning = "20";
            }
        }

        return LocalDate.of(Integer.parseInt(yearBeginning + year), monthNum, dayNum);
    }

    private boolean simpleNatRegNumCheck(String natRegNum) {
        String[] parts = natRegNum.split("-");
        String fullDate = parts[0];
        String idAndControl = parts[1];
        String[] dateParts = fullDate.split("\\.");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        String[] idControlParts = idAndControl.split("\\.");
        String id = idControlParts[0];
        String control = idControlParts[1];

        int controlNum = Integer.parseInt(control);
        String allButControl = year + month + day + id;

        int res = 97 - (Integer.parseInt(allButControl) % 97);
        boolean valid = controlNum == res;

        if (!valid) {
            final int magicNum = 2000000000; // gebruikt voor mensen die in/na 2000 zijn geboren
            res = 97 - ((magicNum + Integer.parseInt(allButControl)) % 97);
            valid = controlNum == res;
        }

        return valid;
    }

    private boolean isMatchingBirthDateAndRRN(String natRegNum) throws DomainException {
        LocalDate dtFromRRN = getDateFromRRN(natRegNum);
        LocalDate doFromBD = LocalDate.of(getBirthDate().getYear(), getBirthDate().getMonthValue(), getBirthDate().getDayOfMonth());
        return dtFromRRN.isEqual(doFromBD);
    }
    //endregion
}