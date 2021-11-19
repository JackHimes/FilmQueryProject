package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static String url = "jdbc:mysql://localhost:3306/sdvid";

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

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT id, title, description, release_year, language_id"
					+ ", rental_duration, rental_rate, length, replacement_cost,"
					+ " rating, special_features FROM film WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String pass = "student";

		return actor; // temp

//		try {
//			Connection conn = DriverManager.getConnection(url, user, pass);
//			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			stmt.setInt(1, actorId);
//			ResultSet actorResult = stmt.executeQuery();
//			if (actorResult.next()) {
//				Actor actor = new Actor("id", "first_name", "last_name", null)
//				actor = new Actor(); // Create the object
//				// Here is our mapping of query columns to our object fields:
//				actor.setId(actorResult.getInt(1));
//				actor.setFirstName(actorResult.getString(2));
//				actor.setLastName(actorResult.getString(3));
//				actor.setFilms(findFilmsByActorId(actorId)); // An Actor has Films
//			}
//			// ...
//			return actor;
//			
//		}
//		catch(SQLException e){
//			e.printStackTrace();
//			
//		}
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}

}
