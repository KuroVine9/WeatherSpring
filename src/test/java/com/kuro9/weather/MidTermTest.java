package com.kuro9.weather;

import com.kuro9.weather.entity.MidTerm;
import com.kuro9.weather.entity.id.MidTermPK;
import com.kuro9.weather.repository.MidTermRepository;
import com.kuro9.weather.service.MidTermCacheProxy;
import com.kuro9.weather.service.MidTermService;
import com.kuro9.weather.service.interfaces.MidTermInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(NoSuchElementException.class, () -> mid.readMidTermLog("hello"));
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
        String timeCode = ReflectionTestUtils.invokeMethod(midTermCacheProxy, "getTimeCode");
        try {

            MidTerm data = new MidTerm(
                    new MidTermPK("testreg", timeCode),
                    404, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", "");
            repo.save(data);


            var output = testObj.readMidTermLog("testreg");


            assertEquals(404, output.getRnSt3Am());
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            repo.deleteById(new MidTermPK("testreg", timeCode));
        }

        assertTrue(result);
    }
}
