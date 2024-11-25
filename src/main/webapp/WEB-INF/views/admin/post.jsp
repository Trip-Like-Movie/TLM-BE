<%@ page import="com.TripLikeMovie.backend.domain.comment.domain.vo.CommentVo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.TripLikeMovie.backend.domain.post.domain.vo.PostInfoVo" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.0.3/dist/tailwind.min.css" rel="stylesheet">

    <title>게시물 상세</title>
    <!-- CSS 또는 필요한 외부 라이브러리 추가 -->
</head>
<body>

<div class="navbar bg-base-100 shadow h-20 fixed top-0 left-0 right-0 z-50 flex justify-between items-center px-6">
    <div class="flex items-center">
        <a href="/" class="btn btn-ghost text-primary text-xl mr-4">Trip Like Movie</a>
        <span class="text-lg font-semibold text-gray-800">관리자 페이지</span>
    </div>
    <!-- 영화 등록하러 가기 링크 추가 -->
    <div class="flex items-center space-x-4">
        <a href="${pageContext.request.contextPath}/api/v1/admin/movie" class="btn btn-ghost text-primary text-lg mr-4">영화 등록하러 가기</a>
        <a href="${pageContext.request.contextPath}/api/v1/admin/post" class="btn btn-ghost text-primary text-lg mr-4">게시글 목록 보기</a>
    </div>
</div>


<div class="w-full h-full flex pt-20">
    <div class="w-3/5 h-full flex flex-col items-center justify-between overflow-hidden">
        <div class="w-full flex justify-between items-center px-12 mt-4">
            <!-- 영화 제목은 가운데 정렬 -->
            <h2 class="text-xl font-bold mx-auto">${post.movieTitle}</h2>

            <!-- 삭제하기 버튼은 오른쪽에 위치 -->
            <form action="/api/v1/admin/post/${post.id}" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                <button type="submit" class="px-6 py-3 bg-red-500 text-white rounded-lg hover:bg-red-700 focus:outline-none">삭제하기</button>
            </form>
        </div>
        <!-- 게시물 이미지 -->
        <div class="w-3/4 mt-6 mb-8">
            <div class="uploaded-container flex items-center justify-center">
                <!-- 이전 이미지 버튼 -->
                <button onclick="changeImage(-1)" class="h-fit btn btn-secondary py-3 px-3 rounded-full shadow-2xl text-xl">←</button>

                <!-- 이미지 표시 -->
                <div class="w-4/5 m-6 uploaded-photos relative aspect-video rounded-lg overflow-hidden shadow-2xl">
                    <img id="postImage" src="http://localhost:8080/${post.imageUrls.get(0)}" alt="여행지 사진" class="w-full h-full object-cover">
                </div>

                <!-- 다음 이미지 버튼 -->
                <button onclick="changeImage(1)" class="h-fit btn btn-secondary py-3 px-3 rounded-full shadow-2xl text-xl">→</button>
            </div>
        </div>


        <!-- 장소 정보 -->
        <div class="text-sm mt-2">
            <p>${post.locationName} : ${post.locationAddress}</p>
        </div>

        <!-- 작성자 정보 -->
        <div class="actions my-4 px-12 flex items-center justify-between w-4/5 max-w-[600px]">
            <div class="flex items-center">
                <a href="/user/${post.authorId}" class="btn btn-ghost btn-circle avatar mr-2">
                    <div class="w-10 rounded-full">
                        <img src="http://localhost:8080/${post.authorImageUrl}" alt="프로필 이미지">
                    </div>
                </a>
                <a href="/user/${post.authorId}" class="font-bold text-lg">${post.authorNickname}</a>
            </div>
            <div class="flex items-center space-x-4">
                <button class="text-error flex items-center space-x-1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" viewBox="0 0 24 24" stroke="currentColor" style="fill: currentcolor;">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                    </svg>
                    <span class="font-semibold">${post.likeCount}</span>
                </button>
            </div>
        </div>

        <!-- 게시물 내용 -->
        <div class="px-12 flex-1 text-sm w-4/5 max-w-[600px] overflow-y-auto mb-8">
            <p class="whitespace-pre-line bg-base-200 p-8 rounded-lg">${post.content}</p>
        </div>

    </div>



    <div class="w-2/5 p-4 flex flex-col h-full">
        <!-- 댓글 목록 -->
        <div class="comment-list space-y-2 overflow-y-auto flex-1 pr-2">

            <%

                PostInfoVo post = (PostInfoVo) request.getAttribute("post");

                // comments 리스트를 가져옵니다.
                List<CommentVo> comments = post.getComments();

                // comments가 null이 아니고 비어 있지 않다면 반복합니다.
                if (comments != null && !comments.isEmpty()) {
                    for (CommentVo comment : comments) {
            %>
            <div class="comment-item p-2 bg-base-200 rounded-lg flex items-center justify-between">
                <a href="/user/<%= comment.getAuthorId() %>" class="btn btn-ghost btn-circle avatar mr-2">
                    <div class="w-10 rounded-full">
                        <img src="http://localhost:8080/<%= comment.getAuthorImageUrl() %>" alt="댓글 작성자 이미지">
                    </div>
                </a>
                <div class="flex-1">
                    <div class="font-semibold text-sm w-fit">
                        <a href="/user/<%= comment.getAuthorId() %>" class=""><%= comment.getAuthorNickname() %></a>
                    </div>
                    <p><%= comment.getContent() %></p>
                </div>
            </div>
            <%
                } // for 루프 종료
            } else {
            %>
            <!-- 댓글이 없을 경우 -->
            <p class="text-center">댓글이 없습니다.</p>
            <%
                } // if 종료
            %>


        </div>
    </div>
</div>

<script>
  // Java에서 전달된 JSON 문자열을 JavaScript 배열로 변환
  const imageUrls = JSON.parse('${imageUrlsJson}');
  let currentIndex = 0;


  // 이미지 변경 함수
  function changeImage(direction) {
    currentIndex += direction;

    // 인덱스가 범위를 벗어날 경우 순환
    if (currentIndex < 0) currentIndex = imageUrls.length - 1;
    if (currentIndex >= imageUrls.length) currentIndex = 0;

    // 이미지 업데이트
    const postImage = document.getElementById('postImage');
    postImage.src = `http://localhost:8080/` + imageUrls[currentIndex]; // 경로를 명확히 연결
  }
</script>
</body>
</html>
