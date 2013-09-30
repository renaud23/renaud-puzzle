package puzzle.generator.descriptor;

public class PieceDescripteur  {
	
	public static final String INSTALL_PATH = "templates/";
	
	/**
	 * Chemin d'installation.
	 */
	private String path;
	/**
	 * taille moyenne de la piece.
	 */
	private int pieceCote;
	/**
	 * variation supplémentaire induite par un creux.
	 */
	private int pieceVarMin;
	/**
	 * Variation supplémentaire induite par un téton.
	 */
	private int pieceVarMax;
	/**
	 * description du set de piéce.
	 */
	private String label;
	
	
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getPieceCote() {
		return pieceCote;
	}
	public void setPieceCote(int pieceCote) {
		this.pieceCote = pieceCote;
	}
	public int getPieceVarMin() {
		return pieceVarMin;
	}
	public void setPieceVarMin(int pieceVarMin) {
		this.pieceVarMin = pieceVarMin;
	}
	public int getPieceVarMax() {
		return pieceVarMax;
	}
	public void setPieceVarMax(int pieceVarMax) {
		this.pieceVarMax = pieceVarMax;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
