/*!
 * Start Bootstrap - SB Admin v7.0.4 (https://startbootstrap.com/template/sb-admin)
 * Copyright 2013-2021 Start Bootstrap
 * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
 */
//
// Scripts
//
window.addEventListener("DOMContentLoaded", (event) => {
  checkScreenWidth();
  // Toggle the side navigation
  const sidebarToggle = document.body.querySelector("#sidebarToggle");
  if (sidebarToggle) {
    // Uncomment Below to persist sidebar toggle between refreshes
    if (localStorage.getItem("sb|sidebar-toggle") === "true") {
      document.body.classList.toggle("sb-sidenav-toggled");
    }
    sidebarToggle.addEventListener("click", (event) => {
      event.preventDefault();
      document.body.classList.toggle("sb-sidenav-toggled");
      localStorage.setItem(
        "sb|sidebar-toggle",
        document.body.classList.contains("sb-sidenav-toggled")
      );
    });
  }
  const year = document.querySelector("#currentYear");
  year.textContent = new Date().getFullYear();

  const date = new Date();
  const [month, dayValue, currentYear, dayNameInt] = [
    date.getMonth(),
    date.getDate(),
    date.getFullYear(),
    date.getDay()
  ];
  let dayText = "";
  switch (dayNameInt) {
    case 0:
      dayText = "Sun";
      break;
    case 1:
      dayText = "Mon";
      break;
    case 2:
      dayText = "Tue";
      break;
    case 3:
      dayText = "Wed";
      break;
    case 4:
      dayText = "Thur";
      break;
    case 5:
      dayText = "Fri";
      break;
    case 6:
      dayText = "Sat";
      break;
    default:
      break;
  }
  let monthText = "";
  switch (month) {
    case 0:
      monthText = "January";
      break;
    case 1:
      monthText = "February";
      break;
    case 2:
      monthText = "March";
      break;
    case 3:
      monthText = "April";
      break;
    case 4:
      monthText = "May";
      break;
    case 5:
      monthText = "June";
      break;
    case 6:
      monthText = "July";
      break;
    case 7:
      monthText = "August";
      break;
    case 8:
      monthText = "September";
      break;
    case 9:
      monthText = "October";
      break;
    case 10:
      monthText = "November";
      break;
    case 11:
      monthText = "December";
      break;
  }

  const today = `${dayText}, ${dayValue} ${monthText} ${currentYear}`;
  const todayText=document.querySelector("#todayText")
  todayText.textContent=today
  console.log("Today: ",today);


});

function getDate(dateString) {
  let dateStr = dateString;
  let d = new Date(dateStr).toISOString();
  let n = d.split("T");
  let date = n[0];
  return date;
}

function calculateAge(dob) {
  var dob = new Date(dob);
  //calculate month difference from current date in time
  var month_diff = Date.now() - dob.getTime();

  //convert the calculated difference in date format
  var age_dt = new Date(month_diff);

  //extract year from date
  var year = age_dt.getUTCFullYear();

  //now calculate the age of the user
  var age = Math.abs(year - 1970);

  //display the calculated age
  //console.log("Age of the date entered: " + age + " years");
  return age;
}

function checkScreenWidth() {
  if (window.screen.width < 800) {
    document.body.classList.toggle("sb-sidenav-toggled");
  }
}
