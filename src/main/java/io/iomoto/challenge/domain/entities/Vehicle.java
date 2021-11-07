package io.iomoto.challenge.domain.entities;

import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "vehicle")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private String vin;
    private String licensePlateNumber;
    private String name;
    private BasicDBObject properties;
}
