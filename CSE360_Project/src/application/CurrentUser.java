package application;

public class CurrentUser {
	private static int id;
	private static String role;
	
	public static int getId() {
		return id;
	}
	
	public static void setId(int Id) {
		CurrentUser.id = id;
	}
	
	public static String getRole() {
		return role;
	}
	
	public static void setRole(String role) {
		CurrentUser.role = role;
	}
	
	public static void clear() {
		CurrentUser.id = 0;
		CurrentUser.role = null;
	}
}