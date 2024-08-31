package domain;

import org.inferred.freebuilder.FreeBuilder;
import java.util.Optional;
import java.util.Set;

/**
 * CandidateQuery
 * Immutable class that represents a query to search for candidates with builder pattern.
 */
@FreeBuilder
public interface CandidateQuery {
    Optional<Set<String>> ids();
    Optional<String> name();

    class Builder extends CandidateQuery_Builder {}
}
