'use strict';
module.exports = {
  up: async (queryInterface, DataTypes) => {
    await queryInterface.createTable('ExamResults', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: DataTypes.INTEGER
      },
      exam_type_id:{
        type:DataTypes.INTEGER,
        allowNull: false
      },
      student_id:{
        type:DataTypes.INTEGER,
        allowNull: false
      },
      subject_id:{
        type:DataTypes.INTEGER,
        allowNull: false
      },
      classroom_id:{
        type:DataTypes.INTEGER,
        allowNull: false
      },
      marks: {
        type: DataTypes.INTEGER,
        allowNull: false
      },
      grade: {
        type: DataTypes.STRING,
        allowNull: false
      },
      status: {
      type:DataTypes.STRING,
      allowNull:false      
      },
      createdAt: {
        allowNull: false,
        type: DataTypes.DATE
      },
      updatedAt: {
        allowNull: false,
        type: DataTypes.DATE
      }
    });
  },
  down: async (queryInterface, DataTypes) => {
    await queryInterface.dropTable('ExamResults');
  }
};