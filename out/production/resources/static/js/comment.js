var comment = {
  // 이벤트 등록
  init: function() {
    var _this = this;
    // 버튼 변수화
    const viewComments = document.querySelectorAll('#viewComment');
    const editComments = document.querySelectorAll('#editComment');
    const editBtns = document.querySelectorAll('#editBtn');
    const commitBtns = document.querySelectorAll('#commitBtn');
    const articleIdxs = document.querySelectorAll('#articleIdx');
    const commentIdxs = document.querySelectorAll('#commentIdx');
    const deleteBtns = document.querySelectorAll('#commentDeleteBtn');

    // 생성
    const createBtn = document.querySelector('#createBtn');
    const articleIdxNew = document.querySelector('#articleIdxNew').value;
    createBtn.onclick = function(){
        var newComment = document.querySelector('#newComment').value;
            console.log(newComment);

        var commentAjax = {
          comment: newComment,
        };
            fetch('/article/comment/' + articleIdxNew, { // 요청을 보냄
              method: 'POST',
              body: JSON.stringify(commentAjax),
              headers: {
                'Content-Type': 'application/json;charset=UTF-8'
              }
            }).then(function(response) { // 응답 처리
              if (response.ok) { // 성공
                alert('댓글이 작성되었습니다.');
              } else { // 실패
                alert('댓글 작성을 실패했습니다.');
              }
              window.location.reload(true); // 페이지 리로드
            });
    };

for(var i=0; i<editBtns.length; i++){
    btn_click(i);
}
function btn_click(idx){
console.log(idx);
    editBtns[idx].onclick = function(){
    console.log("버튼클릭");
    console.log(viewComments[idx].value);
        viewComments[idx].style.display = "none";
        editComments[idx].style.display = "";
        editBtns[idx].style.display = "none";
        commitBtns[idx].style.display = "";
    };

    //댓글 수정
    commitBtns[idx].onclick = function(){
        var commentIdxVal = commentIdxs[idx].value;
        var editCommentVal = editComments[idx].value;
        var articleIdxVal = articleIdxs[idx].value;
        var commentAjax = {
          commentIdx: commentIdxVal,
          comment: editCommentVal,
          articleIdx: articleIdxVal,
        };
            fetch('/article/comment/edit', { // 요청을 보냄
              method: 'POST',
              body: JSON.stringify(commentAjax),
              headers: {
                'Content-Type': 'application/json;charset=UTF-8'
              }
            }).then(function(response) { // 응답 처리
              if (response.ok) { // 성공
                alert('댓글이 수정되었습니다.');
              } else { // 실패
                alert('댓글 수정을 실패했습니다.');
              }
              window.location.reload(true); // 페이지 리로드
            });
    };

    //댓글 삭제
    deleteBtns[idx].onclick = function(){
        var commentIdxVal = commentIdxs[idx].value;
        var articleIdxVal = articleIdxs[idx].value;
        var commentAjax = {
          commentIdx: commentIdxVal,
          articleIdx: articleIdxVal,
        };
            fetch('/article/comment/delete', { // 요청을 보냄
              method: 'POST',
              body: JSON.stringify(commentAjax),
              headers: {
                'Content-Type': 'application/json;charset=UTF-8'
              }
            }).then(function(response) { // 응답 처리
              if (response.ok) { // 성공
                alert('댓글이 삭제되었습니다.');
              } else { // 실패
                alert('댓글 삭제를 실패했습니다.');
              }
              window.location.reload(true); // 페이지 리로드
            });
    };
  }
 },
};
// 객체 초기화
comment.init();