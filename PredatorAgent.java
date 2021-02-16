
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

		if( Math.random() > 0.5 ){ // au hasard
			_orient = (_orient+1) %4;
		}else{
			_orient = (_orient-1+4) %4;
		}

		for(Agent a : _worl.agents){

			if (a instanceof PreyAgent){

				if (_y-1==a._y && _x==a._x){ // nord

					_orient=0;
				}
				if (_y+1==a._y && _x==a._x){ // sud

					_orient=2;
				}
				if (_y==a._y && _x+1==a._x){ // est 

					_orient=1;
				}
				if (_y-1==a._y && _x-1==a._x){ // ouest

					_orient=3;
				}
			}	
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
