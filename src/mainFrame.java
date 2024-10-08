import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class mainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private ArrayList<User> users;
    private ArrayList<Product> products;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					 ArrayList<Product> products = ProductParser.parseProducts("Stock.txt");
					 ArrayList<User> users = UserParser.parseUsers("UserAccounts.txt", products);
			
					mainFrame frame = new mainFrame(users, products);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	   public void updateProducts(ArrayList<Product> newProducts) {
	        this.products = newProducts;
	    } //update products array after adding a new product 
	
	/**
	 * Create the frame.
	 */
	public mainFrame(ArrayList<User> users, ArrayList<Product> products) {
		this.users = users;
		this.products = products;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		//contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pick your name from below to enter the relevant panel.");
		//lblNewLabel.setBounds(66, 21, 330, 16);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		

		
	    userListModel = new DefaultListModel<>();
        for (User user : users) {
            userListModel.addElement(user.getName());
        }
	
        userList = new JList<>(userListModel);
        //userList.setBounds(55, 49, 341, 176);
        contentPane.add(userList, BorderLayout.CENTER);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        
		JButton btnNewButton = new JButton("Select User");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
						int selectedIndex = userList.getSelectedIndex();
		                if (selectedIndex != -1) {
		                    User selectedUser = users.get(selectedIndex);
		                    dispose(); // Close main tab
		                    if (selectedUser instanceof Admin) {
		                    	
		                        new AdminTab((Admin) selectedUser, products, mainFrame.this);
		               
		                    } else if (selectedUser instanceof Customer) {
		            
		                    	 new CustomerTab((Customer) selectedUser, products, mainFrame.this);
		           
		                    }
		                }
		            }
		});
		//btnNewButton.setBounds(111, 237, 229, 29);
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		

	}
}

