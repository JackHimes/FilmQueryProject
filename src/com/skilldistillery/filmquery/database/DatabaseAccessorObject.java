package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		String user = "student";
		String pass = "student";
		Film film = null;
		Actor actor;
		List<Actor> listOfActors = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, film.rental_duration, film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features, language.name, actor.first_name, actor.last_name, actor.id FROM film JOIN language ON film.language_id = language.id JOIN film_actor ON film.id = film_actor.film_id JOIN actor ON film_actor.actor_id = actor.id WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
				actor = new Actor(rs.getInt("actor.id"), rs.getString("actor.first_name"), rs.getString("actor.last_name"));
				listOfActors.add(actor);
				
				film = new Film(filmId, rs.getString("title"), rs.getString("description"), rs.getInt("release_year"),
						rs.getInt("language_id"), rs.getInt("rental_duration"), rs.getDouble("rental_rate"),
						rs.getInt("length"), rs.getDouble("replacement_cost"), rs.getString("rating"),
						rs.getString("special_features"), rs.getString("language.name"));
			}
			film.setActors(findActorsByFilmId(filmId));

			rs.close();
			stmt.close();
			conn.close();

			return film;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			while (actorResult.next()) {
				actor = new Actor(actorId, actorResult.getString("first_name"), actorResult.getString("last_name"));

			}

			actorResult.close();
			stmt.close();
			conn.close();

			return actor;

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Actor actor;
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT actor.first_name, actor.last_name, actor.id FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film_actor.film_id = film.id WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet listOfActors = stmt.executeQuery();

			while (listOfActors.next()) {
				actor = new Actor(listOfActors.getInt("actor.id"), listOfActors.getString("actor.first_name"),
						listOfActors.getString("actor.last_name"));
				actors.add(actor);
			}
			listOfActors.close();
			stmt.close();
			conn.close();
			
			return actors;
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	public List<Film> searchByKeyword(String keyword){
		
		List<Film> filteredFilms = new ArrayList<>();
		Film film;
		String user = "student";
		String pass = "student";
		
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features, language.name FROM film JOIN language ON film.language_id = language.id WHERE title LIKE ? OR description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet filmSet = stmt.executeQuery();
			
			while(filmSet.next()) {
				film = new Film(filmSet.getInt("film.id"), filmSet.getString("title"), filmSet.getString("description"), filmSet.getInt("release_year"),
						filmSet.getInt("language_id"), filmSet.getInt("rental_duration"), filmSet.getDouble("rental_rate"),
						filmSet.getInt("length"), filmSet.getDouble("replacement_cost"), filmSet.getString("rating"),
						filmSet.getString("special_features"), filmSet.getString("language.name"));
				
				filteredFilms.add(film);
			}
			filmSet.close();
			stmt.close();
			conn.close();
			
			return filteredFilms;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filteredFilms;
	}
}

