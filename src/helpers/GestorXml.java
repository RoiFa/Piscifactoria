package helpers;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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

    public static int listRewards(){
        String ruta = "src//rewards//";
        Path path = Paths.get(ruta);
        String menu="";
        String almacen="",pisciMar="",pisciRio="";
        String[] charsAlm = new String[]{"X","X","X","X"};
        String[] charsMar = new String[]{"X","X"};
        String[] charsRio = new String[]{"X","X"};
        if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
            File folder = new File(ruta);
            File[] files = folder.listFiles();
            Arrays.sort(files);
            for(int i=0,j=0;i<files.length;i++,j++){
                Document reward = leible(ruta+files[i].getName());
                Element root = reward.getRootElement();
                switch (root.element("name").getText()) {
                    case "almacen_a.xml","almacen_b.xml","almacen_c.xml","almacen_d.xml":
                        if(almacen.equals("")){
                            almacen = "Partes del Almacén central \n";
                        }
                        switch (Character.toUpperCase(root.element("name").getText().charAt(8))) {
                            case 'A':charsAlm[0] = "A";break;
                            case 'B':charsAlm[1] = "B";break;
                            case 'C':charsAlm[2] = "C";break;
                            case 'D':charsAlm[3] = "D";break;
                        }
                        j--;
                        break;
                    case "pisci_m_a.xml","pisci_m_b.xml":
                        if(pisciMar.equals("")){
                            pisciMar = "Partes de Piscifactoría de Mar \n";
                        }
                        switch (Character.toUpperCase(root.element("name").getText().charAt(8))) {
                            case 'A':charsMar[0] = "A";break;
                            case 'B':charsMar[1] = "B";break;
                        }
                        j--;
                        break;
                    case "pisci_r_a.xml","pisci_r_b.xml":
                        if(pisciRio.equals("")){
                            pisciRio = "Partes de Piscifactoría de Río \n";
                        }
                        switch (Character.toUpperCase(root.element("name").getText().charAt(8))) {
                            case 'A':charsRio[0] = "A";break;
                            case 'B':charsRio[1] = "B";break;
                        }
                        j--;
                        break;
                
                    default:
                        menu += (j+1)+". "+files[i].getName()+root.element("quantity").getText()+"\n";
                        break;
                }
            }
            if(!almacen.equals("")){
                menu += (files.length+1)+almacen+charsAlm[0]+charsAlm[1]+charsAlm[2]+charsAlm[3]+"\n";
            }
            if(!pisciMar.equals("")){
                menu += (files.length+2)+pisciMar+charsMar[0]+charsMar[1]+charsMar[2]+charsMar[3]+"\n";
            }
            if(!pisciRio.equals("")){
                menu += (files.length+3)+pisciRio+charsRio[0]+charsRio[1]+charsRio[2]+charsRio[3]+"\n";
            }
            System.out.println(menu);
            return Reader.readTheNumber();
        }else{
            System.out.println("No existen recompensas a reclamar");
            return -1;
        }
    }

    public static int selectReward(int option){
        String ruta = "src//rewards//";
        Path path = Paths.get(ruta);
        if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
            File folder = new File(ruta);
            File[] files = folder.listFiles();
            Arrays.sort(files);
            if((files.length+1)==option||(files.length+2)==option||(files.length+3)==option){
                return option;
            }
            for(int i=0,j=0;i<files.length;i++){
                if(files[i].getName().substring(0, 4).equals("pisci")||files[i].getName().substring(0, 4).equals("almac")){
                    j--;
                }
                if(j==option){
                    return i;
                }
            }
        }
        return -1;
    }

    public static void claimReward(){
        int opcion = selectReward(listRewards());
        if(opcion!=0&&opcion!=-1){
            String ruta = "src//rewards//";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
            File folder = new File(ruta);
            File[] files = folder.listFiles();
            Arrays.sort(files);
            //TODO comprobar el ABCD de los edificio y la existencia de las rewards
            }
        }
    }

    public static void rewardAlga(int lvl){
        int res = (lvl-1)>=3 ? ((lvl-1)*300)+((lvl-1)==4 ? 800 : 100) : ((lvl-1)*100+(lvl==3 ? 300 : 100));
        Document doc = leible("src//rewards//algas_"+lvl+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Algas "+romanNum[lvl]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" cápsula de algas para alimentar peces filtradores y omnívoros");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("food").addAttribute("type","algae").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
        save(doc, "src//rewards//algas_"+lvl+".xml");
    }

    public static void rewardPienso(int lvl){
        int res = (lvl-1)>=3 ? ((lvl-1)*300)+((lvl-1)==4 ? 800 : 100) : ((lvl-1)*100+(lvl==3 ? 300 : 100));
        Document doc = leible("src//rewards//pienso_"+lvl+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Pienso de peces "+romanNum[lvl]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("food").addAttribute("type","animal").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
        save(doc, "src//rewards//pienso_"+lvl+".xml");
    }

    public static void rewardGeneral(int lvl){
        int res = (lvl-1)>=2 ? ((lvl-1)>=3 ? (((lvl-1)*150)+(lvl==5 ? 400 : 50)) : ((lvl-1)*100)+50) : ((lvl-1)*50)+50;
        Document doc = leible("src//rewards//comida_"+lvl+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Comida general "+romanNum[lvl]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" unidades de pienso multipropósito para todo tipo de peces.");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("food").addAttribute("type","general").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
        save(doc, "src//rewards//comida_"+lvl+".xml");
    }

    public static void rewardAlmacen(String part){
        Document doc = leible("src//rewards//almacen_"+part+".xml");
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
        save(doc, "src//rewards//almacen_"+part+".xml");
    }

    public static void rewardCoins(int lvl){
        int res = ((lvl-1)*200)+((lvl==5) ? 200 : ((lvl==4) ? 150 : 100));
        Document doc = leible("src//rewards//monedas_"+lvl+"xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Monedas "+romanNum[(lvl-1)]);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText(res+" monedas");
        root.addElement("rarity").addText(String.valueOf(lvl-1));
        Element give = root.addElement("give");
        give.addElement("coins").addText(String.valueOf(res));
        root.addElement("quantity").addText("1");
        save(doc, "src//rewards//monedas_"+lvl+"xml");
    }

    public static void rewardPisci(int rarity,String part){
        String type = (rarity==3) ? "río" : "mar";
        Document doc = leible("src//rewards//pisci_"+type.charAt(0)+"_"+part+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Piscifactoría de "+type+" ["+part+"]");
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText("Materiales para la construcción de una piscifactoría de "+type+". Con la parte A y B, puedes obtenerla de forma gratuita.");
        root.addElement("rarity").addText(String.valueOf(rarity));
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", String.valueOf((rarity-3))).addText("Piscifactoría de"+type);
        give.addElement("part").addText(part);
        give.addElement("total").addText("AB");
        root.addElement("quantity").addText("1");
        save(doc, "src//rewards//pisci_"+type.charAt(0)+"_"+part+".xml");
    }

    public static void rewardTanq(int rarity){
        String type = (rarity==3) ? "río" : "mar";
        Document doc = leible("src//rewards//tanque_"+type.charAt(0)+".xml");
        Element root = doc.addElement("reward");
        root.addElement("name").addText("Tanque de "+type);
        root.addElement("origin").addText(Simulador.getNombre());
        root.addElement("desc").addText("Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de "+type+".");
        root.addElement("rarity").addText(String.valueOf(rarity));
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", String.valueOf(rarity)).addText("Piscifactoría de"+type);
        give.addElement("part").addText("A");
        give.addElement("total").addText("A");
        root.addElement("quantity").addText("1");
        save(doc, "src//rewards//tanque_"+type.charAt(0)+".xml");
    }
}