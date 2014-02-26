package com.example.view;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public interface IDrawable {
	public FloatBuffer getVertexBuffer();
	public FloatBuffer getUvBuffer();
	public short[] getIndices();
	public ShortBuffer getDrawListBuffer();
}
