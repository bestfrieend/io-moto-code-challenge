package io.iomoto.challenge.platform.utils;

import io.iomoto.challenge.platform.exceptions.InvalidPropertiesException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.AssertTrue;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UtilityTest {

    @Test
    public void getStackTraceTest() {
        Utility.getStackTrace(new Exception("This is test exception"));
    }

    @Test
    public void validJsontoMapTest() {
        String json = "{\"color\":\"red\", \"weight\":\"1000\"}";
        Map map = Utility.toMap(json);
        assertEquals(!json.isEmpty(), !map.isEmpty());
    }

    @Test
    public void invalidJsontoMapTest() {
        String invalidJson = "{\"color\":\"red\", \"weight\":\"1000\"";
        InvalidPropertiesException thrown = assertThrows(InvalidPropertiesException.class, () -> {
            Utility.toMap(invalidJson);
        });
    }

    @Test
    public void validXMLtoMapTest() {
        String validXML = "<xml><color>red</color><weight>1000</weight></xml>";
        Map map = Utility.toMap(validXML);
        assertTrue(!map.isEmpty() == !validXML.isEmpty());
    }


    @Test
    public void inValidXMLtoMapTest() {
        String invalidXML = "<xml><color>red</color><weight>1000</weight>";
        InvalidPropertiesException thrown = assertThrows(InvalidPropertiesException.class, () -> {
            Utility.toMap(invalidXML);
        });
    }
}