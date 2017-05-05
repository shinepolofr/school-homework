package samllgame;

public class Bullet extends FlyingObject{
	private int speed=3;
	
	/**初始化数据*/
	public Bullet(int x,int y){
		this.x=x;
		this.y=y;
		this.image=GamePanel.bullet;
	}
	
	/**移动*/
	@Override
	public void step(){
		y-=speed;
	}
	
	/**越界处理*/
	@Override
	public boolean outOfBounds(){
		return y<-height;
	}
}
