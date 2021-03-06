package com.mycompany.ecommerce.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PurchaseOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOrder;

	// ---------- One to One relationship with Payment
	// ---------- Using cascade to avoid Transient error in persistence
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address deliveryAddress;

	@OneToMany(mappedBy = "id.purchaseOrder")
	private Set<ItemPurchaseOrder> itemPurchaseOrders = new HashSet<>();

	public PurchaseOrder() {

	}

	public PurchaseOrder(Integer id, Date dateOrder, Client client, Address deliveryAddress) {
		super();
		this.id = id;
		this.dateOrder = dateOrder;
		this.client = client;
		this.deliveryAddress = deliveryAddress;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Set<ItemPurchaseOrder> getItemPurchaseOrders() {
		return itemPurchaseOrders;
	}

	public void setItemPurchaseOrders(Set<ItemPurchaseOrder> itemPurchaseOrders) {
		this.itemPurchaseOrders = itemPurchaseOrders;
	}

	public double getTotalValue() {

		double sum = 0.0;
		for (ItemPurchaseOrder itemPurchaseOrder : itemPurchaseOrders) {

			sum += itemPurchaseOrder.getSubTotal();

		}

		return sum;
	}

	public double getTotalDiscount() {

		double sum = 0.0;
		for (ItemPurchaseOrder itemPurchaseOrder : itemPurchaseOrders) {

			sum += itemPurchaseOrder.getDiscount();

		}

		return sum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PurchaseOrder other = (PurchaseOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Order number: ");
		builder.append(id);
		builder.append(", Date order: ");
		builder.append(sdf.format(getDateOrder()));
		builder.append(", Client: ");
		builder.append(getClient().getName());
		builder.append(", Payment status: ");
		builder.append(getPayment().getStatus().getDescription());
		builder.append("\nDetails: \n");
		for (ItemPurchaseOrder itemPurchaseOrder : getItemPurchaseOrders()) {
			builder.append(itemPurchaseOrder.toString());
		}
		builder.append("\nTotal discount:");
		builder.append(nf.format(getTotalDiscount()));
		builder.append("\nTotal value:");
		builder.append(nf.format(getTotalValue()));
		return builder.toString();
	}

}
