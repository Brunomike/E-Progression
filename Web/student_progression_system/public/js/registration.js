"use strict";
 //Toggle password visibility Registration page
 const togglePasswordReg = document.querySelector("#togglePassword1");
 const passwordLogin=document.body.querySelector('.password');
 const confirmpasswordLogin=document.body.querySelector('.confirmpassword');

    togglePasswordReg.addEventListener('click', (e) =>{       
      console.log("Clicker");         
        const pass1=passwordLogin.getAttribute('type') === 'password' ? 'text' : 'password';
        const pass2=confirmpasswordLogin.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordLogin.setAttribute('type',pass1);
        confirmpasswordLogin.setAttribute('type',pass2);
        togglePasswordReg.classList.toggle('bi-eye')   
})

// Fetch all the forms we want to apply custom Bootstrap validation styles to
var forms = document.querySelectorAll(".needs-validation");

// Loop over them and prevent submission
Array.prototype.slice.call(forms).forEach(function (form) {
  form.addEventListener(
    "submit",
    function (event) {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }

      form.classList.add("was-validated");
    },
    false
  );
});

let countriesCode = [
"ke",
"us",
"ug",
"tz",
"AF",
"AX",
"AL",
"DZ",
"AS",
"AD",
"AO",
"AI",
"AQ",
"AG",
"AR",
"AM",
"AW",
"AU",
"AT",
"AZ",
"BH",
"BS",
"BD",
"BB",
"BY",
"BE",
"BZ",
"BJ",
"BM",
"BT",
"BO",
"BQ",
"BA",
"BW",
"BV",
"BR",
"IO",
"BN",
"BG",
"BF",
"BI",
"KH",
"CM",
"CA",
"CV",
"KY",
"CF",
"TD",
"CL",
"CN",
"CX",
"CC",
"CO",
"KM",
"CG",
"CD",
"CK",
"CR",
"CI",
"HR",
"CU",
"CW",
"CY",
"CZ",
"DK",
"DJ",
"DM",
"DO",
"EC",
"EG",
"SV",
"GQ",
"ER",
"EE",
"ET",
"FK",
"FO",
"FJ",
"FI",
"FR",
"GF",
"PF",
"TF",
"GA",
"GM",
"GE",
"DE",
"GH",
"GI",
"GR",
"GL",
"GD",
"GP",
"GU",
"GT",
"GG",
"GN",
"GW",
"GY",
"HT",
"HM",
"VA",
"HN",
"HK",
"HU",
"IS",
"IN",
"ID",
"IR",
"IQ",
"IE",
"IM",
"IL",
"IT",
"JM",
"JP",
"JE",
"JO",
"KZ",
"KI",
"KP",
"KR",
"KW",
"KG",
"LA",
"LV",
"LB",
"LS",
"LR",
"LY",
"LI",
"LT",
"LU",
"MO",
"MK",
"MG",
"MW",
"MY",
"MV",
"ML",
"MT",
"MH",
"MQ",
"MR",
"MU",
"YT",
"MX",
"FM",
"MD",
"MC",
"MN",
"ME",
"MS",
"MA",
"MZ",
"MM",
"NA",
"NR",
"NP",
"NL",
"NC",
"NZ",
"NI",
"NE",
"NG",
"NU",
"NF",
"MP",
"NO",
"OM",
"PK",
"PW",
"PS",
"PA",
"PG",
"PY",
"PE",
"PH",
"PN",
"PL",
"PT",
"PR",
"QA",
"RE",
"RO",
"RU",
"RW",
"BL",
"SH",
"KN",
"LC",
"MF",
"PM",
"VC",
"WS",
"SM",
"ST",
"SA",
"SN",
"RS",
"SC",
"SL",
"SG",
"SX",
"SK",
"SI",
"SB",
"SO",
"ZA",
"GS",
"SS",
"ES",
"LK",
"SD",
"SR",
"SJ",
"SZ",
"SE",
"CH",
"SY",
"TW",
"TJ",
"TZ",
"TH",
"TL",
"TG",
"TK",
"TO",
"TT",
"TN",
"TR",
"TM",
"TC",
"TV",
"UG",
"UA",
"AE",
"GB",
"UM",
"UY",
"UZ",
"VU",
"VE",
"VN",
"VG",
"VI",
"WF",
"EH",
"YE",
"ZM",
"ZW",
];
let phoneInputField = document.body.querySelector("#phoneNumber")
//let phoneInputField1= document.body.querySelector("#phoneNumber1")

let phoneInput = window.intlTelInput(phoneInputField, {
preferredCountries: ["ke", "ug", "tz"],
utilsScript:
  "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js",
});


document.body.querySelector("#phoneNumber").addEventListener("focusout", (e) => {
  const phoneNumber = phoneInput.getNumber();

  let sendPhoneNumber = document.body.querySelector("#send");
  sendPhoneNumber.value = phoneNumber;
});


// let phoneInput1=window.intlTelInput(phoneInputField1,{
//   preferredCountries: ["ke", "ug", "tz"],
// utilsScript:
//   "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js",
// })

// document.body.querySelector("#phoneNumber1").addEventListener("focusout",(e)=>{
//   const phoneNumber = phoneInput1.getNumber();

//   let sendPhoneNumber = document.body.querySelector("#send1");
//   sendPhoneNumber.value = phoneNumber;
// })





