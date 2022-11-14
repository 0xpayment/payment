package OxPay.Payment.security.model;

import com.google.common.base.Strings;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Authority {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_MASTER("MASTER"),
    ROLE_OPERATOR("OPERATOR")
    ;

    private final String value;

    Authority(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static Set<Authority> getSetFromString(final String authoritiesString) {
        final var authorityArray = Optional.ofNullable(authoritiesString).orElse("").split(",");
        return Stream.of(authorityArray)
                .filter(s -> !Strings.isNullOrEmpty(s))
                .map(Authority::valueOf)
                .collect(Collectors.toSet());
    }

    public static String getStringFromSet(final Set<Authority> authoritySet) {
        return authoritySet.stream().map(Authority::toString).collect(Collectors.joining(","));
    }
}
