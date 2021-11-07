package io.iomoto.challenge.platform.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.iomoto.challenge.platform.exceptions.InvalidPropertiesException;
import org.json.JSONException;
import org.json.XML;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class Utility {
    final static ObjectMapper mapper = new ObjectMapper();

    /**
     * Full stack information
     *
     * @param e Exception
     * @return Full StackTrace
     */
    public static String getStackTrace(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

    public static Map toMap(String properties) {
        try {
            return mapper.readValue(properties, Map.class);
        } catch (JsonProcessingException e) {
            Map map;
            try {
                map = XML.toJSONObject(properties).toMap();
            } catch (JSONException jE) {
                throw new InvalidPropertiesException("Invalid properties format");
            }
            if (!map.isEmpty() && !properties.isEmpty()) {
                return map;
            } else {
                throw new InvalidPropertiesException("Invalid properties format");

            }

        }
    }

}
