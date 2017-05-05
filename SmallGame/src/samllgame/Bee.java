package samllgame;

import java.util.Random;

/** �۷� */
public class Bee extends FlyingObject implements GameListener{
	private int xSpeed = 1;   //x�����ƶ��ٶ�
	private int ySpeed = 2;   //y�����ƶ��ٶ�
	private int awardType;    //��������
	
	/** ��ʼ������ */
	public Bee(){
		this.image = GamePanel.bee;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt(GamePanel.WIDTH - width);
		awardType = rand.nextInt(2);   //��ʼ��ʱ������
	}
	
	/** ��ý������� */
	public int getType(){
		return awardType;
	}

	/** Խ�紦�� */
	@Override
	public boolean outOfBounds() {
		return y>GamePanel.HEIGHT;
	}

	/** �ƶ�����б�ŷ� */
	@Override
	public void step() {      
		x += xSpeed;
		y += ySpeed;
		if(x > GamePanel.WIDTH-width){  
			xSpeed = -1;
		}
		if(x < 0){
			xSpeed = 1;
		}
	}

	
}
