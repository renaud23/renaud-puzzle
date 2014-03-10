package com.gl2d.core.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.gl2d.core.Shader;

import android.opengl.GLES20;

public class RenderableLine implements GLRenderable{
	private FloatBuffer VertexBuffer;

	

	protected int PositionHandle;
	protected int ColorHandle;


	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 3;
	static float LineCoords[] = {
	    0.0f, 0.0f, 0.0f,
	    1.0f, 0.0f, 0.0f
	};

	private final int VertexCount = LineCoords.length / COORDS_PER_VERTEX;
	private final int VertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

	// Set color with red, green, blue and alpha (opacity) values
	float color[] = { 0.0f, 0.0f, 0.0f, 1.0f };

	public RenderableLine(){
	    // initialize vertex byte buffer for shape coordinates
	    ByteBuffer bb = ByteBuffer.allocateDirect(
	            // (number of coordinate values * 4 bytes per float)
	            LineCoords.length * 4);
	    // use the device hardware's native byte order
	    bb.order(ByteOrder.nativeOrder());

	    // create a floating point buffer from the ByteBuffer
	    VertexBuffer = bb.asFloatBuffer();
	    // add the coordinates to the FloatBuffer
	    VertexBuffer.put(LineCoords);
	    // set the buffer to read the first coordinate
	    VertexBuffer.position(0);
	}

	public void SetVerts(float v0, float v1, float v2, float v3, float v4, float v5){
	    LineCoords[0] = v0;
	    LineCoords[1] = v1;
	    LineCoords[2] = v2;
	    LineCoords[3] = v3;
	    LineCoords[4] = v4;
	    LineCoords[5] = v5;

	    VertexBuffer.put(LineCoords);
	    // set the buffer to read the first coordinate
	    VertexBuffer.position(0);

	}

	public void SetColor(float red, float green, float blue, float alpha){
	    color[0] = red;
	    color[1] = green;
	    color[2] = blue;
	    color[3] = alpha;
	}

	public void draw(float[] mvpMatrix){
	    
	}

	@Override
	public int compareTo(GLRenderable arg0) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void render(float[] mtrxProjectionAndView) {
		// Add program to OpenGL ES environment
	    GLES20.glUseProgram(Shader.sp_line);

	    // get handle to vertex shader's vPosition member
	    PositionHandle = GLES20.glGetAttribLocation(Shader.sp_line, "vPosition");

	    // Enable a handle to the triangle vertices
	    GLES20.glEnableVertexAttribArray(PositionHandle);

	    // Prepare the triangle coordinate data
	    GLES20.glVertexAttribPointer(PositionHandle, COORDS_PER_VERTEX,
	                                 GLES20.GL_FLOAT, false,
	                                 VertexStride, VertexBuffer);

	    // get handle to fragment shader's vColor member
	    ColorHandle = GLES20.glGetUniformLocation(Shader.sp_line, "vColor");

	    // Set color for drawing the triangle
	    GLES20.glUniform4fv(ColorHandle, 1, color, 0);

	    // get handle to shape's transformation matrix
	    int MVPMatrixHandle = GLES20.glGetUniformLocation(Shader.sp_line, "uMVPMatrix");
//	    ArRenderer.checkGlError("glGetUniformLocation");

	    // Apply the projection and view transformation
	    GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mtrxProjectionAndView, 0);
//	    ArRenderer.checkGlError("glUniformMatrix4fv");


	    // Draw the triangle
	    GLES20.glDrawArrays(GLES20.GL_LINES, 0, VertexCount);

	    // Disable vertex array
	    GLES20.glDisableVertexAttribArray(PositionHandle);
		
	}

}
