package nl.conspect.drivedok.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TempService {

    Logger logger = LoggerFactory.getLogger(TempService.class);

    private static String DELETE_ME = "Ik tel tot ";

    // should trigger code coverage
    public void triggerCodeCoverage() {
        for (int i = 0; i < 5; i++) {
            logger.info(DELETE_ME.concat(String.valueOf(i)));
        }
    }
}
