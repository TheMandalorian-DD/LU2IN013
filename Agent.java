
public abstract class Agent {

	World _world;
	
	static int redId   = 0;
	static int greenId = 1;
	static int blueId  = 2;
	
	int 	_x;
	int 	_y;
	int 	_orient;
	int 	_etat;
	
	int 	_redValue;
	int 	_greenValue;
	int 	_blueValue;
	
	public Agent( int __x, int __y, World __w )
	{
		_x = __x;
		_y = __y;
		_world = __w;
		
		_redValue = 255;
		_greenValue = 0;
		_blueValue = 0;
		
		_orient = 0;
	}

	public int distance(int x, int y){ //Retourne la distance entre deux agents
		return Math.abs(_x-x)+Math.abs(_y-y);
	}
	
	abstract public void step( );

	
}
