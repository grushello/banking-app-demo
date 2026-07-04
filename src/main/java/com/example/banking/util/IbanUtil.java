package com.example.banking.util;

import java.util.UUID;

public class IbanUtil {

    private IbanUtil() {
        throw new IllegalStateException("Utility class");
    }

    // to Generate IBAN
    public static String generateIban() {
        return "LV" + UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 18);
    }

    // to Mask IBAN
    public static String maskIban(String iban) {
        if (iban == null || iban.length() < 8) {
            return iban;
        }
        return iban.substring(0, 4)
                + "*".repeat(iban.length() - 8)
                + iban.substring(iban.length() - 4);
    }
}
