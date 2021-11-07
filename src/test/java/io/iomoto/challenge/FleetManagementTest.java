package io.iomoto.challenge;

import io.iomoto.challenge.domain.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by
 *
 * @author Wasif
 * on
 * @date 11/4/2021
 */
@SpringBootTest
public class FleetManagementTest {
    @Autowired
    VehicleService vehicleService;

    @Test
    public void contextLoads() {
    }
}
