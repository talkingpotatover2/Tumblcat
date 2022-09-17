function includeHTML(){
    var header = document.getElementById('header_part');
    var footer = document.getElementById('footer_part');
    header.innerHTML = '<div id="wrapper">'+
        '<header>'+
            '<div id="menu">'+
                '<ul class="main1">'+
                    '<li style="border: none;"><a href="#"><b style="font-size: 20px;"><span style="font-size: 20px;">≡</span> 카테고리</b></a>'+
                        '<ul class="main2">'+
                            '<li><a href="#">전체</a>'	+			
                            '<li><a href="#">웹툰 리소스</a></li>'+
                            '<li><a href="#">반려동물</a></li>'+
                            '<li><a href="category.html">주얼리</a></li>'+
                            '<li><a href="#">음악</a></li>'+
                            '</li>'+
                        '</ul>'+
                    '</li>'+
                    '<li><a href="index.html">홈</a></li>'	+				
                    '<li><a href="#">공개예정</a></li>'+
                    '<li><a href="project_form.html">프로젝트 올리기</a></li>'+
                    '<li><a href="login.html">로그인</a></li>'+
                    '<li><a href="signup.html">회원가입</a></li>'+
                    '<li style="border: none;"><a href="#">마이페이지</a>'+
                        '<ul class="main2">'+
                            '<li><a href="profile.html">프로필</a></li>'+
                            '<li><a href="pledged.html">후원현황</a></li>'+
                            '<li><a href="liked.html">관심프로젝트</a></li>'+
                            '<li><a href="notice.html">알림</a></li>'+
                            '<li><a href="myproject.html">내가만든프로젝트</a></li>'+
                            '<li><a href="setting_account_a.html">설정</a></li>'+
                            '<li><a href="#">로그아웃</a></li>'	+
                        '</ul>'+
                    '</li>'+
                '</ul>'+
            '</div>'+
        '</header>'+
    '</div>';

footer.innerHTML=	'<footer id="footer">'
    +'<div>'
        +'<ul>'
            +'<li class="fo1"><a href="index.html"><b>tumblcat</b></a></li>'
            +'<li><a target="_blank" href="https://tumblbug.com/notices">공지사항</a></li>'
            +'<li><a target="_blank" href="https://help.tumblbug.com/hc/ko/articles/115006476168-%ED%85%80%EB%B8%94%EB%B2%85-tumblbug-%EC%9D%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80%EC%9A%94-">서비스 소개</a></li>'
        +'</ul>'
        +'<ul>'
            +'<li class="fo2"><b>이용안내</b></li>'
            +'<li><a target="_blank" href="https://help.tumblbug.com/hc/ko">헬프센터</a></li>'
            +'<li><a target="_blank" href="https://tumblbug.com/onboarding">첫후원가이드</a></li>'
            +'<li><a target="_blank" href="https://creator.tumblbug.com/?v=1">창착자가이드</a></li>'
        +'</ul>'
        +'<ul>'
            +'<li class="fo3"><b>정책</b></li>'
            +'<li><a target="_blank" href="https://help.tumblbug.com/hc/ko/articles/360051940533?v=1">이용약관</a></li>'
            +'<li><a target="_blank" href="https://help.tumblbug.com/hc/ko/articles/7766423598745">개인정보처리방침</a></li>'
            +'<li><a target="_blank" href="https://help.tumblbug.com/hc/ko/articles/360052346633-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8B%AC%EC%82%AC-%EA%B8%B0%EC%A4%80">프로젝트심사기준</a></li>'
        +'</ul>'
    +'</div>'

    +'<div id="fo3">'
    +'<hr>'
        +'<p><span>회사명</span> 텀블캣(주) <span>주소</span> 서울특별시 영등포구 선유로 130'
        +'Copyright 2012 funnycom</p>'
    +'</div>'
    +'<hr>'
    +'<div id="fo4">'
        +'<p>텀블캣은 플랫폼 제공자로서 프로젝트의 당사자가 아니며 직접적인 통신판매를 진행하지않습니다.</p>'
    +'</div>'
    +'</footer>';
}