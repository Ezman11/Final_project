var seconds;
var countdownTimer;

function startTimer() {
  if (!seconds || seconds == 0) {
    seconds = 10;
    clearInterval(countdownTimer);
    countdownTimer = setInterval(secondPassed, 1000)
    secondPassed();
  }
}

function secondPassed() {
  var minutes = Math.round((seconds - 30) / 60);
  var remainingSeconds = seconds % 60;

  if (remainingSeconds < 10) {
    remainingSeconds = "0" + remainingSeconds;
  }

  document.getElementById('countdown').innerHTML = minutes + ":" + remainingSeconds;
  document.getElementById('countdown').disabled = true;

  if (seconds < 0) {
    clearInterval(countdownTimer);
    document.getElementById('countdown').disabled = false;
    document.getElementById('countdown').innerHTML = "OTP ใหม่";
  } else {
    seconds--;
  }
}

startTimer();