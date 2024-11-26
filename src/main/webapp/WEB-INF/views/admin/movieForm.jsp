<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.0.3/dist/tailwind.min.css" rel="stylesheet">
    <title>영화 등록</title>
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
    </div>
</div>


<div class="w-full h-full flex justify-center items-center pt-32">
    <div class="w-full max-w-md bg-white p-8 rounded shadow-md">
        <h2 class="text-2xl font-semibold text-center mb-4">영화 등록 폼</h2>

        <!-- 영화 등록 폼 -->
        <form action="/api/v1/admin/movie" method="post" enctype="multipart/form-data">
            <!-- 영화 제목 -->
            <div class="mb-4">
                <label for="movieTitle" class="block text-sm font-medium text-gray-700">영화 제목</label>
                <input type="text" id="movieTitle" name="movieData" class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500" placeholder="영화 제목" required>
            </div>
            <%-- 예외 메시지 출력 --%>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
            <div class=" text-red-500">
                <%= errorMessage %>
            </div>
            <%
                }
            %>

            <!-- 영화 포스터 -->
            <div class="mb-4">
                <label for="moviePoster" class="block text-sm font-medium text-gray-700">영화 포스터</label>
                <input type="file" id="moviePoster" name="moviePoster" class="mt-1 block w-full text-sm text-gray-500 border border-gray-300 rounded-md" required>
            </div>

            <!-- 제출 버튼 -->
            <div class="flex justify-center mt-6">
                <button type="submit" class="w-full bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-700">영화 등록</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
