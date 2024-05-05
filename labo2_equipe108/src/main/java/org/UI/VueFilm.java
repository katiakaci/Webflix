package org.UI;

import org.log660.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;


public class VueFilm extends JFrame{

	private Film film;
	private Client client;

	public VueFilm(Film film, Client c) {
		this.film = film;
		this.client = c;
		this.setTitle("Webflix");
		this.setSize(600, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		panelSettings();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void panelSettings() {
		JPanel affichage = new JPanel(new GridBagLayout());
		JScrollPane scrollFrame = new JScrollPane(affichage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		affichage.setAutoscrolls(true);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;

		JLabel titleLabel = new JLabel(film.getTitre());
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		affichage.add(titleLabel, constraints);
		constraints.gridy++;

		JLabel afficheLabel = null;
		try {
			BufferedImage image = ImageIO.read(new URL(film.getPoster()));
			afficheLabel = new JLabel(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
			constraints.anchor = GridBagConstraints.CENTER;
			affichage.add(afficheLabel, constraints);
			constraints.gridy++;
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		affichage.add(new JLabel("Année de sortie: "+film.getAnnee()), constraints);
		constraints.gridy++;

		affichage.add(new JLabel("Langue originale: "+film.getLangue()), constraints);
		constraints.gridy++;

		affichage.add(new JLabel("Durée: "+film.getDuree()+" minutes"), constraints);
		constraints.gridy++;

		affichage.add(new JLabel("Pays de production: "+FacadeSysteme.getInstance().getPaysProduction(film.getId())), constraints);
		constraints.gridy++;

		affichage.add(new JLabel("Genres: "+FacadeSysteme.getInstance().getGenresFilm(film.getId())), constraints);
		constraints.gridy++;

		JLabel realisateurLabel = new JLabel("<html>Réalisateur: <font color='blue'>"+FacadeSysteme.getInstance().getRealisateur(film.getRealisateur()).getNom()+"</font></html>");
		realisateurLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		realisateurLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame personWindow = new VueActeurRealisateur(film, client, film.getRealisateur());
				personWindow.setVisible(true);
				setVisibleAndDispose();
			}
		});
		affichage.add(realisateurLabel, constraints);
		constraints.gridy++;

		affichage.add(new JLabel("Scénaristes: "+FacadeSysteme.getInstance().getScenaristes(film.getId())), constraints);
		constraints.gridy++;

		affichage.add(new JLabel("Acteurs:"), constraints);
		constraints.gridy++;
		for(ActeurRealisateur acteur : FacadeSysteme.getInstance().getActeurs(film.getId())) {
			JLabel actorsLabel = new JLabel(String.format("<html><div style='width: 350px; text-align: center; '><font color='blue'>%s</font>"
					+ " joue le rôle de %s</div></html>", 
					acteur.getNom(), FacadeSysteme.getInstance().getRoleForActor(acteur.getId(), film.getId())));
			actorsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			actorsLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFrame personWindow = new VueActeurRealisateur(film, client, acteur.getId());
					personWindow.setVisible(true);
					setVisibleAndDispose();
				}
			});
			affichage.add(actorsLabel, constraints);
			constraints.gridy++;
		}

		affichage.add(new JLabel(String.format("<html><div style='width: 350px; text-align: center; '>Bandes-annonces:<br>%s</div></html>", FacadeSysteme.getInstance().getBandesAnnonces(film.getId()))), constraints);
		constraints.gridy++;

		affichage.add(new JLabel(String.format("<html><div style='width: 350px; text-align: center; '>Résumé scénario:<br>%s</div></html>", film.getResumeScenario())), constraints);
		constraints.gridy++;

		affichage.add(new JLabel(String.format("<html><div style='width: 350px; text-align: center; '>Cote moyenne:<br>%s</div></html>", FacadeSysteme.getInstance().getCote(film.getId()))), constraints);
		constraints.gridy++;

		affichage.add(new JLabel(String.format("<html><div style='width: 350px; text-align: center; '>Films similaires:</div></html>")), constraints);
		constraints.gridy++;
		List<String> meilleursFilms = FacadeSysteme.getInstance().getAutresFilms(film.getId(), client);
		String meilleursFilmsString = "";
		if (meilleursFilms.isEmpty()) {
			affichage.add(new JLabel(String.format("<html><div style='width: 350px; text-align: center; '>Aucun film similaire à recommander</div></html>")), constraints);
			constraints.gridy++;
		}
		else {
			for(int i=0; i<meilleursFilms.size(); i++) {
				String filmSimilaire = i+1 + ". " + meilleursFilms.get(i);
				affichage.add(new JLabel(String.format("<html><div style='width: 350px; text-align: center; '>%s</div></html>", filmSimilaire)), constraints);
				constraints.gridy++;

				JLabel filmSimilaireImage = null;
				try {
					BufferedImage image = ImageIO.read(new URL(FacadeSysteme.getInstance().getPosterFilmSimilaire(meilleursFilms.get(i))));
					filmSimilaireImage = new JLabel(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
					constraints.anchor = GridBagConstraints.CENTER;
					affichage.add(filmSimilaireImage, constraints);
					constraints.gridy++;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		constraints.gridy++;

		JButton rentButton = new JButton("Louer");
		rentButton.addActionListener(e -> {
			if(film.getQteDisponible() <= 0) {
				JOptionPane.showMessageDialog(this, "Il n'y a plus de copie disponible.");
			}
			else if(FacadeSysteme.getInstance().isMaximumNumberOfRentalsReached(client)) {
				JOptionPane.showMessageDialog(this, "Vous avez atteint votre nombre maximum de locations.");
			}
			else {
				System.out.print("Quantité disponible AVANT location :"+film.getQteDisponible());
				FacadeSysteme.getInstance().rentMovie(client, film);
				System.out.print("Quantité disponible APRÈS location :"+film.getQteDisponible());
				JOptionPane.showMessageDialog(this, "Le film a été loué.");
			}			
		});
		affichage.add(rentButton, constraints);
		constraints.gridy++;

		JButton backButton = new JButton("Retour");
		backButton.addActionListener(e -> {
			JFrame listMoviesWindow = new VueListeFilms(client);
			listMoviesWindow.setVisible(true);
			this.setVisible(false);
			this.dispose();
		});
		affichage.add(backButton, constraints);

		scrollFrame.setViewportView(affichage);
		this.add(scrollFrame);
	}

	private void setVisibleAndDispose() {
		this.setVisible(false);
		this.dispose();
	}

}