package me.adbi.javastolos.domain.models;

import me.adbi.javastolos.domain.enums.DriversLicense;
import me.adbi.javastolos.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {
    @Test
    void validNatRegNum() throws DomainException {
        Driver d = new Driver(null, "Doe", "John", "00.09.11-589.46", new ArrayList<>(), LocalDate.of(2000,9,11), null, null, null);
        assertTrue(d.simpleNatRegNumCheck(d.getNatRegNumber()));
    }
}
