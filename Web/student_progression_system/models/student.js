'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Student extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({Parent,Dorm,Stream,Fees,ExamResults}) {
      // define association here
      this.belongsTo(Parent,{foreignKey:'parent_id'})
      this.belongsTo(Dorm,{foreignKey:'dorm_id'})
      this.belongsTo(Stream,{foreignKey:'stream_allocated_id'})  
      this.hasMany(Fees,{foreignKey:'student_id'})  
      this.hasMany(ExamResults,{foreignKey:'student_id'})
    }
  };
  Student.init({
    adm_no: {
      type:DataTypes.INTEGER,
      allowNull:false,
      primaryKey:true,
    },
    first_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    last_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    other_names: {
      type:DataTypes.STRING,
      allowNull:true
    },
    date_joined: {
      type:DataTypes.DATE,
      allowNull:false
    },
    dob: {
      type:DataTypes.DATE,
      allowNull:false
    },
    gender: {
      type:DataTypes.STRING,
      allowNull:false
    },
    kcpe_results: {
      type:DataTypes.INTEGER,
      allowNull:false
    },
    image_link: {
      type:DataTypes.STRING,
      allowNull:true
    }
  }, {
    sequelize,
    tableName:'students_table',
    modelName: 'Student',
  });
  return Student;
};