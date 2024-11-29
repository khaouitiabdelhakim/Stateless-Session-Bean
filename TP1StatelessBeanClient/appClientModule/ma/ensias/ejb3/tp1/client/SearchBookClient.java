package ma.ensias.ejb3.tp1.client;

import java.util.List;
import java.util.Properties;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ma.ensias.ejb3.tp1.SearchBookFacadeRemote;

public class SearchBookClient {
	public SearchBookClient() {
	}

	public static void main(String[] args) {
		SearchBookClient searchFacadeTest = new SearchBookClient();
		searchFacadeTest.doTest();
	}

	@AroundInvoke
	public Object timerLog(InvocationContext ctx) throws Exception {
		String beanClassName = ctx.getClass().getName();
		String businessMethodName = ctx.getMethod().getName();
		String target = beanClassName + "." + businessMethodName;
		long startTime = System.currentTimeMillis();
		System.out.println("Invoking " + target);
		try {
			return ctx.proceed();
		} finally {
			System.out.println("Exiting " + target);
			long totalTime = System.currentTimeMillis() - startTime;
			System.out.println("Business method {" + businessMethodName + "} in " + beanClassName + " takes "
					+ totalTime + "ms to execute");
		}
	}

	InitialContext getInitialContext() throws javax.naming.NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		return new InitialContext(p);
	}

	void doTest() {
		try {
			InitialContext ic = getInitialContext();
			System.out.println("SearchFacade Lookup");
			SearchBookFacadeRemote searchFacade = (SearchBookFacadeRemote) ic.lookup("SearchBookFacade/remote");

			System.out.println("Searching books");
			// Dans ce code, le mot clé de recherche est donne en dur
			// Vous pouvez améliorer ce code en lisant ce mot clé
			// à partir de l’entrée standard
			List<String> bookList = searchFacade.searchBookByCountry("Morocco");

			System.out.println("Printing books list");
			for (String book : (List<String>) bookList) {
				System.out.println(" -- " + book);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
