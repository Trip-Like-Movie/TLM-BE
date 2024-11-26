package com.TripLikeMovie.backend.domain.member.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangeNicknameRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordVerifyEmailRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.domain.post.domain.vo.PostInfoVo;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.MemberAllPost;
import com.TripLikeMovie.backend.global.error.exception.image.NotProfileImageException;
import com.TripLikeMovie.backend.global.error.exception.image.ProfileImageDeleteException;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedEmailException;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedNicknameException;
import com.TripLikeMovie.backend.global.error.exception.member.EmailPasswordNotMatchException;
import com.TripLikeMovie.backend.global.error.exception.member.MemberNotFoundException;
import com.TripLikeMovie.backend.global.utils.image.ImageUtils;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final Set<String> nicknameSet = ConcurrentHashMap.newKeySet();
    private final PasswordEncoder encoder;
    private final MemberUtils memberUtils;
    private final ImageUtils imageUtils;

    @Override
    @Transactional(readOnly = true)
    public void validateEmail(SendVerificationRequest sendVerificationRequest) {
        memberRepository.findByEmail(sendVerificationRequest.getEmail()).ifPresent(member -> {
            throw DuplicatedEmailException.EXCEPTION;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public void validateNickname(VerifyNicknameRequest verifyNicknameRequest) {
        memberRepository.findByNickname(verifyNicknameRequest.getNickname()).ifPresent(member -> {
            throw DuplicatedNicknameException.EXCEPTION;
        });
        if (nicknameSet.contains(verifyNicknameRequest.getNickname())) {
            throw DuplicatedNicknameException.EXCEPTION;
        }
        nicknameSet.add(verifyNicknameRequest.getNickname());
    }

    @Override
    @Transactional(readOnly = true)
    public void signUpSuccess(String nickname) {
        nicknameSet.remove(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public void changePasswordVerifyEmail(ChangePasswordVerifyEmailRequest changePasswordVerifyEmail) {
        Member member = memberRepository.findByEmail(changePasswordVerifyEmail.getEmail())
            .orElseThrow(() -> MemberNotFoundException.EXCEPTION);

        if (!member.getNickname().equals(changePasswordVerifyEmail.getNickname())) {
            throw EmailPasswordNotMatchException.EXCEPTION;
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Member member = memberRepository.findByEmail(changePasswordRequest.getEmail())
            .orElseThrow(() -> MemberNotFoundException.EXCEPTION);

        String encodedPassword = encoder.encode(changePasswordRequest.getPassword());
        member.changePassword(encodedPassword);
        memberRepository.save(member);
    }

    @Override
    public void changeNickname(ChangeNicknameRequest changeNicknameRequest) {
        memberRepository.findByNickname(changeNicknameRequest.getNickname()).ifPresent(member -> {
            throw DuplicatedNicknameException.EXCEPTION;
        });
        Member member = memberUtils.getMemberFromSecurityContext();

        member.changeNickname(changeNicknameRequest.getNickname());
        memberRepository.save(member);
    }


    @Override
    public void deleteProfileImage() {
        Member member = memberUtils.getMemberFromSecurityContext();

        String imagePath = member.getImageUrl();

        if (imagePath == null) {
            throw NotProfileImageException.EXCEPTION;
        }
        // 파일 삭제
        File file = new File(imagePath);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw ProfileImageDeleteException.EXCEPTION;
            }
        }
        member.updateProfileImage(null); // Member에서 프로필 이미지 제거
        memberRepository.flush();

    }

    @Override
    public void updateProfileImage(MultipartFile file) {
        Member member = memberUtils.getMemberFromSecurityContext();

        String imagePath = member.getImageUrl();

        // 기존 프로필 이미지가 있으면 파일 삭제 및 엔티티 삭제
        imageUtils.deleteImage(imagePath);

        // 새로운 이미지 저장
        String filePath = imageUtils.saveImage(file, "profiles/");

        // Member 엔티티 업데이트
        member.updateProfileImage(filePath);
        memberRepository.flush();

    }

    @Override
    public Member findById(Integer memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> MemberNotFoundException.EXCEPTION);
    }

    @Override
    public List<MemberAllPost> getAllPosts(Integer memberId) {
        Member member = findById(memberId);

        return convertToMemberAllPosts(member.getPosts());
    }

    @Override
    public void withdraw() {
        Member member = memberUtils.getMemberFromSecurityContext();
        memberRepository.delete(member);
    }

    @Override
    public List<MemberAllPost> getAllPosts(Integer memberId, Integer movieId) {
        Member member = findById(memberId);  // 회원 정보 찾기
        List<Post> posts = member.getPosts(); // 회원의 모든 게시물 목록 가져오기

        // movieId가 주어진 movieId와 일치하는 게시물만 필터링
        List<Post> filteredPosts = posts.stream()
            .filter(post -> post.getMovie().getId().equals(movieId)) // movieId가 일치하는 게시물 필터링
            .collect(Collectors.toList()); // 필터링된 결과를 리스트로 수집

        // 필요한 형태로 변환해서 반환 (MemberAllPost 타입으로 변환한다고 가정)

        return convertToMemberAllPosts(filteredPosts);
    }

    // MemberAllPost 타입으로 변환하는 메소드 (예시)
    private List<MemberAllPost> convertToMemberAllPosts(List<Post> posts) {
        // 변환 로직 구현
        return posts.stream()
            .map(post -> {
                PostInfoVo postInfoVo = post.getPostInfoVo();
                MemberAllPost memberAllPost = new MemberAllPost();
                memberAllPost.setId(postInfoVo.getId());
                memberAllPost.setMovieTitle(postInfoVo.getMovieTitle());
                memberAllPost.setCommentsSize(postInfoVo.getComments().size());
                memberAllPost.setFirstImageUrl(postInfoVo.getImageUrls().get(0));
                memberAllPost.setLikeCount(postInfoVo.getLikeCount());
                memberAllPost.setCreatedAt(postInfoVo.getCreatedAt());
                memberAllPost.setUpdatedAt(postInfoVo.getUpdatedAt());
                return memberAllPost;
            })
            .collect(Collectors.toList()); //
    }

}