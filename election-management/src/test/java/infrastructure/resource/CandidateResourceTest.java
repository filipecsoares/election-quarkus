package infrastructure.resource;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.*;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.Candidate;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

@QuarkusTest
@TestHTTPEndpoint(CandidateResource.class)
class CandidateResourceTest {
    @InjectMock
    CandidateApi api;

    @Test
    void create() {
        var in = Instancio.create(CreateCandidate.class);

        given().contentType(MediaType.APPLICATION_JSON).body(in)
                .when().post()
                .then().statusCode(RestResponse.StatusCode.CREATED);

        verify(api).create(in);
        verifyNoMoreInteractions(api);
    }

    @Test
    void list() {
        var out = Instancio.stream(Candidate.class).limit(4).toList();

        when(api.list()).thenReturn(out);

        var response = given()
                .when().get()
                .then().statusCode(RestResponse.StatusCode.OK).extract().as(Candidate[].class);

        verify(api).list();
        verifyNoMoreInteractions(api);

        assertEquals(out, Arrays.stream(response).toList());
    }

    @Test
    void update() {
        var id = "123";
        var in = Instancio.create(UpdateCandidate.class);
        var out = Instancio.create(Candidate.class);

        when(api.update(id, in)).thenReturn(out);

        var response = given().contentType(MediaType.APPLICATION_JSON).body(in)
                .when().put("/{id}", id)
                .then().statusCode(RestResponse.StatusCode.OK).extract().as(Candidate.class);

        verify(api).update(id, in);
        verifyNoMoreInteractions(api);

        assertEquals(out, response);
    }

    @Test
    void updateNonExistentCandidate() {
        var id = "non-existent-id";
        var in = Instancio.create(UpdateCandidate.class);

        when(api.update(id, in)).thenThrow(new NotFoundException());

        given().contentType(MediaType.APPLICATION_JSON).body(in)
                .when().put("/{id}", id)
                .then().statusCode(RestResponse.StatusCode.NOT_FOUND);

        verify(api).update(id, in);
        verifyNoMoreInteractions(api);
    }
}
