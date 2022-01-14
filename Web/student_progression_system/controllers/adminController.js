const fs=require("fs")
const {User,Fees,ExamType,Parent,News,Teacher,Stream,Dorm,ExamResults,Student,Subject,Classroom}=require("../models")
const { Op } = require("sequelize")
const bcrypt=require('bcrypt')
const saltRounds=10
const excelToJson=require('convert-excel-to-json')
const router = require("../routes/api")


function getCurrentUser(req){
    const app=req.app
    const userInfo=app.get('currentUser')
    return userInfo
}
function validatePassword(p){
        const errors = [];
        if (p.length < 8) {
            errors.push("❌ Password must be at least 8 characters\n");        
        }
        if (p.length > 32) {
            errors.push("❌ Password must be at max 32 characters\n");        
        }
        if (p.search(/[a-z]/) < 0) {
            errors.push("❌ Password must contain at least one lower case letter.\n");         
        }
        if (p.search(/[A-Z]/) < 0) {
            errors.push("❌ Password must contain at least one upper case letter.\n");         
        }
    
        if (p.search(/[0-9]/) < 0) {
            errors.push("❌ Password must contain at least one digit.\n");        
        }
       if (p.search(/[!@#\$%\^&\*_]/) < 0) {
            errors.push("❌ Password must contain at least one special char from -[ ! @ # $ % ^ & * _ ]\n");         
        }
        if (errors.length > 0) {
            //console.log(errors.join("\n"));
            //return false;
            //return errors.join('\n')        
            return [false,errors]
        }else{
            return [true];
        }                
}

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

const today = `${dayText}, ${dayValue} ${monthText} ${currentYear}`
//console.log(today);

exports.home=async(req,res)=>{
  //console.log({token:req.csrfToken()});
  try {
    const admins=await User.count({where:{[Op.or]: [{ user_role:"admin"}, { user_role:"superadmin"}]}})                    
      const parents=await  User.count({where:{user_role:"parent"}})
      const academics=await News.count({where:{news_category:"academics"}})
      const events=await News.count({where:{news_category:"events"}})
      const examsDone=await ExamType.count()     
      const latestExam=await ExamType.findOne({        
        order: [ [ 'createdAt', 'DESC' ]],
        })
     const totalTransactions=await Fees.count()
     const debit=await Fees.sum('debit')
     const credit=await Fees.sum('credit')
     const totalBalance=parseInt(credit-debit)
        
     
        res.render("admin/home", {
            msgSuccessDashboard: req.flash("msgSuccessDashboard"),
            msgFailureDashboard: req.flash("msgFailureDashboard"),            
            userInfo:getCurrentUser(req),
            admins:admins,
            parents:parents,
            academics:academics,
            events:events,
            examsDone:examsDone,
            latestExam:latestExam.exam_name,
            totalTransactions:totalTransactions,
            totalBalance:totalBalance,
            today:today
          })         
      
} catch (error) {
    //res.status(500).json({error: error.message})
    res.status(500).render('error',{error:error})
}

}

exports.user_management=async(req,res)=>{
    res.redirect('/user/')
}

exports.add_user=async(req,res)=>{
    const {first_name,phone_number,last_name,year_of_birth,email_address,user_status,user_role,user_password,user_gender,user_confirm_password,sent_number}=req.body
    try {
        const users= await User.findAll()
        if(users.length>0){
            //App contains several users
            users.forEach(user => {
                if(user.email_address===email_address){
                    //todo add flash messages to the admin panel
                    //req.flash("msgFailureReg",`❌ User ${email_address} already exists!`)
                    //console.log("User already exists!");
                    res.redirect('/admin/user-management')
                }                               
            })
            if(user_password===user_confirm_password){
                let passCheck=validatePassword(user_password)
                if (passCheck[0]===true) {
                    bcrypt.hash(user_password,saltRounds,async(err,hash) => {
                        if(err){
                            //console.log(err.message);
                            res.status(500).render('error',{error:err})
                        }else{                            
                          try {
                            const newUser=await User.create({
                                email_address:email_address,
                                first_name:first_name,
                                last_name:last_name,
                                phone_number:sent_number,
                                user_gender:user_gender,
                                user_role:user_role,
                                user_status:user_status,
                                year_of_birth:year_of_birth,
                                user_password:hash,
                                user_confirm_password:hash
                            })
                            
                            req.flash("msgSuccessDashboard","✔ Account created successfully!")
                            //console.log("User created successfully!")
                            return res.status(200).redirect('/admin/user-management')
                          } catch (error) {
                            //res.status(500).json(error,message)
                            res.status(500).render('error',{error:error})
                          }                                    
                        }
                    })
                }else{
                    //Password did not meet set criteria    
                    req.flash("msgFailureDashboard",`${passCheck[1]}`)
                    res.redirect('/admin/user-management')
                }
            }else{
                req.flash('msgFailureDashboard',"❌ Passwords do not match!")
                res.redirect('/admin/user-management')
            } 
        }else{
            //No existing users
            if(user_password===user_confirm_password){
                let passCheck=validatePassword(user_password)
                if (passCheck[0]===true) {
                    bcrypt.hash(user_password,saltRounds,async(err,hash) => {
                        if(err){
                            //console.log(err.message);
                            res.status(500).render('error',{error:err})
                        }else{                            
                          try {
                            const newUser=await User.create({
                                email_address:email_address,
                                first_name:first_name,
                                last_name:last_name,
                                phone_number:sent_number,
                                user_gender:user_gender,
                                user_role:"admin",
                                user_status:"disabled",
                                year_of_birth:year_of_birth,
                                user_password:hash,
                                user_confirm_password:hash
                            })
                            req.flash("msgSuccessDashboard","✔ User added successfully!")
                            return res.status(200).redirect('/admin/user-management')
                          } catch (error) {
                              //res.status(500).json(error)
                              res.status(500).render('error',{error:error})
                          }
                            
                        }
                    })
                }else{
                    //Password did not meet set criteria   
                    req.flash("msgFailureDashboard",`${passCheck[1]}`)
                    res.redirect('/admin/user-management')

                    // res.render('auth/registration',{        
                    //      msgSuccessReg: req.flash("msgSuccessReg"),
                    //     msgFailureReg: req.flash("msgFailureReg"),
                    //     msgError: req.flash("msgError"),
                    //     user_Info:[user_Info],
                    //     msgError:passCheck[1],
                    //     csrfToken: req.csrfToken() 
                    // })
                }
            }else{
                req.flash('msgFailureDashboard',"❌ Passwords do not match!")
                res.redirect('/admin/user-management')
            }
        }
    } catch (error) {
     //res.status(500).json(error.message)
     res.status(500).render('error',{error:error})
    }
}

exports.file_management=async(req,res)=>{
    let folderPath = './public/uploads';
     try {
        const files =await fs.readdir(folderPath, (err, files) => {            
            if (err) {
              //console.log(err);
              res.status(500).render('error',{error:err})
            }                    
                res.render('admin/file-management',{
                    msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                    msgFailureDashboard: req.flash("msgFailureDashboard"),
                    userInfo:getCurrentUser(req),
                    files: files,
                    csrfToken: req.csrfToken() ,
                    today:today
                })            
          });
     } catch (error) {
         //res.status(500).json(error.message);
         res.status(500).render('error',{error:error})
     } 
}

exports.add_file=async(req,res)=>{
    let folderPath = './public/uploads';
    //console.log(req.files);
    try {
        if (req.files==null) {
            req.flash("msgFailureDashboard", "❌ No files were selected!");
            res.redirect("/admin/file-management");
          } else {
            let files = req.files.sentFiles
            if (files.length >= 2) {
              files.forEach((file) => {
                let fileName = file.name
                file.mv("./public/uploads/" + fileName, (err) => {
                  if (err) {                    
                    //console.log(err)
                    res.status(500).render('error',{error:err})
                  } else {
                    //console.log(fileName + " Uploaded!")
                  }
                })
              })
              req.flash("msgSuccessDashboard", "✔ Files Uploaded Successfully!")              
              res.redirect("/admin/file-management")
              
              //console.log("Multiple files Uploaded!");
            } else {
              let fileName = files.name
              files.mv("./public/uploads/" + fileName, (err) => {
                if (err) {
                  //console.log(err)
                  res.status(500).render('error',{error:err})
                } else {
                  //console.log(fileName + " Uploaded!")
                }
              });
              req.flash("msgSuccessDashboard", "✔ File Uploaded Successfully!")
              
                res.redirect("/admin/file-management")              
              //console.log("1 file Uploaded! ")
            }
          }
    } catch (error) {
      //res.status(500).json({error:error.message})
      res.status(500).render('error',{error:error})
    } 
}

exports.delete_file=async(req,res)=>{
    let fileName=req.params.name
    //console.log(fileName)
    let filePath = "./public/uploads/" + fileName    
         try {
             await fs.unlink(filePath, (err) => {
                if (err) {
                       console.log(err);
                } else {
                  req.flash("msgSuccessDashboard", "✔ File Deleted Successfully!")
                  setTimeout(() => {
                    res.redirect('/admin/file-management')  
                  }, 800);
                }
              });
         } catch (error) {
            //res.status(500).json(error.message)
            res.status(500).render('error',{error:error})
         }
}

exports.upload_profile_image=async(req,res)=>{
    //console.log(req.body);
    let uuid=req.params.uuid
    let url=req.body.photoURL
    if(req.files==null){
        req.flash("msgFailureDashboard", "❌ No Image was selected!")
        res.redirect('/admin/edit-profile/'+uuid)
    }else{    
        if(url==""){
            req.flash("msgFailureDashboard", "❌ Failed to upload image!")
            res.redirect('/admin/edit-profile/'+uuid)
        }else{
            try {
                const user = await User.update({user_profile_image_link:url},{
                    where: {uuid: uuid}
                })
               
                //console.log(user)
                req.flash("msgSuccessDashboard","✔ Profile Image Updated Successfully!")
                res.redirect('/admin/edit-profile/'+uuid)
            
            } catch (error) {
                //res.json({error: error.message})
                res.status(500).render('error',{error:error})
            }
    }

    }
}

exports.news_management=async(req,res)=>{
    try {
        const news =await News.findAll()
        if(news){
            res.render('admin/news-management',{
                msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                msgFailureDashboard: req.flash("msgFailureDashboard"),
                userInfo:getCurrentUser(req),
                news:news,
                today:today
            })
        }else{
            res.render('admin/news-management',{
                msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                msgFailureDashboard: req.flash("msgFailureDashboard"),
                userInfo:getCurrentUser(req)
            })
        }
    } catch (error) {
        //res.status(500).json({error:error.message})
        res.status(500).render('error',{error:error})
    }
}

exports.news_item=async(req,res)=>{
    let id=req.params.id
    try {
        let news=await News.findOne({where:{id:id}})
        if (news){
            res.render('admin/news-item',{ msgSuccessDashboard: req.flash("msgSuccessDashboard"),
            msgFailureDashboard: req.flash("msgFailureDashboard"),            
            userInfo:getCurrentUser(req)
            ,news:news})
        }else{
            res.flash("msgFailureDashboard","❌ No news with the specified id")
            res.redirect('/admin/news-management')
        }   
    } catch (error) {
        res.status(500).render('error',{error:error})
    }    
}

exports.delete_news=async(req,res)=>{
    let newsID=req.params.id
    try {
        const news=await News.destroy({where:{id:newsID}})
        req.flash("msgSuccessDashboard","✔ News Deleted Successfully!")
        res.redirect('/admin/news-management')
    } catch (error) {
        //res.status(500).json({error:error.message})
        res.status(500).render('error',{error:error})
    }
}

exports.upload_news=async(req,res)=>{
    const {news_title,news_sub_title,news_content,news_category}=req.body
    console.log(req.body)
    try {
     const news=await News.create({
         news_title:news_title,
         news_sub_title:news_sub_title,
         news_content:news_content,
         news_category:news_category
     })
     if(news){
         req.flash('msgSuccessDashboard',"✔ News Added Successfully!")
         res.redirect('/admin/news-management')
     }else{
         req.flash("msgFailureDashboard","❌ Error Adding News!")
         res.redirect('/admin/data-management')
     }
    } catch (error) {
        //res.status(500).json({error:error.message})
        res.status(500).render('error',{error:error})
    }
}

exports.data_management=async(req,res)=>{
    const user = getCurrentUser(req)
    //console.log(user)    
    try {
        let folderPath = "./public/uploads"
        const files = await fs.readdir(folderPath,async (err, files) => {
            if (err) {
                req.flash('msgFailureDashboard',"❌ Error Finding Files Folder!")              
                res.redirect('admin/academics')
            }else{
                const user_Info=await User.findOne({where:{uuid:user.uuid}})        
                const streams=await Stream.findAll()
                const dorms=await Dorm.findAll()
                const parents=await Parent.findAll()
                //console.log(user_Info)
                res.render('admin/data-management',{
                    msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                    msgFailureDashboard: req.flash("msgFailureDashboard"),
                    userInfo:getCurrentUser(req),user:user_Info,
                    files:files,
                    streams:streams,
                    dorms:dorms,
                    parents:parents,
                    today:today
                })
            }
        })
        
    } catch (error) {
        //res.status(500).json(error.message)
        res.status(500).render('error',{error:error})
    }
}

exports.add_parent=async(req,res)=>{
    const {first_name,phone_number,last_name,year_of_birth,other_names,email_address,sent_number}=req.body    
    try {
        const parent=await Parent.create({
            first_name:first_name,
            last_name:last_name,
            other_names:other_names,
            email_address:email_address,
            phone_number:sent_number,
            dob:year_of_birth
        })
        req.flash("msgSuccessDashboard", "✔ Parent Added Successfully!")
        res.redirect('/admin/data-management')
    } catch (error) {        
        //res.status(500).json({error: error.message})
        res.status(500).render('error',{error:error})
    } 
}

exports.add_teacher=async(req,res)=>{
    const {first_name,phone_number,last_name,year_of_birth,other_names,email_address,teacher_gender,sent_number}=req.body
try {
    const teacher=await Teacher.create({
        first_name:first_name,
        last_name:last_name,
        other_names:other_names,
        email_address:email_address,
        phone_number:sent_number,
        gender:teacher_gender,
        dob:year_of_birth
    })
    if (teacher) {
        req.flash("msgSuccessDashboard","✔ Teacher Added Successfully!")
        res.redirect('/admin/data-management')
    }else{
        req.flash("msgFailureDashboard","❌ Error Adding Teacher!")
        res.redirect('/admin/data-management')
    }
 
} catch (error) {
    //res.status(500).json({error: error.message})
    res.status(500).render('error',{error:error})
}
}

exports.add_student=async(req,res)=>{
    const {adm_no,year_of_birth,first_name,date_joined,last_name,student_gender,kcpe_results,student_parent,student_dorm,student_stream}=req.body
    try {
        const student=await Student.create({
            adm_no:adm_no,
            first_name:first_name,
            last_name:last_name,
            date_joined:date_joined,
            dob:year_of_birth,
            gender:student_gender,
            kcpe_results:kcpe_results,
            dorm_id:student_dorm,
            parent_id:student_parent,
            stream_allocated_id:student_stream   
        })    

        if (student) {
            req.flash('msgSuccessDashboard','✔ Student Added Successfully!')
            res.redirect('/admin/data-management')
        }else{
            req.flash("msgFailureDashboard",'❌  Error Adding New Student!')
            res.redirect('/admin/data-management')
        }
    } catch (error) {
        //res.status(500).json({error:error.message})
        res.status(500).render('error',{error:error})
    }
}

exports.bulk_update=async(req,res)=>{
    let selectedTable=req.body.selectedTable    
    let selectedFile=req.body.selectedItem

    let filePath = "./public/uploads/" + selectedFile
    const result =  await excelToJson({
      sourceFile: filePath,
    })
    const { Sheet1: sheetOne } = result    

    if (selectedTable==="parents_table") {
        const [columnInitials, ...parentsRecords] = sheetOne
            try {
                for (p in parentsRecords) {
                    let parentRecord={
                        email_address:parentsRecords[p].A,
                        first_name:parentsRecords[p].B,
                        last_name:parentsRecords[p].C,
                        other_names:parentsRecords[p].D?parentsRecords[p].D:null,                        
                        dob:parentsRecords[p].E,
                        phone_number:parentsRecords[p].F                        
                    }
    
                    const foundParent=await Parent.create({
                        email_address:parentRecord.email_address,
                        first_name:parentRecord.first_name,
                        last_name:parentRecord.last_name,
                        other_names:parentRecord.other_names,
                        dob:parentRecord.dob,
                        phone_number:parentRecord.phone_number                       
                    })
    
                    if(foundParent){
                        //console.log({success:"Record ADDED successfully",this:foundParent})
                    }else{
                        req.flash('msgFailureDashboard','❌ Error Adding Parent')
                        res.redirect('/admin/data-management')
                    }
                }
            } catch (error) {
                //res.status(500).json({error: error.message})
                res.status(500).render('error',{error:error})
            }
        req.flash("msgSuccessDashboard","✔  Parent(s) Added Successfully!")
        res.redirect('/admin/data-management')
    }else if(selectedTable==="students_table"){
        const [columnInitials, ...studentsRecords] = sheetOne
        try {
            for (s in studentsRecords) {
                let studentRecord={
                    adm_no:studentsRecords[s].A,
                    first_name:studentsRecords[s].B,
                    last_name:studentsRecords[s].C,
                    other_names:studentsRecords[s].D?studentsRecords[s].D:null,
                    date_joined:studentsRecords[s].E,
                    dob:studentsRecords[s].F,
                    gender:studentsRecords[s].G,
                    kcpe_results:studentsRecords[s].H,
                    dorm_id:studentsRecords[s].I,
                    parent_id:studentsRecords[s].J,
                    stream_id:studentsRecords[s].K
                }

                const parent=await Parent.findOne({where:{email_address:studentRecord.parent_id}})
                const stream =await Stream.findOne({where:{abbreviation:studentRecord.stream_id}})
                const dorm=await Dorm.findOne({where:{dorm_name:studentRecord.dorm_id}})

                const foundStudent=await Student.create({
                    adm_no:studentRecord.adm_no,
                    first_name:studentRecord.first_name,
                    last_name:studentRecord.last_name,
                    other_names:studentRecord.other_names,
                    date_joined:studentRecord.date_joined,
                    dob:studentRecord.dob,
                    gender:studentRecord.gender,
                    kcpe_results:studentRecord.kcpe_results,
                    dorm_id:dorm.id,
                    parent_id:parent.id,
                    stream_allocated_id:stream.id
                })

                if(foundStudent){
                    //console.log({success:"Record ADDED successfully",this:foundStudent})
                }else{
                    req.flash('msgFailureDashboard','❌ Error Adding Student')
                    res.redirect('/admin/data-management')
                }
            }
            req.flash("msgSuccessDashboard","✔  Student(s) Added Successfully!")
            res.redirect('/admin/data-management')
           
        } catch (error) {
            res.status(500).render('error',{error:error})
            // req.flash('msgFailureDashboard','❌ Failed to Add Students')
            // res.redirect('/admin/data-management')     
                  
        }    
    }else if(selectedTable==="teachers_table"){
        const [columnInitials, ...teachersRecords] = sheetOne 
        for (t in teachersRecords) {
            try {
                let teacherRecord={
                    first_name:teachersRecords[t].A,
                    last_name:teachersRecords[t].B,
                    other_names:teachersRecords[t].C?teachersRecords[t].C:null,
                    email_address:teachersRecords[t].D,
                    phone_number:teachersRecords[t].E,
                    gender:teachersRecords[t].F,
                    dob:teachersRecords[t].G,
                }

                let foundTeacher=await Teacher.create({
                    first_name:teacherRecord.first_name,
                    last_name:teacherRecord.last_name,
                    other_names:teacherRecord.other_names,
                    email_address:teacherRecord.email_address,
                    phone_number:teacherRecord.phone_number,
                    gender:teacherRecord.gender,
                    dob:teacherRecord.dob
                })
                if(foundTeacher){
                    //console.log({success:"Record ADDED successfully",this:foundTeacher})
                }else{
                    req.flash('msgFailureDashboard','❌ Error Adding Teacher')
                    res.redirect('/admin/data-management')
                }

            } catch (error) {
                //res.status(500).json({error:error.message})
                res.status(500).render('error',{error:error})
            }
        }
        req.flash("msgSuccessDashboard","✔  Teacher(s) Added Successfully!")
        res.redirect('/admin/data-management')
    }else if(selectedTable==="subjects_table"){
        const [columnInitials,...subjectsRecords] = sheetOne 
        console.log(subjectsRecords)
        for(s in subjectsRecords){
            try {
                let subjectRecord={
                    name:subjectsRecords[s].A,
                    abbreviation:subjectsRecords[s].B
                }
                
                let foundSubject=await Subject.create({
                    subject_title:subjectRecord.name,
                    subject_abbreviation:subjectRecord.abbreviation
                })
                if(foundSubject){
                    //console.log({success:"Record ADDED successfully",this:foundSubject})
                }else{
                    req.flash('msgFailureDashboard','❌ Error Adding Subject')
                    res.redirect('/admin/data-management')
                }
            } catch (error) {
                //res.status(500).json({error: error.message}) 
                res.status(500).render('error',{error:error})
            }            
        }
        req.flash("msgSuccessDashboard","✔  Subject(s) Added Successfully!")
            res.redirect('/admin/data-management') 

    }else if(selectedTable==="dorms_table"){
        const [columnInitials, ...dormsRecords] = sheetOne
        for ( d in dormsRecords) {
            try {
                let dormRecord={
                    name:dormsRecords[d].A,
                    capacity:dormsRecords[d].B
                }
                
                let foundDorm=await Dorm.create({
                    dorm_name:dormRecord.name,
                    dorm_capacity:dormRecord.capacity
                })
                if(foundDorm){
                    //console.log({success:"Record ADDED successfully",this:foundDorm})
                }else{
                    req.flash('msgFailureDashboard','❌ Error Adding Dorm')
                    res.redirect('/admin/data-management')
                }
            } catch (error) {
                //res.status(500).json({error:error.message})
                res.status(500).render('error',{error:error})
            }
        }
        req.flash("msgSuccessDashboard","✔  Dorm(s) Added Successfully!")
        res.redirect('/admin/data-management')
    } 
}

exports.academics_management=async(req,res)=>{
    if (req.method==="GET") {
        let folderPath = "./public/uploads"   
        const files = await fs.readdir(folderPath,async (err, files) => {
            if (err) {
                req.flash('msgFailureDashboard',"❌ Error Finding Files Folder!")              
                res.redirect('admin/academics')
            }else{
                const examTypes= await ExamType.findAll()            
              res.render('admin/academics',{
                  msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                  msgFailureDashboard: req.flash("msgFailureDashboard"),
                  userInfo:getCurrentUser(req),
                  files:files,
                  examTypes:examTypes,
                  today:today
              })
            }
        })
    }else{
    let selectedExam=req.body.selectedExamType
    let selectedFile=req.body.selectedFileItem
    //console.log(selectedExam);

    let filePath = "./public/uploads/" + selectedFile
    const result = excelToJson({
      sourceFile: filePath,
    })
    const { Sheet1: sheetOne } = result
    
    // console.log(examRecords.length);
    // //console.log(examRecords)
    const [columnInitials,...examRecords] = sheetOne    

    if(examRecords.length==0){
        req.flash('msgFailureDashboard',"❌ File Doesn't contain any records!")
        res.redirect('/admin/data-management')
    }else{
        if(selectedExam =="none" || selectedExam=="Select Exam Type"){
            //Excel File Provided with specific id of the exam type            
          try {
            for (e in examRecords) {
                let examRecord={
                    marks:examRecords[e].A,
                    grade:examRecords[e].B,
                    status:examRecords[e].C,
                    examType:examRecords[e].D,
                    studentID:examRecords[e].E,
                    subjectID:examRecords[e].F,
                    classrommID:examRecords[e].G
                }
                const exam=await ExamType.findOne({where:{exam_name:examRecord.examType}})
                const subject=await Subject.findOne({where:{subject_abbreviation:examRecord.subjectID}})
                const classroom=await Classroom.findOne({where:{class_abbreviation:examRecord.classrommID}})                              

                let foundResult=await ExamResults.create({
                    marks:examRecord.marks,
                    grade:examRecord.grade,
                    status:examRecord.status,
                    exam_type_id:exam.id,
                    student_id:examRecord.studentID,
                    subject_id:subject.id,
                    classroom_id:classroom.id
                })

                if(foundResult){
                    //req.flash('msgSuccessDashboard','✔  Exam Result Added Successfully!')
                }else{
                    req.flash('msgFailureDashboard','❌ Failed to Add Exam Result')
                    res.redirect('/admin/data-management')
                }                
            }
           
          } catch (error) {
                //res.status(500).json({error:error.message})
                res.status(500).render('error',{error:error})
          }
        }else{
            //Select implicitly the exam type to add to the record                   
           try {
               for (const e in examRecords) {
                let examRecord={
                    marks:examRecords[e].A,
                    grade:examRecords[e].B,
                    status:examRecords[e].C,
                    examType:selectedExam,
                    studentID:examRecords[e].E,
                    subjectID:examRecords[e].F,
                    classrommID:examRecords[e].G
                }

                const exam=await ExamType.findOne({where:{exam_name:selectedExam}})
                const subject=await Subject.findOne({where:{subject_abbreviation:examRecord.subjectID}})
                const classroom=await Classroom.findOne({where:{class_abbreviation:examRecord.classrommID}})

                let foundResult=await ExamResults.create({
                    marks:examRecord.marks,
                    grade:examRecord.grade,
                    status:examRecord.status,
                    exam_type_id:exam.id,
                    student_id:examRecord.studentID,
                    subject_id:subject.id,
                    classroom_id:classroom.id
                })
               }
           } catch (error) {
            res.status(500).json({error:error.message})
           }
        //    req.flash("msgSuccessDashboard","✔  Exam Records Added Successfully!")
        //    res.redirect('/admin/academics')
        }   
        req.flash("msgSuccessDashboard","✔  Exam Records Added Successfully!")
        res.redirect('/admin/academics')         
    }

    }
}

exports.add_exam_type=async(req,res)=>{
    const {exam_name,exam_description,exam_start_date}=req.body
    try {
        const exam=await ExamType.create({
            exam_name:exam_name,
            exam_description:exam_description,
            start_date:exam_start_date})
            req.flash("msgSuccessDashboard","✔ Exam Type Added Successfully!")
            res.redirect('/admin/academics')
    } catch (error) {
        //res.status(500).json({error: error.message})
        res.status(500).render('error',{error:error})
    }
}

exports.finance_management=async(req,res)=>{
    if(req.method==="GET"){
        let folderPath = "./public/uploads"   
        const files = await fs.readdir(folderPath, (err, files) => {
            if (err) {
                req.flash('msgFailureDashboard',"❌ Error Finding Files Folder!")              
                res.redirect('/admin/finances')
            }else{
              res.render('admin/finance-management',{
                  msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                  msgFailureDashboard: req.flash("msgFailureDashboard"),
                  userInfo:getCurrentUser(req),
                  files:files,
                  today:today
              })
            }
        })
    }else{
        let selectedFile = req.body.selectedItem
    let filePath = "./public/uploads/" + selectedFile
    const result =  await excelToJson({
      sourceFile: filePath,
    })
    const { Sheet1: sheetOne } = result
    const [columnInitials, ...financeRecords] = sheetOne    

    if(financeRecords.length<0){
        req.flash('msgFailureDashboard',"❌ File Doesn't contain any records!")
        res.redirect('/admin/finances')
    }else{
        if (financeRecords<2) {
            let record=financeRecords[0]
            const{paymentDescription,transactionDate,debit,credit,balance,student_id}=record
            console.log(record)
        }else{                           
            for(record in financeRecords){   
                console.log(financeRecords[record])
                try {
                    let feeRecord={
                        description:financeRecords[record].A,
                        transaction_date:financeRecords[record].B,
                        debit:financeRecords[record].C,
                        credit:financeRecords[record].D,
                        balance:financeRecords[record].E,
                        student_id:financeRecords[record].F
                        
                    }
                    //console.log({recordType:feeRecord})

                    const foundRecord= await Fees.create({
                        payment_description:feeRecord.description,
                        transaction_date:feeRecord.transaction_date,
                        debit:feeRecord.debit,
                        credit:feeRecord.credit,
                        balance:feeRecord.balance,
                        student_id:feeRecord.student_id
                    })
                    //console.log({success:"Record ADDED successfully",this:foundRecord})
                } catch (error) {
                   //res.status(500).json({error: error.message}) 
                   res.status(500).render('error',{error:error})
                }                     
            }
            req.flash("msgSuccessDashboard","✔  Fees Records Added Successfully!")
            res.redirect('/admin/finances')           
        }


    }
    }
}

exports.admin_profile=async(req,res)=>{
    const user = getCurrentUser(req)
    console.log(user);
    try {
        const user_Info=await User.findOne({where:{uuid:user.uuid}})        
        console.log(user_Info)
        res.render('admin/profile',{
            msgSuccessDashboard: req.flash("msgSuccessDashboard"),
            msgFailureDashboard: req.flash("msgFailureDashboard"),
            userInfo:getCurrentUser(req),user:user_Info
        })
    } catch (error) {
        //res.status(500).json(error.message)
        res.status(500).render('error',{error:error})
    } 
}

exports.edit_profile=async(req,res)=>{
    if (req.method==="GET") {
        const uuid = req.params.uuid;
        try {
            const user=await User.findOne({where:{uuid}})
            console.log(user);
            res.render('admin/edit-profile',{
                msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                msgFailureDashboard: req.flash("msgFailureDashboard"),
                user:user,userInfo:getCurrentUser(req),
                csrfToken: req.csrfToken()
            })
        } catch (error) {
            //res.status(500).json(error.message);
            res.status(500).render('error',{error:error})
        } 
    }else{
        let uuid = req.params.uuid
        try {
            console.log(req.body);
            const {first_name,last_name,other_names,user_gender,email_address,phone_number,birthday,sent_number,profile_url} =req.body
            console.log(sent_number);
            const updateUser=await User.update(
                {
                    other_names:other_names,phone_number:sent_number,year_of_birth:birthday,user_profile_image_link:profile_url
                },{
                    where: {uuid:uuid}
                })
                req.flash("msgSuccessDashboard", "✔ Profile updated successfully!")            
                res.redirect(`/admin/profile`)
        } catch (error) {
            //res.status(500).json({message: error.message})
            res.status(500).render('error',{error:error})
        }
    }
}

exports.change_password=async(req,res)=>{
    if (req.method==="GET") {
        res.render('admin/change-password',{
            msgSuccessDashboard: req.flash("msgSuccessDashboard"),
            msgFailureDashboard: req.flash("msgFailureDashboard"),
            userInfo:getCurrentUser(req),
            csrfToken:req.csrfToken()
        })
    }else{
        let uuid = req.params.uuid
        console.log(req.body);
        try {
            const {currentPassword,newPassword,confirmPassword} =req.body        
            const user = await User.findOne({where:{uuid:uuid}})
            let foundPass=user.user_password
            bcrypt.compare(currentPassword,foundPass,(err,passResult)=>{
                if(err){
                    console.log(err.message);
                }else{
                    if(passResult==true){
                        if(newPassword===confirmPassword){
                           try {
                            bcrypt.hash(newPassword,saltRounds,async(error,hash)=>{
                                if(error){
                                    console.log(error.message);
                                }else{
                                    try {
                                        const updateUser=await User.update({user_password:hash,user_confirm_password:hash},{
                                            where: {uuid:uuid}
                                        })
                                        req.flash("msgSuccessDashboard","Password updated successfully!")                                    
                                        res.redirect(`/admin/change-password/${uuid}`)
                                    } catch (er) {
                                        //res.status(500).json({error: er.message});
                                        res.status(500).render('error',{error:error})
                                    }
                                }
                            })
                           } catch (error) {
                                //res.status(500).json({error: error.message});   
                                res.status(500).render('error',{error:error})
                           }
                        }else{
                            req.flash("msgFailureDashboard","❌ Passwords do not match!")                        
                            res.redirect(`/admin/change-password/${uuid}`)
                        }
                    }else{
                        req.flash("msgFailureDashboard","❌ Current password does not match!")                    
                        res.redirect(`/admin/change-password/${uuid}`)
                    }
                }
            })
            res.redirect(`/admin/change-password/${uuid}`)
        } catch (error) {
            //res.status(500).json({error: error.message})
            res.status(500).render('error',{error:error})
        }
    }
}

exports.progress_charts=async(req,res)=>{
    res.render('admin/progress-charts',{
        msgSuccessDashboard: req.flash("msgSuccessDashboard"),
        msgFailureDashboard: req.flash("msgFailureDashboard"),
        userInfo:getCurrentUser(req)
    })
}

exports.reports=async(req,res)=>{
    const examTypes= await ExamType.findAll()   
    res.render('admin/reports',{
        msgSuccessDashboard: req.flash("msgSuccessDashboard"),
        msgFailureDashboard: req.flash("msgFailureDashboard"),
        userInfo:getCurrentUser(req),
        today:today,
        examTypes:examTypes
    })
}


exports.sendResults=async(req,res)=>{
    let selectedExam=req.body.selectedItem    
    if (selectedExam != "Select Exam to Send") {
        try {
            const exam=await ExamType.findOne({where:{exam_name:selectedExam}})
            const update=await ExamResults.update({status:"active"},{
                where:{exam_type_id:exam.id}
            })
            const allParents=await Parent.findAll()            
            const parentsNoAccount=[]
            createNotificationMessage(selectedExam,`${selectedExam} have been processed and can be viewed in the Results screen.`)
                    
            for (const a in allParents) {       
                const p=await User.findOne({where:{email_address:allParents[a].email_address}})
                if (!p) {                    
                    parentsNoAccount.push(allParents[a])
                }
            }            

            const s=await Student.findAll({where:{parent_id:parentsNoAccount[0].id}})            

            let resultsMessage=""   
            for(const parent in parentsNoAccount){
                const students=await Student.findAll({where:{parent_id:parentsNoAccount[parent].id}})                                                
                if(students.length>0){                             
                    for(student in students){
                        const results=await ExamResults.findAll({where:{student_id:students[student].adm_no,exam_type_id:exam.id}})                                        
                        resultsMessage+=`${selectedExam.toLocaleUpperCase()}: \nADM NO: ${students[student].adm_no} \n${students[student].first_name} ${students[student].last_name}, \n`
                        let subjectsGrades=[]
                        for (const result in results) {
                            let subject=await Subject.findOne({where:{id:results[result].subject_id}})                                                        
                            subjectsGrades.push(results[result])
                            resultsMessage+=subject.subject_abbreviation+" "
                            resultsMessage+=results[result].marks+" "
                            resultsMessage+=results[result].grade+",\n"                                                        
                        }                 
                        let grade=await getGrade(subjectsGrades,students[student].adm_no)   
                        resultsMessage+=`MEAN GRADE:${grade}`         
                            if (results.length!=0) {
                                //console.log(resultsMessage)                            
                                twilioClient(resultsMessage,parentsNoAccount[parent].phone_number) 
                            }else{
                                //console.log("EMPTY")
                            }                                                                                                                    
                    }   
                    resultsMessage=""                                                                                              
                    
                }             
            }                                                                            
            req.flash("msgSuccessDashboard",`✔ Results for ${selectedExam} Sent Successfully!`)                                    
            res.redirect(`/admin/reports`)

           } catch (error) {
               res.status(500).render('error',{error:error})
           }        
    }else{
        req.flash("msgFailureDashboard","❌ No Exam Selected")                                    
        res.redirect(`/admin/reports`)
    }
  
    
}

function twilioClient(message,phoneNo){
    const accountSid = process.env.TWILIO_ACCOUNT_SID
    const authToken = process.env.AUTH_TOKEN_TWILIO; 
    const client = require('twilio')(accountSid, authToken)
     
    client.messages 
          .create({ 
             body: message,  
             messagingServiceSid: process.env.MESSAGING_SERVICE_SID,      
             to: phoneNo 
           }) 
          .then(message => console.log(message.status)) 
          .done();
            
}

async function getGrade(gradeArray,admNo){
    let points=0
    let grade=""
    let optionalSubjects=[]
    
    for (const g in gradeArray) {
        let subject=await Subject.findOne({where:{id:gradeArray[g].subject_id}})
        if(admNo==gradeArray[g].student_id){
            if(subject.subject_title=="Mathematics"){
                points+=getPoints(gradeArray[g].grade)
            }
            if(subject.subject_title=="English"){
                points+=getPoints(gradeArray[g].grade)
            }
            if(subject.subject_title=="Kiswahili"){
                points+=getPoints(gradeArray[g].grade)
            }
            if(subject.subject_title=="Biology"){
                points+=getPoints(gradeArray[g].grade)
            }
            if(subject.subject_title=="Chemistry"){
                points+=getPoints(gradeArray[g].grade)
            }

            if(subject.subject_title!="Mathematics" && subject.subject_title!="English" && subject.subject_title!="Kiswahili" && subject.subject_title!="Biology" && subject.subject_title!="Chemistry"){
                optionalSubjects.push(getPoints(gradeArray[g].grade))
            }
        }
    }    

    if (optionalSubjects.length>0) {
        let high = optionalSubjects[0]
        let higher = optionalSubjects[0]
        let highest = optionalSubjects[0]

        for(const z in optionalSubjects){
            if (optionalSubjects[z]>highest) {
                highest=optionalSubjects[z]
            }
            if (optionalSubjects[z]<high) {
                high=optionalSubjects[z]
            }
        }

        for(const z in optionalSubjects){
            if (optionalSubjects[z]<highest && optionalSubjects[z]>high) {
                higher=optionalSubjects[z]                
            }
        }      
        points+=highest
        points+=higher
        
    }    
    grade=calculateGrade(points)   
    return grade 
}


function calculateGrade(points){
    let grade = ""
    let average = parseInt(points / 7)    

    switch (average){
        case 12:
            grade="A"
            break;
        case 11:
            grade="A-"
            break;
        case 10:
            grade="B+"
            break;
        case 9:
            grade="B"
            break;
        case 8:
            grade="B-"
            break;
        case 7:
            grade="C+"
            break;
        case 6:
            grade="C"
            break;
        case 5:
            grade="C-"
            break;
        case 4:
            grade="D+"
            break;
        case 3:
            grade="D"
            break;
        case 2:
            grade="D-"
            break;
        case 1:
            grade="E"
            break;
    }
    //console.log(grade);
    return grade

}

function getPoints(grade){
    let points=0
    switch(grade){
        case "A":
            points+=12
            break;
        case "A-":
            points+=11
            break;
        case "B+":
            points+=10
            break;
        case "B":
            points+=9
            break;
        case "B-":
            points+=8
            break;
        case "C+":
            points+=7
            break;
        case "C":
            points+=6
            break;
        case "C-":
            points+=5
            break;
        case "D+":
            points+=4
            break;
        case "D":
            points+=3
            break;
        case "D-":
            points+=2
            break;
        case "E":
            points+=1
            break;
    }
    return points
}

function createNotificationMessage(title,content){
    var message = { 
        app_id: process.env.TWILIO_APP_ID,
        contents: {"en": `${content}`},
        headings:{"en":`${title}`},
        included_segments: ["All"]
        //included_segments: ["Subscribed Users"]
      };
      
      sendNotification(message);
}

var sendNotification = function(data) {
    var headers = {
      "Content-Type": "application/json; charset=utf-8",
      "Authorization":`Basic ${process.env.TWILIO_REST_API_KEY}`
    };
    
    var options = {
      host: "onesignal.com",
      port: 443,
      path: "/api/v1/notifications",
      method: "POST",
      headers: headers
    };
    
    var https = require('https');
    var req = https.request(options, function(res) {  
      res.on('data', function(data) {
        console.log("Response:");
        console.log(JSON.parse(data));
      });
    });
    
    req.on('error', function(e) {
      console.log("ERROR:");
      console.log(e);
    });
    
    req.write(JSON.stringify(data));
    req.end();
  };
  
