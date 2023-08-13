package com.nestjavatraining.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.nestjavatraining.connectionpool.ConnectionPool;
import com.nestjavatraining.entity.Product;

public class ProductDaoImpl implements ProductDao {

	@Override
	public void saveProduct(Product product) {

		Connection connection = null;
		String insertSQL = "INSERT INTO `product`(`productcode`, `productname`, `productdescription`, `activationdate`, `expirtydate`) VALUES (?,?,?,?,?)";
		PreparedStatement prepStmt = null;
		try {
			DataSource ds = ConnectionPool.getDataSource();
			connection = ds.getConnection();
			prepStmt = connection.prepareStatement(insertSQL);
			
			prepStmt.setString(1,product.getProductCode());
			prepStmt.setString(2,product.getProductName());
			prepStmt.setString(3,product.getProductDescription());
			
			prepStmt.setDate(4,(Date) product.getActivationDate());
			prepStmt.setDate(5,(Date) product.getExpiryDate());
			
			int j=prepStmt.executeUpdate();

            if (j!=0)  //Just to ensure data has been inserted into the database
            {System.out.println("Success! Product Added to database"); }
            
            else
            {System.out.println("Failed to add Product to database"); }



            connection.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Product> listAllProducts() {
		
		List<Product> productList = new ArrayList<Product>();
		Connection connection = null;
		String selectSQL = "Select * from product";
		PreparedStatement prepStmt = null;
		try {
			DataSource ds = ConnectionPool.getDataSource();
			connection = ds.getConnection();
			prepStmt = connection.prepareStatement(selectSQL);
			ResultSet resultSet = prepStmt.executeQuery();
			while (resultSet.next()) {
				productList.add(new Product(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
						resultSet.getDate(5), resultSet.getDate(6)));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public Product getProduct(String productCode) {

		Product product=null;
		
		Connection connection=null;
		String searchSQL="SELECT `slno`, `productcode`, `productname`, `productdescription`, `activationdate`, `expirtydate` FROM `product` WHERE `productcode`=?";
		PreparedStatement prepStmt=null;
			
		try {
			DataSource ds = ConnectionPool.getDataSource();
			connection = ds.getConnection();
			prepStmt=connection.prepareStatement(searchSQL);
						
			prepStmt.setString(1, productCode);
			
			ResultSet resultSet=prepStmt.executeQuery();
			
			while (resultSet.next()) {
				
				product = new Product(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getDate(5), resultSet.getDate(6));
						
			}
			

			connection.close();
		}
		 catch (SQLException e) {
				e.printStackTrace();
		}
		
		return product;
		

	}

	@Override
	public void deleteProduct(String productCode) {

		Connection connection = null;
		
		String deleteSQL="DELETE FROM `product` WHERE `productcode`=?";
		
		PreparedStatement prepStmt = null;

		try {
			DataSource ds = ConnectionPool.getDataSource();
			connection = ds.getConnection();
			prepStmt = connection.prepareStatement(deleteSQL);
			
			prepStmt.setString(1,productCode);

			
			int j=prepStmt.executeUpdate();

            if (j!=0)  //Just to ensure data has been deleted from the database
            {System.out.println("Success! Product deleted from database"); }
            
            else
            {System.out.println("Failed to delete Product from database"); }


            connection.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
