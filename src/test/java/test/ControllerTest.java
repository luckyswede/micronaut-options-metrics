package test;

import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import static io.micronaut.http.HttpRequest.OPTIONS;
import static io.micronaut.http.HttpRequest.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@MicronautTest
public class ControllerTest {
    @Inject
    @Client("/")
    HttpClient client;
    @Inject
    CompositeMeterRegistry meterRegistry;

    @Test
    public void test() {
        request(3);
        request(7);

        List<Id> optionsMeterIds = new LinkedList<>();
        List<Id> otherMeterIds = new LinkedList<>();
        meterRegistry.forEachMeter(m -> {
            Id id = m.getId();
            if (id.getName().startsWith("http.server.requests")) {
                if (id.getTag("method").equalsIgnoreCase("OPTIONS")) {
                    optionsMeterIds.add(id);
                } else {
                    otherMeterIds.add(id);
                }
            }
        });
        System.out.println(otherMeterIds);
        System.out.println(optionsMeterIds);
        assertEquals(1, otherMeterIds.size());
        assertEquals("/create/{id}", otherMeterIds.get(0).getTag("uri"));
        assertEquals(1, optionsMeterIds.size());
        assertEquals("/create/{id}", optionsMeterIds.get(0).getTag("uri"));
    }

    private void request(int id) {
        client.toBlocking().exchange(OPTIONS("/create/" + id)
            .header("Origin", "https://test.com")
            .header("Access-Control-Request-Method", "POST"));
        client.toBlocking().exchange(POST("/create/" + id, ""));
    }
}

