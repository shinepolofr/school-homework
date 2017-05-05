package samllgame;
import java.awt.image.BufferedImage;
public abstract class FlyingObject {
	protected int x;
	protected int y;
	protected int width;    
	protected int height;   
	protected BufferedImage image;
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public BufferedImage getImage(){
		return image;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public boolean shootBy(Bullet bullet){
		int x = bullet.x;  //子弹横坐标
		int y = bullet.y;  //子弹纵坐标
		return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
	}
	public abstract void step();
	public abstract boolean outOfBounds();
}
