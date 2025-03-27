package org.UI;

import org.log660.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueAnalyse extends JFrame {
	private long nbLocationTotal;

	public VueAnalyse () {
		setTitle("Webflix");
		setSize(700,500);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		nbLocationTotal = FacadeSysteme.getInstance().getNbLocationTotal();

		getContentPane().add(getFilterPanel(), BorderLayout.NORTH);
		getContentPane().add(new JLabel("Le nombre total de location est: "+ nbLocationTotal), BorderLayout.CENTER );

		setLocationRelativeTo(null);
		setVisible(true);
	}



	private JPanel getFilterPanel(){
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 3));
		filterPanel.setPreferredSize(new Dimension(400, 78));

		JTextField ageField = new JTextField("TOUS",10);
		JTextField provinceField = new JTextField("TOUS",5);
		JTextField jourField= new JTextField("TOUS",5);
		JTextField moisField = new JTextField("TOUS",12);

		filterPanel.add(new JLabel("Groupe d'âge:"));
		filterPanel.add(ageField);
		filterPanel.add(new JLabel("Province"));
		filterPanel.add(provinceField);
		filterPanel.add(new JLabel());
		filterPanel.add(new JLabel("Jour:"));
		filterPanel.add(jourField);
		filterPanel.add(new JLabel("Mois:"));
		filterPanel.add(moisField);

		JButton filterButton = new JButton("Filtrer");
		filterButton.addActionListener( e -> {
			String age = ageField.getText();
			String province = provinceField.getText();
			String jour = jourField.getText();
			String mois = moisField.getText();

			nbLocationTotal = FacadeSysteme.getInstance().getNbLocationTotalFiltrer(age,province,mois,jour);
			getContentPane().remove(1);
			getContentPane().add(new JLabel("Le nombre total de location est: "+ nbLocationTotal), BorderLayout.CENTER );;
			this.revalidate();

			System.out.println(nbLocationTotal);
		});
		filterPanel.add(filterButton);

		return filterPanel;
	}

//	private JPanel getTotalLocation(int nbLocation) {
//		JPanel affichageFilm = new JPanel();
//		JScrollPane scrollFrame = new JScrollPane(affichageFilm, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		affichageFilm.setAutoscrolls(true);
//		scrollFrame.setPreferredSize(new Dimension(800,3000));
//		int height = 0;
//		for(Film film : films) {
//			JButton chaqueFilmBouton = new JButton(film.toString());
//			chaqueFilmBouton.addActionListener( e -> {
//				new VueFilm(film, clientConnectee);
//				this.setVisible(false);
//				this.dispose();
//			});
//			affichageFilm.add(chaqueFilmBouton);
//			height+=10;
//		}
//		affichageFilm.setPreferredSize(new Dimension(100,height));
//		return scrollFrame;
//	}

}
