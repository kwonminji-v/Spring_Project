package com.spring.board.repository;

import com.spring.board.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired private GuestbookRepository guestbookRepository;

    @Test //더미데이터 만들기
    public void insertDumies() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            guestbookRepository.save(guestbook);
        });
    }

    @Test
    public void updateTest() {
        //300번 선택
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        //존재하는 번호로 테스트 , isPresent() : !=null과 같은 의미
        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }
}