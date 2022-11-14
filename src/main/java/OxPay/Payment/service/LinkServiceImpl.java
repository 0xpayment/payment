package OxPay.Payment.service;

import OxPay.Payment.exception.NotFoundException;
import OxPay.Payment.model.domain.LinkDo;
import OxPay.Payment.model.entity.Link;
import OxPay.Payment.persistence.LinkRepository;
import com.OxPay.uid.UidGenerator;

import java.time.Instant;
import java.util.List;

public class LinkServiceImpl implements LinkService {

    private final UidGenerator uidGenerator;
    private final LinkRepository linkRepository;

    public LinkServiceImpl(final UidGenerator uidGenerator, final LinkRepository linkRepository) {
        this.uidGenerator = uidGenerator;
        this.linkRepository = linkRepository;
    }

    @Override
    public Long createLink(final LinkDo linkDo) {
        final Link link = new Link();

        link.setId(this.uidGenerator.getUID());
        link.setDescription(linkDo.getDescription());
        link.setMerchantId(linkDo.getMerchantId());
        link.setAmount(linkDo.getAmount());
        link.setCurrency(linkDo.getCurrency());
        link.setName(linkDo.getName());
        link.setRequireCustomerEmail(linkDo.getRequireCustomerEmail());
        link.setRequireCustomerName(linkDo.getRequireCustomerName());
        link.setCreatedAt(Instant.now());
        link.setUpdatedAt(Instant.now());

        this.linkRepository.save(link);

        return link.getId();
    }

    @Override
    public List<Link> getLinks(final Long merchantId) {
        return this.linkRepository.findByMerchantIdOrderByCreatedAt(merchantId);
    }

    @Override
    public Link getLinkById(final Long linkId) {
        final var linkOptional = this.linkRepository.findById(linkId);
        return linkOptional.orElseThrow(() -> new NotFoundException("could not find the link with id: " + linkId));
    }

    @Override
    public Link getLinkByIdForMerchant(final Long linkId, final Long merchantId) {
        final var linkOptional = this.linkRepository.findByIdAndMerchantId(linkId, merchantId);
        return linkOptional.orElseThrow(() -> new NotFoundException("could not find the link with id: " + linkId));
    }

    @Override
    public Link updateLink(final LinkDo linkDo) {
        final var linkOptional = this.linkRepository.findByIdAndMerchantId(linkDo.getId(), linkDo.getMerchantId());
        final var link = linkOptional.orElseThrow(() -> new NotFoundException("could not find the link with id: " + linkDo.getId()));

        link.setDescription(linkDo.getDescription());
        link.setMerchantId(linkDo.getMerchantId());
        link.setAmount(linkDo.getAmount());
        link.setCurrency(linkDo.getCurrency());
        link.setName(linkDo.getName());
        link.setRequireCustomerEmail(linkDo.getRequireCustomerEmail());
        link.setRequireCustomerName(linkDo.getRequireCustomerName());
        link.setUpdatedAt(Instant.now());

        this.linkRepository.save(link);
        return link;
    }

    @Override
    public void deleteLinkById(final Long linkId, final Long merchantId) {
        final var linkOptional = this.linkRepository.findByIdAndMerchantId(linkId, merchantId);
        linkOptional.ifPresent(this.linkRepository::delete);
    }
}
