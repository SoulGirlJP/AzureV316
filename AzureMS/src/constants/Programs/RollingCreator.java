package constants.Programs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.math.BigInteger;

import client.Character.MapleCharacter;
import launcher.ServerPortInitialize.ChannelServer;
import connections.Packets.MainPacketCreator;
import connections.Packets.SecondaryStat;


public class RollingCreator extends javax.swing.JFrame {

	public RollingCreator() {
		this.setTitle("돌림판 아이템 추가");
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);
		initComponents();
	}

	@SuppressWarnings("unchecked")
	// Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{

		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();
		jTextField2 = new javax.swing.JTextField();

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setText("아이템코드 : ");

		jLabel2.setText("아이템갯수 :");

		jButton1.setText("추가");
		jButton1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jButton1ActionPerformed(evt);
			}
		});

		jTextField1.setText("아이템코드");

		jTextField2.setText("아이템갯수");
		jTextField2.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jTextField2ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(25, 25, 25)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel2).addComponent(jLabel1))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 93,
														Short.MAX_VALUE)
												.addComponent(jTextField2))))
						.addGap(21, 21, 21)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton1)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		for (ChannelServer cs : ChannelServer.getAllInstances()) {
			for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters().values()) {
				chr.getClient().sendPacket(MainPacketCreator.t2(BigInteger.ONE.shiftLeft(chr.tt)));
				chr.tt = Integer.parseInt(jTextField1.getText());
				chr.getClient().sendPacket(SecondaryStat.t(Integer.parseInt(jTextField2.getText()), BigInteger.ONE.shiftLeft(chr.tt)));
				jTextField1.setText((chr.tt + 1) + "");
			}
		}
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new RollingCreator().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	// End of variables declaration//GEN-END:variables
}
