package com.mycompany.ecommerce.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.ecommerce.domain.enums.EClientType;

@Entity
public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	// ---------- unique = true: alternate key
	@Column(unique = true)
	private String email;
	private String document;
	
	private Integer type;
	
	// ---------- Does not show password on JSON
	@JsonIgnore
	private String password;
	
	// ---------- CascadeType.ALL: when a client is deleted, all addresses must be deleted
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private List<Address> addresses = new ArrayList<>();
	
	// ---------Using Set to avoid repetitions
	@ElementCollection
	@CollectionTable(name = "phone")
	private Set<String> phones = new HashSet<>();
	
	// ---------- @JsonIgnore: omits object in response JSON
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	List<PurchaseOrder> purchaseOrders = new ArrayList<>();
	
	public Client () {
		
		
	}

	public Client(Integer id, String name, String email, String document, EClientType eClientType, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.document = document;
		this.type = (eClientType == null) ? null : eClientType.getCode();
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public EClientType getType() {
		return EClientType.toEnum(type);
	}

	public void setType(EClientType eClientType) {
		this.type = eClientType.getCode();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public List<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}

	public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
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
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
