<%@ page import="com.TripLikeMovie.backend.domain.post.presentation.dto.response.AllPostResponse" %>
<%@ page import="java.util.List" %>
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

<!-- 메인 컨텐츠 -->
<main class="mt-20 max-w-7xl mx-auto px-4 pt-20">
    <h1 class="text-xl font-semibold text-gray-700 mb-6">게시글 목록</h1>

    <div class="overflow-x-auto">
        <table class="min-w-full bg-white shadow-md rounded-lg border border-gray-200">
            <thead>
            <tr class="bg-gray-200 text-gray-700 text-left">
                <th class="px-4 py-2">ID</th>
                <th class="px-4 py-2">영화 제목</th>
                <th class="px-4 py-2">작성자 닉네임</th>
                <th class="px-4 py-2">좋아요 수</th>
                <th class="px-4 py-2">댓글 수</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<AllPostResponse> posts = (List<AllPostResponse>) request.getAttribute("posts");

                if (posts != null && !posts.isEmpty()) {
                    for (AllPostResponse post : posts) {
            %>
            <tr class="border-t border-gray-200">
                <td>
                    <a href="/api/v1/admin/post/<%= post.getId() %>"
                       class="text-blue-500 hover:text-blue-700 font-semibold underline">
                        <%=post.getId()%>
                    </a>
                </td>
                <td class="px-4 py-2 text-gray-800"><%= post.getMovieTitle() %>
                </td>
                <td class="px-4 py-2 text-gray-800"><%= post.getMemberNickname() %>
                </td>
                <td class="px-4 py-2 text-gray-800 text-center"><%= post.getLikedCount() %>
                </td>
                <td class="px-4 py-2 text-gray-800 text-center"><%= post.getCommentsCount() %>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="6" class="px-4 py-2 text-gray-700 text-center">게시글이 없습니다.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>

</body>
</html>
