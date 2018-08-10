package com.unical.sokoban.ai;

import com.unical.sokoban.logic.World;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class Solver {

	private World world;
	private int[][] sokobanMatrix;
	private static String encodingResource = "encodings/sokoban";
	private static Handler handler;

	public Solver(World world) {
		this.world = world;
		sokobanMatrix = world.getLogicMap();
	}

	public void solve() {

		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		InputProgram facts = new ASPInputProgram();

			try {
				ASPMapper.getInstance().registerClass(MoveBox.class);
			} catch (ObjectNotValidException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAnnotationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		for (int i = 0; i < world.boxes.size(); i++) {
			try {
				int x = (int) (world.boxes.get(i).getX() / 64);
				int y = (int) (world.boxes.get(i).getY() / 64);
				facts.addObjectInput(new BoxEmbasp(i, x, y));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < world.getRows(); i++) {

			for (int j = 0; j < world.getCols(); j++) {

				try {
					facts.addObjectInput(new Cell(i,j,sokobanMatrix[i][j]));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);
		Output o = handler.startSync();

		AnswerSets answers = (AnswerSets) o;
		
		if (answers.getAnswersets().size() == 0) {
			System.out.println("NO ANSWER SETS");
		}
		
		for (AnswerSet a : answers.getAnswersets()) {
			try {
				for (Object obj : a.getAtoms()) {
					if (obj instanceof MoveBox) {
						MoveBox movement = (MoveBox) obj;
						System.out.println(movement);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
