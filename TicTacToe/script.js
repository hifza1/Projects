//stores all the <button> elements in the buttons variable
let buttons = document.getElementsByTagName("button");

//need to store the h1 element with ID 'turn' to show the user
//who's turn it currently is
let currTurnHeading = document.getElementById("turn");

//for loop adds an eventListener to each button
for (let i = 0; i < buttons.length; i++) {
  let currButton = buttons[i];
  currButton.addEventListener("click", buttonPressed);
}

//need to keep track of the currentPlayer
let currPlayer = "X";

//need to keep track of the number of clicks
let numClicks = 0;

function buttonPressed(event) {
  //mark the button that was clicked with whoever's turn it is
  //AKA -> change the innerText of that button to match currPlayer
  let pressedButton = event.target;

  if (currPlayer == "X") {
    pressedButton.innerText = "X";
    currPlayer = "O";
    currTurnHeading.innerText = "O's turn";
  } else {
    pressedButton.innerText = "O";
    currPlayer = "X";
    currTurnHeading.innerText = "X's turn";
  }

  numClicks++;

  //remove eventListener when a button is clicked
  pressedButton.removeEventListener("click", buttonPressed);

  // enable the function isThereAWinner to determine
  //who the winner is
  if (isThereAWinner()) {
    disableButtons();
    if (currPlayer === "X") {
      currTurnHeading.innerText = "O wins!";
    } else {
      currTurnHeading.innerText = "X wins!";
    }
    return;
  }

  // determine if it is a tie
  if (numClicks === 9) {
    disableButtons();
    currTurnHeading.innerText = "It's a tie";
  }
}

function isThereAWinner() {
  //this function should return true if there is a current
  //winning play on the board

  if (
    buttons[0].innerText === buttons[1].innerText &&
    buttons[0].innerText === buttons[2].innerText &&
    buttons[0].innerText !== ""
  ) {
    return true;
  }
  if (
    buttons[3].innerText === buttons[4].innerText &&
    buttons[3].innerText === buttons[5].innerText &&
    buttons[3].innerText !== ""
  ) {
    return true;
  }
  if (
    buttons[6].innerText === buttons[7].innerText &&
    buttons[6].innerText === buttons[8].innerText &&
    buttons[6].innerText !== ""
  ) {
    return true;
  }

  if (
    buttons[0].innerText === buttons[3].innerText &&
    buttons[0].innerText === buttons[6].innerText &&
    buttons[0].innerText !== ""
  ) {
    return true;
  }
  if (
    buttons[1].innerText === buttons[4].innerText &&
    buttons[1].innerText === buttons[7].innerText &&
    buttons[1].innerText !== ""
  ) {
    return true;
  }
  if (
    buttons[2].innerText === buttons[5].innerText &&
    buttons[2].innerText === buttons[8].innerText &&
    buttons[2].innerText !== ""
  ) {
    return true;
  }

  if (
    buttons[0].innerText === buttons[4].innerText &&
    buttons[0].innerText === buttons[8].innerText &&
    buttons[0].innerText !== ""
  ) {
    return true;
  }

  if (
    buttons[2].innerText === buttons[4].innerText &&
    buttons[2].innerText === buttons[6].innerText &&
    buttons[2].innerText !== ""
  ) {
    return true;
  }

  return false;
}

// to disable the buttons that were not pressed after a winner is determined.
function disableButtons() {
  for (let i = 0; i < buttons.length; i++) {
    buttons[i].disabled = true;
  }
}
