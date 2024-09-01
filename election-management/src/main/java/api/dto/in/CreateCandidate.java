package api.dto.in;

import domain.Candidate;
import java.util.Optional;

public record CreateCandidate(Optional<String> photo,
        String name,
        String email,
        Optional<String> phone,
        Optional<String> jobTitle) {
    public Candidate toDomain() {
        return Candidate.create(photo(), name(), email(), phone(), jobTitle());
    }
}
