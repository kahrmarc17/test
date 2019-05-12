package at.fh.swenga.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class InstrumentModel {

	private int id;
	 
	private String category;
	private String name;
	private String description;
	private double price;
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@NotNull(message = "Date cannot be null")
	private Date dateofavailability;
	private double weight;
	private int amount;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getDateofavailability() {
		return dateofavailability;
	}
	public void setDateofavailability(Date dateofavailability) {
		this.dateofavailability = dateofavailability;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	
	
	public InstrumentModel() {
		// TODO Auto-generated constructor stub
	}

	
	public InstrumentModel(int id, String category, String name, String description, double price,
			Date dateofavailability, double weight, int amount) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.description = description;
		this.price = price;
		this.dateofavailability = dateofavailability;
		this.weight = weight;
		this.amount = amount;
		}
	
	
	
	
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			InstrumentModel other = (InstrumentModel) obj;
			if (id != other.id)
				return false;
			return true;
		}
			
		
		
}
	
