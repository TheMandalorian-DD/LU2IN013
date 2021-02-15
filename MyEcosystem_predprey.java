import java.io.*;
import java.util.ArrayList;

public class MyEcosystem_predprey extends CAtoolbox {


	public static void main(String[] args) {

		// initialisation generale
	    
		int dx = 10;
		int dy = 10;

		int nbPrey =1;
		int nbPred = 1;
		
		int displayWidth = 200;  // 200
		int displayHeight = 200; // 200

		// pick dimension for display
		if ( displayWidth < 200 )
			displayWidth = 200;
		else
			if ( displayWidth > 600 )
				displayWidth = 600;
			else
				if ( displayWidth < 300 )
					displayWidth = displayWidth * 2; 
		if ( displayHeight < 200 )
			displayHeight = 200;
		else
			if ( displayHeight > 600 )
				displayHeight = 600;
			else
				if ( displayHeight < 300 )
					displayHeight = displayHeight * 2; 
		
		
		int delai = 200;//100; // -- delay before refreshing display -- program is hold during delay, even if no screen update was requested. USE WITH CARE. 
		int nombreDePasMaximum = Integer.MAX_VALUE;
		int it = 0;
		int displaySpeed = 1;//50; // from 1 to ...
		
		CAImageBuffer image = new CAImageBuffer(dx,dy);
	    ImageFrame imageFrame =	ImageFrame.makeFrame( "My Ecosystem", image, delai, displayWidth, displayHeight );

	    // création du fichier
//for (int f=1; f<=3; f++){
	    BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("courbe_"+3+".txt",true));
            out.write(0+" "+nbPrey+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

	    // initialise l'ecosysteme
	    
		World world = new World(dx,dy,true,true);
		
		for ( int i = 0 ; i != nbPrey ; i++ )
			world.add(new PreyAgent((int)(Math.random()*dx),(int)(Math.random()*dy),world));
		for ( int i = 0 ; i != nbPred ; i++ )
			world.add(new PredatorAgent((int)(Math.random()*dx),(int)(Math.random()*dy),world));


		
	    // mise a jour de l'etat du monde

	    int l = 25; //itération famine
	    int cpt = 0;
		
		while ( it != nombreDePasMaximum )
		{

			nbPred=0;
			nbPrey=0;
			// 1 - display
			
			if ( it % displaySpeed == 0 )
				world.display(image); 

			// 2 - update

			// Reproduction des animaux

			Agent agent = world.agents.get((int)(Math.random()*world.agents.size()));
			if (agent instanceof PredatorAgent){
				((PredatorAgent)agent).reproduce();
			}else{
				((PreyAgent)agent).reproduce();
			}

			// Predateurs mangent les proies sur la meme case

			ArrayList<Agent> agents_remove = new ArrayList<>();

			for (Agent i : world.agents){

				for (Agent j : world.agents) {

					if (i instanceof PredatorAgent && j instanceof PredatorAgent) {

						continue;
					}

					if (i instanceof PredatorAgent && j instanceof PreyAgent){

						if (i._x == j._x && i._y == j._y){

							agents_remove.add(j);

							((PredatorAgent)i)._predator = false //il n'a plus faim

						}

					// Les predateurs poursuit les proies

						int r = 5;
					
						for ( int x2 = i._x-r ; x2 <= i._x+r ; x2++ ){

							for ( int y2 = i._y-r ; y2 <= i._y+r ; y2++ ){

								if (x2 < 0 || x2 >= dx || y2 < 0 || y2 >= dy) continue;

								if (j._x==x2 && j._y==y2){

									i._orient=j._orient;
								}
							}
						}
					}
				}
			}

			// Les predateur meurent s'ils n'ont pas mangé à L itérations

			if (cpt==l){ // au bout de L itération

				cpt = 0;

				for(Agent i : world.agents){

					if (i instanceof PredatorAgent){

						if (((PredatorAgenti)i)._predator){ //si l'animal n'a toujours pas mangé

							agents_remove.add(i);

						} else {

							((PredatorAgenti)i)._predator = true; //il commence à avoir faim

						}
					}
				}
			}
			
					
			world.agents.removeAll(agents_remove);

			

			

			// Les predateur meurent s'ils n'ont pas mangé à L itérations
					


			// if (((PredatorAgent)i)._predator && l==20){
						// 	agents_remove.add(i);
						// 	continue;
						// }

			world.step();

			for(Agent a : world.agents){
				if (a instanceof PredatorAgent){
					nbPred++;
				}
				else{
					nbPrey++;
				}
			}

			
			// 3 - iterate
			
			it++;

			cpt++;

			// On écrit dans le fichier

			 try {
                out.write(it+" "+nbPrey+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (nbPrey==0 || nbPred==0) break;
			
			try {
				Thread.sleep(delai);
			} catch (InterruptedException e) 
			{}


		}
		try {
            	out.close();
            } catch (IOException e) {
            e.printStackTrace();}
		
	}

}