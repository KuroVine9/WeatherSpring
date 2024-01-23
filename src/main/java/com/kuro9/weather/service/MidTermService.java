package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.MidTermDto;
import com.kuro9.weather.entity.MidTerm;
import com.kuro9.weather.entity.id.MidTermPK;
import com.kuro9.weather.repository.MidTermRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * db에 존재하는 데이터를 조작하는 클래스.
 */
@Service
public class MidTermService extends MidTermInterface {
    private final MidTermRepository midTermRp;

    public MidTermService(MidTermRepository mid) {
        midTermRp = mid;
    }

    @Transactional
    public void createMidTermLog(MidTermDto midTerm) {
        midTermRp.save(new MidTerm(midTerm.getRegId(), midTerm.getTmFc(), midTerm.toData()));
    }

    @Override
    public MidTermDto readMidTermLog(String regId) {
        String tmFc = getMidTermTimeCode();
        MidTermPK pk = new MidTermPK(regId, tmFc);
        MidTerm midTerm = midTermRp.findById(pk).orElseThrow();
        return new MidTermDto(regId, tmFc, midTerm.toData());
    }
}
