'use strict';
module.exports = {
  up: async (queryInterface, DataTypes) => {
    await queryInterface.createTable('Fees', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: DataTypes.INTEGER
      },
      student_id: {
        type: DataTypes.INTEGER,
        allowNull: false
      },
      payment_description: {
        type: DataTypes.STRING,
        allowNull: false
      },
      transaction_date: {
        type: DataTypes.DATE,
        allowNull: false
      },
      debit: {
        type: DataTypes.DECIMAL,
        allowNull: false
      },
      credit: {
        type: DataTypes.DECIMAL,
        allowNull: false
      },
      balance: {
        type: DataTypes.DECIMAL,
        allowNull: false
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
    await queryInterface.dropTable('Fees');
  }
};