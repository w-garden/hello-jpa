package hellojpa.manyMany;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

//회원 상품 식별자 클래스
@Getter
@Setter
public class MemberProductId implements Serializable {
    private String member; //MemberProduct.member 와 연결
    private String product; //MemberProduct.product 와 연결

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberProductId that = (MemberProductId) o;

        if (!Objects.equals(member, that.member)) return false;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        int result = member != null ? member.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
