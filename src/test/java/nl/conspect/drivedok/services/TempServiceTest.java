package nl.conspect.drivedok.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TempServiceTest {

    TempService tempService = new TempService();

    @Test
    void takeCover() {
        var result = tempService.triggerCodeCoverage();
        Assertions.assertEquals(
                "Ik tel tot 0Ik tel tot 1Ik tel tot 2Ik tel tot 3Ik tel tot 4",
                result);
    }
}
