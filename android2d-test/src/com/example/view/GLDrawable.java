package com.example.view;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public interface GLDrawable {
	public FloatBuffer getVertexBuffer();
	public FloatBuffer getUvBuffer();
	public short[] getIndices();
	public ShortBuffer getDrawListBuffer();
	public int getTextureIndice();
}
