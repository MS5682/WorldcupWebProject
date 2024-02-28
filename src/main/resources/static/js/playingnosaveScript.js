var saveplay = true
var userId = $('#userId').val();

$(document).ready(function () { // 세이브 파일 체크
    if ($('#issave').val() == 'true') {
        if (confirm('저장된 월드컵이 있습니다. 이어서 진행하시겠습니까?')) {
            saveplay = false;

            $.ajax({
                type: 'get',
                url: '/play/playing/loadsave',
                data: {
                    userId: userId,
                    worldcupId: $('#worldNum').val()
                },
                dataType: 'text',
                success: function (result) {
                    candi = JSON.parse(result);

                    $.ajax({
                        type: 'get',
                        url: '/play/playing/next',
                        data: {
                            userId: userId,
                            worldcupId: $('#worldNum').val()
                        },
                        success: function (result) {
                            nextVal = result;
                            for (i=0;i<candi.length;i++) {
                                candi[i].roundnext = nextVal[i]
                            }

                            savestart()

                            saveroundmark()
                        }
                    })
                },
                error: function (request, status, error) {
                    alert('불러오기 실패')
                }
            })
        } else {
            // 저장 삭제 ajax 추가
            $.ajax({
                type: 'get',
                url: '/play/playing/savedelete',
                data: {
                    userId: userId,
                    worldcupId: $('#worldNum').val()
                },
                success: function (result) {
                    $('.modal').modal('show');

                    savestart()
                },
                error: function (request, status, error) {
                    alert('삭제 실패')
                }
            })
        }
    } else {
        $('.modal').modal('show');

        savestart()
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
var currentRound = 0;
var nextRound;
var progress = 0;

// 후보 받고
var allCandiList
var outCandiList = [];

var candi


function savestart() {

    if (saveplay) { // 저장한걸로 하면 false
        allCandiList = $('#candi').val();
        candi = JSON.parse(allCandiList);
        shuffle(candi)  // 후보 섞음
    } else {
        // 확인 버튼을 안누르기 때문에 확인 버튼 누르면 하는걸 여기서 해야함
        lastround = candi[candi.length - 1].roundnext;
        for (i=0;i<candi.length;i++) {
            if (candi[i].roundnext < lastround) {
                candiOrder++
            }

            if (candi[i].roundnext == lastround) {
                currentRound++;
            }
        }

        if (candi.length < 65) {
            nextRound = 32
        } else if (candi.length < 33) {
            nextRound = 16
        } else if (candi.length < 17) {
            nextRound = 8
        } else if (candi.length < 9) {
            nextRound = 4
        } else if (candi.length < 5) {
            nextRound = 2
        }

        leftCandiName.innerText = candi[candiOrder].name
        rightCandiName.innerText = candi[candiOrder + 1].name

        if (candi[candiOrder].path.charAt(0) == 2) {
            leftImg.src = '/worldcup/choice/display?fileName=' + candi[candiOrder].path + '/' + candi[candiOrder].uuid + '_' + candi[candiOrder].imgName
        } else if (candi[candiOrder].path.charAt(0) == 'h') {
            $('.left').html(
                '<iframe style="min-width: 100%; height: 80vh; margin-top: 10px"' +
                'src="https://www.youtube.com/embed/' + candi[candiOrder].imgName + '">' +
                '</iframe>' +
                '<p class="text-center leftName">' + candi[candiOrder].name + '</p>'
            );

        }

        if (candi[candiOrder + 1].path.charAt(0) == 2) {
            rightImg.src = '/worldcup/choice/display?fileName=' + candi[candiOrder + 1].path + '/' + candi[candiOrder + 1].uuid + '_' + candi[candiOrder + 1].imgName
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
}

leftCandiName = document.querySelector('.leftName')
rightCandiName = document.querySelector('.rightName')
leftImg = document.querySelector('.oddImg')
rightImg = document.querySelector('.evenImg')

var candiOrder = 0;

var title = $('.worldcup-name').text();

var intext = '';

$('.okButton').on('click', function () {    // 사직 버튼 누르면 월드컵 초기 세팅
    var name = document.querySelector('.worldcup-name');
    for (i = 0; i < $('.totalRound').val(); i++) {
        totalRound = totalRound * 2;
    }

    candi.length = totalRound;

    currentRound = totalRound;
    nextRound = totalRound / 2;

    name.innerText = title + ' ' + totalRound + '강' + ' ' + (candiOrder+1) + ' / ' + nextRound;

    intext = title + ' ' + (nextRound * 2) + '강';

    var progressbar = document.querySelector('.progress-bar');
    progressbar.innerText = currentRound + '강';

    if ($('.form-check-input').is(':checked')) {
        time = $('.limitTime').val() * 1000 * 60;

        timer(time);
    }
    $('.modal').modal('hide');

    leftCandiName.innerText = candi[candiOrder].name
    rightCandiName.innerText = candi[candiOrder + 1].name

    if (candi[candiOrder].path.charAt(0) == 2) {
        leftImg.src = '/worldcup/choice/display?fileName=' + candi[candiOrder].path + '/' + candi[candiOrder].uuid + '_' + candi[candiOrder].imgName
    } else if (candi[candiOrder].path.charAt(0) == 'h') {
        $('.left').html(
            '<iframe style="min-width: 100%; height: 80vh; margin-top: 10px"' +
            'src="https://www.youtube.com/embed/' + candi[candiOrder].imgName + '">' +
            '</iframe>' +
            '<p class="text-center leftName">' + candi[candiOrder].name + '</p>'
        );

    }

    if (candi[candiOrder + 1].path.charAt(0) == 2) {
        rightImg.src = '/worldcup/choice/display?fileName=' + candi[candiOrder + 1].path + '/' + candi[candiOrder + 1].uuid + '_' + candi[candiOrder + 1].imgName
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

function saveroundmark() {
    var name = document.querySelector('.worldcup-name');

    name.innerText = title + ' ' + (nextRound * 2) + '강' + '  ' + (candiOrder+1) + ' / ' + nextRound;

    intext = title + ' ' + (nextRound * 2) + '강';


    var progressbar = document.querySelector('.progress-bar');
    progressbar.innerText = (nextRound * 2) + '강';

    progress = candiOrder

    $('.progress-bar').css("width", progress / (totalRound - 1) * 100 + "%");

}

function returnClick() {
    $('.left').css("pointer-events", "auto")
    $('.right').css("pointer-events", "auto")
}

function changeCandi() {
    leftCandiName.innerText = candi[candiOrder].name
    rightCandiName.innerText = candi[candiOrder + 1].name

    if (candi[candiOrder].path.charAt(0) == 2) {
        $('.left').html(
            '<img src="/worldcup/choice/display?fileName=' + candi[candiOrder].path + '/' + candi[candiOrder].uuid + '_' + candi[candiOrder].imgName + '" ' +
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
            '<img src="/worldcup/choice/display?fileName=' + candi[candiOrder + 1].path + '/' + candi[candiOrder + 1].uuid + '_' + candi[candiOrder + 1].imgName + '" ' +
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

    checkRound(candi[0].choiceNum);

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

    checkRound(candi[0].choiceNum);

    setTimeout(returnClick, 2000);
    setTimeout(changeCandi, 2000);

    timer(time);
})


function checkRound(choiceNum) {
    var name = document.querySelector('.worldcup-name');
    var roundmark = '   ' + (candiOrder + 1) + ' / ' + nextRound;
    if (currentRound == 0) {
        if (nextRound > 4) {
            name.innerText = title + ' ' + nextRound + '강';
            intext = name.innerText
            name.innerText += ' ' + '1 / ' + ( nextRound / 2 );
        } else if (nextRound == 4) {
            name.innerText = title + ' ' + '준결승';
            intext = name.innerText
            name.innerText += ' ' + '1 / ' + ( nextRound / 2 );
        } else if (nextRound == 2) {
            name.innerText = title + ' ' + '결승';
            intext = name.innerText
            name.innerText += ' ' + '1 / ' + ( nextRound / 2 );
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
    } else {
        name.innerText = intext + roundmark;
    }

    if (nextRound == 0.5) {
        alert('결과창으로 넘어가기')

        clearInterval(timerID)

        if (userId == '') {
            nologinsave(choiceNum)
        } else {
            finalsave(choiceNum)
        }
    }
}

function startsave() {
    if (userId != '') {
        $.ajax({
            type: 'post',
            url: 'playing/save',
            contentType: 'application/json',
            data: JSON.stringify({
                winner: candi,
                roundNext: nextRound * 2,
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

}

function leftsave() {
    if (userId != '') {
        $.ajax({
            type: 'post',
            url: 'playing/autosave',
            contentType: 'application/json',
            data: JSON.stringify({
                winner: [candi[candiOrder - 1]],
                loser: [outCandiList.at(-1)],
                roundNext: nextRound,
                userId: userId,
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

}

function rightsave(choiceNum) {
    if (userId != '') {
        $.ajax({
            type: 'post',
            url: 'playing/autosave',
            contentType: 'application/json',
            data: JSON.stringify({
                winner: [candi[candiOrder]],
                loser: [outCandiList.at(-1)],
                roundNext: nextRound,
                userId: userId,
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
}

function finalsave(choiceNum) {
    $.ajax({
        type: 'post',
        url: 'playing/finalsave',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: [candi[0]],
            loser: [outCandiList.at(-1)],
            userId: userId,
            worldNum: $('#worldNum').val()
        }),
        success: function (result) {

            // 이긴거 번호 보내기
            location.replace("playResult?worldcupNum=" + $('#worldNum').val() + "&choiceNum=" + choiceNum);
        },
        error: function (request, status, error) {
            console.log(error)
        }
    })
}

function nologinsave(choiceNum) {
    $.ajax({
        type: 'post',
        url: 'playing/nologinsave',
        contentType: 'application/json',
        data: JSON.stringify({
            winner: candi,
            loser: outCandiList,
            worldNum: $('#worldNum').val()
        }),
        success: function (result) {
            console.log(result);
            // 이긴거 번호 보내기
            location.replace("playResult?worldcupNum=" + $('#worldNum').val() + "&choiceNum=" + choiceNum)
        },
        error: function (request, status, error) {
            console.log(error)
        }
    })
}