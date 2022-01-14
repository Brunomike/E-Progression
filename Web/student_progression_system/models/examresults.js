'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class ExamResults extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({ExamType,Student,Subject,Classroom}) {
      // define association here
      this.belongsTo(ExamType,{foreignKey:'exam_type_id'})
      this.belongsTo(Student,{foreignKey:'student_id'})
      this.belongsTo(Subject,{foreignKey:'subject_id'})
      this.belongsTo(Classroom,{foreignKey:'classroom_id'})
    }
  };
  ExamResults.init({
    marks: {
      type:DataTypes.INTEGER,
      allowNull:false
    },
    grade: {
      type:DataTypes.STRING,
      allowNull:false
    },
    status: {
    type:DataTypes.STRING,
    allowNull:false      
    }
  }, {
    sequelize,
    tableName:'exam_results_table',
    modelName: 'ExamResults',
  });
  return ExamResults;
};