import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class CustomerTab extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Customer customer;
	   private ArrayList<Product> products;
	private CardLayout cardLayout;
   private JTable productsTable;
    private DefaultTableModel productsTableModel;
    private JTable basketTable;
    private DefaultTableModel basketTableModel;
    private mainFrame mainFrame;

    
    public CustomerTab(Customer customer, ArrayList<Product> products, mainFrame mainFrame) {
    	this.mainFrame = mainFrame;
        this.customer = customer;
        this.products = products;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        

        cardLayout = new CardLayout();
        setContentPane(contentPane);
        contentPane.setLayout(cardLayout);
        
        
        
        
        //===MAIN PANEL - Header===
        JPanel mainCustomerPanel = new JPanel(new BorderLayout());
      
    
        JLabel customerPanelHeader = new JLabel("Customer Panel", SwingConstants.CENTER);
        customerPanelHeader.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        mainCustomerPanel.add(customerPanelHeader, BorderLayout.NORTH);
        
        
        //===BUTTON PANEL===
        JPanel buttonsPanel = new JPanel(new GridLayout(1,3));
        
        //PROFILE 
        JButton customerInfoBtn = new JButton("Customer Profile");
        customerInfoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "customerProfilePanel");
            }
        });
        buttonsPanel.add(customerInfoBtn); //button leads to separate card (profile)
        
        JPanel customerProfilePanel = new JPanel(new BorderLayout()); //profile card layout
        JTextArea profileTextArea = new JTextArea(customer.toString());
        profileTextArea.setEditable(false);
        customerProfilePanel.add(profileTextArea, BorderLayout.CENTER);
        
        JButton profileBackButton = new JButton("Back to Customer Home"); //back button to main panel
        profileBackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(contentPane, "mainCustomerPanel");
        	}
        });
        
        customerProfilePanel.add(profileBackButton, BorderLayout.SOUTH);
        
        
        //ALL PRODUCTS
        JButton productViewBtn = new JButton("View All Products");
        productViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
  
                    ArrayList<String> productStrings = customer.viewAllProducts(products);
                    
                    // Clear the table
                    productsTableModel.setRowCount(0);
                 

                    // Add All Products to the Table
                    for (String productString : productStrings) {
                        String[] productData = productString.split(", ");
                        productsTableModel.addRow(productData);
                    }
                    
                    
                    cardLayout.show(contentPane, "productViewPanel");
         
            }
        });

        buttonsPanel.add(productViewBtn); //button leads to separate card (all products)

        JPanel productViewPanel = new JPanel();  //products card layout
        
        JButton productsBackButton = new JButton("Back to Customer Home"); //back button to main panel
        productsBackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(contentPane, "mainCustomerPanel");
        	}
        });
        
        
        productViewPanel.setLayout(new GridLayout(5, 1, 0, 0));

        //Product View Panel - search and filter sections:
        JPanel rowPanel = new JPanel(new GridLayout(1, 2));
        productViewPanel.add(rowPanel);

        
        JComboBox searchCombo = new JComboBox();
        rowPanel.add(searchCombo);
        
        JButton searchButton = new JButton("Search by Barcode");
        rowPanel.add(searchButton);
        
        productViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Populate Search Combo Box with all Barcodes
            	searchCombo.removeAllItems();
            	searchCombo.addItem("All Products");
            	for (Product product : products) {
            		searchCombo.addItem(Integer.toString(product.getBarcode()));
            	}
         
            }
        });
        
        
        //Search By Barcode button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedBarcode = (String) searchCombo.getSelectedItem();

                productsTableModel.setRowCount(0); //clear table

                if (selectedBarcode.equals("All Products")) { //if All, display all products
                    for (Product product : products) {
                        productsTableModel.addRow(customer.getProductForCustomer(product).split(", "));
                    }
                } else {
                    // Else display selected product
                    Product product = customer.searchByBarcode(products, Integer.parseInt(selectedBarcode));
                    if (product != null) {
                        productsTableModel.addRow(customer.getProductForCustomer(product).split(", "));
                    }
                }
            }
        });
        
        JPanel rowPanel2 = new JPanel(new GridLayout(1, 2));
        productViewPanel.add(rowPanel2);
        
        JComboBox filterCombo = new JComboBox();
        rowPanel2.add(filterCombo);
        
        JButton filterButton = new JButton("Filter Mice by Number of Buttons");
        rowPanel2.add(filterButton);

        //
        productViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterCombo.removeAllItems();
                filterCombo.addItem("Choose");

                // Add number of buttons on the mice to combo
                Set<Integer> numOfButtonsSet = new HashSet<>();
                for (Product product : products) {
                    if (product instanceof Mouse) {
                        numOfButtonsSet.add(((Mouse) product).getNumOfButtons()); 
                    }
                }
                for (Integer numOfButtons : numOfButtonsSet) {
                    filterCombo.addItem(Integer.toString(numOfButtons));//add num of buttons to combo
                }
            }
        });
        
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get selected number 
        	   String stringNumOfButtons = (String) filterCombo.getSelectedItem();
        	     if (stringNumOfButtons.equals("Choose")) {
        	            return;
        	        }
            	
                int numOfButtons = Integer.parseInt((String) filterCombo.getSelectedItem());
                
                // Clear table
                productsTableModel.setRowCount(0);

                // Display only the mice with the selected number of buttons
                ArrayList<Product> filteredProducts = customer.filterMiceByButtons(products, numOfButtons);
                for (Product product : filteredProducts) {
                    productsTableModel.addRow(customer.getProductForCustomer(product).split(", "));
                }
            }
        });
        
        

        //Table of Products
        String[] columnNames = {"Barcode", "Brand", "Colour", "Connectivity", "Quantity In Stock", "Retail Price", "Category", "Mouse/Keyboard Type", "Additional Info"};
        productsTableModel = new DefaultTableModel(columnNames, 0) {
        	 public boolean isCellEditable(int row, int column){ //ensure table can't be edited
        	     return false;
        	 }
        };
        productsTable = new JTable(productsTableModel);
        JScrollPane productsScrollPane = new JScrollPane(productsTable);

        productViewPanel.add(productsScrollPane);
        
        
        //Add To Cart
        JPanel rowPanel3 = new JPanel(new GridLayout(1, 2));
        productViewPanel.add(rowPanel3);

        
        JComboBox addToCartCombo = new JComboBox();
        rowPanel3.add(addToCartCombo);
        
        JButton addToCartBtn = new JButton("Add To Cart");
        rowPanel3.add(addToCartBtn);
        
        productViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Populate Add Cart Combo Box with all Barcodes
            	addToCartCombo.removeAllItems();
            	addToCartCombo.addItem("Choose");
            	for (Product product : products) {
            		addToCartCombo.addItem(Integer.toString(product.getBarcode()));
            	}
         
            }
        });
        
        addToCartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get selected number 
        	   String selectedProductBarcode = (String) addToCartCombo.getSelectedItem();
	    	   if (selectedProductBarcode.equals("Choose")) {
    	            return;
	    	   }
	    	   
	    	   Product selectedProduct = null;
	    	   for (Product product: products) {
	    		   if (Integer.toString(product.getBarcode()).equals(selectedProductBarcode)) {
	    			   selectedProduct = product;
	    			   break;
	    		   }
	    	   }
	    	   
	    	   if (selectedProduct != null) {
	    		   try {
					customer.addToBasket(selectedProduct);
				} catch (Exception error) {

					JOptionPane.showMessageDialog(null, error.getMessage());
				}
	    
	    	   }
    	     
    	     

            }
        });
        
        
        
        //Back Button
        productViewPanel.add(productsBackButton); //4th row
        
      
        
        
        
        //BASKET 
        JButton basketBtn = new JButton("View Basket"); 
        basketBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	basketTableModel.setRowCount(0);
            	
                ArrayList<Product> basketProducts = customer.viewBasket();//basket arraylist

                // Add all items from the basket
                for (Product product : basketProducts) {
                    basketTableModel.addRow(customer.getProductForCustomer(product).split(", ")); 
                }
                cardLayout.show(contentPane, "basketPanel");
            }
        });
        buttonsPanel.add(basketBtn); //button leads to separate card (basket)
        
        JPanel basketPanel = new JPanel(new GridLayout(4,1,0,0));  //products card layout
        
        JButton basketBackBtn = new JButton("Back to Customer Home"); //back button to main panel
        basketBackBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(contentPane, "mainCustomerPanel");
        	}
        });
        //basket table of added products
        String[] basketColumnNames = {"Barcode", "Brand", "Colour", "Connectivity", "Quantity In Stock", "Retail Price", "Category", "Mouse/Keyboard Type", "Additional Info"};
        basketTableModel = new DefaultTableModel(basketColumnNames, 0) {
        	 public boolean isCellEditable(int row, int column){ //ensure table can't be edited
        	     return false;
        	 }
        };
        basketTable = new JTable(basketTableModel);
        JScrollPane basketScrollPane = new JScrollPane(basketTable);

        basketPanel.add(basketScrollPane);
        
       //Clear Basket 
       JButton clearBasketBtn = new JButton("Clear Basket");
       basketPanel.add(clearBasketBtn);
       
       clearBasketBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
        	   customer.clearBasket();
        	   basketTableModel.setRowCount(0);
           }
       });
       
       //Complete Payment
       JButton completePaymentBtn = new JButton("Complete Payment");
       completePaymentBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               cardLayout.show(contentPane, "paymentOptionPanel");
           }
       });
       basketPanel.add(completePaymentBtn);
       
        
       
       basketPanel.add(basketBackBtn); // 4th row
       
       JPanel paymentOptionPanel = new JPanel(new GridLayout(4, 1));
       JLabel paymentOptionLabel = new JLabel("Choose Payment Method:", SwingConstants.CENTER);
       paymentOptionLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
       paymentOptionPanel.add(paymentOptionLabel);

       //Go to PayPal Frame
       JButton payPalBtn = new JButton("PayPal");
       payPalBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               cardLayout.show(contentPane, "payPalPaymentPanel");
           }
       });
       paymentOptionPanel.add(payPalBtn);

       //Go to Credit Card Frame
       JButton creditCardBtn = new JButton("Credit Card");
       creditCardBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               cardLayout.show(contentPane, "creditCardPaymentPanel");
           }
       });
       paymentOptionPanel.add(creditCardBtn);
       
       JButton completeBackToMain = new JButton("Back to Basket"); //Complete Payment Back Btn
       completeBackToMain.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               cardLayout.show(contentPane, "basketPanel");
           }
       });
       paymentOptionPanel.add(completeBackToMain);
       
       //Paypal Frame
       JPanel payPalFrame = new JPanel(new GridLayout(4, 1, 0, 0));
       JLabel payPalEmailLabel = new JLabel("Enter PayPal Email: ");
       JTextField inputPayPalEmail = new JTextField();
       JButton payPalCompleteButton = new JButton("Complete Payment");
       JButton payPalCompleteBackBtn = new JButton("Back to Choose Payment Method");
       
       payPalCompleteBackBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               cardLayout.show(contentPane, "paymentOptionPanel");
           }
       });
       
       payPalCompleteButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        String email = inputPayPalEmail.getText();
    	        if (!customer.validEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid PayPal email.");
                    return;
                }
    	        Paypal paypal = new Paypal(email);
    	        Address customerAddress = customer.getAddress();
    	        try {
                    Receipt receipt = customer.completePayment(paypal, customerAddress);
                    showReceipt(receipt);
                    cardLayout.show(contentPane, "receiptPanel");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
    	    }
    	});

       
       payPalFrame.add(payPalEmailLabel);
       payPalFrame.add(inputPayPalEmail);
       payPalFrame.add(payPalCompleteButton);
       payPalFrame.add(payPalCompleteBackBtn);
       
       contentPane.add(payPalFrame, "payPalPaymentPanel");
       
       
       
       //Credit Card Frame
       JPanel creditCardFrame = new JPanel(new GridLayout(5, 1, 0, 0));
       JLabel creditCardNumberLabel = new JLabel("Enter Credit Card Number (6 digits): ");
       JTextField inputCreditCardNumber = new JTextField();
       JLabel creditCardSecurityLabel = new JLabel("Enter Security Number (3 digits): ");
       JTextField inputCreditCardSecurity = new JTextField();
       JButton creditCardCompleteButton = new JButton("Complete Payment");
       JButton creditCardCompleteBackBtn = new JButton("Back to Choose Payment Method");

       creditCardCompleteBackBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               cardLayout.show(contentPane, "paymentOptionPanel");
           }
       });

       creditCardCompleteButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String cardNumberStr = inputCreditCardNumber.getText();
               String securityNumberStr = inputCreditCardSecurity.getText();

               try {
                   int cardNumber = Integer.parseInt(cardNumberStr);
                   int securityNumber = Integer.parseInt(securityNumberStr);

                   if (!customer.validCreditCardDetails(cardNumber, securityNumber)) {
                       JOptionPane.showMessageDialog(null, "Please enter valid Credit Card details.");
                       return;
                   }

                   CreditCard creditCard = new CreditCard(cardNumber, securityNumber);
                   Address deliveryAddress = customer.getAddress(); // Assuming customer has the address

                   try {
                       Receipt receipt = customer.completePayment(creditCard, deliveryAddress);
                       showReceipt(receipt);
                       cardLayout.show(contentPane, "receiptPanel");
                   } catch (Exception ex) {
                       JOptionPane.showMessageDialog(null, ex.getMessage());
                   }

               } catch (NumberFormatException ex) {
                   JOptionPane.showMessageDialog(null, "Please enter valid numbers for Credit Card details.");
               }
           }
       });

       creditCardFrame.add(creditCardNumberLabel);
       creditCardFrame.add(inputCreditCardNumber);
       creditCardFrame.add(creditCardSecurityLabel);
       creditCardFrame.add(inputCreditCardSecurity);
       creditCardFrame.add(creditCardCompleteButton);
       creditCardFrame.add(creditCardCompleteBackBtn);

       // Add Credit Card frame to the content pane
       contentPane.add(creditCardFrame, "creditCardPaymentPanel");

       
       
       

       contentPane.add(paymentOptionPanel, "paymentOptionPanel");
        

        
       //Add to contentPane
		mainCustomerPanel.add(buttonsPanel, BorderLayout.CENTER);

        contentPane.add(customerProfilePanel, "customerProfilePanel");
        contentPane.add(basketPanel, "basketPanel");
        contentPane.add(productViewPanel, "productViewPanel");
		contentPane.add(mainCustomerPanel, "mainCustomerPanel"); 
		
		cardLayout.show(contentPane, "mainCustomerPanel");
        
		//Back To Main Btn
        JButton backToMainBtn = new JButton("Back to Main");
        backToMainBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		   dispose();
            	   mainFrame.setVisible(true); 
        	}
        });
        mainCustomerPanel.add(backToMainBtn, BorderLayout.SOUTH);
        

        
       //===GENERAL BUTTON STYLES
		styleButton(basketBtn);
		styleButton(productViewBtn);
		styleButton(customerInfoBtn);
		contentPane.setBackground(Color.black); 
		mainCustomerPanel.setBackground(Color.black); 
		customerPanelHeader.setForeground(Color.white);
		buttonsPanel.setBackground(Color.black);
        
        
        
        setVisible(true); // Make the frame visible
    }
    

    private void showReceipt(Receipt receipt) {
        JPanel receiptPanel = new JPanel(new BorderLayout());
        JTextArea receiptTextArea = new JTextArea(receipt.toString());
        receiptTextArea.setEditable(false);

        JButton backToMainBtn = new JButton("Back to Main");
        backToMainBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "mainCustomerPanel");
            }
        });

        receiptPanel.add(new JScrollPane(receiptTextArea), BorderLayout.CENTER);
        receiptPanel.add(backToMainBtn, BorderLayout.SOUTH);
        contentPane.add(receiptPanel, "receiptPanel");
    }
    
    public void styleButton(JButton button) {
    	  button.setOpaque(true);
          button.setForeground(Color.WHITE);
          button.setBackground(new Color(113, 29, 225));
          Border lineBorder = BorderFactory.createLineBorder(Color.black, 2);
          button.setBorder(lineBorder);
    }

}



