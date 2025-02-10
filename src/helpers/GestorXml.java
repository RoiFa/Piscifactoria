package helpers;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import main.Almacen;
import main.Simulador;
import piscifactoria.Piscifactoria;

public class GestorXml {

    /** Almacena los numeros romanos */
    private static String[] romanNum = new String[]{"I","II","III","IV","V"};

    /** Almacena las partes existentes de recompensas de almacen */
    private static String[] charsAlm = new String[]{"X","X","X","X"};

    /** Almacena las partes existentes de recompensas de Mar */
    private static String[] charsMar = new String[]{"X","X"};

    /** Almacena las partes existentes de recompensas de Rio */
    private static String[] charsRio = new String[]{"X","X"};

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
                ErrorWriter.writeInErrorLog("Error al parsear un documento a editable");
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
            ErrorWriter.writeInErrorLog("Error al guardar un documento xml");
        }finally{
            try {
                writer.close();
            } catch (Exception e) {}
        }
        System.out.println("Guardado");
    }

    /**
     * Muestra por pantalla todos los archivos de recompensa disponibles
     * @return Opción escrita por el usuario
     */
    public static int listRewards(){
        try {
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            String menu="";
            String almacen="",pisciMar="",pisciRio="";
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                Arrays.sort(files);
                for(int i=0,j=0;i<files.length;i++,j++){
                    Document reward = leible(ruta+"/"+files[i].getName());
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
                            menu += (j+1)+". "+root.element("name").getText()+" Nº:"+root.element("quantity").getText()+"\n";
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
                return Reader.readTheNumber(1,0);
            }else{
                System.out.println("No existen recompensas a reclamar");
                return -1;
            }
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al listar las recompensas");
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Traduce el número optenido por la posición correspondiente al archivo seleccionado en el menú
     * @param option Nº de opcion del archivo
     * @return Posición en el array de documentos del archivo
     */
    public static int selectReward(int option){
        try {
            String ruta = "rewards";
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
                    j++;
                }
            }
            return -1;                
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al seleccionar una de las recompensas");
            return -1;
        }
    }

    /**
     * Canjea la recompensa seleccionada y la otorga al jugador
     */
    public static void claimReward(){
        boolean aChar=false,bChar=false,cChar=false,dChar=false;
        int opcion = selectReward(listRewards());
        try {
            if(opcion!=-1){
                String ruta = "rewards";
                Path path = Paths.get(ruta);
                if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                    File folder = new File(ruta);
                    File[] files = folder.listFiles();
                    Arrays.sort(files);
                    Document reward = leible(ruta+"/"+files[opcion-1].getName());
                    Element root = reward.getRootElement();
                    if((files.length+1)==opcion&&(charsAlm[0].equals("A")&&charsAlm[1].equals("B")&&charsAlm[2].equals("C")&&charsAlm[3].equals("D"))){
                        if(Simulador.instancia.almacen!=null){
                            Simulador.instancia.almacen = new Almacen();
                            System.out.println("Has canjeado exitosamente las partes de almacen por un almacen nuevo!");
                            TranscriptWriter.writeInTranscript("Recompensa almacen usada(ABCD)");
                            for(File file : files){
                                if(file.getName()=="almacen_a.xml"&&!aChar){
                                    deplete(file);
                                    aChar=true;
                                }
                                if(file.getName()=="almacen_b.xml"&&!bChar){
                                    deplete(file);
                                    bChar=true;
                                }
                                if(file.getName()=="almacen_c.xml"&&!cChar){
                                    deplete(file);
                                    cChar=true;
                                }
                                if(file.getName()=="almacen_d.xml"&&!dChar){
                                    deplete(file);
                                    dChar=true;
                                }
                            }
                        }else{
                            System.out.println("Ya posees un almacen");
                        }
                    }else if((files.length+1)==opcion){
                        System.out.println("No tienes suficientes partes de almacen para crear uno");
                    }
                    if((files.length+2)==opcion&&(charsMar[0].equals("A")&&charsMar[1].equals("B"))){
                        ArrayList<Piscifactoria> piscis = Simulador.instancia.getPiscis();
                        String name="";
                        while (name=="") {
                            System.out.println("Que nombre quiere para la nueva piscifactoría?(mar)");
                            name = Reader.readTheLine();
                            TranscriptWriter.writeInTranscript("Recompensa Piscifactoría(Mar) usada(AB)");
                        }
                        piscis.add(new Piscifactoria("mar", name));
                        for(File file : files){
                            if(file.getName()=="almacen_a.xml"&&!aChar){
                                deplete(file);
                                aChar=true;
                            }
                            if(file.getName()=="almacen_b.xml"&&!bChar){
                                deplete(file);
                                bChar=true;
                            }
                        }
                    }else if((files.length+2)==opcion){
                        System.out.println("No tienes suficientes partes de piscifactoria de Mar para crear una");
                    }
                    if((files.length+3)==opcion&&(charsRio[0].equals("A")&&charsRio[1].equals("B"))){
                        ArrayList<Piscifactoria> piscis = Simulador.instancia.getPiscis();
                        String name="";
                        while (name=="") {
                            System.out.println("Que nombre quiere para la nueva piscifactoría?(rio)");
                            name = Reader.readTheLine();
                            TranscriptWriter.writeInTranscript("Recompensa Piscifactoría(Río) usada(AB)");
                        }
                        piscis.add(new Piscifactoria("rio", name));
                        for(File file : files){
                            if(file.getName()=="almacen_a.xml"&&!aChar){
                                deplete(file);
                                aChar=true;
                            }
                            if(file.getName()=="almacen_b.xml"&&!bChar){
                                deplete(file);
                                bChar=true;
                            }
                        }
                    }else if((files.length+2)==opcion){
                        System.out.println("No tienes suficientes partes de piscifactoria de Rio para crear una");
                    }
                    Element give = root.element("give");
                    switch (root.element("name").getText().substring(0, 5)) {
                        case "Algas":
                            shareFood(Integer.parseInt(give.element("food").getText()), 0);
                            deplete(files[opcion-1]);
                            TranscriptWriter.writeInTranscript("Recompensa "+root.element("name").getText()+" usada");
                            break;
                        case "Piens":
                            shareFood(0, Integer.parseInt(give.element("food").getText()));
                            deplete(files[opcion-1]);
                            TranscriptWriter.writeInTranscript("Recompensa "+root.element("name").getText()+" usada");
                            break;
                        case "Comid":
                            shareFood(Integer.parseInt(give.element("food").getText()), Integer.parseInt(give.element("food").getText()));
                            deplete(files[opcion-1]);
                            TranscriptWriter.writeInTranscript("Recompensa "+root.element("name").getText()+" usada");
                            break;
                        case "Moned":
                            Simulador.instancia.monedas.anadir(Integer.parseInt(give.element("coins").getText()));
                            deplete(files[opcion-1]);
                            TranscriptWriter.writeInTranscript("Recompensa "+root.element("name").getText()+" usada");
                            break;
                        case "Tanqu":
                            ArrayList<Piscifactoria> piscis = Simulador.instancia.getPiscis();
                            int pisci = Simulador.selectPisc();
                            if (give.element("building").attribute("code").getText().equals("3")&&piscis.get(pisci).getTipo().equals("rio")){
                                piscis.get(pisci).addTank();
                                System.out.println("Se agrego su tanque de Rio correctamente");
                                deplete(files[opcion-1]);
                            }else if(give.element("building").attribute("code").getText().equals("4")&&piscis.get(pisci).getTipo().equals("mar")){
                                piscis.get(pisci).addTank();
                                System.out.println("Se agrego su tanque de Mar correctamente");
                                deplete(files[opcion-1]);
                            }else{
                                System.out.println("El tipo del tanque no coincide con la piscifactoría seleccionada(mar/rio)");
                            }
                            TranscriptWriter.writeInTranscript("Recompensa "+root.element("name").getText()+" usada");
                            break;
                        default:
                        System.out.println(root.element("name").getText().substring(0, 5));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al canjear una de las recompensas");
        }
    }

    /**
     * Borra o reducen en uno la cantidad del documento de recompensa
     * @param file Documendo de recompensa a reducir
     */
    public static void deplete(File file){
        try {
            Document reward = leible("rewards/"+file.getName());
            Element root = reward.getRootElement();
            if(Integer.parseInt(root.element("quantity").getText())>=2){
                root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())-1));
                save(reward, "rewards/"+file.getName());
            }else{
                file.delete();
            }
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al eliminar o reducir la cantidad de una recompensa");
        }
    }

    /**
     * Distribuye la comida equitativamente entre las piscifacotrias
     * @param vegetal cantidad de comida vegetal a repartir
     * @param pienso cantidad de comida animal a repartir
     */
    public static void shareFood(int vegetal,int pienso){
        try {
            ArrayList<Piscifactoria> piscis = Simulador.instancia.getPiscis();
            int numPiscis = piscis.size();
            int cantRepartCarne = pienso/numPiscis;
            int cantRepartVeget = vegetal/numPiscis;
            for (Piscifactoria pisci : piscis) {
                if(cantRepartCarne>pisci.getComidaMax()){
                    pisci.addFood(pisci.getComidaMax(),0);
                    pienso -= pisci.getComidaMax();
                }else{
                    pisci.addFood(cantRepartCarne,0);
                    pienso -= cantRepartCarne;
                }
                if(cantRepartVeget>100){
                    pisci.addFood(0,pisci.getComidaMax());
                    vegetal -= pisci.getComidaMax();
                }else{
                    pisci.addFood(0,cantRepartVeget);
                    vegetal -= cantRepartVeget;
                }
            Simulador.instancia.setPiscis(piscis);
            }
            
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al repartir la comida de las recompensas");
        }
    }

    /**
     * Crea un documento de recompensa de comida vegetal
     * @param lvl Nivel de la recompensa
     */
    public static void rewardAlga(int lvl){
        try {
            Document doc=null;
            Element root=null;
            boolean done=false;
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                for(File file : files) {
                    if(file.getName().equals("algas_"+lvl+".xml")&&!done){
                        doc = leible("rewards/"+file.getName());
                        root = doc.getRootElement();
                        root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())+1));
                        save(doc, "rewards/algas_"+lvl+".xml");
                        done=true;
                    }
                }
                if(!done){
                    int res = (lvl-1)>=3 ? ((lvl-1)*300)+((lvl-1)==4 ? 800 : 100) : ((lvl-1)*100+(lvl==3 ? 300 : 100));
                    doc = leible("rewards/algas_"+lvl+".xml");
                    root = doc.addElement("reward");
                    root.addElement("name").addText("Algas "+romanNum[lvl-1]);
                    root.addElement("origin").addText(Simulador.instancia.getNombre());
                    root.addElement("desc").addText(res+" cápsula de algas para alimentar peces filtradores y omnívoros");
                    root.addElement("rarity").addText(String.valueOf(lvl-1));
                    Element give = root.addElement("give");
                    give.addElement("food").addAttribute("type","algae").addText(String.valueOf(res));
                    root.addElement("quantity").addText("1");
                    save(doc, "rewards/algas_"+lvl+".xml");
                }
                LogWriter.writeInLog("Recompensa "+root.element("name").getText()+" creada");
            }
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al crear una recompensa de algas");
        }
    }

    /**
     * Crea un documento de recompensa de comida animal
     * @param lvl Nivel de la recompensa
     */
    public static void rewardPienso(int lvl){
        try {
            Document doc=null;
            Element root=null;
            boolean done=false;
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                for(File file : files) {
                    if(file.getName().equals("pienso_"+lvl+".xml")&&!done){
                        doc = leible("rewards/"+file.getName());
                        root = doc.getRootElement();
                        root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())+1));
                        save(doc, "rewards/pienso_"+lvl+".xml");
                        done=true;
                    }
                }
                if(!done){
                    int res = (lvl-1)>=3 ? ((lvl-1)*300)+((lvl-1)==4 ? 800 : 100) : ((lvl-1)*100+(lvl==3 ? 300 : 100));
                    doc = leible("rewards/pienso_"+lvl+".xml");
                    root = doc.addElement("reward");
                    root.addElement("name").addText("Pienso de peces "+romanNum[lvl-1]);
                    root.addElement("origin").addText(Simulador.instancia.getNombre());
                    root.addElement("desc").addText(res+" unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
                    root.addElement("rarity").addText(String.valueOf(lvl-1));
                    Element give = root.addElement("give");
                    give.addElement("food").addAttribute("type","animal").addText(String.valueOf(res));
                    root.addElement("quantity").addText("1");
                    save(doc, "rewards/pienso_"+lvl+".xml");
                }
                LogWriter.writeInLog("Recompensa "+root.element("name").getText()+" creada");
            }
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al crear una recompensa de pienso");
        }
    }

    /**
     * Crea un documento de recompensa de ambos tipos de comida
     * @param lvl Nivel de la recompensa
     */
    public static void rewardGeneral(int lvl){
        try {
            Document doc=null;
            Element root=null;
            boolean done=false;
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                for(File file : files) {
                    if(file.getName().equals("comida_"+lvl+".xml")&&!done){
                        doc = leible("rewards/"+file.getName());
                        root = doc.getRootElement();
                        root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())+1));
                        save(doc, "rewards/comida_"+lvl+".xml");
                        done=true;
                    }
                }
                if(!done){
                    int res = (lvl-1)>=2 ? ((lvl-1)>=3 ? (((lvl-1)*150)+(lvl==5 ? 400 : 50)) : ((lvl-1)*100)+50) : ((lvl-1)*50)+50;
                    doc = leible("rewards/comida_"+lvl+".xml");
                    root = doc.addElement("reward");
                    root.addElement("name").addText("Comida general "+romanNum[lvl-1]);
                    root.addElement("origin").addText(Simulador.instancia.getNombre());
                    root.addElement("desc").addText(res+" unidades de pienso multipropósito para todo tipo de peces.");
                    root.addElement("rarity").addText(String.valueOf(lvl-1));
                    Element give = root.addElement("give");
                    give.addElement("food").addAttribute("type","general").addText(String.valueOf(res));
                    root.addElement("quantity").addText("1");
                    save(doc, "rewards/comida_"+lvl+".xml");
                }
                LogWriter.writeInLog("Recompensa "+root.element("name").getText()+" creada");
            }   
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al crear una recompensa de comida general");
        }
    }

    /**
     * Crea un documento de recompensa de una parte de almacen
     * @param part Parte a crear del almacen
     */
    public static void rewardAlmacen(String part){
        try {
            Document doc=null;
            Element root=null;
            boolean done=false;
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                for(File file : files) {
                    if(file.getName().equals("almacen_"+part+".xml")&&!done){
                        doc = leible("rewards/"+file.getName());
                        root = doc.getRootElement();
                        root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())+1));
                        save(doc, "rewards/almacen_"+part+".xml");
                        done=true;
                    }
                }
                if(!done){
                    doc = leible("rewards/almacen_"+part+".xml");
                    root = doc.addElement("reward");
                    root.addElement("name").addText("Almacén central ["+part+"]");
                    root.addElement("origin").addText(Simulador.instancia.getNombre());
                    root.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
                    root.addElement("rarity").addText("3");
                    Element give = root.addElement("give");
                    give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                    give.addElement("part").addText(part);
                    give.addElement("total").addText("ABCD");
                    root.addElement("quantity").addText("1");
                    save(doc, "rewards/almacen_"+part+".xml");
                }
                LogWriter.writeInLog("Recompensa "+root.element("name").getText()+" creada");
            }            
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al crear una recompensa de parte de almacen");
        }
    }

    /**
     * Crea un documento de recompensa de monedas
     * @param lvl Nivel de la recompensa
     */
    public static void rewardCoins(int lvl){
        try {
            Document doc=null;
            Element root=null;
            boolean done=false;
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                for(File file : files) {
                    if(file.getName().equals("monedas_"+lvl+".xml")&&!done){
                        doc = leible("rewards/"+file.getName());
                        root = doc.getRootElement();
                        root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())+1));
                        save(doc, "rewards/monedas_"+lvl+".xml");
                        done=true;
                    }
                }
                if(!done){
                    int res = ((lvl-1)*200)+((lvl==5) ? 200 : ((lvl==4) ? 150 : 100));
                    doc = leible("rewards/monedas_"+lvl+".xml");
                    root = doc.addElement("reward");
                    root.addElement("name").addText("Monedas "+romanNum[(lvl-1)]);
                    root.addElement("origin").addText(Simulador.instancia.getNombre());
                    root.addElement("desc").addText(res+" monedas");
                    root.addElement("rarity").addText(String.valueOf(lvl-1));
                    Element give = root.addElement("give");
                    give.addElement("coins").addText(String.valueOf(res));
                    root.addElement("quantity").addText("1");
                    save(doc, "rewards/monedas_"+lvl+".xml");
                }
                LogWriter.writeInLog("Recompensa "+root.element("name").getText()+" creada");
            }            
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al crear una recompensa de monedas");
        }
    }

    /**
     * Crea un documento de recompensa de un pieza de piscifactoria
     * @param tipo Tipo de piscifactoria(Mar/Rio)
     * @param part Parte de la piscifactoria(A/B)
     */
    public static void rewardPisci(String tipo,String part){
        try {
            int rarity = tipo.equals("rio") ? 3 : 4;
            Document doc=null;
            Element root=null;
            boolean done=false;
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                for(File file : files) {
                    if(file.getName().equals("pisci_"+tipo.charAt(0)+"_"+part+".xml")&&!done){
                        doc = leible("rewards/"+file.getName());
                        root = doc.getRootElement();
                        root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())+1));
                        save(doc, "rewards/pisci_"+tipo.charAt(0)+"_"+part+".xml");
                        done=true;
                    }
                }
                if(!done){
                    doc = leible("rewards/pisci_"+tipo.charAt(0)+"_"+part+".xml");
                    root = doc.addElement("reward");
                    root.addElement("name").addText("Piscifactoría de "+tipo+" ["+part+"]");
                    root.addElement("origin").addText(Simulador.instancia.getNombre());
                    root.addElement("desc").addText("Materiales para la construcción de una piscifactoría de "+tipo+". Con la parte A y B, puedes obtenerla de forma gratuita.");
                    root.addElement("rarity").addText(String.valueOf(rarity));
                    Element give = root.addElement("give");
                    give.addElement("building").addAttribute("code", String.valueOf((rarity-3))).addText("Piscifactoría de"+tipo);
                    give.addElement("part").addText(part);
                    give.addElement("total").addText("AB");
                    root.addElement("quantity").addText("1");
                    save(doc, "rewards/pisci_"+tipo.charAt(0)+"_"+part+".xml");
                }
                LogWriter.writeInLog("Recompensa "+root.element("name").getText()+" creada");
            }            
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al crear una recompensa de pieza de piscifactoría");
        }
    }

    /**
     * Crea un documento de recompensa de un tanque de Mar o Rio
     * @param rarity
     */
    public static void rewardTanq(String type){
        try {
            int rarity= type.equals("mar") ? 4 : 3;
            Document doc=null;
            Element root=null;
            boolean done=false;
            String ruta = "rewards";
            Path path = Paths.get(ruta);
            if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                File folder = new File(ruta);
                File[] files = folder.listFiles();
                for(File file : files) {
                    if(file.getName().equals("tanque_"+type.charAt(0)+".xml")&&!done){
                        doc = leible("rewards/"+file.getName());
                        root = doc.getRootElement();
                        root.element("quantity").setText(""+(Integer.parseInt(root.element("quantity").getText())+1));
                        save(doc, "rewards/tanque_"+type.charAt(0)+".xml");
                        done=true;
                    }
                }
                if(!done){
                    doc = leible("rewards/tanque_"+type.charAt(0)+".xml");
                    root = doc.addElement("reward");
                    root.addElement("name").addText("Tanque de "+type);
                    root.addElement("origin").addText(Simulador.instancia.getNombre());
                    root.addElement("desc").addText("Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de "+type+".");
                    root.addElement("rarity").addText(String.valueOf(rarity));
                    Element give = root.addElement("give");
                    give.addElement("building").addAttribute("code", String.valueOf(rarity)).addText("Piscifactoría de"+type);
                    give.addElement("part").addText("A");
                    give.addElement("total").addText("A");
                    root.addElement("quantity").addText("1");
                    save(doc, "rewards/tanque_"+type.charAt(0)+".xml");
                }
                LogWriter.writeInLog("Recompensa "+root.element("name").getText()+" creada");
            }
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al crear una recompensa de tanque");
        }
    }

    /**
     * Genera una recompensa de manera aleatoria en base a probabilidades
     */
    public static void randomReward(){
        int typeR = 0;
        int subType = 0;
        int lvl = 0;
        typeR = RNG.RandomInt(100);
        subType = RNG.RandomInt(100);

        if(typeR<=50){
            if(subType<=60){
                lvl=1;
            }else if(subType<=90){
                lvl=2;
            }else{
                lvl=3;
            }
            switch (RNG.RandomInt(3)) {
                case 0: rewardAlga(lvl); break;
                case 1: rewardPienso(lvl); break;
                case 2: rewardGeneral(lvl); break;
                default:
                    break;
            }
        }else if(typeR<=90){
            if(subType<=60){
                lvl=1;
            }else if(subType<=90){
                lvl=2;
            }else{
                lvl=3;
            }
            rewardCoins(lvl);
        }else{
            if(RNG.RandomInt(10)<6){
                rewardTanq("rio");
            }else{
                rewardTanq("mar");
            }
        }
    }
}