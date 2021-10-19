package com.seefoodquickly.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private Date createdAt;
	
	private Date updatedAt;
	
	@NotBlank
	@Size(min = 2, max = 50)
	private String itemName;
	
	@NotBlank
	@Size(min = 2, max = 300)
	private String description;
	
	private float price;
	
	//time to make in minutes
	private int makeMinutes;
	
	//Relationship Attributes
	@OneToMany(mappedBy="itemProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Item> items;
	
	@OneToOne(mappedBy="product", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Picture picture;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	//Empty constructor
	
	public Product() {
	}

	//Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getMakeMinutes() {
		return makeMinutes;
	}

	public void setMakeMinutes(int makeMinutes) {
		this.makeMinutes = makeMinutes;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Product(String itemName, String description, float price, int makeMinutes, List<Item> items) {
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.makeMinutes = makeMinutes;
		this.items = items;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	
	
	

}
