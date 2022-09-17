$(document).ready(function(){

        $('.box').each(function(){
            var content = $(this).children('.content');
            var content_txt = content.text();
            var content_txt_short = content_txt.substring(0,100)+"...";
            var btn_more = $('<a href="javascript:void(0)" class="more">더보기</a>');


            $(this).append(btn_more);

            if(content_txt.length >= 100){
                content.html(content_txt_short)

            }else{
                btn_more.hide()
            }

            btn_more.click(toggle_content);
            // 아래 bind가 안 되는 이유는??
            // btn_more.bind('click',toggle_content);

            function toggle_content(){
                if($(this).hasClass('short')){
                    // 접기 상태
                    $(this).html('더보기');
                    content.html(content_txt_short)
                    $(this).removeClass('short');
                }else{
                    // 더보기 상태
                    $(this).html('접기');
                    content.html(content_txt);
                    $(this).addClass('short');

                }
            }
            
        });
        
        // 프로필 변경시 버튼 토글
        $('#name-change').click( function() {
	        $('#profile_ori_name').toggle();
	        $('#profile_cha_name').toggle();
	        if( $(this).val() == '변경' ) {
	          $(this).val('취소');
	        }
	        else {
	          $(this).val('변경');
	        }
	    });
	
	    $('#desc-change').click( function() {
	        $('#profile_ori_desc').toggle();
	        $('#profile_cha_desc').toggle();
	        if( $(this).val() == '변경' ) {
	          $(this).val('취소');
	        }
	        else {
	          $(this).val('변경');
	        }
	    });
    });

    /* 모달 */

	function showMsg(){
		var dialog = document.getElementById("myMsgDialog");
		dialog.showModal();
	}
	
	function closeMsg(){
		var dialog = document.getElementById("myMsgDialog");
		dialog.close();
	}
