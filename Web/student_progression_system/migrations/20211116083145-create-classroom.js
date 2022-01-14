"use strict";
module.exports = {
  up: async (queryInterface, DataTypes) => {
    await queryInterface.createTable("Classrooms", {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: DataTypes.INTEGER,
      },
      year_started: {
        type: DataTypes.DATE,
        allowNull: false,
      },
      class_name: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      class_abbreviation: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      class_progress: {
        type: DataTypes.INTEGER,
        allowNull: false,
      },
      class_teacher_id: {
        type: DataTypes.INTEGER,
        allowNull: false,
      },
      parent_id: {
        type: DataTypes.INTEGER,
        allowNull: false,
      },
      dorm_id: {
        type: DataTypes.INTEGER,
        allowNull: false,
      },
      stream_allocated_id: {
        type: DataTypes.INTEGER,
        allowNull: false,
      },
      createdAt: {
        allowNull: false,
        type: DataTypes.DATE,
      },
      updatedAt: {
        allowNull: false,
        type: DataTypes.DATE,
      },
    });
  },
  down: async (queryInterface, DataTypes) => {
    await queryInterface.dropTable("Classrooms");
  },
};
