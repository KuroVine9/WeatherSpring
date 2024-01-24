package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.UltraTermDto;
import com.kuro9.weather.dataclass.UltraTermDto.UltraTermCategory;
import com.kuro9.weather.entity.UltraTerm;
import com.kuro9.weather.entity.id.UltraTermPK;
import com.kuro9.weather.repository.UltraTermRepository;
import com.kuro9.weather.service.interfaces.UltraTermInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.NoSuchElementException;

@Primary
@Service
public class UltraTermCacheProxy extends UltraTermInterface {

    private final UltraTermService service;
    private final UltraTermRepository repo;

    public UltraTermCacheProxy(UltraTermService service, UltraTermRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @Transactional
    public void storeUltraTermData(UltraTermDto dto) {
        var keyBuilder = UltraTermPK.builder()
                .baseDate(dto.getBaseDate())
                .baseTime(dto.getBaseTime())
                .nx(dto.getNx())
                .ny(dto.getNy());

        for (var data : dto.getItems()) {
            repo.save(new UltraTerm(
                    keyBuilder.category(data.getCategory()).build(),
                    data.getFcstDate(),
                    data.getFcstTime(),
                    data.getFcstValue()
            ));
        }
    }


    @Override
    public UltraTermDto readUltraTermLog(int nx, int ny) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException {
        String baseTime = getBaseTime(), baseDate = getBaseDate();
        var dbResult = repo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNy(baseDate, baseTime, nx, ny);

        if (dbResult == null || dbResult.isEmpty()) {
            var apiResult = service.readUltraTermLog(nx, ny);
            storeUltraTermData(apiResult);
            return apiResult;
        }

        List<UltraTermCategory> items = dbResult.stream().map(item ->
                new UltraTermCategory(
                        item.getId().getCategory(),
                        item.getFcstDate(),
                        item.getFcstTime(),
                        item.getFcstValue()
                )
        ).toList();

        return new UltraTermDto(baseDate, baseTime, nx, ny, items);
    }
}
