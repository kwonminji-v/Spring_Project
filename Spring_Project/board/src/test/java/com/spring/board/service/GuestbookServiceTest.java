package com.spring.board.service;

import com.spring.board.dto.GuestbookDTO;
import com.spring.board.dto.PageRequestDTO;
import com.spring.board.dto.PageResultDTO;
import com.spring.board.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class GuestbookServiceTest {

    @Autowired private GuestbookService service;

    @Test
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("예시 제목입니다....")
                .content("예시 내용입니다...")
                .writer("예를 들면 이사람이다.")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV = " + resultDTO.isPrev());
        System.out.println("Next = " + resultDTO.isNext());
        System.out.println("Total = " + resultDTO.getTotalPage());
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("==========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}