package me.adbi.javastolos.api.controllers;

import me.adbi.javastolos.data.exceptions.DataException;
import me.adbi.javastolos.domain.exceptions.DomainException;
import me.adbi.javastolos.domain.models.Driver;
import me.adbi.javastolos.domain.services.DriversService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/drivers")
public class DriversController {

    private final DriversService driversService;

    @Autowired
    public DriversController(DriversService driversService) {
        this.driversService = driversService;
    }

    @GetMapping("/{id}")
    public Driver getDriver(@PathVariable Integer id) throws DataException, SQLException, DomainException {
        return driversService.getDriver(id);
    }

}
