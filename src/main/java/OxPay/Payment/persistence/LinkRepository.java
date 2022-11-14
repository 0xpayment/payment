package OxPay.Payment.persistence;

import OxPay.Payment.model.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByMerchantIdOrderByCreatedAt(final Long merchantId);

    Optional<Link> findByIdAndMerchantId(final long linkId, final Long merchantId);
}
