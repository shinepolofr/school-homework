package samllgame;

import java.util.Random;



/**
 * 敌飞机: 是飞行物，也是敌人
 */
public class Airplane extends FlyingObject implements GameListener {
	private int speed = 3;  //移动步骤
	
	/** 初始化数据 */
	public Airplane(){
		this.image = GamePanel.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;          
		Random rand = new Random();
		x = rand.nextInt(GamePanel.WIDTH - width);
	}

	/** //越界处理 */
	@Override
	public 	boolean outOfBounds() {   
		return y>GamePanel.HEIGHT;
	}

	/** 移动 */
	@Override
	public void step() {   
		y += speed;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
