package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.ShortTermDto;
import com.kuro9.weather.entity.ShortTerm;
import com.kuro9.weather.entity.id.ShortTermPK;
import com.kuro9.weather.repository.ShortTermRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShortTermService extends ShortTermInterface {
    private final ShortTermRepository repo;

    public ShortTermService(ShortTermRepository repo) {
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
    public ShortTermDto readShortTermLog(int nx, int ny, int hourOffset) throws NoSuchElementException {
        String baseDate = getBaseDate(), baseTime = getBaseTime(), fcstDate = getOffsetDate(hourOffset), fcstTime = getOffsetTime(hourOffset);
        var result = repo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNyAndFcstDateAndFcstTime(baseDate, baseTime, nx, ny, fcstDate, fcstTime);
        if (result == null || result.isEmpty()) {
            throw new NoSuchElementException();
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
