package connectionhibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class GetRequests {
	
	public static void main(String[] args) {
		
		// create session factory
		SessionFactory myFactory = new Configuration().configure("hibernate.cfg.xml")
									.addAnnotatedClass(Client.class)
									.addAnnotatedClass(DetailsClient.class)
									.addAnnotatedClass(Request.class)
									.buildSessionFactory();
		
		// create session
		Session mySession = myFactory.openSession();
		
		try {
			
			// start a transaction
			mySession.beginTransaction();
			
			// get client from DB
//			Client clientE = mySession.get(Client.class, 9);
			
			// HQL instruction
			Query<Client> consult = mySession.createQuery("SELECT CL FROM Client CL JOIN FETCH CL.orders WHERE CL.id=:clientIdX", Client.class);
			
			consult.setParameter("clientIdX", 9);
			
			Client clientE = consult.getSingleResult();
			
			System.out.println("Client: " + clientE);
			
//			System.out.println("Orders: " + clientE.getOrders());
			
			// commit transaction
			mySession.getTransaction().commit();

			mySession.close();
			
			System.out.println("Orders: " + clientE.getOrders());
			
			System.out.println(" <> ---- Orders successfully obtained! ");
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
//			mySession.close();
			myFactory.close();
			
		}
		
	}
}



/*

	System.out.println("Orders: " + clientE.getOrders())
	mySession.close();
	System.out.println("Orders: " + clientE.getOrders())
	
	fetch=FetchType.LAZY

Los pedidos se pueden obtener después de cerrar, pero requiere que previamente ya se haya guardado en memoria, es decir repetir el método.
Otra opción implica implementar una instrucción HQL

/*
/*

	mySession.close();
	System.out.println("Orders: " + clientE.getOrders())
	
	fetch=FetchType.EAGER

Los pedidos se pueden obtener incluso después de cerrar, y no habrá inconvenientes.

*/



