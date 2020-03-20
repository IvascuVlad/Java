import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class CatalogUtil {
    public static void save(Catalog catalog) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            oos.writeObject(catalog);
        }
    }
    public static Catalog load(String path) throws InvalidCatalogException {
        Catalog catalog = new Catalog(" "," ");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = in.readObject();
            catalog = (Catalog) obj;
            if(catalog.getName() == " " && catalog.getPath() == " ")
                throw new InvalidCatalogException(new IndexOutOfBoundsException());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return catalog;
    }
    public static void view(Document doc) {
        Desktop desktop = Desktop.getDesktop();
        //… browse or open, depending of the location type
        try{
            URI uri = new URI(doc.getLocation());
            desktop.browse(uri);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}