package nl.conspect.drivedok.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TempServiceTest {

    TempService tempService = new TempService();

    @Test
    void takeCover() {
        Assertions.assertEquals("", tempService.triggerCodeCoverage());
    }
}