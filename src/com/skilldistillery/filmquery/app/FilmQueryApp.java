package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {
		try {
			Film film = db.findFilmById(1001);
			System.out.println(film);
			
		} catch (NullPointerException e) {
			System.out.println("id does not match a film");
		}

//    Actor actor = db.findActorById(1);
//    System.out.println(actor);
//    
//    List<Actor> actors = db.findActorsByFilmId(1);
//    System.out.println(actors);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		int inputResult = 0;
		while (inputResult != 3) {
			displayMenu();
			inputResult = input.nextInt();
			input.nextLine();
			if (inputResult == 1) {
				try {
					
					Film film;
					System.out.print("Please input the films id: ");
					int filmId = input.nextInt();
					film = db.findFilmById(filmId);
					if (film != null) {
						System.out.println(film);
						
					}else {
						System.out.println("Movie not found");
					}
				} catch (NullPointerException e) {
					System.out.println("Movie of that id does not exist");
				}
				
				
			}else if (inputResult == 2) {
				try {
					
					List<Film> filteredFilms = new ArrayList<>();
					System.out.print("Please enter the keyword to search for: ");
					String keyword = input.nextLine();
					filteredFilms = db.searchByKeyword(keyword);
					if (filteredFilms.size() != 0) {
						for (Film film : filteredFilms) {
							System.out.println(film);
						}
						
					}else {
						System.out.println("No films contain those keywords");
					}
					
				} catch (NullPointerException e) {
					System.out.println("No films contain those keywords");
				}
				
			}else if (inputResult == 3 ) {
				System.out.println("GoodBye!");
			}
			else {
				System.out.println("Invalid response");
			}
			
		}

	}

	public void displayMenu() {
		System.out.println("===============================");
		System.out.println("|                             |");
		System.out.println("|  Please select an option:   |");
		System.out.println("|  1.Look up film by id       |");
		System.out.println("|  2.Look up film by keyword  |");
		System.out.println("|  3.Exit the program         |");
		System.out.println("|                             |");
		System.out.println("===============================");
	}

}
