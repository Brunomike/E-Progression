'use strict';
module.exports = {
  up: async (queryInterface, DataTypes) => {
    await queryInterface.createTable('Students', {     
      adm_no: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        allowNull:false
      },
      first_name: {
        type: DataTypes.STRING,
        allowNull:false
      },
      last_name: {
        type: DataTypes.STRING,
        allowNull:false
      },
      other_names: {
        type: DataTypes.STRING,
        allowNull:true
      },
      date_joined: {
        type: DataTypes.DATE,
        allowNull:false
      },
      dob: {
        type: DataTypes.DATE,
        allowNull:false
      },
      gender: {
        type: DataTypes.STRING,
        allowNull:false
      },
      kcpe_results: {
        type: DataTypes.INTEGER,
        allowNull:false
      },
      image_link: {
        type: DataTypes.STRING,
        allowNull:true
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
    await queryInterface.dropTable('Students');
  }
};