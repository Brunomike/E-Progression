const {User}=require('../models')
const bcrypt=require('bcrypt');
const { async } = require('@firebase/util');
const saltRounds=10

// const date = new Date();
// const [month, day, year] = [date.getMonth(), date.getDate(), date.getFullYear()];
// const today=`${year}-${month}-${day}`

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

const today = `${dayText}, ${dayValue} ${monthText} ${currentYear}`;

exports.root=async(req,res)=>{
    //console.log(req.csrfToken() );
    try {
        const users=await User.findAll()
        console.log(users)
        res.render('admin/user-management',{
            msgSuccessDashboard: req.flash("msgSuccessDashboard"),
            msgFailureDashboard: req.flash("msgFailureDashboard"),
            users:users,
            today,
            userInfo:getCurrentUser(req),
            csrfToken: req.csrfToken() ,
            today:today
        }) 
    } catch (error) {
        res.status(500).json(error.message);      
    } 
}

exports.view_user=async(req,res)=>{
    const uuid = req.params.uuid;
    try {
        const user=await User.findOne({where:{uuid}})
        console.log(user);
        res.render('user/view-user',{
            msgSuccessDashboard: req.flash("msgSuccessDashboard"),
            msgFailureDashboard: req.flash("msgFailureDashboard"),
            user:user,
            userInfo:getCurrentUser(req)
        })
    } catch (error) {
        res.status(500).json(error.message);
    }
}

exports.edit_user=async(req,res)=>{
    if (req.method==="GET") {
        const uuid = req.params.uuid;
        try {
            const user=await User.findOne({where:{uuid}})
            if(user.user_role==="superadmin"){
                req.flash("msgFailureDashboard","❌ You Cannot Edit a Super Admin!")
                res.redirect(`/user/view/${uuid}`)
            }else{
                res.render('user/edit-user',{
                    msgSuccessDashboard: req.flash("msgSuccessDashboard"),
                    msgFailureDashboard: req.flash("msgFailureDashboard"),
                    user:user,
                    userInfo:getCurrentUser(req),
                    csrfToken: req.csrfToken() 
                })
            }       
        } catch (error) {
            res.status(500).json(error.message);
        }
    }else{
        const {other_names,user_status,user_role,year_of_birth,sent_number}=req.body
        try {
            if (sent_number=="") {
                const user=await User.update({other_names:other_names,user_status:user_status,user_role:user_role},
                    {where: {uuid:req.params.uuid}})
                    req.flash("msgSuccessDashboard","✔ User Update Successfully!")
                    res.redirect(`/user/edit/${req.params.uuid}`)
            }else{
                const user=await User.update({other_names:other_names,user_status:user_status,user_role:user_role,phone_number:sent_number},
                    {where: {uuid:req.params.uuid}})
                    req.flash("msgSuccessDashboard","✔ User Update Successfully!")
                    res.redirect(`/user/edit/${req.params.uuid}`)
            }
        } catch (error) {
            res.status(500).json({error:error.message})
        }
        
        //res.redirect(`/user/edit/${req.params.uuid}`)
      
    }
}

exports.upload_photo=async(req,res)=>{
    console.log(req.body)     
    let uuid = req.params.uuid
    let url=req.body.photoURL
    if(req.files==null){
        req.flash("msgFailureDashboard", "❌ No Image was selected!")
        res.redirect('/user/edit/'+uuid)
    }else{    
        if(url==""){
            req.flash("msgFailureDashboard", "❌ Failed to upload image!")
            res.redirect(`/user/edit/${uuid}`)
        }else{
            try {
                const user = await User.update({user_profile_image_link:url},{
                    where: {uuid: uuid}
                })
               
                console.log(user)
                req.flash("msgSuccessDashboard","✔ Profile Image Updated Successfully!")
                res.redirect(`/user/edit/${uuid}`)
            
            } catch (error) {
                res.json({error: error.message})
            }
    }

    }
}

exports.delete_user=async(req,res)=>{
    const uuid=req.params.uuid
    try {
        const user=await User.findOne({where:{uuid:uuid}})
        if(user.user_role === "superadmin"){
            req.flash('msgFailureDashboard',"❌ You Cannot Delete a Super Admin!")
            res.redirect('/admin/user-management')
        }else{
            await User.destroy({where:{uuid:uuid}})        
            req.flash("msgSuccessDashboard","✔ User Deleted Successfully!")
            res.redirect('/admin/user-management')
        }       
    } catch (error) {
        res.status(500).json(error.message)
    }
}

exports.set_status=async(req,res)=>{
    const uuid = req.params.uuid    
    const status = req.params.status
    try {
        const role=await User.findOne({where:{uuid:uuid}})
        if(role.user_role==="superadmin"){
            req.flash("msgFailureDashboard","❌ You Cannot Change Status of a Super Admin!")
            res.redirect('/admin/user-management')
        }else{
            const user=await User.update({user_status:status=="active"?"disabled":"active"},{
                where:{
                    uuid:uuid
                }
            })        
        req.flash("msgSuccessDashboard","✔ User status updated successfully!")
        res.redirect('/admin/user-management')
        }
        
    } catch (error) {
        res.status(500).json(error.message)
    }
}

exports.set_role=async(req,res)=>{
    const uuid = req.params.uuid
    const foundRole = req.body.user_role
    console.log(req.body);
   try {
    const role=await User.findOne({where:{uuid:uuid}})
    if(role.user_role==="superadmin"){
        req.flash("msgFailureDashboard","❌ You Cannot Assign Role to a Super Admin!")
        res.redirect('/admin/user-management')
    }else{
        const user=await User.update({user_role:foundRole},{
            where: {uuid:uuid}
        })
        
            req.flash("msgSuccessDashboard","✔ User role updated successfully!")
            res.redirect('/admin/user-management')
    }
   
   } catch (error) {
    res.status(500).json(error.message)
   }
}

exports.change_password=async(req,res)=>{
    //console.log(req.body);
    const uuid = req.params.uuid
    const {user_password,user_confirm_password} = req.body
    if (user_password===user_confirm_password) {        
        let passCheck=validatePassword(user_password)
        if (passCheck[0]===true) {
            bcrypt.hash(user_password,saltRounds,async(err,hash) => {
                if(err){
                    console.log(err.message);
                }else{                    
                  try {
                   const user=await User.update({user_password:hash,user_confirm_password:hash},{
                       where: {uuid:uuid}
                   })                    
                    req.flash("msgSuccessDashboard","✔ Password changed successfully!")
                    return res.redirect('/admin/user-management')                                    
                  } catch (error) {
                    res.status(500).json(error,message)
                  }                                    
                }
            })
        }else{
          //Password did not meet set criteria
          req.flash("msgSuccessDashboard",`${passCheck[1]}`)
          res.redirect('/admin/user-management')
        }
    }else{
        req.flash("msgSuccessDashboard","✔ Passwords do not match!")        
        res.redirect('/admin/user-management')
    }
}