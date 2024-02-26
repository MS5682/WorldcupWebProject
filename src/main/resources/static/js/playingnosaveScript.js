var saveplay = true

$(document).ready(function () { // 세이브 파일 체크
    if ($('#issave').val() == 'true') {
        console.log('저장있음')
        if (confirm('저장된 월드컵이 있습니다. 이어서 진행하시겠습니까?')) {
            $.ajax({
                type: 'get',
                url: '/play/playing/loadsave',
                data: {
                    userId: 'test1234',
                    worldcupId: $('#worldNum').val()
                },
                dataType: 'text',
                success: function (result) {
                    console.log(result);
                },
                error: function (request, status, error) {
                    console.log(error)
                }
            })
        } else {
            $('.modal').modal('show');
        }
    } else {
        console.log('저장없음')
        $('.modal').modal('show');
    }
});

$('.form-check-input').on('click', function () {    // 타이머 설정 시 타이머 시간 보이게 하기
    if ($('.form-check-input').is(':checked')) {
        $('.limitTime').css("visibility", "visible");
    } else {
        $('.limitTime').css("visibility", "hidden");
    }
})

$('.totalRound').on('click', function () {  // 전체 라운드 선택 안할시 확인 버튼 안보이게 하기
    if ($('.totalRound').val() == 0) {
        $('.okButton').css("visibility", "hidden")
    } else {
        $('.okButton').css("visibility", "visible")
    }
})

// 타이머 기능
var timerID;
var count;

function timer(time) {
    count = time / 1000;

    timerID = setInterval(function () {
        count--;
        var timerText = document.querySelector('.viewTimer');
        timerText.innerHTML = '<h2>' + count + "</h2>";

        if (count == 0) {
            ranNum = Math.floor(Math.random() * 2 + 1)
            randomClick(ranNum);

            count = time / 1000;
        }
    }, 1000)
}

// 타이머 기능 끝

function shuffle(array) {   // 후보 섞기
    array.sort(() => Math.random() - 0.5)
}

function randomClick(num) { // 타이머 시간 끝나면 랜덤하기 클릭
    if (num == 1) {
        $('.oddImg').click();
    } else if (num == 2) {
        $('.evenImg').click();
    }
}

var totalRound = 1;
var currentRound;
var nextRound;
var progress = 0;

// 후보 받고
var allCandiList
var outCandiList = [];

var candi


// function savestart() {
//     console.log('saveplay 확인용')
//     console.log(saveplay)
//
//     if (saveplay) { // 저장한걸로 하면 false
//         allCandiList = $('#candi').val();
//         candi = JSON.parse(allCandiList);
//         shuffle(candi)  // 후보 섞음
//     } else {
//         // 확인 버튼을 안누르기 때문에 확인 버튼 누르면 하는걸 여기서 해야함
//         allCandiList = $('#candi').val();
//         console.log('allcandilist')
//         console.log(allCandiList)
//         candi = allCandiList;
//         console.log('저장된 후보들')
//         console.log(candi)
//     }
// }

allCandiList = $('#candi').val();
candi = JSON.parse(allCandiList);
shuffle(candi)

leftCandiName = document.querySelector('.leftName')
rightCandiName = document.querySelector('.rightName')
leftImg = document.querySelector('.oddImg')
rightImg = document.querySelector('.evenImg')

var candiOrder = 0;

var title = $('.worldcup-name').text();

$('.okButton').on('click', function () {    // 사직 버튼 누르면 월드컵 초기 세팅
    var name = document.querySelector('.worldcup-name');
    for (i = 0; i < $('.totalRound').val(); i++) {
        totalRound = totalRound * 2;
    }

    candi.length = totalRound;

    name.innerText = title + ' ' + totalRound + '강';
    currentRound = totalRound;
    nextRound = totalRound / 2;

    var progressbar = document.querySelector('.progress-bar');
    progressbar.innerText = currentRound + '강';

    if ($('.form-check-input').is(':checked')) {
        console.log('고른 제한 시간 : ' + $('.limitTime').val())
        time = $('.limitTime').val() * 1000 * 60;

        timer(time);
    }
    $('.modal').modal('hide');

    leftCandiName.innerText = candi[candiOrder].name
    rightCandiName.innerText = candi[candiOrder + 1].name

    if (candi[candiOrder].path.charAt(0) == 2) {
        leftImg.src = '../../uploads/' + candi[candiOrder].path + '/' + candi[candiOrder].uuid + '_' + candi[candiOrder].imgName
    } else if (candi[candiOrder].path.charAt(0) == 'h') {
        $('.left').html(
            '<iframe style="min-width: 100%; height: 80vh; margin-top: 10px"' +
            'src="https://www.youtube.com/embed/' + candi[candiOrder].imgName + '">' +
            '</iframe>' +
            '<p class="text-center leftName">' + candi[candiOrder].name + '</p>'
        );

    }

    if (candi[candiOrder + 1].path.charAt(0) == 2) {
        rightImg.src = '../../uploads/' + candi[candiOrder + 1].path + '/' + candi[candiOrder + 1].uuid + '_' + candi[candiOrder + 1].imgName
    } else if (candi[candiOrder + 1].path.charAt(0) == 'h') {
        $('.right').html(
            '<iframe style="min-width: 100%; height: 80vh; margin-top: 10px"' +
            'src="https://www.youtube.com/embed/' + candi[candiOrder + 1].imgName + '">' +
            '</iframe>' +
            '<p class="text-center rightName">' + candi[candiOrder + 1].name + '</p>'
        );
    }

    startsave()

    candiOrder += 1;
})

function returnClick() {
    $('.left').css("pointer-events", "auto")
    $('.right').css("pointer-events", "auto")
}

function changeCandi() {
    leftCandiName.innerText = candi[candiOrder].name
    rightCandiName.innerText = candi[candiOrder + 1].name

    if (candi[candiOrder].path.charAt(0) == 2) {
        $('.left').html(
            '<img src="../../uploads/' + candi[candiOrder].path + '/' + candi[candiOrder].uuid + '_' + candi[candiOrder].imgName + '" ' +
            'class="img-thumbnail oddImg" style="min-width: 100%; height: 80vh; margin-top: 10px">' +
            '<p class="text-center leftName">' + candi[candiOrder].name + '</p>'
        )
    } else if (candi[candiOrder].path.charAt(0) == 'h') {
        $('.left').html(
            '<iframe style="min-width: 100%; height: 80vh; margin-top: 10px"' +
            'src="https://www.youtube.com/embed/' + candi[candiOrder].imgName + '">' +
            '</iframe>' +
            '<p class="text-center leftName">' + candi[candiOrder].name + '</p>'
        );
    }

    if (candi[candiOrder + 1].path.charAt(0) == 2) {
        $('.right').html(
            '<img src="../../uploads/' + candi[candiOrder + 1].path + '/' + candi[candiOrder + 1].uuid + '_' + candi[candiOrder + 1].imgName + '" ' +
            'class="img-thumbnail evenImg" style="min-width: 100%; height: 80vh; margin-top: 10px">' +
            '<p class="text-center rightName">' + candi[candiOrder + 1].name + '</p>'
        )
    } else if (candi[candiOrder + 1].path.charAt(0) == 'h') {
        $('.right').html(
            '<iframe style="min-width: 100%; height: 80vh; margin-top: 10px"' +
            'src="https://www.youtube.com/embed/' + candi[candiOrder + 1].imgName + '">' +
            '</iframe>' +
            '<p class="text-center rightName">' + candi[candiOrder + 1].name + '</p>'
        );
    }

    candiOrder += 1;
}

$('.left').on('click', function () {
    candi[candiOrder - 1].win += 1;
    candi[candiOrder].lose += 1;
    outCandiList.push(candi[candiOrder]);
    candi.splice(candiOrder, 1)

    if (candi.length <= 1) {
        candi[0].first += 1;
    }

    clearInterval(timerID);

    // $('.oddImg').animate({minWidth: '1500px'}, 2000)
    // $('.evenImg').animate({minWidth: '0px'}, 2000)
    // $('.oddImg').animate({minWidth: '100%'})
    // $('.evenImg').animate({minWidth: '100%'})

    $('.left').css("pointer-events", "none")
    $('.right').css("pointer-events", "none")

    currentRound -= 2;
    progress += 1;

    $('.progress-bar').css("width", progress / (totalRound - 1) * 100 + "%");

    leftsave();

    checkRound();

    setTimeout(returnClick, 2000);
    setTimeout(changeCandi, 2000);

    timer(time);
})

$('.right').on('click', function () {
    candi[candiOrder].win += 1;
    candi[candiOrder - 1].lose += 1;
    outCandiList.push(candi[candiOrder - 1]);
    candi.splice(candiOrder - 1, 1)

    if (candi.length <= 1) {
        candi[0].first += 1;
    }

    clearInterval(timerID);

    // $('.evenImg').animate({minWidth: '1500px'}, 2000)
    // $('.oddImg').animate({minWidth: '0px'}, 2000)
    // $('.evenImg').animate({width: '100%'})
    // $('.oddImg').animate({width: '100%'})

    $('.left').css("pointer-events", "none")
    $('.right').css("pointer-events", "none")

    currentRound -= 2;
    progress += 1;

    $('.progress-bar').css("width", progress / (totalRound - 1) * 100 + "%");

    rightsave()

    checkRound();

    setTimeout(returnClick, 2000);
    setTimeout(changeCandi, 2000);

    timer(time);
})

function checkRound() {
    if (currentRound == 0) {
        var name = document.querySelector('.worldcup-name');
        if (nextRound > 4) {
            name.innerText = title + ' ' + nextRound + '강';
        } else if (nextRound == 4) {
            name.innerText = title + ' ' + '준결승';
        } else if (nextRound == 2) {
            name.innerText = title + ' ' + '결승';
        }

        var progressbar = document.querySelector('.progress-bar');
        if (nextRound > 4) {
            progressbar.innerText = ' ' + nextRound + '강';
        } else if (nextRound == 4) {
            progressbar.innerText = ' ' + '준결승';
        } else if (nextRound == 2) {
            progressbar.innerText = ' ' + '결승';
        }

        candiOrder = 0;

        currentRound = nextRound;
        nextRound = currentRound / 2;
    }

    if (nextRound == 0.5) {
        alert('결과창으로 넘어가기')

        clearInterval(timerID)

        finalsave()
    }
}

function startsave() {
    $.ajax({
        type: 'post',
        url: 'playing/save',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: candi,
            round: nextRound * 2,
            worldNum: $('#worldNum').val()
        }),
        success: function (result) {
            console.log(result)
        },
        error: function (request, status, error) {
            console.log(error)
        }
    })
}

function leftsave() {
    $.ajax({
        type: 'post',
        url: 'playing/autosave',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: [candi[candiOrder - 1]],
            loser: [outCandiList.at(-1)],
            round: nextRound,
            userId: 'test1234',
            worldNum: $('#worldNum').val()
        }),
        success: function (result) {
            console.log(result)
        },
        error: function (request, status, error) {
            alert(error);
        }
    })
}

function rightsave() {
    $.ajax({
        type: 'post',
        url: 'playing/autosave',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: [candi[candiOrder]],
            loser: [outCandiList.at(-1)],
            round: nextRound,
            userId: 'test1234',
            worldNum: $('#worldNum').val()
        }),
        success: function (result) {
            console.log(result)
        },
        error: function (request, status, error) {
            alert(error)
        }
    })
}

function finalsave() {
    $.ajax({
        type: 'post',
        url: 'playing/finalsave',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: [candi[0]],
            loser: [outCandiList.at(-1)],
            userId: 'test1234',
            worldNum: $('#worldNum').val()
        }),
        success: function (result) {
            // location.replace("playResult?worldCupID=" + $('#worldNum').val())
        },
        error: function (request, status, error) {
            console.log(error)
        }
    })
}