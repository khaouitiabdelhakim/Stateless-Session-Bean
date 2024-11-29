package ma.ensias.ejb3.tp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class SearchBookFacade
 */
@Stateless
public class SearchBookFacade implements SearchBookFacadeRemote, SearchBookFacadeLocal {

	private HashMap<String, List<String>> countryBookMap = new HashMap<>();

	/**
	 * Default constructor.
	 */
	public SearchBookFacade() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
    public void initializeCountryBookList() {
        // Ajout des livres pour l'Australie
        List<String> australiaBooks = new ArrayList<>();
        australiaBooks.add("Welcome to Australia");
        australiaBooks.add("Australia History");

        // Ajout des livres pour le Maroc
        List<String> moroccoBooks = new ArrayList<>();
        moroccoBooks.add("Welcome to Morocco");
        moroccoBooks.add("Morocco History");

        // Remplissage de la HashMap
        countryBookMap.put("Australia", australiaBooks);
        countryBookMap.put("Morocco", moroccoBooks);

        System.out.println("CountryBookMap initialized with default values.");
    }

	@PreDestroy
    public void destroyBookList() {
        countryBookMap.clear();
        System.out.println("CountryBookMap has been cleared.");
    }
	
	public List<String> searchBookByCountry(String country) {
        return countryBookMap.get(country);
    }

	public List<String> bookSearch(String bookType) {
		List<String> bookList = new ArrayList<String>();
		if (bookType.equals("java")) {
			bookList.add("Java for dummies");
			bookList.add("Beginnig Java 6");
		} else if (bookType.equals("C++")) {
			bookList.add("C++ for dummies");
		}
		return bookList;
	}

}
