package com.spring.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spring.board.entity.Guestbook;
import com.spring.board.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Test //Querydsl 테스트 - 단일 항목 테스트 (title에 1이 포함된 내용을 출력)
    public void testQuery1() {

        //페이징 처리
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        //findAll -> JpaRepository but querydsl1 -> QuerydslPredicateExecutor 사용

        //Predicate
        //Q도메인 클래스를 사용하면 엔티티 클래스에 선언된 필드를 변수로 사용할 수 있음
        QGuestbook qGuestbook = QGuestbook.guestbook;

        //BooleanBuilder : 쿼리의 where 문에 들어가는 조건을 넣어주는 컨테이너
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //title like %1% 쿼리를 코드로 구현
        BooleanExpression expression = qGuestbook.title.contains("1");

        //만들어진 쿼리 조건들의 결합
        booleanBuilder.and(expression);

        //BooleanBuilder는 guestbookRepository에 추가된 QuerydslPredicateExecutor 인터페이스의 findAll()을 사용
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable
        );
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

    }
}