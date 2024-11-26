<%@ page import="java.util.List" %>
<%@ page import="com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
          rel="stylesheet">
    <title>관리자 페이지</title>
</head>
<body class="bg-gray-100">

<div class="navbar bg-base-100 shadow h-20 fixed top-0 left-0 right-0 z-50 flex justify-between items-center px-6">
    <div class="flex items-center">
        <a href="/" class="btn btn-ghost text-primary text-xl mr-4">Trip Like Movie</a>
        <span class="text-lg font-semibold text-gray-800">관리자 페이지</span>
    </div>
    <!-- 영화 등록하러 가기 링크 추가 -->
    <div class="flex items-center space-x-4">
        <a href="${pageContext.request.contextPath}/api/v1/admin/movie" class="btn btn-ghost text-primary text-lg mr-4">영화 등록하러 가기</a>
        <a href="${pageContext.request.contextPath}/api/v1/admin/post" class="btn btn-ghost text-primary text-lg mr-4">게시글 목록 보기</a>
        <a href="${pageContext.request.contextPath}/api/v1/admin/movies" class="btn btn-ghost text-primary text-lg mr-4">영화 목록 보기</a>
    </div>
</div>

<main class="mt-20 max-w-7xl mx-auto px-4 pt-20">
    <h1 class="text-xl font-semibold text-gray-700 mb-6">게시글 목록</h1>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <%
            List<MovieInfoVo> movies = (List<MovieInfoVo>) request.getAttribute("movies");

            if (movies != null && !movies.isEmpty()) {
                for (MovieInfoVo movie : movies) {
        %>
        <!-- 세로로 더 긴 카드 레이아웃 -->
        <div class="bg-white shadow-md rounded-lg border border-gray-200 overflow-hidden flex flex-col max-w-xs">
            <!-- 이미지 영역 -->
            <img src="http://localhost:8080/<%= movie.getImageUrl() %>"
                 alt="<%= movie.getTitle() %>"
                 class="w-full h-72 object-cover">
            <!-- 카드 내용 -->
            <div class="p-4 flex flex-col items-start">
                <h2 class="text-lg font-bold text-gray-800 mb-3"><%= movie.getTitle() %></h2>
                <p class="text-base text-gray-600 mb-4">ID: <%= movie.getId() %></p>
                <a href="/api/v1/admin/movie/<%= movie.getId() %>"
                   class="text-blue-500 hover:text-blue-700 font-semibold underline mt-auto text-base">
                    자세히 보기
                </a>
            </div>
        </div>


        <%
            }
        } else {
        %>
        <!-- 게시글이 없을 때 -->
        <div class="col-span-full text-center text-gray-700">
            게시글이 없습니다.
        </div>
        <%
            }
        %>
    </div>
</main>



</body>
</html>
