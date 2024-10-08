import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminTab extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Admin admin;
	private ArrayList<Product> products;
	private CardLayout cardLayout;
	private mainFrame mainFrame;
	   private JTable productsTable;
	    private DefaultTableModel productsTableModel;
	    
	    



	public AdminTab(Admin admin, ArrayList<Product> products, mainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.products = products;
		this.admin = admin;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


        cardLayout = new CardLayout();
        setContentPane(contentPane);
        contentPane.setLayout(cardLayout);
		
		
		 //===MAIN PANEL - Header===
        JPanel mainAdminPanel = new JPanel(new BorderLayout());
      
    
        JLabel adminPanelHeader = new JLabel("Admin Panel", SwingConstants.CENTER);
        adminPanelHeader.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        mainAdminPanel.add(adminPanelHeader, BorderLayout.NORTH);
        
        
        //===BUTTON PANEL===
        JPanel buttonsPanel = new JPanel(new GridLayout(1,3));
        
        //PROFILE 
        JButton adminInfoBtn = new JButton("Admin Profile");
        adminInfoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "adminProfilePanel");
            }
        });
        buttonsPanel.add(adminInfoBtn); //button leads to separate card (profile)
        
        JPanel adminProfilePanel = new JPanel(new BorderLayout()); //profile card layout
        JTextArea profileTextArea = new JTextArea(admin.toString());
        profileTextArea.setEditable(false);
        adminProfilePanel.add(profileTextArea, BorderLayout.CENTER);
        
        JButton profileBackButton = new JButton("Back to Admin Home"); //back button to main panel
        profileBackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(contentPane, "mainAdminPanel");
        	}
        });
        
        adminProfilePanel.add(profileBackButton, BorderLayout.SOUTH);
        
        
        
        //PRODUCTS 
        JButton allProductsBtn = new JButton("See All Products");
        
        allProductsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	  try {
                      // Reload products from Stock.txt
            		  ArrayList<Product> newProducts = ProductParser.parseProducts("Stock.txt");
            		    mainFrame.updateProducts(newProducts);

                      ArrayList<String> productStrings = admin.viewAllProducts(products);

                      // Clear the table
                      productsTableModel.setRowCount(0);

                      // Add All Products to the Table
                      for (String productString : productStrings) {
                          String[] productData = productString.split(", ");
                          productsTableModel.addRow(productData);
                      }

                      cardLayout.show(contentPane, "productViewPanel");
                  } catch (IOException ioException) {
                      ioException.printStackTrace();
                      JOptionPane.showMessageDialog(null, "Error loading products: " + ioException.getMessage());
                  } catch (Exception exception) {
                      exception.printStackTrace();
                      JOptionPane.showMessageDialog(null, "An error occurred: " + exception.getMessage());
                  }
         
            }
        });
        
        JPanel productViewPanel = new JPanel();  //products layout
        
     
        
        String[] columnNames = {"Barcode", "Category", "Type", "Brand", "Colour","Connectivity", "Quantity In Stock", "Original Price", "Retail Price", "Additional Info"};
        productsTableModel = new DefaultTableModel(columnNames, 0) {
        	 public boolean isCellEditable(int row, int column){ //ensure table can't be edited
        	     return false;
        	 }
        };
        productViewPanel.setLayout(new GridLayout(2, 1, 0, 0));
        productsTable = new JTable(productsTableModel);
        JScrollPane productsScrollPane = new JScrollPane(productsTable);

        productViewPanel.add(productsScrollPane);
        
        JButton productsBackButton = new JButton("Back to Admin Home"); //back button to main panel
        productsBackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(contentPane, "mainAdminPanel");
        	}
        });
        productViewPanel.add(productsBackButton);
        
        buttonsPanel.add(allProductsBtn); 
        
        
        
        
        //ADD STOCK 
        JButton addStockBtn = new JButton("Add To Stock");
        
        
        addStockBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    cardLayout.show(contentPane, "chooseStockToAddPanel");
         
            }
        });
        
        JPanel chooseStockToAddPanel = new JPanel(new GridLayout(2,1,0,0));//choose between keyboard and mouse
        
        JPanel topRowPanel = new JPanel(new GridLayout(1,2, 0, 0)); // top half has the two buttons
        JButton addKeyboardToStockBtn = new JButton("Add Keyboard Product");
        addKeyboardToStockBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    cardLayout.show(contentPane, "addKeyboardPanel"); // go here to add keyboard
         
            }
        });
        
        JButton addMouseToStockBtn = new JButton("Add Mouse Product");
        addMouseToStockBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    cardLayout.show(contentPane, "addMousePanel"); //go here to add mouse
         
            }
        });
        
        topRowPanel.add(addMouseToStockBtn);
        topRowPanel.add(addKeyboardToStockBtn);
        chooseStockToAddPanel.add(topRowPanel);
        
        JPanel bottomRowPanel = new JPanel(); //bottom half has back button
        JButton stockBackButton = new JButton("Back to Admin Home");
        stockBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "mainAdminPanel");
            }
        });
        bottomRowPanel.add(stockBackButton);
        chooseStockToAddPanel.add(bottomRowPanel);
        

        buttonsPanel.add(addStockBtn); //add to main admin panel
        contentPane.add(chooseStockToAddPanel, "chooseStockToAddPanel");
        
        JPanel addKeyboardPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        //FORM TO ADD KEYBOARD DETAILS
        addKeyboardPanel.add(new JLabel("Barcode: "));
        JTextField inputKeyboardBarcode = new JTextField();
        addKeyboardPanel.add(inputKeyboardBarcode);
        addKeyboardPanel.add(new JLabel("Brand:"));
        JTextField inputKeyboardBrand = new JTextField();
        addKeyboardPanel.add(inputKeyboardBrand);
        addKeyboardPanel.add(new JLabel("Colour:"));
        JTextField inputKeyboardColour = new JTextField();
        addKeyboardPanel.add(inputKeyboardColour);
        addKeyboardPanel.add(new JLabel("Connectivity (wired, wireless):"));
        JTextField inputKeyboardConnectivity = new JTextField();
        addKeyboardPanel.add(inputKeyboardConnectivity);
        addKeyboardPanel.add(new JLabel("Quantity In Stock:"));
        JTextField inputKeyboardQuantity = new JTextField();
        addKeyboardPanel.add(inputKeyboardQuantity);
        addKeyboardPanel.add(new JLabel("Original Cost:"));
        JTextField inputKeyboardOriginalCost = new JTextField();
        addKeyboardPanel.add(inputKeyboardOriginalCost);
        addKeyboardPanel.add(new JLabel("Retail Price:"));
        JTextField inputKeyboardRetailPrice = new JTextField();
        addKeyboardPanel.add(inputKeyboardRetailPrice);
        addKeyboardPanel.add(new JLabel("Keyboard Layout (UK, US):"));
        JTextField inputKeyboardLayout = new JTextField();
        addKeyboardPanel.add(inputKeyboardLayout);
        addKeyboardPanel.add(new JLabel("Keyboard Type (standard, flexible, gaming):"));
        JTextField inputKeyboardType = new JTextField();
        addKeyboardPanel.add(inputKeyboardType);
        
        JButton addKeyboardSubmitButton = new JButton("Add Keyboard to Stock");
        addKeyboardSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    admin.addKeyboardStock(
                        Integer.parseInt(inputKeyboardBarcode.getText()),
                        inputKeyboardBrand.getText(),
                        inputKeyboardColour.getText(),
                        ConnectivityType.valueOf(inputKeyboardConnectivity.getText().toUpperCase()),
                        Integer.parseInt(inputKeyboardQuantity.getText()),
                        Double.parseDouble(inputKeyboardOriginalCost.getText()),
                        Double.parseDouble(inputKeyboardRetailPrice.getText()),
                        KeyboardLayout.valueOf(inputKeyboardLayout.getText().toUpperCase()),
                        KeyboardType.valueOf(inputKeyboardType.getText().toUpperCase())
                    );
                    JOptionPane.showMessageDialog(null, "Keyboard added successfully!");
                    inputKeyboardBarcode.setText("");
                    inputKeyboardBrand.setText("");  
                    inputKeyboardColour.setText("");   
                    inputKeyboardConnectivity.setText("");   
                    inputKeyboardQuantity.setText("");   
                    inputKeyboardOriginalCost.setText("");   
                    inputKeyboardRetailPrice.setText("");   
                    inputKeyboardLayout.setText("");   
                    inputKeyboardType.setText("");   
                    cardLayout.show(contentPane, "mainAdminPanel");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Invalid input: " + exception.getMessage());
                }
            }
        });

        addKeyboardPanel.add(addKeyboardSubmitButton);

        JButton backToMainFromKeyboardButton = new JButton("Back to Stock Selector");
        backToMainFromKeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "chooseStockToAddPanel");
            }
        });
        addKeyboardPanel.add(backToMainFromKeyboardButton);

        contentPane.add(addKeyboardPanel, "addKeyboardPanel");
        
        
        JPanel addMousePanel = new JPanel(new GridLayout(10, 2, 0, 0));
        
        addMousePanel.add(new JLabel("Barcode:"));
        JTextField inputMouseBarcode = new JTextField();
        addMousePanel.add(inputMouseBarcode);
        addMousePanel.add(new JLabel("Brand:"));
        JTextField inputMouseBrand = new JTextField();
        addMousePanel.add(inputMouseBrand);
        addMousePanel.add(new JLabel("Color:"));
        JTextField inputMouseColour = new JTextField();
        addMousePanel.add(inputMouseColour);
        addMousePanel.add(new JLabel("Connectivity (wired, wireless):"));
        JTextField inputMouseConnectivity = new JTextField();
        addMousePanel.add(inputMouseConnectivity);
        addMousePanel.add(new JLabel("Quantity In Stock:"));
        JTextField inputMouseQuantity = new JTextField();
        addMousePanel.add(inputMouseQuantity);
        addMousePanel.add(new JLabel("Original Cost:"));
        JTextField inputMouseOriginalPrice = new JTextField();
        addMousePanel.add(inputMouseOriginalPrice);
        addMousePanel.add(new JLabel("Retail Price:"));
        JTextField inputMouseRetailPrice = new JTextField();
        addMousePanel.add(inputMouseRetailPrice);
        addMousePanel.add(new JLabel("Mouse Type (standard, gaming):"));
        JTextField inputMouseType = new JTextField();
        addMousePanel.add(inputMouseType);
        addMousePanel.add(new JLabel("Number of Buttons:"));
        JTextField inputMouseBtnNum = new JTextField();
        addMousePanel.add(inputMouseBtnNum);
        
        
        JButton addMouseSubmitButton = new JButton("Add Mouse");
        addMouseSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    admin.addMouseStock(
                        Integer.parseInt(inputMouseBarcode.getText()),
                        inputMouseBrand.getText(),
                        inputMouseColour.getText(),
                        ConnectivityType.valueOf(inputMouseConnectivity.getText().toUpperCase()),
                        Integer.parseInt(inputMouseQuantity.getText()),
                        Double.parseDouble(inputMouseOriginalPrice.getText()),
                        Double.parseDouble(inputMouseRetailPrice.getText()),
                        MouseType.valueOf(inputMouseType.getText().toUpperCase()),
                        Integer.parseInt(inputMouseBtnNum.getText())
                    );
                    JOptionPane.showMessageDialog(null, "Mouse added successfully!");
                    inputMouseBarcode.setText("");
                    inputMouseBrand.setText("");  
                    inputMouseColour.setText("");   
                    inputMouseConnectivity.setText("");   
                    inputMouseQuantity.setText("");   
                    inputMouseOriginalPrice.setText("");   
                    inputMouseRetailPrice.setText("");   
                    inputMouseType.setText("");   
                    inputMouseBtnNum.setText("");   
                    cardLayout.show(contentPane, "mainAdminPanel");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Invalid input: " + exception.getMessage());
                } 
            }
        });

        addMousePanel.add(addMouseSubmitButton);

        JButton backToMainFromMouseButton = new JButton("Back to Stock Selector");
        backToMainFromMouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "chooseStockToAddPanel"
                		+ "");
            }
        });
        addMousePanel.add(backToMainFromMouseButton);

        contentPane.add(addMousePanel, "addMousePanel");

        
        //Back To Main Btn
        JButton backToMainBtn = new JButton("Back to Main");
        backToMainBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		   dispose();
            	   mainFrame.setVisible(true); 
        	}
        });
        mainAdminPanel.add(backToMainBtn, BorderLayout.SOUTH);
        
        
        
        mainAdminPanel.add(buttonsPanel, BorderLayout.CENTER);
        contentPane.add(adminProfilePanel, "adminProfilePanel");	
        contentPane.add(mainAdminPanel, "mainAdminPanel"); 
        contentPane.add(productViewPanel, "productViewPanel");
		cardLayout.show(contentPane, "mainAdminPanel");
		
       //===GENERAL BUTTON STYLES
		styleButton(allProductsBtn);
		styleButton(addStockBtn);
		styleButton(adminInfoBtn);
		contentPane.setBackground(Color.black); 
		mainAdminPanel.setBackground(Color.black); 
		adminPanelHeader.setForeground(Color.white);
		buttonsPanel.setBackground(Color.black);
	
	    setVisible(true);
        
	}
	
    public void styleButton(JButton button) {
  	  button.setOpaque(true);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(113, 29, 225));
        Border lineBorder = BorderFactory.createLineBorder(Color.black, 2);
        button.setBorder(lineBorder);
  }

}

