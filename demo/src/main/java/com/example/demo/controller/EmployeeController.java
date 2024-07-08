package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.EmpTax;
import com.example.demo.model.Employee;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	 private List<Employee> employees = new ArrayList<>();

	    @PostMapping("add")
	    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
	        employees.add(validateEmployeeData(employee));
	        return new ResponseEntity<>(employee, HttpStatus.CREATED);
	    }

	    

	    @GetMapping("get")
	    public ResponseEntity<List<Employee>> getAllEmployees() {
	        return new ResponseEntity<>(employees, HttpStatus.OK);
	    }
	    

	    private Employee validateEmployeeData(Employee employee) {
	        if (employee.getEmployeeId() == null || employee.getEmployeeId().trim().isEmpty()) {
	            throw new InvalidEmployeeDataException("Employee ID is required");
	        }
	        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
	            throw new InvalidEmployeeDataException("First name is required");
	        }
	        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
	            throw new InvalidEmployeeDataException("Last name is required");
	        }
	        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
	            throw new InvalidEmployeeDataException("Email is required");
	        }
	        if (!employee.getEmail().trim().contains("@")) {
	            throw new InvalidEmployeeDataException("Invalid email");
	        }
	        if (employee.getPhoneNumber() == null || employee.getPhoneNumber().isEmpty() || employee.getPhoneNumber().length()<10) {
	            throw new InvalidEmployeeDataException("Phone number is required");
	        }
	        if (employee.getDoj() == null) {
	            throw new InvalidEmployeeDataException("Date of joining is required");
	        }
	        if (employee.getSalary() == 0l || employee.getSalary() <= 0) {
	            throw new InvalidEmployeeDataException("Salary must be a positive number");
	        }
			return employee;
	    }

	  
	    public class InvalidEmployeeDataException extends RuntimeException {

	        public InvalidEmployeeDataException(String message) {
	            super(message);
	        }
	    }

	    
	    @PostMapping("/add-Tax")
	    public ResponseEntity<EmpTax> getEmployeeTax(@RequestBody Employee employee) {
	    	  EmpTax EmpTax = new EmpTax();
	        if (employee == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        Employee employee1 = calculateEmployeeTax(employee);
	        EmpTax.setEmployeeId(employee1.getEmployeeId());
	        EmpTax.setFirstName(employee1.getFirstName());
	        EmpTax.setLastName(employee1.getLastName());
	        EmpTax.setTotalSalary(employee1.getTotalSalary());
	        
	        EmpTax.setTaxAmount(employee1.getTaxAmount());
	        EmpTax.setCessAmount(employee1.getCessAmount());
	        return new ResponseEntity<>(EmpTax, HttpStatus.OK);
	    }
	    
	 
	    private Employee calculateEmployeeTax(Employee employee) {
	        double totalSalary = calculateTotalSalary(employee);
	        double taxAmount = calculateTaxAmount(totalSalary);
	        double cessAmount = calculateCessAmount(totalSalary);
	        System.out.println("totalSalary---"+totalSalary+"taxAmount----"+ taxAmount+ "cessAmount---"+cessAmount);
	        Employee employee1 = employee;
	        employee1.setTotalSalary(totalSalary);
	        employee1.setTaxAmount(taxAmount);
	        employee1.setCessAmount(cessAmount);
	        
	      
	        return employee1;
	    }
	    
	    public static double calculateTotalSalary(Employee employee) {
	    	
	    	 LocalDate startDate = (parseDateLocal(employee.getDoj().toString()));
	    	 LocalDate currentDate = LocalDate.now();

	         long monthsWorked = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), currentDate.withDayOfMonth(1));

	    	  if (startDate.getDayOfMonth() >= 16 && startDate.getMonthValue() == 5) {
	              
	              if (startDate.getMonthValue() == 5) {
	                  monthsWorked--; 
	              }
	              monthsWorked--; 
	          }

	          // Calculate total salary
	          double totalSalary = employee.getSalary() * monthsWorked;
	          return totalSalary;
	    }
	        
	       

	    
	    private double calculateTaxAmount(double totalSalary) {
	        double taxAmount = 0;
	        if (totalSalary <= 250000) {
	            taxAmount = 0;
	        } else if (totalSalary <= 500000) {
	            taxAmount = (totalSalary - 250000) * 0.05;
	            System.out.println("taxAmount--> -250000-==="+ taxAmount);
	        } else if (totalSalary <= 1000000) {
	            taxAmount = 12500 + (totalSalary - 500000) * 0.10;
	            System.out.println("taxAmount-> -500000-==="+ taxAmount);
	        } else {
	            taxAmount = 37500 + (totalSalary - 1000000) * 0.20;
	            System.out.println("taxAmount-->-1000000-==="+ taxAmount);
	        }
	       
	        return taxAmount;
	    }
	    
	    private double calculateCessAmount(double totalSalary) {
	        double cessAmount = 0;
	        if (totalSalary > 2500000) {
	            cessAmount = (totalSalary - 2500000) * 0.02;
	        }
	        return cessAmount;
	    }
	    
	    public Date parseDate(String str) {
	    	
	    	
	         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	         Date date = (Date) dtf.parse(str);
	         System.out.println("Parsed date: " + date);
	    	
	    	return date;
	    }
	    public static LocalDate parseDateLocal(String str) {
	    	   System.out.println("input date: " + str);
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
        LocalDate date1 = LocalDate.parse(str, dtf);
        System.out.println("Parsed date: " + date1);
        return date1;
        }
}