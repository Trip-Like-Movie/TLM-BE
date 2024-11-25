<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>관리자 로그인</title>
</head>
<body class="bg-gray-50">

<!-- 상단 네비게이션 바 -->
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


<main class="w-full max-w-md mx-auto p-6 flex-1 flex items-center justify-center pt-32">
    <div class="text-center w-full">
        <h1 class="text-center text-2xl font-bold mb-4">로그인</h1>

        <!-- Spring form 사용 -->
        <form:form method="POST" action="login" class="space-y-6">
            <!-- 이메일 입력 -->
            <div>
                <label class="block text-sm font-medium text-gray-700" for="email">이메일</label>
                <input type="email" id="email" name="email" class="input input-bordered w-full px-4 py-2 mt-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" placeholder="example@tlm.com" required />
            </div>
            <!-- 비밀번호 입력 -->
            <div>
                <label class="block text-sm font-medium text-gray-700" for="password">비밀번호</label>
                <input type="password" id="password" name="password" class="input input-bordered w-full px-4 py-2 mt-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" placeholder="비밀번호를 입력하세요" required />
            </div>
            <!-- 로그인 버튼 -->
            <div>
                <button type="submit" class="btn btn-primary w-full py-2 px-4 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    로그인
                </button>
            </div>
            <input type="hidden" name="redirectUri" value="${param.redirectUri}" />

        </form:form>
    </div>
</main>

</body>
</html>
