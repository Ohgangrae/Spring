package com.example.ex02.controller;

import com.example.ex02.domain.vo.ExampleVO;
import com.example.ex02.domain.vo.ScrollVO;
import com.example.ex02.domain.vo.TaskVO;
import com.example.ex02.domain.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

@Controller
@RequestMapping("/ex/*")
@Slf4j
public class ExampleController {
    //    value에 경로를 작성하고, method에 호출할 서블릿 메소드를 설정한다.
    @RequestMapping(value = "/example", method = {RequestMethod.GET, RequestMethod.POST})
    public void ex01() {
        log.info("ex01...............");
    }

    @GetMapping("")
    public void ex02() {
        log.info("ex02................");
    }

    @GetMapping("/ex03")
    public String ex03(ExampleVO exampleVO) {
        log.info("------------------------------");
        log.info(exampleVO.toString());
        log.info("------------------------------");
        return "ex03";
    }

//    실습
//    domain.vo.TaskVO 선언
//    int num, int kor, int eng, int math 선언

    //    "ex/ex04" URL 요청에 실행될 메소드 선언
//    GET 방식
//    ex04.html 선언
//    총점과 평균 출력(타임리프 구글링)
    @GetMapping("/ex04")
    public String ex04(TaskVO taskVO) {
        log.info("-----------------------------");
        log.info(taskVO.toString());
        log.info("-----------------------------");
        return "ex04";
    }

//    실습
//    아이디와 비밀번호를 입력받은 후 아이디가 admin일 경우 admin.html로 이동
//    아이디가 user일 경우 user.html로 이동
//    메소드는 리턴타입을 void로 선언한다.

//    - admin.html : 관리자 페이지 출력
//    - user.html : 일반 회원 페이지 출력

//    경로 : localhost:10002/ex/login
//    action : ex/login -> 결과 : /ex/ex/login
//    action : /ex/login -> 결과 : /ex/login

    @GetMapping("/login")
    public void login() {
    }

    @PostMapping("/login")
    public String login(UserVO userVO) {
        return userVO.getUserId().equals("admin") ? "admin" : "user";
    }

//    실습
//    이름을 입력하고 출근 또는 퇴근 버튼을 클릭한다.
//    출근 시간은 09:00이며, 퇴근 시간 18:00이다.
//    출근 버튼 클릭 시 9시가 넘으면 지각으로 처리하고,
//    퇴근 버튼 클릭 시 18시 전이라면 퇴근이 아닌 업무시간으로 처리한다.
//    자바에서 현재 시간 구하기
//     - SimpleDateFormat 생성자에 전달받은 날짜 형식을 작성한다.
//     - parse() 메소드에 작성한 형식에 맞는 문자열을 전달하면 Date타입으로 변한다.
//     - format() 메소드에 Date 타입을 전달하면 문자열로 변한다.

//    Calendar.getInstance() : 현재 시간
//    시간, 분만 가져오기 - get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE)

//    - 모든 html은 work 경로 안에 생성하며 아래와 같이 분기 별로 html문서 한 개씩 작성한다.
//    - getToWork.html
//    - leaveWork.html
//    - late.html
//    - work.html

    @GetMapping("/checkIn")
    public String checkIn() {
        return "/work/checkIn";
    }

    @GetMapping("/getToWork")
    public String getToWork(String name, Model model) {
        log.info("***************************************");
        log.info(name);
        log.info("***************************************");
        SimpleDateFormat sdf = new SimpleDateFormat("HH시 mm분");
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean lateCondition = hour >= 9 && minute > 0;

        model.addAttribute("name", name);
        model.addAttribute("now", sdf.format(c.getTime()));

        return lateCondition ? "work/late" : "work/getToWork";
    }

    @GetMapping("/leaveWork")
    public String leaveWork(String name, Model model) {
        log.info("***************************************");
        log.info(name);
        log.info("***************************************");
        SimpleDateFormat sdf = new SimpleDateFormat("aaa hh시 mm분");
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean leaveWorkCondition = hour >= 18 && minute > 0;

        model.addAttribute("name", name);
        model.addAttribute("now", sdf.format(c.getTime()));

        return leaveWorkCondition ? "work/leaveWork" : "work/work";
    }

//    실습
//    무기를 강화하기 위해서 아래에 있는 강화 주문서를 사용할 수 있다.

//    10% 공격력 주문서 : 공격력 + 80
//    60% 공격력 주문서 : 공격력 + 40
//    100% 공격력 주문서 : 공격력 + 10

//    한 번만 강화할 수 있으며, 10% 확률로 대성공을 한다.
//    대성공 시 해당 주문서 공격력의 5배가 증가한다.

//    강화하기 버튼을 눌렀을 때 알맞는 결과를 출력한다.

//    ScrollVO 클래스 선언
//    10%, 60%, 100% 주문서의 공격력 수치를 저장한다.
//    기본 생성자를 호출 했을 떄에는 위에 작성된 공격력 수치를 기본 값으로 설정하고,
//    만약 새로운 값을 받게 되면 해당 공격력 수치로 변경되도록 생성자를 오버로딩한다.

    @GetMapping("/upgrade")
    public String upgrade() {
        return "upgrade/form";
    }

    @PostMapping("/upgrade")
    public String upgrade(String choice, Model model) {
        log.info("*************** choice :::" + choice);
        ScrollVO scrollVO = new ScrollVO();

        int strength = 0;
        boolean check = false;
        switch (Integer.parseInt(choice)) {
            case 0: //10%
                check = getChance(10);
                strength = scrollVO.getScroll10();
                break;
            case 1: //60%
                check = getChance(60);
                strength = scrollVO.getScroll60();
                break;
            case 2: //100%
                check = getChance(100);
                strength = scrollVO.getScroll100();
                break;
        }
        if (!check) {
            return "upgrade/fail";
        }
        if (getChance(100)) {
            strength *= 5;
            model.addAttribute("strength", strength);
            return "upgrade/superSuccess";
        }
        model.addAttribute("strength", strength);
        return "upgrade/success";
    }

    public boolean getChance(int rating) {
        Random r = new Random();
        int[] numbers = new int[10];
        int index = r.nextInt(numbers.length);
        for (int i = 0; i < rating / 10; i++) {
            numbers[i] = 1;
        }

        return numbers[index] == 1;
    }

//    실습
//    사용자가 입력한 바코드 번호에 알맞는 상품명을 전달한다.

    @GetMapping("/market")
    public String goMarket() {
        return "product/market";
    }

    @PostMapping("/check")
    public String check(String barcode, Model model) {
        String productName = null;
        switch (barcode) {
            case "4383927":
                productName = "오징어 땅콩";
                break;
            case "0832147":
                productName = "초코 우유";
                break;
            case "9841631":
                productName = "벌꿀 피자";
                break;
            case "5587578":
                productName = "샌드 위치";
                break;
        }
        model.addAttribute("productName", productName);
        return "product/cashier";
    }


//    실습

//    아이디 : apple
//    비밀번호 : banana
//    로그인 성공 시 apple님 환영합니다.
//    로그인 실패 시 로그인 실패

    @GetMapping("/userLogin")
    public String userLogin() {
        return "login/login";
    }

    @PostMapping("/userLogin")
    public String userLogin(UserVO userVO) {
        if (userVO.getUserId().equals("apple")) {
            if (userVO.getUserPw().equals("banana")) {
                return "login/success";
            }
        }
        return "login/fail";
    }

    //    노래방 기계 제작
//    사용자의 점수에 따른 알맞는 메세지 출력
    @GetMapping("/song")
    public String goSong() {
        return "song/songbox";
    }

    @PostMapping("/song")
    public String sendMessage(String score, Model model) {
        String msg = null;
        if (Integer.parseInt(score) > 50) {
            msg = "가수의 실력이군요";
        } else {
            msg = "소질이 없어요.";
        }
        model.addAttribute("msg", msg);
        return "song/result";
    }

    @GetMapping("/info")
//    @ModelAttribute("KEY") Object obj
//    전달받은 파라미터를 화면쪽으로 보낼 때 쉽고 간편하게 사용할 수 있다.
//    여러 개의 데이터를 보낼 때에는 Model 데이터 전달자를 사용하고,
//    2개 이하의 데이터를 보낼 때에는 @ModelAttribute()를 사용하는 것이 좋다.
    public void getInfo(@ModelAttribute("name") String name, @ModelAttribute("age") Integer age) {
    }

    @GetMapping("/datas")
//    동일한 이름의 파라미터가 여러 개 들어올 때에는 배열 또는 List로 매개변수를 설정한다.
//    이 때 동일한 이름으로 받아야 하기 때문에 @RequestParam("KEY")을 사용해서 전달받을 데이터의 KEY값을 지정해준다.
//    KEY 파라미터명이 전달되면 뒤에 있는 매개변수로 들어간다.
    public void getDatas(@RequestParam("data") ArrayList<Integer> datas) {
        log.info(String.valueOf(datas.size()));
        datas.stream().map(String::valueOf).forEach(log::info);
    }

    @GetMapping("/different")
//    파라미터 명과 매개변수 명이 다르면 직접 지정해준다.
    public void getData(@RequestParam("data") String name) {

    }
}



















