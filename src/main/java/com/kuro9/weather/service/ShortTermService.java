package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.ShortTermDto;
import com.kuro9.weather.repository.ShortTermRepository;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShortTermService extends ShortTermInterface {
    private final ShortTermRepository repo;

    public ShortTermService(ShortTermRepository repo) {
        this.repo = repo;
    }

    @Override
    public ShortTermDto readShortTermLog(int nx, int ny) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException {
        String baseDate = getBaseDate(), baseTime = getBaseTime();
        var result = repo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNy(baseDate, baseTime, nx, ny);
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
