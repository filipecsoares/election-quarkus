package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.Optional;

@QuarkusTest
class CandidateServiceTest {

    @Inject
    private CandidateService service;

    @InjectMock
    CandidateRepository repository;

    @Test
    void shouldSaveCandidate() {
        var domain = Instancio.create(Candidate.class);

        service.save(domain);

        verify(repository).save(domain);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldFindAll() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();

        when(repository.findAll()).thenReturn(candidates);

        var result = service.findAll();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);

        assertEquals(result, candidates);
    }

    @Test
    void shouldReturnCandidateWhenFindById() {
        Candidate candidate = Instancio.create(Candidate.class);

        when(repository.findById(candidate.id())).thenReturn(Optional.of(candidate));

        Candidate result = service.findById(candidate.id());

        verify(repository).findById(candidate.id());
        verifyNoMoreInteractions(repository);

        assertEquals(candidate, result);
    }

    @Test
    void shouldThrowsWhenIdNotExists() {
        Candidate candidate = Instancio.create(Candidate.class);

        when(repository.findById(candidate.id())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findById(candidate.id()));

        verify(repository).findById(candidate.id());
        verifyNoMoreInteractions(repository);
    }
}
