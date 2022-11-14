package OxPay.Payment.controller;

import OxPay.Payment.exception.NotFoundException;
import OxPay.Payment.model.entity.Merchant;
import OxPay.Payment.persistence.MerchantRepository;
import OxPay.Payment.security.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/merchant")
public class MerchantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantController.class);

    private final MerchantRepository merchantRepository;

    @GetMapping()
    public Merchant getMerchant(Authentication authentication) {
        final var userDetails = (AuthUserDetails) authentication.getPrincipal();
        LOGGER.info("Merchant id {}", userDetails.getMerchantId());
        return this.merchantRepository.findById(userDetails.getMerchantId()).orElseThrow(() -> new NotFoundException("Cannot find merchant."));
    }
}
