'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Classroom extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({Teacher}) {
      // define association here
      this.belongsTo(Teacher,{foreignKey:'class_teacher_id'})
    }
  };
  Classroom.init({
    year_started: {
      type:DataTypes.DATE,
      allowNull:false
    },
    class_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    class_abbreviation: {
      type:DataTypes.STRING,
      allowNull:false
    },
    class_progress: {
      type:DataTypes.INTEGER,
      allowNull:false
    }
    // ,
    // class_teacher_id: {
    //   type:DataTypes.INTEGER,
    //   allowNull:false
    // }
  }, {
    sequelize,
    tableName:'classrooms_table',
    modelName: 'Classroom',
  });
  return Classroom;
};