package OxPay.Payment.controller;

import OxPay.Payment.enums.OrderState;
import OxPay.Payment.exception.BadRequestException;
import OxPay.Payment.factory.LinkFactory;
import OxPay.Payment.model.dto.*;
import OxPay.Payment.security.model.AuthUserDetails;
import OxPay.Payment.service.LinkService;
import OxPay.Payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/links")
public class LinkController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkController.class);

    private final LinkService linkService;
    private final OrderService orderService;

    @PostMapping()
    public CreateLinkResponse createLinkForMerchant(Authentication authentication, @Valid @RequestBody final CreateLinkRequest createLinkRequest) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        LOGGER.info("user merchantId: {}", userDetails.getMerchantId());
        LOGGER.info("user id: {}", userDetails.getId());

        final var linkDo = LinkFactory.createLink(createLinkRequest, userDetails.getMerchantId());
        final var linkId = this.linkService.createLink(linkDo);

        return new CreateLinkResponse(linkId);
    }

    @GetMapping()
    public GetLinksResponse getLinksForMerchant(Authentication authentication) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        return new GetLinksResponse(this.linkService.getLinks(userDetails.getMerchantId()));
    }

    @GetMapping("/{linkId}/orders")
    public GetLinkOrdersResponse getLinkOrdersForMerchant(Authentication authentication, @PathVariable final Long linkId) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        final var link = this.linkService.getLinkById(linkId);
        if (!link.getMerchantId().equals(userDetails.getMerchantId())) {
            throw new BadRequestException("Cannot find Link with Id " + linkId);
        }

        final var orders = this.orderService.getOrdersByLinkId(linkId);
        final var data = new GetLinkOrdersResponse.GetLinkOrdersResponseData(orders.size(), orders);

        return new GetLinkOrdersResponse(data);
    }

    @GetMapping("/getTestLinks")
    public TestGetLinksResponse getLinksForMerchantTest(Authentication authentication) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        final var links = this.linkService.getLinks(userDetails.getMerchantId());
        final var data = new TestGetLinksResponse.TestGetLinksResponseData(links.size(), links);
        return new TestGetLinksResponse(data);
    }

    @GetMapping("/{linkId}")
    public GetLinkResponse getLinkById(Authentication authentication, @PathVariable("linkId") final Long linkId) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        return new GetLinkResponse(this.linkService.getLinkByIdForMerchant(linkId, userDetails.getMerchantId()));
    }

    @PutMapping("/{linkId}")
    public UpdateLinkResponse updateLinkById(Authentication authentication, @PathVariable("linkId") final Long linkId, @RequestBody final UpdateLinkRequest updateLinkRequest) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        final var linkDo = LinkFactory.updateLink(updateLinkRequest, linkId, userDetails.getMerchantId());
        return new UpdateLinkResponse(this.linkService.updateLink(linkDo));
    }

    @DeleteMapping("/{linkId}")
    public void deleteLinkById(Authentication authentication, @PathVariable("linkId") final Long linkId) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        this.linkService.deleteLinkById(linkId, userDetails.getMerchantId());
    }

    @GetMapping("/orders")
    public GetLinkOrdersResponse getMerchantOrders(Authentication authentication) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();

        final var orders = this.orderService.getMerchantOrderByStatus(userDetails.getMerchantId(), null);
        final var data = new GetLinkOrdersResponse.GetLinkOrdersResponseData(orders.size(), orders);
        return new GetLinkOrdersResponse(data);
    }
}
