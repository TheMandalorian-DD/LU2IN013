
public class PreyAgent extends Agent {

	boolean _alive;
	double p_prey = 0.001;
	// int energie;
	
	public PreyAgent( int __x, int __y, World __w )
	{
		super(__x,__y,__w);

		// energie = 10;
		
		_redValue = 0;
		_greenValue = 0;
		_blueValue = 255;
		
		_alive = true;
	}

	public void reproduce(){
		if (Math.random() < p_prey){
			_world.add(new PreyAgent(_x,_y,_world));
		}
	}

	// public void setEnergie(int e){
	// 	energie=e;
	// }

	// public int getEnergie(){
	// 	return energie;
	//}
	
	public void step( )
	{
		// met a jour l'agent

		// this.energie--;
		
		// ... A COMPLETER

		// if (Math.random() < p_prey){
		// 	_world.add(new PreyAgent(_x,_y,_world));
		// }

		int cellColor[] = _world.getCellState(_x, _y);

		cellColor[redId]   = 205;
		cellColor[greenId] = 255;
		cellColor[blueId]  = 255;

		_world.setCellState(_x, _y, cellColor);

		if (Math.random() > 0.5) // au hasard
			_orient = (_orient + 1) % 4;
		else
			_orient = (_orient - 1 + 4) % 4;

		// met a jour: la position de l'agent (depend de l'orientation)
		switch (_orient) {
		case 0: // nord	
			_y = (_y - 1 + _world.getHeight()) % _world.getHeight();
			break;
		case 1: // est
			_x = (_x + 1 + _world.getWidth()) % _world.getWidth();
			break;
		case 2: // sud
			_y = (_y + 1 + _world.getHeight()) % _world.getHeight();
			break;
		case 3: // ouest
			_x = (_x - 1 + _world.getWidth()) % _world.getWidth();
			break;
		}
		
	}
	
}
