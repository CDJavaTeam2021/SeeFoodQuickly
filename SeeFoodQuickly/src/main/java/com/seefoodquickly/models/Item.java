package com.seefoodquickly.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="items")
public class Item {

	
	
	//Relationship attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private Date createdAt;
	
	private Date updatedAt;
	
	private float lineTotal;
	
	private int quantity;
	
	@Transient
	private int cartIndex; //tracks the index in the list representing the cart which is not persisted at checkout
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id", updatable = true)
	private Order parentOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", updatable=true)
	private Product itemProduct;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	//Empty constructor
	
	public Item() {
	}

	
	//getters and setters
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

	public float getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(float lineTotal) {
		this.lineTotal = lineTotal;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getParentOrder() {
		return parentOrder;
	}

	public void setParentOrder(Order parentOrder) {
		this.parentOrder = parentOrder;
	}

	public Product getItemProduct() {
		return itemProduct;
	}

	public void setItemProduct(Product itemProduct) {
		this.itemProduct = itemProduct;
	}

	public int getCartIndex() {
		return cartIndex;
	}

	public void setCartIndex(int cartIndex) {
		this.cartIndex = cartIndex;
	}
	
	
	

}

