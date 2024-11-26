<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.0.3/dist/tailwind.min.css"
          rel="stylesheet">

    <title>영화 조회</title>
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
        <a href="${pageContext.request.contextPath}/api/v1/admin/movie"
           class="btn btn-ghost text-primary text-lg mr-4">영화 등록하러 가기</a>
        <a href="${pageContext.request.contextPath}/api/v1/admin/post"
           class="btn btn-ghost text-primary text-lg mr-4">게시글 목록 보기</a>
        <a href="${pageContext.request.contextPath}/api/v1/admin/movies"
           class="btn btn-ghost text-primary text-lg mr-4">영화 목록 보기</a>
    </div>
</div>

<div class="w-full h-full flex flex-col pt-20 items-center">
    <!-- 콘텐츠 컨테이너 -->
    <div class="w-3/5">
        <!-- 제목, 삭제 버튼, 이미지 -->
        <div class="bg-white shadow-md rounded-lg overflow-hidden">
            <!-- 제목과 삭제 버튼 영역 -->
            <div class="flex justify-between items-center px-6 py-4">
                <!-- 제목: 왼쪽 정렬 -->
                <h2 class="text-3xl font-bold text-left">${movie.title}</h2>
                <!-- 삭제하기 버튼: 오른쪽 정렬 -->
                <form action="/api/v1/admin/movie/delete/${movie.id}" method="post"
                      onsubmit="return confirm('정말 삭제하시겠습니까?');">
                    <button type="submit"
                            class="px-6 py-3 bg-red-500 text-white rounded-lg hover:bg-red-700 focus:outline-none">
                        삭제하기
                    </button>
                </form>
            </div>

            <!-- 이미지 영역 -->
            <div class="uploaded-container flex justify-center">
                <div class="uploaded-photos relative rounded-lg overflow-hidden"
                     style="max-width: 50%; aspect-ratio: 9 / 16;">
                    <img id="postImage" src="http://localhost:8080/${movie.imageUrl}" alt="영화 사진"
                         class="w-full h-auto object-contain">
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
