package br.com.ti_knology.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PurchaseId implements Serializable {
    private Long cartId;
    private Long purchaseId;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseId)) return false;
        PurchaseId that = (PurchaseId) o;
        return Objects.equals(cartId, that.cartId) &&
                Objects.equals(purchaseId, that.purchaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, purchaseId);
    }
}