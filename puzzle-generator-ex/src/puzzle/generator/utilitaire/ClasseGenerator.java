package puzzle.generator.utilitaire;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import puzzle.generator.modele.Puzzle;
import puzzle.generator.modele.frame.Cell;

public class ClasseGenerator {
	private Puzzle puzzle;
//	private FrameEx frame;
//	private PieceDescripteur descriptor;
	private double scalePiece;
	private String classePath;
	private double scaleApercu;
	
	
	
	



	public void genereClasse(String name,String pack){
		try {
			FileWriter out = new FileWriter(new File(classePath+name+".java"));

			out.write("package "+pack+";\n");
			out.write("\n");
			out.write("public class "+name+"{\n");

			out.write("\tprivate final static int[][] frame = {\n");

			

			for(int i=0;i<this.puzzle.getFrame().getLargeur();i++){
				out.write("\t\t{");
				for(int j=0;j<this.puzzle.getFrame().getHauteur();j++){
					Cell cell = this.puzzle.getFrame().getCell(i, j);
					out.write(String.valueOf(cell.getType()));
					if((j%this.puzzle.getFrame().getHauteur()) != (this.puzzle.getFrame().getHauteur()-1)) out.write(",");
				}
				out.write("}");
				if((i%this.puzzle.getFrame().getLargeur()) != (this.puzzle.getFrame().getLargeur()-1)) out.write(",");
				out.write("\n");
			}
			out.write("\t};\n");
			out.write("\n");
			out.write("\tpublic final static int largeur  = "+this.puzzle.getFrame().getLargeur()+";\n");
			out.write("\tpublic final static int hauteur  = "+this.puzzle.getFrame().getHauteur()+";\n");
			out.write("\tpublic final static int nbPiece  = "+this.puzzle.getFrame().getHauteur()*this.puzzle.getFrame().getLargeur()+";\n");
			out.write("\tpublic final static int imageLargeur  = "+this.puzzle.getFrame().getLargeur()* this.puzzle.getDescriptor().getPieceCote()+";\n");
			out.write("\tpublic final static int imageHauteur  = "+this.puzzle.getFrame().getHauteur()* this.puzzle.getDescriptor().getPieceCote()+";\n");
			out.write("\tpublic final static double pieceCote  = "+this.scalePiece* this.puzzle.getDescriptor().getPieceCote()+";\n");
			out.write("\tpublic final static double pieceVarMax  = "+this.scalePiece* this.puzzle.getDescriptor().getPieceVarMax()+";\n");
			out.write("\tpublic final static double pieceVarMin  = "+this.scalePiece* this.puzzle.getDescriptor().getPieceVarMin()+";\n");
			out.write("\tpublic final static double scaleVue  = "+this.scaleApercu+";\n");

			out.write("\n");
			out.write("\tpublic static int[][] getFrame(){\n");
			out.write("\t\treturn frame;\n");
			out.write("\t}\n");

			out.write("}\n");

			out.close();
		} catch (IOException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		}
	}





	public void setScaleApercu(double scaleApercu) {
		this.scaleApercu = scaleApercu;
	}


	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public String getClassePath() {
		return classePath;
	}

	public void setClassePath(String classePath) {
		this.classePath = classePath;
	}

	public void setScalePiece(double scalePiece) {
		this.scalePiece = scalePiece;
	}
}
