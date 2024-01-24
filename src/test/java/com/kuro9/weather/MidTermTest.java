package com.kuro9.weather;

import com.kuro9.weather.repository.MidTermRepository;
import com.kuro9.weather.service.MidTermCacheProxy;
import com.kuro9.weather.service.MidTermInterface;
import com.kuro9.weather.service.MidTermService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MidTermTest {
    private final String testRegionId = "11B00000";
    @Autowired
    private MidTermInterface mid;
    @Autowired
    private MidTermService midService;
    @Autowired
    private MidTermCacheProxy midTermCacheProxy;
    @Autowired
    private MidTermRepository repo;


    @Test
    public void serviceMidTerm() {
        boolean result = false;
        try {
            System.out.println(mid.readMidTermLog(testRegionId));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void invalidRegId() {
        assertThrows(NoSuchElementException.class, () -> {
            mid.readMidTermLog("hello");
        });
    }

    @Test
    public void apiServiceTest() {
        boolean result = false;
        try {
            System.out.println(midService.readMidTermLog(testRegionId));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void dbProxyTest() {
        MidTermCacheProxy testObj = new MidTermCacheProxy(null, repo);
        boolean result = false;
        try {
            midTermCacheProxy.readMidTermLog(testRegionId);

            System.out.println(testObj.readMidTermLog(testRegionId));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }
}
