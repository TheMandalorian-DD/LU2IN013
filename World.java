import java.util.ArrayList;


public class World {

	int _dx;
	int _dy;
	
	int Buffer0[][][];
	int Buffer1[][][];
	
	boolean buffering;
	boolean cloneBuffer; // if buffering, clone buffer after swith
	
	int activeIndex;
	
	ArrayList<Agent> agents;
	
	public World ( int __dx , int __dy, boolean __buffering, boolean __cloneBuffer )
	{
		_dx = __dx;
		_dy = __dy;
		
		buffering = __buffering;
		cloneBuffer = __cloneBuffer;
		
		Buffer0 = new int[_dx][_dy][3];
		Buffer1 = new int[_dx][_dy][3];
		activeIndex = 0;
		
		agents = new ArrayList<Agent>();
		
	    for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
    			Buffer0[x][y][0]=255;
    			Buffer0[x][y][1]=255;
    			Buffer0[x][y][2]=255;
    			Buffer1[x][y][0]=255;
    			Buffer1[x][y][1]=255;
    			Buffer1[x][y][2]=255;
	    	}
	}
	
	public void checkBounds( int __x , int __y )
	{
		if ( __x < 0 || __x > _dx || __y < 0 || __y > _dy )
		{
			System.err.println("[error] out of bounds ("+__x+","+__y+")");
			System.exit(-1);
		}
	}
	
	public int[] getCellState ( int __x, int __y )
	{
		checkBounds (__x,__y);
		
		int color[] = new int[3];

		if ( buffering == false )
		{
			color[0] = Buffer0[__x][__y][0];
			color[1] = Buffer0[__x][__y][1];
			color[2] = Buffer0[__x][__y][2];
		}
		else
		{
			if ( activeIndex == 1 ) // read old buffer
			{
				color[0] = Buffer0[__x][__y][0];
				color[1] = Buffer0[__x][__y][1];
				color[2] = Buffer0[__x][__y][2];
			}
			else
			{
				color[0] = Buffer1[__x][__y][0];
				color[1] = Buffer1[__x][__y][1];
				color[2] = Buffer1[__x][__y][2];
			}
		}
		
		return color;
	}
	
	public void setCellState ( int __x, int __y, int __r, int __g, int __b )
	{
		checkBounds (__x,__y);
		
		if ( buffering == false )
		{
			Buffer0[__x][__y][0] = __r;
			Buffer0[__x][__y][1] = __g;
			Buffer0[__x][__y][2] = __b;
		}
		else
		{
			if ( activeIndex == 0 ) // write new buffer
			{
				Buffer0[__x][__y][0] = __r;
				Buffer0[__x][__y][1] = __g;
				Buffer0[__x][__y][2] = __b;
			}
			else
			{
				Buffer1[__x][__y][0] = __r;
				Buffer1[__x][__y][1] = __g;
				Buffer1[__x][__y][2] = __b;
			}
		}
	}
	
	public void setCellState ( int __x, int __y, int __color[] )
	{
		checkBounds (__x,__y);
			
		if ( buffering == false )
		{
			Buffer0[__x][__y][0] = __color[0];
			Buffer0[__x][__y][1] = __color[1];
			Buffer0[__x][__y][2] = __color[2];
		}
		else
		{
			if ( activeIndex == 0 )
			{
				Buffer0[__x][__y][0] = __color[0];
				Buffer0[__x][__y][1] = __color[1];
				Buffer0[__x][__y][2] = __color[2];
			}
			else
			{
				Buffer1[__x][__y][0] = __color[0];
				Buffer1[__x][__y][1] = __color[1];
				Buffer1[__x][__y][2] = __color[2];
			}	
		}
	}
	
	/**
	 * Update the world state and return an array for the current world state (may be used for display)
	 * @return
	 */
	public void step ( )
	{
		stepWorld();
		stepAgents();
		
		if ( buffering && cloneBuffer )
		{
			if ( activeIndex == 0 )
				for ( int x = 0 ; x != _dx ; x++ )
					for ( int y = 0 ; y != _dy ; y++ )
					{
						Buffer1[x][y][0] = Buffer0[x][y][0];
						Buffer1[x][y][1] = Buffer0[x][y][1];
						Buffer1[x][y][2] = Buffer0[x][y][2];
					}
			else
				for ( int x = 0 ; x != _dx ; x++ )
					for ( int y = 0 ; y != _dy ; y++ )
					{
						Buffer0[x][y][0] = Buffer1[x][y][0];
						Buffer0[x][y][1] = Buffer1[x][y][1];
						Buffer0[x][y][2] = Buffer1[x][y][2];
					}

			activeIndex = (activeIndex + 1 ) % 2; // switch buffer index
		}

	}
	
	public int[][][] getCurrentBuffer()
	{
		if ( activeIndex == 0 || buffering == false ) 
			return Buffer0;
		else
			return Buffer1;		
	}
	
	public int getWidth()
	{
		return _dx; 
	}
	
	public int getHeight()
	{
		return _dy;
	}
	
	public void add (Agent agent)
	{
		agents.add(agent);
	}
	
	public void stepWorld() // world THEN agents
	{
		// ...
	}
	
	public void stepAgents() // world THEN agents
	{
		for ( int i = 0 ; i != agents.size() ; i++ )
		{
			synchronized ( Buffer0 ) {
				agents.get(i).step();
			}
			
		}
		
		// ArrayList<Agent> agents_remove = new ArrayList<>();
		// for (Agent i :agents){
		// 	for (Agent j : agents) {
		// 		if (i == j) {continue;}
		// 		if ((i instanceof PredatorAgent) && (j instanceof PreyAgent)) {
		// 			if (i._x == j._x && i._y == j._y) {
		// 				agents_remove.add(j);
		// 			}
		// 		}
		// 	}
		// }
		// agents.removeAll(agents_remove);
		
	}

	// public void reproduceAgent(){
	// 	for( Agent a : agents ){
	// 		if (a instanceof PredatorAgent){
	// 			((PredatorAgent)a).reproduce();
	// 		}
	// 		else{
	// 			((PreyAgent)a).reproduce();
	// 		}
	// 	}
	// } 
	
	public void display( CAImageBuffer image )
	{
		image.update(this.getCurrentBuffer());

		for ( int i = 0 ; i != agents.size() ; i++ )
			image.setPixel(agents.get(i)._x, agents.get(i)._y, agents.get(i)._redValue, agents.get(i)._greenValue, agents.get(i)._blueValue);
	}
	
}
