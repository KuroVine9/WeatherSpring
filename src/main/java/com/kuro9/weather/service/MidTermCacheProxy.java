package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.MidTermDto;
import com.kuro9.weather.entity.MidTerm;
import com.kuro9.weather.entity.id.MidTermPK;
import com.kuro9.weather.repository.MidTermRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * db에 저장되어 있는지 체크 후 없다면 원래 동작 시행
 */
@Primary
@Service
public class MidTermCacheProxy extends MidTermInterface {

    private final MidTermService midTermService;
    private final MidTermRepository repo;

    public MidTermCacheProxy(MidTermService midTermService, MidTermRepository repo) {
        this.midTermService = midTermService;
        this.repo = repo;
    }

    @Transactional
    public void storeMidTermData(MidTermDto midTerm) {
        repo.save(new MidTerm(midTerm.getRegId(), midTerm.getTmFc(), midTerm.toData()));
    }

    @Override
    public MidTermDto readMidTermLog(String regId) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException {
        String tmFc = getTimeCode();
        MidTermPK pk = new MidTermPK(regId, tmFc);

        Optional<MidTerm> optional = repo.findById(pk);
        if (optional.isEmpty()) {
            var result = midTermService.readMidTermLog(regId);
            storeMidTermData(result);
            return result;
        }

        MidTerm midTerm = optional.get();
        return new MidTermDto(regId, tmFc, midTerm.toData());

    }
}
