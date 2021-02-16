import java.util.Iterator;
import java.util.LinkedList;

public class PreyAgent extends Agent {

	static double p_reproduce = 0.04;
    static int delai_de_famine = 30;
	boolean _alive; 
	int it_non_mange;

	
	public PreyAgent( int __x, int __y, World __w )
	{
		super(__x,__y,__w);
		
		_redValue = 0;
		_greenValue = 0;
		_blueValue = 255;
		
		_alive = true;
	}

	 public void reset_mange() {
        it_non_mange = 0;
    }

    public boolean isAlive() {
        return _alive;
    }

	public void reproduce(){
		if (Math.random() < p_reproduce){
			_world.reproduce(new PreyAgent(_x,_y,_world));
		}
	}
	
	public void step( )
	{
		// met a jour l'agent
		
		// ... A COMPLETER

		// On vérifie si la proie n'a pas mangé après delai_de_famine itérations

		it_non_mange++;
        if (it_non_mange > delai_de_famine){
            _alive = false;
            return;
        }

		// Reproduction

		this.reproduce();



		int cellColor[] = _world.getCellState(_x, _y);

		cellColor[redId]   = 205;
		cellColor[greenId] = 255;
		cellColor[blueId]  = 255;

		_world.setCellState(_x, _y, cellColor);

		if (Math.random() > 0.5) // au hasard
			_orient = (_orient + 1) % 4;
		else
			_orient = (_orient - 1 + 4) % 4;

		// Iterator<PredatorAgent> iterPredator = _world.predatorAgents.iterator();

  //       while (iterPredator.hasNext()) {

  //       	Agent a = iterPredator.next();

		// 		if (_y-1==a._y && _x==a._x){ // si nord

		// 			_orient=2; //alors sud
		// 		}
		// 		if (_y+1==a._y && _x==a._x){ // si sud

		// 			_orient=0; // alors nord
		// 		}
		// 		if (_y==a._y && _x+1==a._x){ // si est 

		// 			_orient=3; // alors ouest
		// 		}
		// 		if (_y-1==a._y && _x-1==a._x){ // si ouest

		// 			_orient=1; // alors est
		// 		}
				
		// }

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
