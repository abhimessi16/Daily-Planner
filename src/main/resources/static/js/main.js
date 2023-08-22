var daystartend = "yourdaytimings";
var isUserPresent = false;

$(document).ready(function() {

    $('#add-or-select').on('change', '#user-select', function() {
        // var selectedOption = $(this).val();
        console.log($('#user-select').val());
        console.log('coming into change fucntion');
        // var options = $('#user-select > option');
        // options.array.forEach(element => {
        //     console.log(element);
        //     console.log(element.classList);
        // });
    });

})

function setActive(eleId){
    var element = document.getElementById(eleId);
    element.classList.remove("inactive");
    element.classList.add("active");
}

function setInactive(eleId){
    var element = document.getElementById(eleId);
    element.classList.remove("active");
    element.classList.add("inactive");
}

function newUserAdd(){
    setActive(daystartend);
}

function submitUserDetails(){

    var username = document.getElementById('username');
    var startTime = document.getElementById('dayStart');
    var endTime = document.getElementById('dayEnd');

    var formData = {
        username: username.value,
        startTime: startTime.value,
        endTime: endTime.value
    };

    username.value = '';
    startTime.value = '';
    endTime.value = '';

    $.ajax({
        url: "/",
        method: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
    }).done(function(data){
        $('#user-select').replaceWith(data);
    });

    setInactive(daystartend);   

}