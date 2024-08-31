package infrastructure.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Optional;

@Entity(name = "candidates")
public class Candidate {
    @Id
    private String id;
    private String photo;
    @Column(name = "name")
    private String name;
    private String email;
    private String phone;
    @Column(name = "job_title")
    private String jobTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public static Candidate fromDomain(domain.Candidate domain) {
        Candidate entity = new Candidate();

        entity.setId(domain.id());
        entity.setPhoto(domain.photo().orElse(null));
        entity.setName(domain.name());
        entity.setEmail(domain.email());
        entity.setPhone(domain.phone().orElse(null));
        entity.setJobTitle(domain.jobTitle().orElse(null));

        return entity;
    }

    public domain.Candidate toDomain() {
        return new domain.Candidate(getId(),
                Optional.ofNullable(getPhoto()),
                getName(),
                getEmail(),
                Optional.ofNullable(getPhone()),
                Optional.ofNullable(getJobTitle()));
    }
}
