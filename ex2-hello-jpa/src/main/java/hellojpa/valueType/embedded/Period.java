package hellojpa.valueType.embedded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
@Embeddable
@Getter
@Setter
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period() {
    }
}
