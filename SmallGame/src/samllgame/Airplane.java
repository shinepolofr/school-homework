package samllgame;

import java.util.Random;



/**
 * �зɻ�: �Ƿ����Ҳ�ǵ���
 */
public class Airplane extends FlyingObject implements GameListener {
	private int speed = 3;  //�ƶ�����
	
	/** ��ʼ������ */
	public Airplane(){
		this.image = GamePanel.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;          
		Random rand = new Random();
		x = rand.nextInt(GamePanel.WIDTH - width);
	}

	/** //Խ�紦�� */
	@Override
	public 	boolean outOfBounds() {   
		return y>GamePanel.HEIGHT;
	}

	/** �ƶ� */
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
