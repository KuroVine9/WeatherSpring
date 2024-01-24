package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.ShortTermDto;
import com.kuro9.weather.entity.ShortTerm;
import com.kuro9.weather.entity.id.ShortTermPK;
import com.kuro9.weather.repository.ShortTermRepository;
import com.kuro9.weather.service.interfaces.ShortTermInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * db에 저장되어 있는지 체크 후 없다면 원래 동작 시행
 */
@Primary
@Service
public class ShortTermCacheProxy extends ShortTermInterface {

    private final ShortTermService shortTermService;
    private final ShortTermRepository repo;

    public ShortTermCacheProxy(ShortTermService shortTermService, ShortTermRepository repo) {
        this.shortTermService = shortTermService;
        this.repo = repo;
    }

    @Transactional
    public void storeShortTermData(ShortTermDto shortTerm) {
        var keyBuilder = ShortTermPK.builder()
                .baseDate(shortTerm.getBaseDate())
                .baseTime(shortTerm.getBaseTime())
                .nx(shortTerm.getNx())
                .ny(shortTerm.getNy());
        for (ShortTermDto.ShortTermCategory data : shortTerm.getItems()) {
            repo.save(new ShortTerm(
                    keyBuilder.category(data.getCategory()).build(),
                    data.getFcstDate(),
                    data.getFcstTime(),
                    data.getFcstValue()
            ));
        }
    }

    @Override
    public ShortTermDto readShortTermLog(int nx, int ny, int hourOffset) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException {
        String baseTime = getBaseTime(), baseDate = getBaseDate(), fcstTime = getOffsetTime(hourOffset), fcstDate = getOffsetDate(hourOffset);

        var result = repo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNyAndFcstDateAndFcstTime(baseDate, baseTime, nx, ny, fcstDate, fcstTime);

        if (result == null || result.isEmpty()) { // DB 데이터 없음
            var apiResult = shortTermService.readShortTermLog(nx, ny, hourOffset);
            storeShortTermData(apiResult);
            return apiResult;
        }

        List<ShortTermDto.ShortTermCategory> items = result.stream()
                .map(item ->
                        new ShortTermDto.ShortTermCategory(
                                item.getId().getCategory(),
                                item.getFcstDate(),
                                item.getFcstTime(),
                                item.getFcstValue())
                ).toList();

        return new ShortTermDto(baseDate, baseTime, nx, ny, items);
    }
}
