package com.TripLikeMovie.backend.domain.post.service;

import com.TripLikeMovie.backend.domain.like.domain.repository.LikeRepository;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.domain.post.domain.repository.PostRepository;
import com.TripLikeMovie.backend.domain.post.domain.vo.PostInfoVo;
import com.TripLikeMovie.backend.domain.post.presentation.dto.request.UpdatePostRequest;
import com.TripLikeMovie.backend.domain.post.presentation.dto.request.WritePostRequest;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.AllPostResponse;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.DetailPostResponse;
import com.TripLikeMovie.backend.global.error.exception.post.PostNotFoundException;
import com.TripLikeMovie.backend.global.error.exception.post.PostNotMatchMemberException;
import com.TripLikeMovie.backend.global.utils.image.ImageUtils;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageUtils imageUtils;
    private final MemberUtils memberUtils;
    private final LikeRepository likeRepository;

    @Override
    public void writePost(Member member, Movie movie, WritePostRequest postData,
        List<MultipartFile> images) {

        Post post = new Post(member, movie, postData.getContent(), postData.getLocationName(),
            postData.getLocationAddress());

        for (MultipartFile image : images) {
            String filePath = imageUtils.saveImage(image, "posts/");
            post.addFilePath(filePath);
        }
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findById(Integer postId) {
        return postRepository.findById(postId).orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllPostResponse> findAll() {
        // Post 엔티티를 조회하고 AllPostResponse로 변환
        List<Post> posts = postRepository.findAll();

        // 각 Post를 AllPostResponse로 변환하여 리스트에 담기
        return posts.stream().map(post -> {

            PostInfoVo postInfoVo = post.getPostInfoVo();

            AllPostResponse response = new AllPostResponse();
            response.setId(postInfoVo.getId()); // Post ID
            response.setFirstImageUrl(postInfoVo.getImageUrls() != null && !postInfoVo.getImageUrls().isEmpty()
                ? postInfoVo.getImageUrls().get(0) : null); // 첫 번째 이미지 URL (첫 번째 이미지가 없다면 null)
            response.setMovieTitle(postInfoVo.getMovieTitle()); // 영화 제목
            response.setMemberId(postInfoVo.getAuthorId()); // 게시글 작성자 ID
            response.setMemberNickname(postInfoVo.getAuthorNickname()); // 게시글 작성자 닉네임
            response.setMemberImageUrl(postInfoVo.getAuthorImageUrl()); // 게시글 작성자 이미지 URL

            return response; // 변환된 AllPostResponse 반환
        }).collect(Collectors.toList()); // List로 수집
    }

    @Override
    public void update(Integer postId, UpdatePostRequest updatePostRequest,
        List<MultipartFile> images) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> PostNotFoundException.EXCEPTION);

        checkPostMember(post);

        post.update(updatePostRequest.getContent(), updatePostRequest.getLocationName(), updatePostRequest.getLocationAddress());

        List<String> imageUrls = post.getImageUrls();

        for (String imageUrl : imageUrls) {
            imageUtils.deleteImage(imageUrl);
        }
        imageUrls.clear();

        for (MultipartFile image : images) {
            String filePath = imageUtils.saveImage(image, "posts/");
            post.addFilePath(filePath);
        }
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> PostNotFoundException.EXCEPTION);
        checkPostMember(post);

        List<String> imageUrls = post.getImageUrls();

        for (String imageUrl : imageUrls) {
            imageUtils.deleteImage(imageUrl);
        }

        postRepository.delete(post);
    }

    @Override
    public DetailPostResponse findByIdDetail(Integer postId) {
        PostInfoVo postInfoVo = postRepository.findById(postId)
            .orElseThrow(() -> PostNotFoundException.EXCEPTION).getPostInfoVo();
        Member member = memberUtils.getMemberFromSecurityContext();

        boolean isLiked = likeRepository.findByMemberIdAndPostId(member.getId(), postInfoVo.getId()).isPresent();



        return DetailPostResponse.builder()
            .id(postInfoVo.getId())
            .content(postInfoVo.getContent())
            .imageUrls(postInfoVo.getImageUrls())
            .locationName(postInfoVo.getLocationName())
            .locationAddress(postInfoVo.getLocationAddress())
            .comments(postInfoVo.getComments())
            .likeCount(postInfoVo.getLikeCount())
            .liked(isLiked)
            .authorId(postInfoVo.getAuthorId())
            .authorNickname(postInfoVo.getAuthorNickname())
            .authorImageUrl(postInfoVo.getAuthorImageUrl())
            .movieId(postInfoVo.getMovieId())
            .movieTitle(postInfoVo.getMovieTitle())
            .movieImageUrl(postInfoVo.getMovieImageUrl())
            .build();
    }

    private void checkPostMember(Post post) {
        Member member = post.getMember();
        Member loginMember = memberUtils.getMemberFromSecurityContext();


        if (!member.equals(loginMember)) {
            throw PostNotMatchMemberException.EXCEPTION;
        }
    }
}
