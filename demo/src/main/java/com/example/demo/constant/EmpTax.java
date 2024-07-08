package com.example.demo.constant;

public class EmpTax {
	  private String employeeId;
	    private String firstName;
	    private String lastName;
	    private double totalSalary;

	    private double taxAmount;

	    private double cessAmount;

		public String getEmployeeId() {
			return employeeId;
		}

		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public double getTotalSalary() {
			return totalSalary;
		}

		public void setTotalSalary(double totalSalary) {
			this.totalSalary = totalSalary;
		}

		public double getTaxAmount() {
			return taxAmount;
		}

		public void setTaxAmount(double taxAmount) {
			this.taxAmount = taxAmount;
		}

		public double getCessAmount() {
			return cessAmount;
		}

		public void setCessAmount(double cessAmount) {
			this.cessAmount = cessAmount;
		}
		
    
}