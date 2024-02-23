$(document).ready(function () {

    $('.modal').modal('show');
});

$('.form-check-input').on('click', function () {
    if ($('.form-check-input').is(':checked')) {
        $('.limitTime').css("visibility", "visible");
    } else {
        $('.limitTime').css("visibility", "hidden");
    }
})

$('.totalRound').on('click', function () {
    if ($('.totalRound').val() == 0) {
        $('.okButton').css("visibility", "hidden")
    } else {
        $('.okButton').css("visibility", "visible")
    }
})

var timerID;
var count;

function timer(time) {
    count = time / 1000;

    timerID = setInterval(function () {
        count--;
        console.log('타이머 남은 시간 : ' + count);
        var timerText = document.querySelector('.viewTimer');
        timerText.innerHTML = '<h2>' + count + "</h2>";

        if (count == 0) {
            ranNum = Math.floor(Math.random() * 2 + 1)
            console.log('1, 2 무작위 숫자 : ' + ranNum);
            randomClick(ranNum);

            count = time / 1000;
        }
    }, 1000)
}

function shuffle(array) {
    array.sort(() => Math.random() - 0.5)
}

function randomClick(num) {
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

var allCandiList = $('#candi').val();
var outCandiList = [];

var candi = JSON.parse(allCandiList);

console.log(candi[0].name)

shuffle(candi)

console.log(candi)

leftCandiName = document.querySelector('.leftName')
rightCandiName = document.querySelector('.rightName')
leftImg = document.querySelector('.oddImg')
rightImg = document.querySelector('.evenImg')

var candiOrder = 0;

$('.okButton').on('click', function () {
    var name = document.querySelector('.worldcup-name');
    for (i = 0; i < $('.totalRound').val(); i++) {
        totalRound = totalRound * 2;
    }

    candi.length = totalRound;
    console.log(candi)

    name.innerText += ' ' + totalRound + '강';
    currentRound = totalRound;
    nextRound = totalRound / 2;

    var progressbar = document.querySelector('.progress-bar');
    progressbar.innerText = currentRound + '강';

    if ($('.form-check-input').is(':checked')) {
        console.log('고른 제한 시간 : ' + $('.limitTime').val())
        time = $('.limitTime').val() * 1000 * 60;

        timer(time);
        console.log('타이머에 적용 될 숫자 : ' + time);
    }
    $('.modal').modal('hide');

    leftCandiName.innerText = candi[candiOrder].name
    rightCandiName.innerText = candi[candiOrder + 1].name

    console.log('왼쪽 첫글자')
    console.log(candi[candiOrder].path.charAt(0))
    console.log('오른쪽 첫글자')
    console.log(candi[candiOrder + 1].path.charAt(0))

    if (candi[candiOrder].path.charAt(0) == 2) {
        leftImg.src = '../../uploads/' + candi[candiOrder].path + '/' + candi[candiOrder].uuid + '_' + candi[candiOrder].imgName
    } else if (candi[candiOrder].path.charAt(0) == 'h') {
        console.log('h 확인용')
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
        console.log('h 확인용')
        $('.right').html(
            '<iframe style="min-width: 100%; height: 80vh; margin-top: 10px"' +
            'src="https://www.youtube.com/embed/' + candi[candiOrder + 1].imgName + '">' +
            '</iframe>' +
            '<p class="text-center rightName">' + candi[candiOrder + 1].name + '</p>'
        );
    }

    startsave()

    console.log(candi)

    candiOrder += 1;
    console.log($('#worldNum').val())
})

function returnClick() {
    $('.left').css("pointer-events", "auto")
    $('.right').css("pointer-events", "auto")
}

function changeCandi() {
    console.log('배열 순서' + candiOrder)
    leftCandiName.innerText = candi[candiOrder].name
    rightCandiName.innerText = candi[candiOrder + 1].name

    if (candi[candiOrder].path.charAt(0) == 2) {
        $('.left').html(
            '<img src="../../uploads/' + candi[candiOrder].path + '/' + candi[candiOrder].uuid + '_' + candi[candiOrder].imgName + '" ' +
            'class="img-thumbnail oddImg" style="min-width: 100%; height: 80vh; margin-top: 10px">' +
            '<p class="text-center leftName">' + candi[candiOrder].name + '</p>'
        )
    } else if (candi[candiOrder].path.charAt(0) == 'h') {
        console.log('h 확인용')
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
        console.log('h 확인용')
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

    console.log('배열 이동 확인')
    console.log(candi)
    console.log(outCandiList)

    clearInterval(timerID);

    // $('.oddImg').animate({minWidth: '1500px'}, 2000)
    // $('.evenImg').animate({minWidth: '0px'}, 2000)
    // $('.oddImg').animate({minWidth: '100%'})
    // $('.evenImg').animate({minWidth: '100%'})

    $('.left').css("pointer-events", "none")
    $('.right').css("pointer-events", "none")

    currentRound -= 2;
    console.log('라운드 진행 : ' + currentRound);
    progress += 1;

    $('.progress-bar').css("width", progress / (totalRound - 1) * 100 + "%");

    checkRound();

    setTimeout(returnClick, 2000);
    setTimeout(changeCandi, 2000);

    leftsave();

    timer(time);
})

$('.right').on('click', function () {
    candi[candiOrder].win += 1;
    candi[candiOrder - 1].lose += 1;
    outCandiList.push(candi[candiOrder - 1]);
    candi.splice(candiOrder - 1, 1)

    console.log('배열 이동 확인')
    console.log(candi)
    console.log(outCandiList)

    clearInterval(timerID);

    // $('.evenImg').animate({minWidth: '1500px'}, 2000)
    // $('.oddImg').animate({minWidth: '0px'}, 2000)
    // $('.evenImg').animate({width: '100%'})
    // $('.oddImg').animate({width: '100%'})

    $('.left').css("pointer-events", "none")
    $('.right').css("pointer-events", "none")

    currentRound -= 2;
    console.log('라운드 진행 : ' + currentRound);
    progress += 1;

    $('.progress-bar').css("width", progress / (totalRound - 1) * 100 + "%");

    checkRound();

    setTimeout(returnClick, 2000);
    setTimeout(changeCandi, 2000);

    rightsave()

    timer(time);
})

function checkRound() {
    if (currentRound == 0) {
        var name = document.querySelector('.worldcup-name');
        if (nextRound > 4) {
            name.innerText = ' ' + nextRound + '강';
        } else if (nextRound == 4) {
            name.innerText = ' ' + '준결승';
        } else if (nextRound == 2) {
            name.innerText = ' ' + '결승';
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

        console.log('마지막 nextRound 체크 : ' + nextRound)
    }

    if (nextRound == 0.5) {
        alert('끝')
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

function rightsave() {
    $.ajax({
        type: 'post',
        url: 'playing/autosave',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: [candi[candiOrder]],
            loser: [outCandiList.at(-1)],
            round: nextRound,
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

function finalsave() {
    $.ajax({
        type: 'post',
        url: 'playing/finalsave',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: [candi[candiOrder]],
            loser: [outCandiList.at(-1)],
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