package com.excilys.cdb.vue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOException;

import Main.CommandLineTable;

public class CLI {

	private static CLI INSTANCE = null;
	private Scanner sc = new Scanner(System.in);
	private static Controller controller = Controller.getInstance();
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);

	public static CLI getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CLI();
		}
		return INSTANCE;
	}

	static boolean quit = false;

	public void run() throws DAOException, ParseException {
		while (!quit) {

			showMenu();
		}

	}

	private void showMenu() throws DAOException, ParseException {
		printMenu();
		boolean wenttocatch = false;
		Scanner scan = new Scanner(System.in);
		int m = 0;
		do {
			if (scan.hasNextInt()) {
				m = scan.nextInt();
				wenttocatch = true;
			} else {
				scan.nextLine();
				System.out.println("Entrez un entier valide");
			}
		} while (!wenttocatch);

		LOGGER.info("Action choisie par l'utilisateur: " + m);
		controller.sendToService(m);
	}

	private void printMenu() {
		System.out.println("Choisissez une action");
		System.out.println("0 - Quitter");
		System.out.println("1 - Lister les ordinateurs");
		System.out.println("2 - Lister les sociétés");
		System.out.println("3 - Détail d'un ordinateur");
		System.out.println("4 - Créer un ordinateur");
		System.out.println("5 - Mettre à jour un ordinateur");
		System.out.println("6 - Supprimer un ordinateur");
	}

	public void quit() throws DAOException, ParseException {
		LOGGER.info("Fermeture de l'application");
		quit = true;
		System.out.println("n'oublie pas de revenir nous voir <3");

	}

	public DTOComputer updateComputer() throws ParseException {
		LOGGER.debug("Mise à jour de l'ordinateur...: ");
		System.out.println("Entrez l'id de l'ordinateur que vous voulez mettre à  jour:");
		Long idold = sc.nextLong();
		LOGGER.info("Id du computer: " + idold);
		System.out.println("Entrez le nouveau nom de l'ordinateur:");
		sc = new Scanner(System.in);
		String newname = sc.nextLine();
		LOGGER.info("Nouveau nom de l'ordinateur: " + newname);
		System.out.println("Entrez la nouvelle date d'introduction de l'ordinateur:");
		String introduced = sc.nextLine();
		LOGGER.info("nouvelle date d'introduction de l'ordinateur: " + introduced);
		System.out.println("Entrez la nouvelle date de sortie de l'ordinateur:");
		String discontinued = sc.nextLine();
		LOGGER.info("nouvelle date de sortie de l'ordinateur: " + discontinued);
		System.out.println("Entrez le nouveau id de la société fabricante:");
		String companyId = sc.nextLine();
		LOGGER.info("nouveau id de la société fabricante: " + companyId);
		DTOComputer dtoc1 = new DTOComputer();
		dtoc1.setId(Long.toString(idold));
		dtoc1.setName(newname);
		dtoc1.setCompanyId(companyId);
		dtoc1.setIntroduced(introduced);
		dtoc1.setDiscontinued(discontinued);
		return (dtoc1);
	}

	public Long deleteComputer() {
		LOGGER.debug("Suppression d'un ordinateur...: ");
		System.out.println("Entrez l'id de l'ordinateur que vous voulez supprimer:");
		Long id = sc.nextLong();
		LOGGER.info("id de l'ordinateur choisi par l'utilisateur: " + id);
		return id;

	}

	public int readInt(String message) {
		System.out.println(message);

		int id = sc.nextInt();
		return id;
	}

	public void showComputerDetails(DTOComputer computer) {
		LOGGER.debug("Affichage des détails d'un ordinateur...: ");
		CommandLineTable st = new CommandLineTable();
		st.setShowVerticalLines(true);
		st.setHeaders("ComputerID", "ComputerName", "Date Introduced", "Date Discontinued", "CompanyID");

		try {
			st.addRow(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(),
					computer.getCompanyId());
			st.print();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			LOGGER.warn("Aucun ordinateur correspondant trouvé");

		}
		quitProgram();

	}

	public void quitProgram() {
		boolean wenttocatch = false;
		Scanner scan = new Scanner(System.in);
		int m = 0;
		do {
			System.out.println("Quitter? 0 = Oui, n'importe quel numéro = Non");
			if (scan.hasNextInt()) {
				m = scan.nextInt();
				wenttocatch = true;
			} else {
				scan.nextLine();
				System.out.println("Entrez un entier valide");
			}
		} while (!wenttocatch);

		if (m == 0) {

			try {
				quit();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				LOGGER.warn("DAOExeption" + e);
				e.printStackTrace();
			} catch (ParseException e) {
				LOGGER.warn("ParseException" + e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				run();
			} catch (DAOException e) {
				LOGGER.warn("DAOExeption" + e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				LOGGER.warn("ParseException" + e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public DTOComputer createComputer() throws ParseException {
		LOGGER.debug("création d'un nouveau ordinateur");
		System.out.println("Entrez le nom de l'ordinateur:");
		sc = new Scanner(System.in);
		String name = sc.nextLine();
		LOGGER.info("nom saisi par l'utilisateur: " + name);
		System.out.println("Entrez la date d'introduction de l'ordinateur sous ce format yyyy-mm-dd:");
		sc = new Scanner(System.in);
		String introduced = sc.nextLine();
		LOGGER.info("date d'introduction saisi par l'utilisateur: " + introduced);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateIntro = dateFormat.parse(introduced);

		System.out.println("Entrez la date de sortie de l'ordinateur:");
		sc = new Scanner(System.in);
		String discontinued = sc.nextLine();
		LOGGER.info("date de sortie saisi par l'utilisateur: " + discontinued);

		Date dateDisc = dateFormat.parse(discontinued);

		while (dateIntro.compareTo(dateDisc) > 0) {
			LOGGER.info("date de sortie inférieur à la date d'entrée");
			System.out.println("Date d'entré en stock: " + introduced
					+ " supérieure à la date de sortie que vous avez saisi, Veuillez entrez une date de sortie ultérieure :");
			discontinued = sc.nextLine();
			dateDisc = dateFormat.parse(discontinued);
		}

		System.out.println("Entrez l'id de la société fabricante:");
		String companyId = sc.nextLine();
		LOGGER.info("id de la société fabricante saisi par l'utilisateur: " + companyId);

		DTOComputer dtoc1 = new DTOComputer();
		dtoc1.setName(name);
		dtoc1.setCompanyId(companyId);
		dtoc1.setIntroduced(introduced);
		dtoc1.setDiscontinued(discontinued);
		return dtoc1;
	}

	public void showComputers(List<DTOComputer> l) {

		LOGGER.debug("Affichage de la liste des ordinateurs...");

		CommandLineTable st = new CommandLineTable();

		for (DTOComputer x : l) {
			st.setShowVerticalLines(true);
			st.setHeaders("ComputerID", "ComputerName", "Date Introduced", "Date Discontinued", "CompanyID:");
			st.addRow(x.getId(), x.getName(), x.getIntroduced(), x.getDiscontinued(), x.getCompanyId());

		}
		st.print();
		quitProgram();

	}

	public void showCompanies(List<Company> lc) {

		LOGGER.debug("Affichage de la liste des sociétés...");

		CommandLineTable st = new CommandLineTable();

		for (Company x : lc) {
			st.setShowVerticalLines(true);
			st.setHeaders("id", "CompanyName");
			st.addRow(Long.toString(x.getId()), x.getName());

		}
		st.print();
		quitProgram();
	}

	public int readPage() {
		System.out.println("Entrez la page à consulter");
		int id = sc.nextInt();
		LOGGER.info("Page demandé par l'utilisateur: " + id);
		return id;
	}

	public int readLimit() {
		System.out.println("Entrez le nombre d'éléments par page");
		int id = sc.nextInt();
		LOGGER.info("Elements par page deemandés par l'utilisateur: " + id);
		return id;
	}

}
