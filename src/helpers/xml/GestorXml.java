package helpers.xml;

import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import main.Simulador;

public class GestorXml {

    private static String[] romanNum = new String[]{"I","II","III","IV","V"};

    /**
     * Si el documento en la ruta no existe, este lo crea, y tras
     * crearlo o obtener el ya existente, o pasa a un formato editable de xml
     * @param ruta Direccion del documento a editar deseado
     * @return Devuelve el documento editable en xml
     */
    public static Document leible(String ruta){
        Document doc = null;
        if(new File(ruta).exists()){
            try {
                SAXReader reader = new SAXReader();
                doc = reader.read(new File(ruta));
            } catch (DocumentException e) {
                System.out.println("Fallo al abrir documento");
                e.printStackTrace();
            }
        }else{
            doc = DocumentHelper.createDocument();
        }
        return doc;
    }

    /**
     * Guarda el documento xml, sus ediciones y cambios en la direccion establecida
     */
    public static void save(Document doc,String ruta){
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileWriter(new File(ruta)),OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.flush();        
        } catch (Exception e) {
            System.out.println("Fallo al guardar");
            e.printStackTrace();
        }finally{
            try {
                writer.close();
            } catch (Exception e) {}
        }
        System.out.println("Guardado");
    }

    public static void rewardAlga(int lvl){
        int res = (lvl-1)>=3 ? ((lvl-1)*300)+((lvl-1)==4 ? 800 : 100) : ((lvl-1)*100+(lvl==3 ? 300 : 100));
        Document doc = leible("algas_"+lvl+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Algas "+romanNum[lvl]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" cápsula de algas para alimentar peces filtradores y omnívoros");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("food").addAttribute("type","algae").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
    }

    public static void rewardPienso(int lvl){
        int res = (lvl-1)>=3 ? ((lvl-1)*300)+((lvl-1)==4 ? 800 : 100) : ((lvl-1)*100+(lvl==3 ? 300 : 100));
        Document doc = leible("pienso_"+lvl+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Pienso de peces "+romanNum[lvl]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("food").addAttribute("type","animal").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
    }

    public static void rewardGeneral(int lvl){
        int res = (lvl-1)>=2 ? ((lvl-1)>=3 ? (((lvl-1)*150)+(lvl==5 ? 400 : 50)) : ((lvl-1)*100)+50) : ((lvl-1)*50)+50;
        Document doc = leible("comida_"+lvl+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Comida general "+romanNum[lvl]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" unidades de pienso multipropósito para todo tipo de peces.");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("food").addAttribute("type","general").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
    }

    public static void rewardAlmacen(String part){
        Document doc = leible("almacen_"+part+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Almacén central ["+part+"]");
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
        root.addElement("rarity").addText("3");
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", "4").addText("Almacén central");
        give.addElement("part").addText(part);
        give.addElement("total").addText("ABCD");
        root.addElement("quantity").addText("1");
    }

    public static void rewardCoins(int lvl){
        int res = ((lvl-1)*200)+((lvl==5) ? 200 : ((lvl==4) ? 150 : 100));
        Document doc = leible("monedas_"+lvl+"xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Monedas "+romanNum[(lvl-1)]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" monedas");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("coins").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
    }

    public static void rewardPisci(int rarity,String part){
        String type = (rarity==3) ? "río" : "mar";
        Document doc = leible("pisci_"+type.charAt(0)+"_"+part+".xml");
        //TODO V
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Almacén central ["+part+"]");
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
        root.addElement("rarity").addText("3");
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", "4").addText("Almacén central");
        give.addElement("part").addText(part);
        give.addElement("total").addText("ABCD");
        root.addElement("quantity").addText("1");
    }
}