package org.UI;

import org.log660.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueListeFilms extends JFrame {
	private Client clientConnectee;
	private List<Film> films;

	public VueListeFilms (Client c) {
		clientConnectee = c;
		setTitle("Webflix");
		setSize(700,500);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		films = FacadeSysteme.getInstance().recupererTousLesFilms();

		getContentPane().add(getFilterPanel(), BorderLayout.NORTH);
		getContentPane().add(getFilmsPanel(films), BorderLayout.CENTER );

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel getFilterPanel(){
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 3));
		filterPanel.setPreferredSize(new Dimension(400, 78));

		JTextField titleField = new JTextField(10);
		JTextField yearField = new JTextField(5);
		JTextField languageField= new JTextField(5);
		JTextField realisateurField = new JTextField(10);
		JTextField countryField = new JTextField(12);
		JTextField genreField = new JTextField(12);
		JTextField actorField = new JTextField(12);

		filterPanel.add(new JLabel("Titre:"));
		filterPanel.add(titleField);
		filterPanel.add(new JLabel("Année:"));
		filterPanel.add(yearField);
		filterPanel.add(new JLabel());
		filterPanel.add(new JLabel("Langue:"));
		filterPanel.add(languageField);
		filterPanel.add(new JLabel("Nom du réalisateur:"));
		filterPanel.add(realisateurField);
		filterPanel.add(new JLabel("Pays de production:"));
		filterPanel.add(countryField);
		filterPanel.add(new JLabel("Genre:"));
		filterPanel.add(genreField);
		filterPanel.add(new JLabel("Acteur:"));
		filterPanel.add(actorField);

		JButton filterButton = new JButton("Filtrer");
		filterButton.addActionListener( e -> {
			String titre = titleField.getText();
			String annee = yearField.getText();
			String langue = languageField.getText();
			String nomRealisateur = realisateurField.getText();
			String paysProduction = countryField.getText();
			String genre = genreField.getText();
			String acteur = actorField.getText();

			films = FacadeSysteme.getInstance().filtrerFilm(titre, annee, langue, nomRealisateur, paysProduction, genre, acteur);
			if(films.size()<=0) JOptionPane.showMessageDialog(this, "Aucun film ne correspond à ces critères de recherche.");
			else {
				this.getContentPane().removeAll();
				this.getContentPane().add(filterPanel, BorderLayout.NORTH);
				this.getContentPane().add(getFilmsPanel(films), BorderLayout.CENTER);
				this.revalidate();
			}
		});
		filterPanel.add(filterButton);

		return filterPanel;
	}

	private JScrollPane getFilmsPanel(List<Film> lesFilm) {
		JPanel affichageFilm = new JPanel();
		JScrollPane scrollFrame = new JScrollPane(affichageFilm, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		affichageFilm.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension(800,3000));
		int height = 0;
		for(Film film : films) {
			JButton chaqueFilmBouton = new JButton(film.toString());
			chaqueFilmBouton.addActionListener( e -> {
				new VueFilm(film, clientConnectee);
				this.setVisible(false);
				this.dispose();
			});
			affichageFilm.add(chaqueFilmBouton);
			height+=10;
		}
		affichageFilm.setPreferredSize(new Dimension(100,height));
		return scrollFrame;
	}

}
