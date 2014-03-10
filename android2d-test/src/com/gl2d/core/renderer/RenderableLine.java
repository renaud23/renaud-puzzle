package com.gl2d.core.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.gl2d.core.Shader;

import android.graphics.PointF;
import android.opengl.GLES20;

public class RenderableLine implements GLRenderable,RenderableOperation{
	private FloatBuffer VertexBuffer;

	private int z;

	protected int PositionHandle;
	protected int ColorHandle;
	
	private float angle;
	
	
	

	
	private float x1;
	private float x2;
	private float y1;
	private float y2;
	private PointF p0 = new PointF();
    private PointF p1 = new PointF();
    private PointF centreRotation = new PointF();

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

	public RenderableLine(float x1,float y1,float x2,float y2){
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
	    
	   this.move(x1, y1, x2, y2);
	}

	public void move(float x1,float y1,float x2,float y2){
		this.x1 = x1;
	    this.y1 = y1;
	    this.x2 = x2;
	    this.y2 = y2;
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
	public int compareTo(GLRenderable o) {
		int val = 1;

		if(this.z < o.getZIndex()) val = -1;// attention on utilise un treeset, faut jamais dire == sinon l'objet n'entrera pas 
		return val;
	}

	@Override
	public int getZIndex() {
		return this.z;
	}

	@Override
	public void render(float[] mtrxProjectionAndView) {
		// Add program to OpenGL ES environment
	    GLES20.glUseProgram(Shader.sp_line);
	    this.checkTransform();
	    this.makeVertice();

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

	@Override
	public void setZIndex(int index) {
		this.z = index;
	}

	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void translate(float vx, float vy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turn(float delta) {
		this.angle += delta;
	}
	
	private void checkTransform(){
		p0.x = this.x1;
		p0.y = this.y1;
		p1.x = this.x2;
		p1.y = this.y2;
		
		float cos = (float) Math.cos(this.angle);
		float sin = (float) Math.sin(this.angle);
		this.turnPoint(p0, cos, sin, centreRotation.x, centreRotation.y);
		this.turnPoint(p1, cos, sin, centreRotation.x, centreRotation.y);
	}
	
	private void makeVertice(){
		LineCoords[0] = p0.x;
	    LineCoords[1] = p0.y;
	    LineCoords[2] = 0;
	    LineCoords[3] = p1.x;
	    LineCoords[4] = p1.y;
	    LineCoords[5] = 0;

	    VertexBuffer.put(LineCoords);
	    VertexBuffer.position(0);
	}
	
	public void setCentreRotation(float cx,float cy){
		this.centreRotation.x = cx;
		this.centreRotation.y = cy;
	}

	private void turnPoint(PointF p,float cos,float sin,float cx,float cy){
		float xi = p.x;
		float yi = p.y;
		
//		Rx = cos(a) * (Px-Ox) – sin(a) * (Py-Oy) + Ox
//		Ry = sin(a) * (Px-Ox) + cos(a) * (Py-Oy) + Oy
		
		float ex = p.x - cx;
		float ey = p.y - cy;
		xi = cos * ex - sin * ey + cx;
		yi = sin * ex + cos * ey + cy;
		
		p.x = xi;
		p.y = yi;
	}
}
