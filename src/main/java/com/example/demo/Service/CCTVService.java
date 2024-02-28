package com.example.demo.Service;

import com.example.demo.domain.CCTV;
import com.example.demo.repository.CCTVMapper;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CCTVService {

    @Autowired
    CCTVMapper cctvMapper;
    private List<CCTV> cctvList = new ArrayList<>();

    @PostConstruct
    public void init() {
        cctvList.add(new CCTV(1, "cctv1", 36.104359, 129.385868, 3));
        cctvList.add(new CCTV(2, "cctv2", 36.104959, 129.385698, 2));
//        cctvList.add(new CCTV(3, "cctv3", 36.104459, 129.386868, 3));
//        cctvList.add(new CCTV(4, "cctv4", 36.103359, 129.386668, 1));
    }

    public List<CCTV> getAllCCTV() {
        System.out.println(cctvMapper.getCCTV().get(0).getxCoordinate()); // 여기 잘확인할것!!
//        cctvMapper.insertCCTV(new CCTV(3, "pi6", 2523.3, 234.4, 230));
        return cctvList;
    }

}
