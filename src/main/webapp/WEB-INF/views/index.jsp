<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
<!--외부 css파일 연결 -->
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;700&display=swap" rel="stylesheet">
</head>
<body>
	<div id="wrap">
	<!-- 상단  -->
		<header id="header">
			<div class="txt_right">
			<c:if test="${sessionScope.mvo == null}">			
				<span><a href="login">로그인</a></span>
			</c:if>
			<c:if test="${sessionScope.mvo != null}">		
				<!--<span><a href="javascript:location.href='logout'">로그아웃</a></span>-->
				<span><a href="javascript:logout()">로그아웃</a></span>			
			</c:if>
			</div>
			<!--  타이틀이기 때문에 h1으로 넣어준다. 접근성을 위해서 -->
			<h1>SK Together</h1> 			
			<ul class="gnb">
				<li><a  href=""><span class="menu m01">기브유</span></a></li>
				<li><a  href=""><span class="menu m02">위드유</span></a></li>
				<li><a  href=""><span class="menu m03">스마트 전통시장</span></a></li>
				<li><a  href=""><span class="menu m04">BRAVO!</span></a></li>
				<li><a  href="bbs"><span class="menu m05">SKT와 사회공헌</span></a></li>
				<li class="end"></li>
			</ul>		
		</header>
	<!-- 상단 끝 -->
	<!-- 콘텐츠 영역 -->
		<div id="contents">
			<div class="main_img">
				<a href="">
					<img src="img/@img00.png" />
				</a>
			</div>
			
			<div class="main_news">
				<div class="news_type01 fl">
					<p class="title">기브유 후원참여</p>
					<a href="" class="news_src">
						<span class="thum_img">
							<img src="./img/@img01.png" alter="기사사진"/>
						</span>
						<span class="ellip subject">난청이지만 피아니스트가 되고픈 한별이의 이야기입니다.</span>
						<span class="writer">by together</span>
						<span class="more_view">자세히 보기</span>
						<span class="fclear"></span>
					</a> 
				</div>
				<div class="news_type01 cen">
					<p class="title">기브유 후원금 쓰임현황</p>
					<a href="" class="news_src">
						<span class="thum_img">
							<img src="./img/@img02.png" alt="기사사진"/>
						</span>
						<span class="ellip subject">레티하씨 가정에 희망의 집 선물</span>
						<span class="writer">by 한국해비타트</span>
						<span class="more_view">자세히 보기</span>
						<span class="fclear"></span>
					</a> 
				</div>
				<div class="news_type01 fr">
					<p class="title">기브유 나눔영상</p>
					<a href="" class="news_src">
						<span class="thum_img">
							<img src="img/@img03.png" alter="기사사진"/>
							<span class="btn_ply" title="동영상 재생">
									<a href="#"></a>
							</span>
						</span>
						<span class="ellip subject">1리터의 생명을 선물해주세요</span>
						<span class="writer">by hungersaver</span>
						<span class="more_view">자세히 보기</span>
						<span class="fclear"></span>
					</a> 
				</div>
			</div>
			<div class="main_board">
			
				<!--공지사항-->
					<div class="board_type01 fl">
						<p class="title">공지사항</p>
						<span class="more_view"><a href="Controller?type=notice">더보기</a></span>
						<ul class="notice">
							<%--
							<c:if test="${notice ne null }">
								<c:forEach items="${notice}" var="vo">	
								<li>
									<a href="">${vo.content}</a>
									<span class="date">${vo.reg_date}</span>
								</li>								
								</c:forEach>							
							</c:if>		--%>
							
							<c:if test="${ar ne null}">
								<c:forEach items="${ar}" var="vo">
									<li>
										<a href="" class="elip">${vo.content}</a>
										<span class="date">${fn:substring(vo.reg_date, 0, 10)}</span>
									</li>
								</c:forEach>
							</c:if>
							<c:if test="${ar eq null}">
								<li>등록된 공지가 없습니다.</li>
							</c:if>
							
					
						</ul>
					</div>
					<!--공지사항 끝 -->
					
					
					<!-- 트위터-->
					<div class="board_type01 cen">
						<p class="title">T-together 트위터</p>
						<span class="more_view"><a href="">더보기</a></span>
						<div class="article">
							<span class="thum_img">
								<img src="img/@img04.png" alt="profile Image"/>
							</span>
							<span class="src">[캠페인] 햇살이 따스한 봄날이 다가옵니다. 그리고 여름이 지나갑니다.</span>
							<div class="fclear"></div>
						</div>
					</div>
					<!--트위터 끝-->
					
					<!-- 배너-->
					<div class="board_type01 fr">
						<span class="banner b01">
							<a href="">T-together와 함께할 기관/단체를 모십니다.</a>
						</span>
						<span class="banner b02">
							<a href="">T-Together 이제는 모바일과 함께해요.</a>
						</span>
					</div>
					<div class="fclear"></div>
					<!-- 배너 끝 -->
			</div>
		</div>
	<!-- 콘텐츠 영역 끝-->
	<!--하단 영역 -->
			<div id="footer">
				<div class="footer_area">
					<ul class="footer_guide">
						<li><a href="">개인정보취급방침</a></li>
						<li><a href="">웹회원 이용약관</a></li>
						<li><a href="">책임한계와 법적고지</a></li>
						<li><a href="">이메일 무단수집 거부</a></li>
					</ul>
					<address>
						서울시 중구 을지로 몇가 번지 
						대표이사:마루치 
						고객상담:국번없이 114 혹은 02-1234-1234 (평일 09:00~17:00)
					</address>
					<p class="copyright">
						COPYRIGHT (c) 2020 SK Telecom. All rights reserved. 
					</p>
				</div>
			</div>
	<!--하단 영역 끝 -->
	</div>
	<form method="post" name="frm" action="Controller">
		<input type="hidden" name="type"  />
	</form>
	
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>
	function logout() {
		$.ajax({
			url: "logout",
			dataType: "json"
		}).done(function(data){
			if(data.res == "0"){
				alert("정상적으로 로그아웃되었습니다.");
				location.href="/";
			}
		});
	}
</script>
</body>
</html>