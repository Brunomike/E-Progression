'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class ExamType extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({ExamResults}) {
      // define association here
      this.hasMany(ExamResults,{foreignKey:'exam_type_id'})
    }
    toJSON(){
      return {...this.get(),id:undefined}
    }
  };
  ExamType.init({
    exam_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    exam_description: {
      type:DataTypes.STRING,
      allowNull:true
    },
    start_date: {
      type:DataTypes.DATE,
      allowNull:false
    }
  }, {
    sequelize,
    tableName:'exam_types_table',
    modelName: 'ExamType',
  });
  return ExamType;
};