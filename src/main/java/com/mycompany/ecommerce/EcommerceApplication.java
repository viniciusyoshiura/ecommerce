package com.mycompany.ecommerce;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mycompany.ecommerce.domain.Address;
import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.domain.City;
import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.domain.ItemPurchaseOrder;
import com.mycompany.ecommerce.domain.Payment;
import com.mycompany.ecommerce.domain.PaymentCreditCard;
import com.mycompany.ecommerce.domain.PaymentSlip;
import com.mycompany.ecommerce.domain.Product;
import com.mycompany.ecommerce.domain.PurchaseOrder;
import com.mycompany.ecommerce.domain.State;
import com.mycompany.ecommerce.domain.enums.EClientType;
import com.mycompany.ecommerce.repositories.AddressRepository;
import com.mycompany.ecommerce.repositories.CategoryRepository;
import com.mycompany.ecommerce.repositories.CityRepository;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.repositories.ItemPurchaseOrderRepository;
import com.mycompany.ecommerce.repositories.PaymentRepository;
import com.mycompany.ecommerce.repositories.ProductRepository;
import com.mycompany.ecommerce.repositories.PurchaseOrderRepository;
import com.mycompany.ecommerce.repositories.StateRepository;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ItemPurchaseOrderRepository itemPurchaseOrderRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	// ---------- Run when initializing application
	@Override
	public void run(String... args) throws Exception {
		
		// ---------- Category and product
		Category category1 = new Category(null, "Computing");
		Category category2 = new Category(null, "Office");
		
		Product product1 = new Product(null, "Computer", 2000.00);
		Product product2 = new Product(null, "Printer", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);
		
		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product2));
		
		product1.getCategories().addAll(Arrays.asList(category1));
		product2.getCategories().addAll(Arrays.asList(category1, category2));
		product3.getCategories().addAll(Arrays.asList(category1));
		
		categoryRepository.saveAll(Arrays.asList(category1, category2));
		productRepository.saveAll(Arrays.asList(product1, product2, product3));
		
		// ---------- City and State
		State state1 = new State(null, "São Paulo");
		State state2 = new State(null, "Minas Gerais");
		
		City city1 = new City(null, "Uberlândia", state2);
		City city2 = new City(null, "São Paulo", state1);
		City city3 = new City(null, "Ribeirão Preto", state1);
		
		state1.getCities().addAll(Arrays.asList(city2, city3));
		state2.getCities().addAll(Arrays.asList(city1));
		
		stateRepository.saveAll(Arrays.asList(state1, state2));
		
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));
		
		Client client1 = new Client(null, "Maria Silva", "mara@gmail.com", "611.904.270-95", EClientType.PHYSICALPERSON);
		
		client1.getPhones().addAll(Arrays.asList("999999999", "111111111"));
		
		Address address1 = new Address(null, "Avenida João Dias", "2046", "Prédio 2", "Santo Amaro", "04724-003", client1, city2);
		Address address2 = new Address(null, "Rua Othay Ribeiro de Azambuja Neto", "184", "Chácara Green Valley", "Shopping Park", "04724-003", client1, city1);
		
		client1.getAddresses().addAll(Arrays.asList(address1, address2));
		
		clientRepository.saveAll(Arrays.asList(client1));
		
		addressRepository.saveAll(Arrays.asList(address1, address2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		PurchaseOrder purchaseOrder1 = new PurchaseOrder(null, sdf.parse("18/05/2020 14:23"), client1, address1);
		PurchaseOrder purchaseOrder2 = new PurchaseOrder(null, sdf.parse("28/05/2020 09:52"), client1, address2);
		
		Payment payment1 = new PaymentCreditCard(null, 2, purchaseOrder1, 6);
		purchaseOrder1.setPayment(payment1);
		
		Payment payment2 = new PaymentSlip(null, 1, purchaseOrder2, sdf.parse("05/06/2020 23:59"), null);
		purchaseOrder2.setPayment(payment2);
		
		client1.getPurchaseOrders().addAll(Arrays.asList(purchaseOrder1,purchaseOrder2));
		
		purchaseOrderRepository.saveAll(Arrays.asList(purchaseOrder1, purchaseOrder2));
		
		paymentRepository.saveAll(Arrays.asList(payment1, payment2));
		
		ItemPurchaseOrder itemPurchaseOrder1 = new ItemPurchaseOrder(purchaseOrder1, product1, 0.00, 1, 2000.00);
		ItemPurchaseOrder itemPurchaseOrder2 = new ItemPurchaseOrder(purchaseOrder1, product3, 0.00, 2, 80.00);
		ItemPurchaseOrder itemPurchaseOrder3 = new ItemPurchaseOrder(purchaseOrder2, product2, 100.00, 1, 800.00);
		
		itemPurchaseOrderRepository.saveAll(Arrays.asList(itemPurchaseOrder1, itemPurchaseOrder2, itemPurchaseOrder3));
		
		purchaseOrder1.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder1, itemPurchaseOrder2));
		purchaseOrder2.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder3));
		
		product1.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder1));
		product2.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder3));
		product3.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder2));
	}

	
	
}
