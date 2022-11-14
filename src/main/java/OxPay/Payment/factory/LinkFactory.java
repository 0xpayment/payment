package OxPay.Payment.factory;

import OxPay.Payment.model.Currency;
import OxPay.Payment.model.domain.LinkDo;
import OxPay.Payment.model.dto.CreateLinkRequest;
import OxPay.Payment.model.dto.UpdateLinkRequest;
import OxPay.Payment.model.entity.Link;

import java.math.BigDecimal;

public class LinkFactory {
    public static LinkDo createLink(final CreateLinkRequest createLinkRequest, final Long merchantId) {
        final LinkDo linkDo = new LinkDo();

        linkDo.setMerchantId(merchantId);
        linkDo.setDescription(createLinkRequest.getDescription());
        linkDo.setName(createLinkRequest.getName().orElse(null));
        linkDo.setAmount(createLinkRequest.getAmount().map(BigDecimal::new).orElse(null));
        linkDo.setCurrency(createLinkRequest.getCurrency().map(Currency::valueOf).orElse(Currency.USD));
        linkDo.setRequireCustomerName(createLinkRequest.getRequireCustomerEmail().orElse(false));
        linkDo.setRequireCustomerEmail(createLinkRequest.getRequireCustomerEmail().orElse(false));

        return linkDo;
    }

    public static LinkDo updateLink(final UpdateLinkRequest updateLinkRequest, final Long linkId, final Long merchantId) {
        final LinkDo linkDo = new LinkDo();

        linkDo.setId(linkId);
        linkDo.setMerchantId(merchantId);
        linkDo.setDescription(updateLinkRequest.getDescription());
        linkDo.setName(updateLinkRequest.getName().orElse(null));
        linkDo.setAmount(updateLinkRequest.getAmount().map(BigDecimal::new).orElse(null));
        linkDo.setCurrency(updateLinkRequest.getCurrency().map(Currency::valueOf).orElse(Currency.USD));
        linkDo.setRequireCustomerName(updateLinkRequest.getRequireCustomerEmail().orElse(false));
        linkDo.setRequireCustomerEmail(updateLinkRequest.getRequireCustomerEmail().orElse(false));

        return linkDo;
    }
}
