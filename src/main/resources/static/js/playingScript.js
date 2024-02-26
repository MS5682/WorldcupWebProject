/*
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

leftCandiName = document.querySelector('.leftName')
rightCandiName = document.querySelector('.rightName')
leftImg = document.querySelector('.oddImg')
rightImg = document.querySelector('.evenImg')*/
