package OxPay.Payment.service;

import OxPay.Payment.model.domain.LinkDo;
import OxPay.Payment.model.entity.Link;

import java.util.List;

public interface LinkService {
    Long createLink(final LinkDo linkDo);
    List<Link> getLinks(final Long merchantId);
    Link getLinkById(final Long linkId);
    Link getLinkByIdForMerchant(final Long linkId, final Long merchantId);
    Link updateLink(final LinkDo linkDo);
    void deleteLinkById(final Long linkId, final Long merchantId);
}
