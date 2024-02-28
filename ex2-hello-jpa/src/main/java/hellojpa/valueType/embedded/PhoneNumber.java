package hellojpa.valueType.embedded;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PhoneNumber {
    String areaCode;
    String localNumber;
    @ManyToOne //엔티티참조
    PhoneServiceProvider provider;
}
