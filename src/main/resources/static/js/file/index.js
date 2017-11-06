$(document).ready(function(){
    $("#fresh_file_list").click(function(){
        listfile();
    });
    function listfile() {
        $("#fileList").empty();
        $.ajax({
            url:'list',
            type:'GET',
            success:function (data) {
                if(data.success){
                    list="";
                    $.each(data.data,function (index,item) {
                        list=list+item+"<br/>"
                    });
                    $("#fileList").html(list);
                }else{
                    alert(data.message);
                }

            }
        });
    }

    listfile();

    $("#file_form").submit(function() {
        var options={
            success:listfile
        };
        $(this).ajaxSubmit(options);
        return false;
    });
});