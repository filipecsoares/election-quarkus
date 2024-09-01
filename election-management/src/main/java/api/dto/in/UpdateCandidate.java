package api.dto.in;

import domain.Candidate;
import java.util.Optional;

public record UpdateCandidate(Optional<String> photo,
        String name,
        String email,
        Optional<String> phone,
        Optional<String> jobTitle) {
    public Candidate toDomain(String id) {
        return new Candidate(id, photo(), name(), email(), phone(), jobTitle());
    }
}
