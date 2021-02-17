import java.util.Iterator;
import java.util.LinkedList;


public class World {

	int _dx;
	int _dy;
	
	int Buffer0[][][];
	int Buffer1[][][];
	
	boolean buffering;
	boolean cloneBuffer; // if buffering, clone buffer after swith
	
	int activeIndex;
	
	//ArrayList<Agent> agents;
	LinkedList<PredatorAgent> predatorAgents;
    LinkedList<PreyAgent> preyAgents;
    LinkedList<PreyAgent> reproduirePreyAgents;
    LinkedList<PredatorAgent> reproduirePredatorsAgents;

	boolean[][] grass; 

	double p_grass = 0.05;
	
	public World ( int __dx , int __dy, boolean __buffering, boolean __cloneBuffer )
	{
		_dx = __dx;
		_dy = __dy;
		
		buffering = __buffering;
		cloneBuffer = __cloneBuffer;
		
		Buffer0 = new int[_dx][_dy][3];
		Buffer1 = new int[_dx][_dy][3];
		activeIndex = 0;
		
		//agents = new ArrayList<Agent>();
		predatorAgents = new LinkedList<>();
        preyAgents = new LinkedList<>();
        reproduirePreyAgents = new LinkedList<>();
        reproduirePredatorsAgents = new LinkedList<>();

        grass = new boolean[_dx][_dy];

        initGrass();
		
	    for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
    			Buffer0[x][y][0]=255;
    			Buffer0[x][y][1]=255;
    			Buffer0[x][y][2]=255;
    			Buffer1[x][y][0]=255;
    			Buffer1[x][y][1]=255;
    			Buffer1[x][y][2]=255;
    			//grass[x][y]=0.5 >= Math.random();
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

		if (agent instanceof PredatorAgent){

			predatorAgents.add((PredatorAgent) agent);

		}

		if (agent instanceof PreyAgent){

			preyAgents.add((PreyAgent) agent);

		}

    }

	public void initGrass(){

		for ( int x = 0 ; x != _dx ; x++ ){

	    	for ( int y = 0 ; y != _dy ; y++ ){

	    		grass[(int)x][(int)y]=(0.5 >= Math.random()); // grass

	    	}

	    }
	    		
	}

	public int[] getNumbers() {
        int[] nb = new int[2];
        preyAgents.forEach(p -> nb[0]++);
        predatorAgents.forEach(p -> nb[1]++);
        return nb;
    }
	
	public void stepWorld() // world THEN agents
	{

		// Predateurs mangent les proies sur la meme case

		Iterator<PredatorAgent> iterPredator = predatorAgents.iterator();

		while (iterPredator.hasNext()) {

            PredatorAgent i = iterPredator.next();

            if (!i.isAlive()){

            	iterPredator.remove();
            
            }

            Iterator<PreyAgent> iterPrey = preyAgents.iterator();

            while (iterPrey.hasNext()) {

                PreyAgent j = iterPrey.next();

                if (i._x == j._x && i._y == j._y) {

                    iterPrey.remove();

                    i.reset_mange();

                    continue;
                }
                if (i._x == j._x){

                    if (i._y == j._y+1) {

                        i.setDirection(2);
                        j.setDirection(2);
                    }

                    if (i._y == j._y-1){

                        i.setDirection(0);
                        j.setDirection(0);
                    }
                }

                if (i._y == j._y){

                    if (i._x == j._x+1){

                        i.setDirection(3);
                        j.setDirection(3);
                    }

                    if (i._x == j._x-1){

                        i.setDirection(1);
                        j.setDirection(1);
                    }
                }
            }
        }

  //       //L'herbe repousse ou pas

		for ( int x = 0 ; x != _dx ; x++ ){

			for ( int y = 0 ; y != _dy ; y++ ){

				if ( ! grass[(int)x][(int)y] ){

					grass[(int)x][(int)y]=(p_grass >= Math.random()); // grass

	    		}
	    	}
	    }

	}

	public void reproduce(Agent agent){

        if (agent instanceof PredatorAgent){

        	reproduirePredatorsAgents.add((PredatorAgent) agent);

        }

        if (agent instanceof PreyAgent){

        	reproduirePreyAgents.add((PreyAgent) agent);

        }
    }
	
	public void stepAgents() // world THEN agents
	{
		preyAgents.forEach(prey -> {

			synchronized (Buffer0) {

				prey.step();
            }
        });

        predatorAgents.forEach(predator -> {

        	synchronized (Buffer0) {

        		predator.step();

            }
        });

        predatorAgents.addAll(reproduirePredatorsAgents);

        preyAgents.addAll(reproduirePreyAgents);

        reproduirePreyAgents.clear();

        reproduirePredatorsAgents.clear();
    

		// On vérifie si les proies se trouvent sur de l'herbe

		Iterator<PreyAgent> iterPrey = preyAgents.iterator();

		while (iterPrey.hasNext()) {

			PreyAgent i = iterPrey.next();

        	if (!i.isAlive()){

        		iterPrey.remove();

        	}

        	if (grass[i._x][i._y]){

        		i.reset_mange();

        		grass[i._x][i._y] = false; 

            }
        }
		
	}
	
	public void display( CAImageBuffer image )
	{
		image.update(this.getCurrentBuffer());
		preyAgents.forEach(p -> image.setPixel(p._x, p._y, p._redValue, p._greenValue, p._blueValue));
        predatorAgents.forEach(p-> image.setPixel(p._x, p._y, p._redValue, p._greenValue, p._blueValue));

	// 	for ( int i = 0 ; i != agents.size() ; i++ )
	// 		image.setPixel(agents.get(i)._x, agents.get(i)._y, agents.get(i)._redValue, agents.get(i)._greenValue, agents.get(i)._blueValue);
	// }
    }
	
}
