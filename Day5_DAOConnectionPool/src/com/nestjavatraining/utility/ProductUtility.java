package com.nestjavatraining.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;

import java.sql.Date;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.nestjavatraining.entity.Product;
import com.nestjavatraining.service.ProductService;
import com.nestjavatraining.service.ProductServiceImpl;

public class ProductUtility {
	
	private static ProductService productService=new ProductServiceImpl();

	public static void main(String[] args) throws IOException 
	{
		
		int c=0;
		Scanner scanner = new Scanner(System.in); 
		
		do 
		{ 
			
			System.out.println("1.Save Product, 2.Delete Product, 3.List All Products, 4.Search Product"); 
			int choice = scanner.nextInt(); 
		
			switch (choice) 
			{ 
			
				case 1: saveProduct(); 
						break; 
						
				case 2: deleteProduct(); 
				
						break; 
						
				case 3: listAllProducts(); 
						break; 
						
				case 4: searchProduct(); 
						break; 
						
				default: System.out.println("Invalid Choice"); 
				
			} 
			
			System.out.println("Do you want to continue?(1/0)");
			c = scanner.nextInt();
			
		} while (c == 1);
		
	}
	
	private static void searchProduct() 
	{   
		System.out.println("Enter Product Code to search:");
		
		Scanner sc = new Scanner(System.in);
		
		String prodCode = sc.next();
		
		Product product = productService.getProduct(prodCode);
		
		if(product!=null)
		{
			System.out.println("Product Code " + " " + " Product Name"+ " " +"  Product Description" + "      "+ "Activation Date"+"       "+"Expiry Date"); 

			System.out.println(product.getProductCode() + "         " + product.getProductName()+ "       " +product.getProductDescription() + "           "+ product.getActivationDate()+"      "+product.getExpiryDate());   
		}
		else
			System.out.println("Product does not exist in database\n");
		
	}
	
	private static void listAllProducts() 
	{ 
		List<Product> productList = productService.listAllProducts(); 
		System.out.println("Product Code " + " " + " Product Name"+ " " +"       Product Description" + "    "+ "Activation Date"+"    "+"Expiry Date"); 
		
		for(Product product : productList) 
			
			System.out.println(product.getProductCode() + "         " + product.getProductName()+ "       " +product.getProductDescription() + "          "+ product.getActivationDate()+"       "+product.getExpiryDate());   
	}
	
	private static void deleteProduct() 
	{ 
		System.out.println("Enter product code to delete:");
		
		Scanner sc1=new Scanner(System.in);
		
		String prodCode=sc1.next();
		
		productService.deleteProduct(prodCode);
		
	}
	
	private static void saveProduct() throws IOException 
	{ 
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter product code: ");
		
		String prodCode=br.readLine();
		
		System.out.println("Enter product name: ");
		
		String prodName=br.readLine();
		
		System.out.println("Enter product description: ");
		
		String prodDesc=br.readLine();
		
		System.out.println("Enter product activation date (yyyy-MM-dd): ");
		
		String inputOpenDate = br.readLine();
		
		Date prodActDate=Date.valueOf(inputOpenDate);//converting string into sql date
		
		System.out.println("Enter product expiry date (yyyy-MM-dd): ");
		
		
		String inputExpDate = br.readLine();

		
		Date prodExpDate=Date.valueOf(inputExpDate);
		
	
		Product product=new Product(prodCode,prodName,prodDesc,prodActDate,prodExpDate);
		
		productService.saveProduct(product);
		
	}

}
