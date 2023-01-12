// (1) 회원정보 수정
function update(userId) {
    event.preventDefault();
   let data = $("#profileUpdate").serialize();
   $.ajax({
       type : "put",
       url : `/api/user/${userId}`,
       data : data,
       contentType : "application/x-www-form-urlencoded",
       dataType : "json"
   }).done(res => {
        location.href=`/user/${userId}`;
   }).fail(error => {
       if(error.data == null) {
           alert(error.responseJSON.message);
       } else {
            alert(JSON.stringify(error.responseJSON.data));
       }
   });
}