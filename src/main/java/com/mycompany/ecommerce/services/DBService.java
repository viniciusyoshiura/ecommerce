package com.mycompany.ecommerce.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.mycompany.ecommerce.domain.enums.EPaymentStatus;
import com.mycompany.ecommerce.repositories.AddressRepository;
import com.mycompany.ecommerce.repositories.CategoryRepository;
import com.mycompany.ecommerce.repositories.CityRepository;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.repositories.ItemPurchaseOrderRepository;
import com.mycompany.ecommerce.repositories.PaymentRepository;
import com.mycompany.ecommerce.repositories.ProductRepository;
import com.mycompany.ecommerce.repositories.PurchaseOrderRepository;
import com.mycompany.ecommerce.repositories.StateRepository;

@Service
public class DBService {

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
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void instantiateTestDatabase() throws ParseException {
		
		// ---------- Category and product
		Category category1 = new Category(null, "Computing");
		Category category2 = new Category(null, "Office");
		Category category3 = new Category(null, "Household linen");
		Category category4 = new Category(null, "Electronics");
		Category category5 = new Category(null, "Garden and pets");
		Category category6 = new Category(null, "Furniture and household goods");
		Category category7 = new Category(null, "Cosmetics and body care");
		Category category8 = new Category(null, "Food and drinks");
		Category category9 = new Category(null, "Sports and outdoors");
		Category category10 = new Category(null, "Clothing");
		Category category11 = new Category(null, "Shoes");
		Category category12 = new Category(null, "Books, movies, music and games");
		Category category13 = new Category(null, "Bags and acessories");
		Category category14 = new Category(null, "Toys and baby products");

		Product product1 = new Product(null, "Computer", 2000.00);
		Product product2 = new Product(null, "Printer", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);
		Product product4 = new Product(null, "Office desk", 300.00);
		Product product5 = new Product(null, "Towel", 50.00);
		Product product6 = new Product(null, "Pillow", 90.00);
		Product product7 = new Product(null, "Smart TV LED 55\" UHD 4K Samsung 55RU7100", 2500.00);
		Product product8 = new Product(null, "Carolina Herrera 212 Men Eau de Toilette 100ml", 340.00);
		Product product9 = new Product(null, "Sapato Metropolitan Aspen Mahogany-Mahogany", 161.91);
		Product product10 = new Product(null, "Lord of the rings box", 272.34);
		Product product11 = new Product(null, "Lacoste polo", 375.00);
		Product product12 = new Product(null, "Jack Daniels 1 Litro Honey", 439.00);

		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product2, product4));
		category3.getProducts().addAll(Arrays.asList(product5, product6));
		category4.getProducts().addAll(Arrays.asList(product7));
		category7.getProducts().addAll(Arrays.asList(product8));
		category8.getProducts().addAll(Arrays.asList(product12));
		category10.getProducts().addAll(Arrays.asList(product11));
		category11.getProducts().addAll(Arrays.asList(product9));
		category12.getProducts().addAll(Arrays.asList(product10));

		product1.getCategories().addAll(Arrays.asList(category1));
		product2.getCategories().addAll(Arrays.asList(category1, category2));
		product3.getCategories().addAll(Arrays.asList(category1));
		product4.getCategories().addAll(Arrays.asList(category2));
		product5.getCategories().addAll(Arrays.asList(category3));
		product6.getCategories().addAll(Arrays.asList(category3));
		product7.getCategories().addAll(Arrays.asList(category4));
		product8.getCategories().addAll(Arrays.asList(category7));
		product9.getCategories().addAll(Arrays.asList(category11));
		product10.getCategories().addAll(Arrays.asList(category12));
		product11.getCategories().addAll(Arrays.asList(category10));
		product12.getCategories().addAll(Arrays.asList(category8));

		categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4, category5, category6,
				category7, category8, category9, category10, category11, category12, category13, category14));
		productRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7,
				product8, product9, product10, product11, product12));

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

		// ---------- Enconced password
		Client client1 = new Client(null, "Maria Silva", "test@klefv.com", "611.904.270-95",
				EClientType.PHYSICALPERSON, bCryptPasswordEncoder.encode("12345678a"));

		client1.getPhones().addAll(Arrays.asList("999999999", "111111111"));

		Address address1 = new Address(null, "Avenida João Dias", "2046", "Prédio 2", "Santo Amaro", "04724-003",
				client1, city2);
		Address address2 = new Address(null, "Rua Othay Ribeiro de Azambuja Neto", "184", "Chácara Green Valley",
				"Shopping Park", "04724-003", client1, city1);

		client1.getAddresses().addAll(Arrays.asList(address1, address2));

		clientRepository.saveAll(Arrays.asList(client1));

		addressRepository.saveAll(Arrays.asList(address1, address2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		PurchaseOrder purchaseOrder1 = new PurchaseOrder(null, sdf.parse("18/05/2020 14:23"), client1, address1);
		PurchaseOrder purchaseOrder2 = new PurchaseOrder(null, sdf.parse("28/05/2020 09:52"), client1, address2);

		Payment payment1 = new PaymentCreditCard(null, EPaymentStatus.SETTLED, purchaseOrder1, 6);
		purchaseOrder1.setPayment(payment1);

		Payment payment2 = new PaymentSlip(null, EPaymentStatus.PENDING, purchaseOrder2, sdf.parse("05/06/2020 23:59"),
				null);
		purchaseOrder2.setPayment(payment2);

		client1.getPurchaseOrders().addAll(Arrays.asList(purchaseOrder1, purchaseOrder2));

		purchaseOrderRepository.saveAll(Arrays.asList(purchaseOrder1, purchaseOrder2));

		paymentRepository.saveAll(Arrays.asList(payment1, payment2));

		ItemPurchaseOrder itemPurchaseOrder1 = new ItemPurchaseOrder(purchaseOrder1, product1, 20.00, 1, 2000.00);
		ItemPurchaseOrder itemPurchaseOrder2 = new ItemPurchaseOrder(purchaseOrder1, product3, 0.00, 2, 80.00);
		ItemPurchaseOrder itemPurchaseOrder3 = new ItemPurchaseOrder(purchaseOrder2, product2, 100.00, 1, 800.00);


		purchaseOrder1.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder1, itemPurchaseOrder2));
		purchaseOrder2.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder3));

		product1.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder1));
		product2.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder3));
		product3.getItemPurchaseOrders().addAll(Arrays.asList(itemPurchaseOrder2));
		
		itemPurchaseOrderRepository.saveAll(Arrays.asList(itemPurchaseOrder1, itemPurchaseOrder2, itemPurchaseOrder3));
		
	}
	
}
