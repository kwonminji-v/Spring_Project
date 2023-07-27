package com.spring.board.service;

import com.spring.board.dto.GuestbookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GuestbookServiceTest {

    @Autowired private GuestbookService guestbookService;

    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title....")
                .content("Sample Content...")
                .writer("User0")
                .build();
        System.out.println(guestbookService.register(guestbookDTO));
    }

}