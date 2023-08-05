package com.spring.board.service;

import com.spring.board.dto.GuestbookDTO;
import com.spring.board.dto.PageRequestDTO;
import com.spring.board.dto.PageResultDTO;
import com.spring.board.entity.Guestbook;
import com.spring.board.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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

    /**entityToDTO()를 이용해서 java.util.Function을 생성하고 PageResultDTO로 구성하는 부분*/
    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result = repository.findAll(pageable);
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}
