package com.spring.board.service;

import com.spring.board.dto.GuestbookDTO;
import com.spring.board.entity.Guestbook;
import com.spring.board.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service //Bean으로 처리되도록 @Service 어노테이션!
@Log4j2
@RequiredArgsConstructor //의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;  //반드시  final로 선언해야 함

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ DTO에서 Entity로 변환  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);
        return entity.getGno();
    }
}
