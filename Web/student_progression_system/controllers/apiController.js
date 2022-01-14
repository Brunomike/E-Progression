const {News,User,Parent,Student,ExamResults,ExamType,Fees,Subject}=require("../models")
const bcrypt=require('bcrypt')
const saltRounds=10

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

exports.registration=async(req,res)=>{
    if (req.method==="POST") {
        console.log(req.body)
        let fName=req.body.firstName
        let lName=req.body.lastName
        let email=req.body.emailAddress
        let gender=req.body.gender
        let phoneNo=req.body.phone
        let dob=req.body.dob
        let password=req.body.password
        let confirmPassword=req.body.confirmPassword

        const user=await User.findOne({where:{email_address:email}})
        if (user) {
            let response={
                message:"User Already Exists!"                
            }
            res.status(200).send(response)
        }else{
            if (password===confirmPassword) {
                let passCheck=validatePassword(password)
                if (passCheck[0]===true) {
                    bcrypt.hash(password,saltRounds,async(err,hash)=>{
                        if (err) {
                            console.log(err.message)
                        }else{
                            try {                               
                                    const newUser=await User.create({
                                        email_address:email,
                                        first_name:fName,
                                        last_name:lName,
                                        phone_number:phoneNo,
                                        user_gender:gender,
                                        user_role:"parent",
                                        user_status:"disabled",
                                        year_of_birth:dob,
                                        user_password:hash,
                                        user_confirm_password:hash
                                    })

                                    if(newUser){
                                        let response={
                                            message:"Account Created Successfully!"
                                        }
                                        res.status(200).send(response)
                                    }else{
                                        let response={
                                            message:"Error in creating New Account!"
                                        }
                                        res.status(200).send(response)
                                    }
                            } catch (error) {                           
                                res.status(500).json(error)
                            }
                        }
                    })
                }else{
                    let response={
                        message:"Passwords do not meet set criteria!"
                      }
                    res.status(200).send(response)
                }
            }else{
                let response={
                    message:"Passwords do not match!"
                }
                res.status(200).send(response)
            }
        }


    }else{

    }        
    // res.status(200).send({message:"Success"})

}

exports.login=async(req,res)=>{
    if (req.method ==="POST") {
        console.log(req.body)
        let email=req.body.email
        password=req.body.password
        const user=await User.findOne({where:{email_address:email}})
        if(user){
            if(user.user_status==="active"){
                if (user.user_role==="admin" || user.user_role==="superadmin") {
                    const response={
                        message:"Admin Found!",
                        user:{}
                    }                    
                    res.status(200).send(response)
                } else{
                    let userPass=user.user_password
                    bcrypt.compare(password,userPass,(err,passResult)=>{
                        if (err) {
                            console.log(err.message)
                        }else{
                            if (passResult==true) {
                                const response={
                                    message:"User Found!",
                                    //user:JSON.stringify(user)
                                    user:user
                                }      
                                //console.log(response)
                                res.status(200).send(response)          
                            }else{
                                const response={
                                    message:"Wrong Password!",
                                    user:{}
                                }                                     
                                res.status(200).send(response)
                            }
                        }
                    })                   
                }                
            }else{
                const response={
                    message:"Account Disabled!",
                    user:{}
                }                
                res.status(200).send(response)
            }             
        }else{            
            const response={
                message:"User Not Found!",
                user:{}
            }            
            res.status(200).send(response)
        }       

    }else{
        //res.render('admin/test',{csrfToken: req.csrfToken() })
        res.render('admin/test')        
    }
}

exports.resetPassword=async(req,res)=>{
    if (req.method==="POST") {
        console.log(req.body)
        let email=req.body.email
        password=req.body.password
        confirmPassword=req.body.confirmPassword
        let user=await User.findOne({where:{email_address:email}})
        if (user) {
            if (password===confirmPassword) {
                let passCheck=validatePassword(password)
                if (passCheck[0]===true) {
                    bcrypt.hash(password,saltRounds,async(err,hash)=>{
                        if(err){
                            console.log(err.message)
                        }else{                            
                            try {
                                const newUserPass=await User.update({user_password:hash,user_confirm_password:hash},
                                    {where:{email_address:email}})  
                                    let response={
                                        message:"Password Changed Successfully!"
                                    }
                                    res.status(200).send(response)                                                                                                                               
                            } catch (error) {
                                res.status(500).json(error.message)
                            }
                        }
                    })
                }else{
                    let response={
                        message:"Passwords do not meet set criteria!"
                    }
                    res.status(200).send(response) 
                }
                
            }else{
                let response={
                    message:"Passwords do not match!"
                }
                res.status(200).send(response) 
            }
        }else{
            let response={
                message:"User Not Found!"
            }
            res.status(200).send(response)
        }


    }
}

exports.home=async(req,res)=>{
     //latest news,greeting message,exam results,fee payment 
     const exams=await ExamResults.findAll()
     const latestNews=await News.findOne({where:{
 
     }})
}

exports.news=async(req,res)=>{
    try {        
        const news=await News.findAll({order:[['createdAt','DESC']]})
        res.send(JSON.stringify(news))
    } catch (error) {
        res.status(500).json({error:error.message})
    }
}

exports.newsItem=async(req,res)=>{
    try {
        console.log(req.params);
        let id=req.params.id
        const selectedNews=await News.findOne({where:{id:id}})
        res.send(selectedNews)
    } catch (error) {
        res.status(500).json({error:error.message})
    }
}

exports.user_profile=async(req,res)=>{
    let uuid=req.params.uuid
    try {
        const user=await User.findOne({where:{uuid:uuid}})
        if(user){
            if (user.user_role==="parent") {
                res.send(user)
            }else{
                res.status(200).json({message:"Not an Parent"})
            }
            
        }
    } catch (error) {
        res.status(500).json({error:error.message})
    }
}

//Exam type,exam results student name
exports.results=async(req,res)=>{
    let uuid=req.params.uuid
    try {
        const user=await User.findOne({where:{uuid:uuid}})
        const student=await Student.findAll({include:[{
            model:Parent,
            where:{
                email_address:user.email_address
            }
        },
        {model:ExamResults}]            
        })
        const studentResults=await ExamResults.findAll({where:{student_id:student[0].adm_no,status:"active"},include:[{
            model:ExamType
        },{model:Subject},{model:Student}
    ]})                    
        res.send(studentResults)

    } catch (error) {
        res.status(500).json({error:error.message})
    }
}

exports.exam_types=async(req,res)=>{
    try {
        const exams=await ExamType.findAll()   
        console.log(exams);
        res.send(exams)
       } catch (error) {
           res.status(500).json({error:error.message})
       }
}

exports.fees=async(req,res)=>{
    let uuid=req.params.uuid
    try {
        const user=await User.findOne({where:{uuid:uuid}})
        const students=await Student.findAll({include:[{
            model:Parent,
            where:{
                email_address:user.email_address
            }
        },
        {model:Fees,include:{model:Student}}]
        })
        let fees=[]
        students.forEach(student=>{
            student.Fees.forEach(fee=>{
                fees.push(fee)
            })            
        })
        //let fees=students[0].Fees
               
        //res.send(fees)
        res.send(fees)
        console.log(fees);

    } catch (error) {
        res.status(500).json({error:error.message})
    }
}

exports.students=async(req,res)=>{
    let uuid=req.params.uuid
    const user=await User.findOne({where:{uuid:uuid}})
    const students=await Student.findAll({include:[{model:Parent,where:{email_address:user.email_address}}]})
    res.send(students)    
}

exports.latestNews=async(req,res)=>{
    const latestNews=await News.findOne({order:[['createdAt','DESC']]})
    res.send(latestNews)
}

exports.totalRecords=async(req,res)=>{
    //const totalPaid=await 
    //const totalBalance=await
}


/** THINGS TO DO
 *  Fix error occuring on login post request in the android application
 *  Set url with specific logged in user's uuid after successful login to enable accessing relevant information/data
 *  Add code for registering a new user
 *  Add code for password change
 *  Add twilio messaging to all parents who haven't installed the application
 *  Clean the up the UI to enable resposiveness to all screens in both the web and android application
 *  Generate reports in form of a pdf file
 *  Fix some cards in the home dashboard of the admin panel to reflect current data from the database
 * 
 */
