import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class MyEcosystem_predprey extends CAtoolbox {


	public static void main(String[] args) {

		// initialisation generale
	    
		int dx = 20; //50
		int dy = 20; //50

		int nbPrey = 10; //40
		int nbPred = 10; //30
		
		int displayWidth = 400;  // 200
		int displayHeight = 400; // 200

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
		
		
		int delai = 1;//100; // -- delay before refreshing display -- program is hold during delay, even if no screen update was requested. USE WITH CARE. 
		int nombreDePasMaximum = Integer.MAX_VALUE;
		int it = 0;
		int displaySpeed = 1;//50; // from 1 to ...
		
		CAImageBuffer image = new CAImageBuffer(dx,dy);
	    ImageFrame imageFrame =	ImageFrame.makeFrame( "My Ecosystem", image, delai, displayWidth, displayHeight );

	    // création du fichier
//for (int f=1; f<=3; f++){
	    BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("question4_2"+".txt",true));
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

	
		
		while ( it != nombreDePasMaximum )
		{
			// 1 - display
			
			if ( it % displaySpeed == 0 )
				world.display(image);  

			// 2 - update

			// Reproduction des animaux

			// Agent agent = world.agents.get((int)(Math.random()*world.agents.size()));
			// if (agent instanceof PredatorAgent){
			// 	((PredatorAgent)agent).reproduce();
			// }else{
			// 	((PreyAgent)agent).reproduce();
			// }

			// Predateurs mangent les proies sur la meme case

			// ArrayList<Agent> agents_remove = new ArrayList<>();

			// for (Agent i : world.agents){

			// 	for (Agent j : world.agents) {

			// 		if (i instanceof PredatorAgent && j instanceof PreyAgent){

			// 			if (i._x == j._x && i._y == j._y){

			// 				agents_remove.add(j);

			// 				((PredatorAgent)i)._predator = false; //il n'a plus faim

			// 			}
			// 		}
			// 	}
			// }

			// Les proies fuient les predateurs

			// for(Agent i : world.agents){

			// 	 if (i instanceof PreyAgent){

			// 	 	int dist = Integer.MAX_VALUE;

			// 	 	Agent close_pred = null;

			// 	 	int r = 4;

			// 	 	for ( int x2 = i._x-r ; x2 <= i._x+r ; x2++ ){

			// 			for ( int y2 = i._y-r ; y2 <= i._y+r ; y2++ ){

			// 				for(Agent j : world.agents){

			// 					if (j instanceof PredatorAgent){

			// 						if ((j._x==x2 && j._y==y2) && (dist > i.distance(j._x,j._y))){

			// 							dist = i.distance(j._x,j._y);

			// 							close_pred = j;

			// 						}
			// 					}
			// 				}
			// 			}
			// 		}
			// 		if (close_pred != null){

			// 			if (close_pred._orient == 0){

			// 				i._orient = 2;
			// 			}
			// 			if (close_pred._orient == 1){

			// 				i._orient = 3;
			// 			}
			// 			if (close_pred._orient == 2){

			// 				i._orient = 0;
			// 			}
			// 			if (close_pred._orient == 3){

			// 				i._orient = 1;
			// 			}
			// 		}
			// 	}
			// }


				

			// Les predateur chassent les proies les plus proches

			// for(Agent i : world.agents){

			// 	if (i instanceof PredatorAgent){

			// 		int dist = Integer.MAX_VALUE;

			// 		Agent close_prey = null;

			// 		int r = 5;

			// 		for ( int x2 = i._x-r ; x2 <= i._x+r ; x2++ ){

			// 			for ( int y2 = i._y-r ; y2 <= i._y+r ; y2++ ){

			// 				for(Agent j : world.agents){

			// 					if (j instanceof PreyAgent){

			// 						if ((j._x==x2 && j._y==y2) && (dist > i.distance(j._x,j._y))){

			// 							dist = i.distance(j._x,j._y);

			// 							close_prey = j;
			// 						}
			// 					}
			// 				}
			// 			}
			// 		}
			// 		if (close_prey != null){
			// 			i._orient = close_prey._orient;
			// 		}
			// 	}
			// }

			// Les predateur meurent s'ils n'ont pas mangé à L itérations

			// if (cpt==l){ // au bout de L itération

			// 	cpt = 0;

			// 	for(Agent i : world.agents){

			// 		if (i instanceof PredatorAgent){

			// 			if (((PredatorAgent)i)._predator){ //si l'animal n'a toujours pas mangé

			// 				agents_remove.add(i);

			// 			} else {

			// 				((PredatorAgent)i)._predator = true; //il commence à avoir faim

			// 			}

			// 		} else {

			// 			if (((PreyAgent)i)._alive){

			// 				agents_remove.add(i);

			// 			} else {

			// 				((PreyAgent)i)._alive = true;

			// 			}

			// 		}
			// 	}
			// }
			
					
			//world.agents.removeAll(agents_remove);

			world.step();

			int nb[]= world.getNumbers(); // nb[0] prey nb[1] predator

			// On écrit dans le fichier

			try {
                out.write(it + " " + nb[0] + " " + nb[1] + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

			

			
			// 3 - iterate
			
			it++;

			//cpt++;

            if (nb[0]==0 || nb[1]==0 || it==500) break;
			
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
