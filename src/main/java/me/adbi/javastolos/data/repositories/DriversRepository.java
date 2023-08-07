package me.adbi.javastolos.data.repositories;

import me.adbi.javastolos.data.exceptions.DataException;
import me.adbi.javastolos.domain.enums.DriversLicense;
import me.adbi.javastolos.domain.exceptions.DomainException;
import me.adbi.javastolos.domain.models.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DriversRepository {
    Connection connection;

    public DriversRepository(String url, String user, String pwd) throws SQLException {
        connection = DriverManager.getConnection(url, user, pwd);
    }

    public List<Driver> getDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT d.DriverID, d.FirstName, d.LastName, d.Address, d.BirthDate, d.NationalRegistrationNumber, d.DriversLicenses, v.VIN, v.LicensePlate, gc.CardNumber " +
                "FROM GasCard gc RIGHT JOIN Driver d ON gc.DriverID=d.DriverID LEFT JOIN Vehicle v ON v.DriverID = d.DriverID WHERE d.Deleted=0;");

        try (ResultSet reader = stmt.executeQuery()) {
            while (reader.next()) {
                int id = reader.getInt("DriverID");
                String fName = reader.getString("FirstName");
                String lName = reader.getString("LastName");
                String address = reader.getString("Address");
                LocalDate birthDate = reader.getDate("BirthDate").toLocalDate();
                String natRegNum = reader.getString("NationalRegistrationNumber");
                List<String> licenseList = Arrays.asList(reader.getString("DriversLicenses").split(","));
                List<DriversLicense> licenses = licenseList.stream()
                        .map(dl -> DriversLicense.valueOf(dl))
                        .collect(Collectors.toList());
                String vin = reader.getString("VIN");
                String cardNum = reader.getString("CardNumber");

                Driver d = new Driver(id, lName, fName, natRegNum, licenses, birthDate, address, vin, cardNum);
                drivers.add(d);
            }
        }
        catch (Exception ex) {

        }
        return drivers;
    }

    public Driver getDriver(int driverId) throws DataException, SQLException {
        Driver d = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT d.DriverID, d.FirstName, d.LastName, d.Address, d.BirthDate, d.NationalRegistrationNumber, d.DriversLicenses, v.VIN, v.LicensePlate, gc.CardNumber " +
                "FROM GasCard gc RIGHT JOIN Driver d ON gc.DriverID=d.DriverID LEFT JOIN Vehicle v ON v.DriverID = d.DriverID WHERE d.Deleted=0 AND d.DriverID = ?;");
        stmt.setInt(1, driverId);

        try (ResultSet reader = stmt.executeQuery()) {
            while (reader.next()) {
                int id = reader.getInt("DriverID");
                String fName = reader.getString("FirstName");
                String lName = reader.getString("LastName");
                String address = reader.getString("Address");
                LocalDate birthDate = reader.getDate("BirthDate").toLocalDate();
                String natRegNum = reader.getString("NationalRegistrationNumber");
                List<String> licenseList = Arrays.asList(reader.getString("DriversLicenses").split(","));
                List<DriversLicense> licenses = licenseList.stream()
                        .map(dl -> DriversLicense.valueOf(dl))
                        .collect(Collectors.toList());
                String vin = reader.getString("VIN");
                String cardNum = reader.getString("CardNumber");

                d = new Driver(id, lName, fName, natRegNum, licenses, birthDate, address, vin, cardNum);
            }
        } catch (DomainException e) {
            throw new DataException(String.format("Repo-Driver: getDriver #%d. DOMAIN EX", driverId),e);
        } catch (SQLException e) {
            throw new DataException(String.format("Repo-Driver: getDriver #%d. SQL EX", driverId),e);
        }
        return d;
    }
}
