package com.kuro9.weather;

import com.kuro9.weather.dataclass.ShortTermDto;
import com.kuro9.weather.dataclass.UltraTermDto;
import com.kuro9.weather.repository.ShortTermRepository;
import com.kuro9.weather.repository.UltraTermRepository;
import com.kuro9.weather.service.ShortTermCacheProxy;
import com.kuro9.weather.service.UltraTermCacheProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class RepoQueryTest {

    @Autowired
    private ShortTermRepository shortRepo;
    @Autowired
    private UltraTermRepository ultraRepo;
    @Autowired
    private ShortTermCacheProxy shortService;
    @Autowired
    private UltraTermCacheProxy ultraService;

    @Test
    public void shortTermRepo() {
        ShortTermDto data = new ShortTermDto("20021024", "0000", 0, 0, new ArrayList<>() {{
            add(new ShortTermDto.ShortTermCategory(
                    "TST", "20021025", "0001", "testValue"
            ));
        }});
        shortService.storeShortTermData(data);

        var result =
                shortRepo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNyAndFcstDateAndFcstTime("20021024", "0000", 0, 0, "20021025", "0001");

        assertFalse(result.isEmpty());
        assertEquals("testValue", result.get(0).getFcstValue());
    }

    @Test
    public void ultraTermRepo() {
        UltraTermDto data = new UltraTermDto("20021024", "0000", 0, 0, new ArrayList<>() {{
            add(new UltraTermDto.UltraTermCategory(
                    "TST", "20001020", "0125", "testValue"
            ));
        }});
        ultraService.storeUltraTermData(data);

        var result =
                ultraRepo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNy("20021024", "0000", 0, 0);

        assertFalse(result.isEmpty());
        assertEquals("testValue", result.get(0).getFcstValue());
    }

}
