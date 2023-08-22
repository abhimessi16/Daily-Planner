var daystartend = "yourdaytimings";
var isUserPresent = false;

$(document).ready(function() {

    

    $('body').on('change', '#user-select', function() {
        
        var data = $('#user-select').val();

        var jsonData = {
            'user': data
        }

        // understand better where to use get and post requests
        // and use them appropriately
        // when you are displaying data which you received from server then use get
        // use post only when you have to send data to the server

        $.ajax({
            url: '/get-all-tasks',
            method: 'GET',
            data: jsonData,
            contentType: 'application/json'
        }).done(function(data) {
            $('.task-container').replaceWith(data);
        }).fail(function(data) {
            console.log(data, 'failure');
        });

    });

    $('body').on('click', '.time-slot', function() {
        var slotTime = $(this).find('.start-time').text();
            
        var user = $('#user-select').val();

        $.get('/get-tasks', {'startTime' : slotTime, 'user': user})
        .done(function(data) {
            console.log(data);
            $('#time-slot-tasks').replaceWith(data);
            $('#time-slot-tasks')[0].classList.add('start-' + slotTime);
            $('#time-slot-tasks').show();
        })

    });

    $('body').on('click', '.close-tasks > .close-button', function() {
        $('#time-slot-tasks').hide();
    });

    $('body').on('click', '.create-task > .btn', function() {
        $('#task-add').show();
    })

    $('body').on('click', '#task-done', function() {
        let taskStatus = $(this).text();
        let user = $('#user-select').val();
        var startTime = '';
        var taskId = '';

        let classes = $('#time-slot-tasks')[0].classList;
        for(let i = 0; i < classes.length; i++){
            console.log(classes[i], classes[i].startsWith('start'));
            if(classes[i].startsWith('start')){
                let startVals = classes[i].split('-');
                startTime = startVals[1];
                break;
            }
        }

        classes = $('#task-done')[0].closest('.task-detail').classList;
        
        for(let i = 0; i < classes.length; i++){
            if(classes[i].startsWith('taskid')){
                let startVals = classes[i].split('-');
                taskId = startVals[1];
                break;
            }
        }

        $.get('/update-status', {
            'user': user,
            'startTime': startTime,
            'taskId': taskId,
            'status': taskStatus
        }).done(function(data) {
            console.log(data);
            $('#main').replaceWith(data);
            $('#user-select')[0].value=user;
            $('#time-slot-tasks')[0].classList.add('start-' + startTime);
            $('#time-slot-tasks').show();
        });

    });

    $('body').on('click', '#task-remove', function() {

        let user = $('#user-select').val();
        var startTime = '';
        var taskId = '';

        let classes = $('#time-slot-tasks')[0].classList;
        for(let i = 0; i < classes.length; i++){
            console.log(classes[i], classes[i].startsWith('start'));
            if(classes[i].startsWith('start')){
                let startVals = classes[i].split('-');
                startTime = startVals[1];
                break;
            }
        }

        classes = $('#task-done')[0].closest('.task-detail').classList;
        
        for(let i = 0; i < classes.length; i++){
            if(classes[i].startsWith('taskid')){
                let startVals = classes[i].split('-');
                taskId = startVals[1];
                break;
            }
        }

        $.get('/remove-task', {
            'user': user,
            'startTime': startTime,
            'taskId': taskId
        }).done(function(data) {
            $('#main').replaceWith(data);
            $('#user-select')[0].value=user;
            $('#time-slot-tasks')[0].classList.add('start-' + startTime);
            $('#time-slot-tasks').show();
        });

    })

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

function addTask(){
    let taskName = $('#name-taskadd');
    let taskDesc = $('#description-taskadd');
    let user = $('#user-select').val();
    var startTime = '';

    let classes = $('#time-slot-tasks')[0].classList;
    for(let i = 0; i < classes.length; i++){
        console.log(classes[i], classes[i].startsWith('start'));
        if(classes[i].startsWith('start')){
            let startVals = classes[i].split('-');
            startTime = startVals[1];
            break;
        }
    }

    var jsonData = {
        'taskName': taskName.val(),
        'taskDescription': taskDesc.val(),
        'user': user,
        'startTime': startTime
    }

    taskName.value = '';
    taskDesc.value = '';

    $.ajax({
        url: "/task-add",
        method: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
    }).done(function(data){
        $('#main').replaceWith(data);
        $('#user-select')[0].value=user;
        $('#time-slot-tasks')[0].classList.add('start-' + startTime);
        $('#time-slot-tasks').show();
        $('#task-add').hide();
    });

}