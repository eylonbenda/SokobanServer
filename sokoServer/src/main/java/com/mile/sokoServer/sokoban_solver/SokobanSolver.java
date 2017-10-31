package com.mile.sokoServer.sokoban_solver;

import java.util.ArrayList;
import java.util.List;

import model.data.Level;
import model.object_data.Position;
import model.object_data.Square;
import model.object_data.SquareTarget;
import searchLib.Action;
import searchLib.BFS;
import searchLib.Searchable;
import searchLib.Searcher;
import searchLib.searchables.MoveBox;
import searchLib.searchables.SearchableBoxTarget;

public class SokobanSolver {

	Level level;

	public SokobanSolver(Level level) {

		this.level = level;
		level.addBoxesToArray();
		level.addTargets();
	}

	public List<Action> solve() {
		
		Level tempLvl = new Level(level);
		List<Action> actions = new ArrayList<>();
		ArrayList<Square> listBoxes = tempLvl.getListBox();
		ArrayList<SquareTarget> listTargets = tempLvl.getSt();

		for (int i=0;i<listBoxes.size();i++) {

			//Square box = i1.next();

			for (int j=0;j<listTargets.size();j++) {

				//SquareTarget target = i2.next();
				Searchable<Position> searchable = new SearchableBoxTarget(tempLvl, listBoxes.get(i).getPosition(),
						listTargets.get(j).getPosition());
				Searcher<Position> searcher = new BFS<Position>();

				MoveBox moveBox = new MoveBox(searchable);
				List<Action> list = moveBox.move(tempLvl, listBoxes.get(i).getPosition(), listTargets.get(j).getPosition());

				if (list != null) {

					listTargets.remove(j);
					
					
					for (Action action : list) {
						
						actions.add(action);
					}
					
					break;
				}
			}

		}
		
		
		
		return actions;
	}
}
