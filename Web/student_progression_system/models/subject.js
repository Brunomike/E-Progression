'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Subject extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({ExamResults}) {
      // define association here
      this.hasOne(ExamResults,{foreignKey:'subject_id'})
    }
  };
  Subject.init({
    subject_title: {
      type:DataTypes.STRING,
      allowNull:false
    },
    subject_abbreviation: {
      type:DataTypes.STRING,
      allowNull:false
    }
  }, {
    sequelize,
    tableName:'subjects_table',
    modelName: 'Subject',
  });
  return Subject;
};