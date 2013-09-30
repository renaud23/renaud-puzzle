package puzzle.generator.descriptor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



public class DescriptotFactory {
	private static Map<String, PieceDescripteur> descripteurs = new HashMap<String, PieceDescripteur>();
	
	
	public static final String INSTALL_PATH = "templates/";
	
	
	static{
		File rootDirectory = new File(PieceDescripteur.INSTALL_PATH);
		if(rootDirectory.isDirectory()){
			for(File dirSet : rootDirectory.listFiles()){
				if(dirSet.isDirectory()){
					File descriptor = new File(dirSet.getAbsolutePath()+File.separator+"description.xml");
					if(descriptor.exists()){
						DescriptotFactory.createDescriptor(descriptor);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static Collection<String> getPieceSetName(){
		return descripteurs.keySet();
	}
	
	public static PieceDescripteur getDescripteur(String id){
		return descripteurs.get(id);
	}
	
	/**
	 * 
	 * @param file
	 */
	private static void createDescriptor(File file){
		SAXBuilder sxb = new SAXBuilder();
		try {
			Document doc = sxb.build(file);
			Element root = doc.getRootElement();
			Element coteElmt = root.getChild("cote");
			Element vMaxElmt = root.getChild("varMax");
			Element vMinElmt = root.getChild("varMin");
			
			PieceDescripteur dsc = new PieceDescripteur();
			dsc.setPath(file.getParent());
			dsc.setPieceCote(Integer.valueOf(coteElmt.getText()));
			dsc.setPieceVarMax(Integer.valueOf(vMaxElmt.getText()));
			dsc.setPieceVarMin(Integer.valueOf(vMinElmt.getText()));
			dsc.setLabel(root.getAttributeValue("label"));
			
			descripteurs.put(root.getAttributeValue("id"), dsc);
			
			
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
