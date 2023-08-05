package com.spring.board.controller;


import com.spring.board.dto.GuestbookDTO;
import com.spring.board.dto.PageRequestDTO;
import com.spring.board.dto.PageResultDTO;
import com.spring.board.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String list() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list") //실제 보여지는 화면에 페이징 기능을 반영
    public void list(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        /** 실제로 model에 추가되는 데이터 :PageResultDTO*/
        //model을 이용해 GuestBookServiceImple에서 반환하는 PageResultDTO를 result 라는 이름으로 전달
        log.info("list 목록 ===============" + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    // 화면을 보여준다
/*    @GetMapping("/register")
    public void register(){

    }
    // 처리 후 목록 페이지로 이동
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){

        // 새로 추가된 엔티티의 번호
        Long gno = guestbookService.register(dto);

        // addFlashAttribute() : 단 한번만 데이터를 전달하는 용도로 사용한다
        // redirectAttributes : 한 번만 화면에서 "msg"라는 이름의 변수를 사용할 수 있도록 처리
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }*/
}

