

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import client.ChatClient;
import common.ChatIF;
import drawpad.OpenDrawPad;

public class ClientGUI extends Frame implements ChatIF, Observer{
	private boolean flag = true;
	private Choice choice = new Choice();
	private List Users = new List();
	private JButton sendB = new JButton("Send");
	private JTextField message = new JTextField();
	private JLabel portLB;
	private JLabel hostLB;
	private JLabel messageLB = new JLabel("Message: ", Label.LEFT);
	private List messageList = new List();
	Panel bottom = new Panel();
	ChatClient client;
	private JButton privateMess = new JButton("Private");
	private JButton channel = new JButton("Channel");
	private JButton forward = new JButton("Forward");
	private JRadioButton available = new JRadioButton("Available",true);
	private JRadioButton notavailable = new JRadioButton("Not Available",false);

    private ButtonGroup group = new ButtonGroup();
    private JButton status = new JButton("Status");
    private JButton logoff = new JButton("Change Login");
    private JButton whoblocksme = new JButton("Who Blocks Me");
    private JButton whoiblock = new JButton("Who I Block");
    private JButton block = new JButton("Blocking");
    private JButton users = new JButton("List Users");
	
    
    private List BmessageList = new List();
    
	public ClientGUI(){
		super("Simple Chat");
		setSize(500, 600);
		setVisible(true);

		setLayout(new BorderLayout(5,5));
		Panel top = new Panel();
		Panel bottom = new Panel();
		add("Center", messageList);
		add("North", top);
		add("South", bottom);

		top.setLayout(new GridLayout(0,3));
		bottom.setLayout(new GridLayout(0,3));
		
//		top.add(hostLB = new JLabel("Host: "+client.getHost()));
//		top.add(portLB = new JLabel("Port: "+client.getPort()));	
		top.add(logoff);
//mannny////////////////////////////////////////////////////
		top.add(users);
////////////////////////////////////////////////////
		bottom.add(messageLB);
		bottom.add(message);
		bottom.add(sendB);
		bottom.add(privateMess);
		bottom.add(channel);
		bottom.add(forward);
		bottom.add(whoblocksme);
		bottom.add(whoiblock);
		bottom.add(block);
		bottom.add(available);
		bottom.add(notavailable);
		group.add(available);
		group.add(notavailable);
		bottom.add(status);
		

		createLoginPopup();

		OpenDrawPad odp = new OpenDrawPad(client, this);
//manny////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		whoiblock.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#whoiblock");
				}
				catch(Exception x){}
			}
		});
	
		whoblocksme.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#whoblocksme");
				}
				catch(Exception x){}
			}
		});
		available.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#available");
				}
				catch(Exception x){}
			}
		});
		notavailable.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#notavailable");
				}
				catch(Exception x){}
			}
		});
		status.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#status tim");
				}
				catch(Exception x){}
			}
		});
		users.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#users");
				}
				catch(Exception x){}
			}
		});

		block.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				createBlockingPopup();
				}
				catch(Exception x){}
			}
		});
		

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		


		sendB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				send();
				message.setText("");
			}
		});

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	

	
	this.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent we){
			System.exit(0);
		}
	});
	
	logoff.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			try {
				client.closeConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			createLoginPopup();
		}
	});

	privateMess.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			setPrivate();
		}
	});
	
	forward.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			setForward();
		}
	});
	
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
public void createBlockingPopup(){
		blockingPopup blocking = new blockingPopup(this);
		blocking.show();
}	

class blockingPopup extends Dialog{
	
	
	

	JTextField user = new JTextField();
	Label username = new Label("User: ", Label.LEFT);
	JButton exit = new JButton("Exit");
	JButton block = new JButton("Block");
	JButton unblock = new JButton("unBlock");
	public blockingPopup(Frame parent){
		super(parent, true);
		
		int H_SIZE = 300;
		int V_SIZE = 400;
		Panel p = new Panel();
		Panel Bcenter = new Panel();
		Panel Bbottom = new Panel();
		Bcenter.setLayout(new GridLayout(6,2));
		add("North", BmessageList);
		add("Center", Bcenter);
		add("South", Bbottom);
		flag = false;
		BmessageList.clear();
		client.send("#users");
		Bcenter.add(username);
		Bcenter.add(user);
		Bcenter.add(block);
		Bcenter.add(unblock);
		Bbottom.add(exit);
		resize(H_SIZE, V_SIZE);
	
	exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			BmessageList.clear();
			dispose();
			flag = true;
		}
	});
	
	}}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		


	
	




	public void display(String message) {

		if(message.split(" ",2)[1].equals("wants you to monitor their messages! Type #accept to have their messages forwarded to you.")){
			setForwardAccept(message.split(" ",2)[0]);
		} else if(flag){
			messageList.add(message);
			messageList.makeVisible(messageList.getItemCount()-1);
		}else{
			BmessageList.add(message);
			BmessageList.makeVisible(messageList.getItemCount()-1);
		}
	}

	public void send(){
		try{
			client.send(message.getText());
		}
		catch(Exception ex){
			messageList.add(ex.toString());
			messageList.makeVisible(messageList.getItemCount()-1);
			messageList.setBackground(Color.yellow);
		}
	}

	public void setClient(String user, String pass, String host, int portint){
		try {
			client = new ChatClient(user, pass, host, portint, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setPrivate(){
		PrivatePopup pp = new PrivatePopup(this);
		pp.show();	
	}
	
	private void setForward(){
		ForwardPopup fp = new ForwardPopup(this);
		fp.show();	
	}
	
	private void createLoginPopup(){
		LoginPopup lp = new LoginPopup(this);
		lp.show();
	}
	
	public void setForwardAccept(String c){
		ForwardAcceptPopup ap = new ForwardAcceptPopup(this, c);
		ap.show();
	}
	
class ForwardAcceptPopup extends Dialog{
		
		int H_SIZE = 300;
		int V_SIZE = 215;
		
		Panel panel = new Panel();
		Label userL;
		JButton cancelforward = new JButton("Cancel Forwarding");
		JButton acceptforward = new JButton("Accept Forwarding");
		
		public ForwardAcceptPopup(Frame parent, String c){
			super(parent, true);
			
			userL = new Label(c+" wants you to monitor their messages.");
			panel.setLayout(new GridLayout(3,2));
			panel.add(userL);
			panel.add(acceptforward);
			panel.add(cancelforward);
			add("Center", panel);
			resize(H_SIZE, V_SIZE);
			
			cancelforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "Monitoring not accepted!";
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});
			
			acceptforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "#accept";
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});			
		}
	}
	
	class ForwardPopup extends Dialog{
		
		int H_SIZE = 200;
		int V_SIZE = 215;
		
		Panel panel = new Panel();
		Label userL = new Label("Forward To:");
		TextField userTF = new TextField();
		JButton cancelforward = new JButton("Cancel Forwarding");
		JButton acceptforward = new JButton("Forward");
		JButton exit = new JButton("Exit");
		
		public ForwardPopup(Frame parent){
			super(parent, true);
			
			panel.setLayout(new GridLayout(5,2));
			panel.add(userL);
			panel.add(userTF);
			panel.add(acceptforward);
			panel.add(cancelforward);
			panel.add(exit);
			add("Center", panel);
			resize(H_SIZE, V_SIZE);
			
			exit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
			
			cancelforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "#cancelmonitor";
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});
			
			acceptforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "#monitor "+userTF.getText();
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});			
		}
	}
	
	class PrivatePopup extends Dialog{
		
		int H_SIZE = 200;
		int V_SIZE = 215;
		
		Panel panel = new Panel();
		Label userL = new Label("User:");
		TextField userTF = new TextField();
		Label messL = new Label("Message:");
		TextField messTF = new TextField();
		JButton exit = new JButton("Exit");
		JButton send = new JButton("Send");

		public PrivatePopup(Frame parent){
			super(parent, true);
			
			panel.setLayout(new GridLayout(0,2));
			panel.add(userL);
			panel.add(userTF);
			panel.add(messL);
			panel.add(messTF);
			panel.add(exit);
			panel.add(send);
			add("Center", panel);
			resize(H_SIZE, V_SIZE);
			
			exit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
			
			send.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "#private "+userTF.getText()+" "+messTF.getText();
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});			
		}
	}
	
	class LoginPopup extends Dialog{

		int H_SIZE = 200;
		int V_SIZE = 215;

		Panel p = new Panel();
		TextField user = new TextField("tim");
		TextField pass = new TextField("pass");
		TextField host = new TextField("localhost");
		TextField port = new TextField("4444");
		Label username = new Label("Username");
		Label password = new Label("Password");
		Label hosttext = new Label("Host");
		Label porttext = new Label("Port");
		JButton exit = new JButton("Exit");
		JButton login = new JButton("Login");

		public LoginPopup(Frame parent){
			super(parent, true);


			p.setLayout(new GridLayout(5,2,5,5));
			p.add(username);
			p.add(user);
			p.add(password);
			p.add(pass);
			p.add(hosttext);
			p.add(host);
			p.add(porttext);
			p.add(port);
			p.add(exit);
			p.add(login);
			add("South",p);
			resize(H_SIZE, V_SIZE);

			exit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.exit(0);
				}
			});

			login.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int portint = Integer.parseInt(port.getText());
					setClient(user.getText(), pass.getText(), host.getText(), portint);
					dispose();
				}
			});

		}
	}

	public static void main(String[] args){
		ClientGUI cg = new ClientGUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		display("Uhhh, Update test?");
		
	}

}
