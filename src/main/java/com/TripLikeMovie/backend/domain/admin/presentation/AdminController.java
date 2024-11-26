package com.TripLikeMovie.backend.domain.admin.presentation;

import com.TripLikeMovie.backend.domain.admin.service.AdminService;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.movie.service.MovieService;
import com.TripLikeMovie.backend.domain.post.domain.vo.PostInfoVo;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.AllPostResponse;
import com.TripLikeMovie.backend.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final PostService postService;
    private final AdminService adminService;
    private final MovieService movieService;
    private final ObjectMapper objectMapper;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/post")
    public String getAllPosts(Model model, HttpServletRequest request) {

        Member admin = (Member)request.getSession().getAttribute("admin");

        // 로그인 상태 확인
        if (admin == null) {  // sessionUser는 로그인된 사용자 정보
            String redirectUri = request.getRequestURL().toString(); // 원래 요청 URL을 가져옴
            request.getSession().setAttribute("redirectUri", redirectUri); // 세션에 저장
            return "redirect:/api/v1/admin/login";
        }

        List<AllPostResponse> posts = postService.findAll();

        model.addAttribute("posts", posts);
        return "admin/posts";
    }


    @GetMapping("/post/{postId}")
    public String getPost(@PathVariable Integer postId, Model model, HttpServletRequest request) {

        Member admin = (Member)request.getSession().getAttribute("admin");

        // 로그인 상태 확인
        if (admin == null) {  // sessionUser는 로그인된 사용자 정보
            String redirectUri = request.getRequestURL().toString(); // 원래 요청 URL을 가져옴
            request.getSession().setAttribute("redirectUri", redirectUri); // 세션에 저장
            return "redirect:/api/v1/admin/login";
        }

        PostInfoVo postInfoVo = postService.findById(postId).getPostInfoVo();

        // ObjectMapper를 사용하여 imageUrls를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String imageUrlsJson = "";
        try {
            imageUrlsJson = objectMapper.writeValueAsString(postInfoVo.getImageUrls());
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("post", postInfoVo);
        model.addAttribute("imageUrlsJson", imageUrlsJson); // JSON 문자열을 JSP로 전달
        return "admin/post";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
        HttpServletRequest request, Model model) {

        // 로그인 처리 로직
        Member admin = adminService.login(email, password);

        if (admin != null) {
            // 로그인 성공 후, 원래 요청한 URL로 리다이렉트
            request.getSession().setAttribute("admin", admin);

            // 세션에서 redirectUri 가져오기
            String redirectUri = (String) request.getSession().getAttribute("redirectUri");

            System.out.println("Redirect URI from session: " + redirectUri);

            if (redirectUri != null && !redirectUri.isEmpty()) {
                return "redirect:" + redirectUri;  // 원래 요청한 URL로 리다이렉트
            }

            return "redirect:/";  // 기본 페이지로 리다이렉트
        }

        model.addAttribute("error", "잘못된 아이디 또는 비밀번호입니다.");
        return "admin/login";  // 로그인 실패 시 로그인 페이지로 돌아감
    }


    @PostMapping("/post/{postId}")
    public String deletePost(@PathVariable Integer postId) {

        adminService.deletePost(postId);

        return "redirect:/api/v1/admin/post";
    }

    @GetMapping("/movie")
    public String movieRegisterForm( HttpServletRequest request) {
        Member admin = (Member)request.getSession().getAttribute("admin");
        if (admin == null) {
            String redirectUri = request.getRequestURL().toString();
            request.getSession().setAttribute("redirectUri", redirectUri);
            return "redirect:/api/v1/admin/login";
        }
        return "admin/movieForm";
    }

    @PostMapping(value = "/movie", consumes = "multipart/form-data")
    public String createMovie(
        @RequestPart("movieData") String movieData,
        @RequestPart("moviePoster") MultipartFile moviePoster,
        Model model) {

        try {
            movieService.duplicateTitle(movieData);
        } catch (Exception e) {
            // 예외 메시지를 model에 추가하여 뷰로 전달
            model.addAttribute("errorMessage", "영화 제목이 이미 존재합니다.");
            return "admin/movieForm"; // 예외가 발생한 경우 영화 등록 페이지로 돌아감
        }

        // 영화 생성
        movieService.createMovie(movieData, moviePoster);

        return "redirect:/api/v1/admin/post";
    }

    @GetMapping("/movies")
    public String getAllMovies(Model model, HttpServletRequest request) {
        Member admin = (Member)request.getSession().getAttribute("admin");
        if (admin == null) {
            String redirectUri = request.getRequestURL().toString();
            request.getSession().setAttribute("redirectUri", redirectUri);
            return "redirect:/api/v1/admin/login";
        }

        List<MovieInfoVo> movies = movieService.findByTitle(null);
        model.addAttribute("movies", movies);
        return "admin/movies";
    }


}
