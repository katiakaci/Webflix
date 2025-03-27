package org.UI;

import org.log660.ActeurRealisateur;
import org.log660.Client;
import org.log660.FacadeSysteme;
import org.log660.Film;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class VueActeurRealisateur extends JFrame{

	private Film film;
	private Client client;
	private ActeurRealisateur acteurRealisateur;

	public VueActeurRealisateur(Film film, Client client, int idActeurRealisateur) {
		this.film = film;
		this.client  = client;
		this.acteurRealisateur = FacadeSysteme.getInstance().getActeurRealisateur(idActeurRealisateur);
		this.setTitle("Webflix");
		this.setSize(600, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		panelSettings();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void panelSettings() {
		JPanel affichage = new JPanel(new GridBagLayout());
		JScrollPane scrollFrame = new JScrollPane(affichage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		affichage.setAutoscrolls(true);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;

		JLabel nomLabel = new JLabel(acteurRealisateur.getNom());
		nomLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		affichage.add(nomLabel, constraints);
		constraints.gridy++;

		JLabel afficheLabel = null;
		try {
			BufferedImage image = ImageIO.read(new URL(acteurRealisateur.getPhoto()));
			afficheLabel = new JLabel(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
			constraints.anchor = GridBagConstraints.CENTER;
			affichage.add(afficheLabel, constraints);
			constraints.gridy++;
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		affichage.add(new JLabel("Date de naissance: "+acteurRealisateur.getDateNaissance()), constraints);
		constraints.gridy++;

		affichage.add(new JLabel("Lieu de naissance: "+acteurRealisateur.getLieuDeNaissance()), constraints);
		constraints.gridy++;

		affichage.add(new JLabel(String.format("<html><div style='width: 350px; text-align: center; '>Biographie:<br>%s</div></html>", acteurRealisateur.getBiographie())), constraints);
		constraints.gridy++;

		JButton backButton = new JButton("Retour");
		backButton.addActionListener(e -> {
			JFrame moviesWindow = new VueFilm(film, client);
			moviesWindow.setVisible(true);
			this.setVisible(false);
			this.dispose();
		});
		affichage.add(backButton, constraints);

		scrollFrame.setViewportView(affichage);
		this.add(scrollFrame);
	}

}