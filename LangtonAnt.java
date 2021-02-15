
public class LangtonAnt extends Agent {

 	public LangtonAnt( int __x, int __y, World __w )
	{
		super(__x,__y,__w);
	}
	
	public void step( )
	{
		// met a jour: (1) la couleur du sol (2) l'orientation de l'agent
		
		int cellColor[] = _world.getCellState(_x, _y);
		
		// !!!!!!!!!! A COMPLETER !!!!!!!!!!
		
		cellColor[redId] = (int)(Math.random()*256.0);
			cellColor[greenId] = (int)(Math.random()*255.0);
			cellColor[blueId]  = (int)(Math.random()*255.0);
			_orient = (int)(Math.random()*4.0);

		_world.setCellState(_x, _y, cellColor);
		
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






