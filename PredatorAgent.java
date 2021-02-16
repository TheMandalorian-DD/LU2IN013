
public class PredatorAgent extends Agent {

	boolean _predator;
	double p_pred = 0.001;
	// int energie;
	// int seuil = 1000;
	
	public PredatorAgent( int __x, int __y, World __w )
	{
		super(__x,__y,__w);
		

		// _redValue = 255;
		// _greenValue = 0;
		// _blueValue = 0;
		
		_predator = true;
	}

	public void reproduce(){
		if (Math.random() < p_pred){
			_world.add(new PredatorAgent(_x,_y,_world));
		}
	}
	
	public void step( )
	{
		// met a jour l'agent
		
		// A COMPLETER


		int cellColor[] = _world.getCellState(_x, _y);

		
		cellColor[redId] = 255;
		cellColor[greenId] = 240;
		cellColor[blueId] = 225;

		_world.setCellState(_x, _y, cellColor);

		int dist = Integer.MAX_VALUE;

		Agent close_prey = null;

		int r = 5;

		for ( int x2 = _x-r ; x2 <= _x+r ; x2++ ){

			for ( int y2 = _y-r ; y2 <= _y+r ; y2++ ){

				for(Agent j : _world.agents){

					if (j instanceof PreyAgent){

						if ((j._x==x2 && j._y==y2) && (dist > super.distance(j._x,j._y))){

							dist = super.distance(j._x,j._y);

							close_prey = j;
						}
					}
				}
			}
		}
		

		if (close_prey != null){

			int d = Integer.MAX_VALUE;
			int i = 0;

			while(i<5){

			}

		}else{

		if( Math.random() > 0.5 ){ // au hasard
			_orient = (_orient+1) %4;

		}else{
			_orient = (_orient-1+4) %4;
		}

		// met a jour: la position de l'agent (depend de l'orientation)
		 switch ( _orient ) 
		 {
         	case 0: // nord	
         		_y = ( _y - 1 + _world.getHeight() ) % _world.getHeight();
         		break;
         	case 1:	// est
         		_x = ( _x + 1 + _world.getWidth() ) % _world.getWidth();
 				break;
         	case 2:	// sud
         		_y = ( _y + 1 + _world.getHeight() ) % _world.getHeight();
 				break;
         	case 3:	// ouest
         		_x = ( _x - 1 + _world.getWidth() ) % _world.getWidth();
 				break;
		 }
	}
	
}
