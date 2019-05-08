package internhub;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Company {

    private @Id @GeneratedValue Long id;
    private String name;

    Company() {}

    Company(String name) {
        this.name = name;
    }

}
