package puzzle.generator.utilitaire;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ClassePieceGenerator {

	private BufferedImage image;
	private int largeur;
	private int hauteur;

	
	public ClassePieceGenerator(BufferedImage image){
		this.image = image;
		this.largeur = this.image.getWidth();
		this.hauteur = this.image.getHeight();
	}
	
	public void genereClasse(String classePath,String name,String pack){
		try {
			this.clean(classePath);
			FileWriter out = new FileWriter(new File(classePath+name+".java"));
			
		
			out.write("package "+pack+";\n");
			out.write("\n");
			out.write("import java.awt.image.BufferedImage;\n");
			out.write("\n");
			out.write("public class "+name+"{\n");
			out.write("\tprivate int largeur="+String.valueOf(this.largeur)+";\n");
			out.write("\tprivate int hauteur="+String.valueOf(this.hauteur)+";\n");
			out.write("\tprivate static "+name+" instance=null;\n");
			
			
			for(int j=0;j<this.hauteur;j++){
				out.write("String l"+String.valueOf(j)+" =");
				out.write("\"");
				for(int i=0;i<this.largeur;i++){
					out.write(String.valueOf(this.image.getRGB(i, j))+";");
				}
				out.write("\";\n");
			}
			
			out.write("\n");
			out.write("\tpublic static "+name+" getInstance(){\n");
			out.write("\t\t if(instance==null)\n");
			out.write("\t\t\tinstance=new "+name+"();\n");
			out.write("\t\treturn instance;\n");
			out.write("\t}\n");
			out.write("\n");
			out.write("\tprivate "+name+"(){\n");
			
		
			
			out.write("\t}\n");
			out.write("\n");
			out.write("\n");
			out.write("\tpublic BufferedImage getImage(){\n");
			out.write("\t\tBufferedImage img = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);\n");
			
			
			//
			for(int j=0;j<this.hauteur;j++){
				out.write("\t\tString[] tab"+String.valueOf(j)+"=this.l"+String.valueOf(j)+".split(\"[;]\");\n");
			}
			out.write("\t\tfor(int i=0;i<largeur;i++){\n");
			for(int j=0;j<this.hauteur;j++){
			
			out.write("\t\t\timg.setRGB(i,"+String.valueOf(j)+",Integer.valueOf(tab"+String.valueOf(j)+"[i]));\n");
			
			}
			out.write("\t\t}\n");

			out.write("\t\treturn img;\n");
			out.write("\t}\n");
			
			out.write("}\n");
			//
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void clean(String classePath){
		
	}
	
	
	
	public static void main(String[] args){
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File("template_large/0.png"));
			ClassePieceGenerator p = new ClassePieceGenerator(img);
			p.genereClasse("D:/projet_java/personnel/generator/src/generator/piece/", "Piece_0", "generator.piece");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
