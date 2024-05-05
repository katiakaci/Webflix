package org.UI;

import org.log660.Client;
import org.log660.FacadeSysteme;

import java.awt.*;

import javax.swing.*;


public class VueConnexion extends JFrame{

	public static void main(String[] args){
		new VueConnexion();
	}

	public VueConnexion() {
		this.setTitle("Webflix");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		panelSettings();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void panelSettings() {
		JPanel affichage = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);

		JButton loginButton = new JButton("Se connecter");
		JButton analyseButton = new JButton("Analyser");
		JTextField emailField = new JTextField(25);
		JPasswordField passwordField = new JPasswordField(25);
		JLabel errorLabel = new JLabel();
		errorLabel.setForeground(Color.RED);

		constraints.gridx = 0;
		constraints.gridy = 0;
		affichage.add(new JLabel("Courriel : "), constraints);
		constraints.gridy++;
		affichage.add(emailField, constraints);
		constraints.gridy++;
		affichage.add(new JLabel("Mot de passe : "), constraints);
		constraints.gridy++;
		affichage.add(passwordField, constraints);
		constraints.gridy++;
		affichage.add(loginButton, constraints);
		constraints.gridy++;
		affichage.add(analyseButton, constraints);
		constraints.gridy++;
		affichage.add(errorLabel, constraints);

		loginButton.addActionListener(e -> {
			String addCourriel = emailField.getText();
			String mdp = new String(passwordField.getPassword());

//			if(addCourriel.contains("@")){
				try {
					//					Client clientRechercher = FacadeSysteme.getInstance().connecterUtilisateur(addCourriel,mdp);
					Client clientRechercher = FacadeSysteme.getInstance().connecterUtilisateur("VivianTHodge37@yahoo.com","Viquiequ8ox");
					if(clientRechercher != null) {
						System.out.println("Le client se connecte");
						new VueListeFilms(clientRechercher);
						this.setVisible(false);
						this.dispose();
					}
					else errorLabel.setText("Le mot de passe est invalide.");
				}
				catch (Exception ex) {
					errorLabel.setText("Aucun client n'a été trouvé avec cet identifiant.");
				}
//			}
//			else errorLabel.setText("Entrer une adresse courriel valide.");
		});
		analyseButton.addActionListener(e -> {
//			String addCourriel = emailField.getText();
//			String mdp = new String(passwordField.getPassword());
//			if(addCourriel.contains("@")){
				try {
//					Client clientRechercher = FacadeSysteme.getInstance().connecterUtilisateur(addCourriel,mdp);
					Client clientRechercher = FacadeSysteme.getInstance().connecterUtilisateur("VivianTHodge37@yahoo.com","Viquiequ8ox");
//					if(clientRechercher != null) {
						System.out.println("Le client se connecte");
						new VueAnalyse();
						this.setVisible(false);
						this.dispose();
//					}
//					else errorLabel.setText("Le mot de passe est invalide.");
				}
				catch (Exception ex) {
					errorLabel.setText("Aucun client n'a été trouvé avec cet identifiant.");
				}
//			}
//			else{
//				errorLabel.setText("Entrer une adresse courriel valide.");
//			}
		});
		this.add(affichage);
	}

}