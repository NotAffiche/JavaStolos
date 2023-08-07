package me.adbi.javastolos.domain.services;

import me.adbi.javastolos.data.exceptions.DataException;
import me.adbi.javastolos.data.repositories.DriversRepository;
import me.adbi.javastolos.domain.models.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DriversService {
    private DriversRepository repo;

    public DriversService(@Value("${mysqlConnectionString}") String connectionString, @Value("${mysqlUser}") String connectionUser, @Value("${mysqlPwd}") String connectionPassword) throws SQLException {
        repo = new DriversRepository(connectionString, connectionUser, connectionPassword);
    }

    public List<Driver> getDrivers() throws SQLException {
        return repo.getDrivers();
    }

    public Driver getDriver(int id) throws DataException, SQLException {
        return repo.getDriver(id);
    }

}
