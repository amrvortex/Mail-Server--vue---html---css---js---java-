package demo.blitz.service;

public class FunctionalityFactory {

    public IFunctionality create (String selector, String type, String query) {
        if (selector.equals("Search")){
            SearchFactory searchFactory = new SearchFactory();
            return searchFactory.create(type,query);
        }
        else if (selector.equals("Sort")){
            SortFactory sortFactory = new SortFactory();
            return sortFactory.create(type);
        }
        return null;
    }
}
