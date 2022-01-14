window.addEventListener("DOMContentLoaded", (event) => {
  try {
    const firebaseConfig = {
      apiKey: "",
      authDomain: "",
      projectId: "",
      storageBucket: "",
      databaseURL: "",
      messagingSenderId: "",
      appId: "",
      measurementId: "",
    };
    //Initailize Firebase
    firebase.initializeApp(firebaseConfig)
        
  } catch (error) {
    console.log(error.message);
  }
//console.log(firebase)

var files=[]
var fileInput=document.getElementById('selectedImage')
let btnUpload=document.getElementById("uploadImage")
let uploadStatus=document.getElementById("uploadStatus")

fileInput.onchange=(e)=>{
  files=e.target.files
  //console.log(files)
  //console.log("Item selected")
  btnUpload.disabled=true
  uploadImage()

}
let imageURL=""
let _csrf=document.getElementById("_csrf").value
   //UPLOAD IMAGE
   let uploadImage=()=>{     
    //console.log("Button Clicked")
    let imageName=files[0].name
    let selectedUser=document.getElementById("userUUID")
    userUUID=selectedUser.value
    let fileName=`${userUUID.trim()}.png`
    let user=`${userUUID}`
    var uploadTask=firebase.storage().ref('Images/'+fileName).put(files[0])
    try {
      uploadTask.on('state_changed',(snapshot)=>{
        var progress=(snapshot.bytesTransferred/snapshot.totalBytes)*100
        uploadStatus.style.display="block"
        setInterval(() => {
          uploadStatus.innerHTML=`Loading...`
        }, 1000);
        if(progress==100){          
          uploadStatus.style.display="none"
        }
        console.log({progress:progress});
      },(error)=>{
        alert("❌ Failed to upload your profile image!")
      },()=>{
        uploadTask.snapshot.ref.getDownloadURL().then((url)=>{          
            imgURL=url
            btnUpload.disabled=false
            console.log(url)

            //Save image to firebase database
          //   firebase.database().ref('Pictures/'+user).set({
          //     Name:user,
          //     Link:imgURL
          // })
       
          let photoURL=document.getElementById("photoURL")
          photoURL.value=imgURL

          //alert('Image added successfully')
        })
      })
    } catch (error) {
      console.log(error)
    }
  }


  const sendLink=(uuid,url)=>{}


})