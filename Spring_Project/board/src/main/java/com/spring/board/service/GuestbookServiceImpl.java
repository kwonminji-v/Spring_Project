package com.spring.board.service;


import com.spring.board.dto.GuestbookDTO;
import com.spring.board.entity.Guestbook;
import com.spring.board.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        // service interface에 구현된 dtoToEntity 활용

        Guestbook entity = dtoToEntity(dto);
        repository.save(entity);
        return entity.getGno();
    }
}
