package samllgame;

public class Bullet extends FlyingObject{
	private int speed=3;
	
	/**��ʼ������*/
	public Bullet(int x,int y){
		this.x=x;
		this.y=y;
		this.image=GamePanel.bullet;
	}
	
	/**�ƶ�*/
	@Override
	public void step(){
		y-=speed;
	}
	
	/**Խ�紦��*/
	@Override
	public boolean outOfBounds(){
		return y<-height;
	}
}
