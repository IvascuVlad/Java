import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException, InvalidCatalogException {
        Main app = new Main();
        app.testCreateSave();
        app.testLoadView();
    }

    private void testCreateSave() throws IOException {
        Catalog catalog =
                new Catalog("Java Resources", "C:\\Users\\ivasc\\Desktop\\Anul_II\\semestrul 2\\Java\\lab5\\catalog.ser");
        Document doc = new Document("java1", "Java Course 1",
                "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        doc.addTag("type", "Slides");
        catalog.add(doc);

        CatalogUtil.save(catalog);
    }

    private void testLoadView() throws InvalidCatalogException {
        Catalog catalog = CatalogUtil.load("C:\\Users\\ivasc\\Desktop\\Anul_II\\semestrul 2\\Java\\lab5\\catalog.ser");
        Document doc = catalog.findById("java1");
        CatalogUtil.view(doc);
    }
}