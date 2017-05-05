package samllgame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;


public class GamePanel extends JPanel{
	public static final int WIDTH =400;//面板宽
	public static final int HEIGHT=654;//面板高
	
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	
	private Timer timer;
	private int intervel=1000/100;//时间间隔
	
	
	//图片
	public static BufferedImage background;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage bee;
	public static BufferedImage airplane;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	
	
	private Bullet[] bullets={};	//子弹数组
	
	private FlyingObject[] flyings = {}; // 敌机数组
	private Hero hero=new Hero();//英雄机
	//初始化图片
	static{
		try{
			background = ImageIO.read(GamePanel.class.getResource("background.jpg"));
			hero0 = ImageIO.read(GamePanel.class.getResource("hero0.png"));
			hero1 = ImageIO.read(GamePanel.class.getResource("hero1.png"));
			bullet = ImageIO.read(GamePanel.class.getResource("bullet.png"));
			bee = ImageIO.read(GamePanel.class.getResource("bee.png"));
			airplane = ImageIO.read(GamePanel.class.getResource("airplane.png"));
			start = ImageIO.read(GamePanel.class.getResource("start.png"));
			pause = ImageIO.read(GamePanel.class.getResource("pause.png"));
			gameover = ImageIO.read(GamePanel.class.getResource("gameover.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//画
	@Override
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		g.drawImage(background, 0, 295, null);
		painthero(g);
		paintBullet(g);
		paintFlyingObject(g);
		paintLife(g);
		paintState(g);
	}
	
	/** 画英雄机*/
	public void painthero(Graphics g){
		g.drawImage(hero0,hero.getX(),hero.getY(),null);
	}
	
	/**画子弹*/
	public void paintBullet(Graphics g){
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			g.drawImage(b.getImage(),b.getX()-b.getWidth(),b.getY(),null);
		}
	}
	
	/**画飞行物*/
	public void paintFlyingObject(Graphics g){
		for(int i=0;i<flyings.length;i++){
			FlyingObject f=flyings[i];
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
		}
	}
	/**画命*/
	public void paintLife(Graphics g) {
		int x = 10; // x坐标
		int y = 25; // y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 字体
		g.setColor(new Color(0xFF0000));
		g.setFont(font); // 设置字体
		g.drawString("LIFE:" + hero.getLife(), x, y); // 画命
	}
	
	/**游戏状态*/
	public void paintState(Graphics g){
		switch(state){
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	public static void main(String[] args){
		JFrame frm=new JFrame("FLY");
		GamePanel game=new GamePanel();//面板对象
		frm.add(game);
		frm.setSize(450, 640);
		frm.setAlwaysOnTop(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setIconImage(new ImageIcon("Images/icon.jpg").getImage());
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
		
		game.action(); //执行
	}
    public void action(){
    	MouseAdapter l = new MouseAdapter(){
    		@Override
    		public void mouseMoved(MouseEvent e) { // 鼠标移动
    			if (state == RUNNING) { // 运行状态下移动英雄机--随鼠标位置
    				int x = e.getX();
    				int y = e.getY();
    				hero.moveTo(x, y);
    			}
    		}
    		
    		@Override
			public void mouseEntered(MouseEvent e) { // 鼠标进入
				if (state == PAUSE) { // 暂停状态下运行
					state = RUNNING;
				}
			}
    		
    		@Override
			public void mouseExited(MouseEvent e) { // 鼠标退出
				if (state == RUNNING) { // 游戏未结束，则设置其为暂停
					state = PAUSE;
				}
			}
    		
    		@Override
			public void mouseClicked(MouseEvent e) { // 鼠标点击
				switch (state) {
				case START:
					state = RUNNING; // 启动状态下运行
					break;
				case GAME_OVER: // 游戏结束，清理现场
					flyings = new FlyingObject[0]; // 清空飞行物
					bullets = new Bullet[0]; // 清空子弹
					hero = new Hero(); // 重新创建英雄机
					state = START; // 状态设置为启动
					break;
				}
			}
    	};
    	this.addMouseListener(l); // 处理鼠标点击操作
		this.addMouseMotionListener(l);; // 处理鼠标滑动操作
    	
		timer =new Timer();//主流程控制
		timer.schedule(new TimerTask(){
			@Override
			public void run(){
				if(state==RUNNING){
					enterAction();//飞行物入场
					stepAction(); // 走一步
					shootAction(); // 英雄机射击
					bangAction(); // 子弹打飞行物
					outOfBoundsAction(); // 删除越界飞行物及子弹
					checkGameOverAction(); // 检查游戏结束
				}
				repaint(); // 重绘，调用paint()方法
			}
		}, intervel, intervel);
    	
    }
    int flyEnteredIndex = 0; // 飞行物入场计数
    
    public void enterAction(){
    	flyEnteredIndex++;
    	if (flyEnteredIndex % 40 == 0) { // 400毫秒生成一个飞行物--10*40
			FlyingObject obj = nextOne(); // 随机生成一个飞行物
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
    }
    
    /** 飞行物入场 */
    public static FlyingObject nextOne() {
		Random random = new Random();
		int type = random.nextInt(20); // [0,20)
		if (type < 4) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}
    
    /** 走一步 */
    public void stepAction() {
		for (int i = 0; i < flyings.length; i++) { // 飞行物走一步
			FlyingObject f = flyings[i];
			f.step();
		}
		for (int i = 0; i < bullets.length; i++) { // 子弹走一步
			Bullet b = bullets[i];
			b.step();
		}
		hero.step(); // 英雄机走一步
	}
    
    int shootIndex=0; //射击计数
    /**英雄机射击*/
    public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) { // 300毫秒发一颗
			Bullet[] bs = hero.shoot(); // 英雄打出子弹
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
					bs.length); // 追加数组
		}
    }
    
    /**子弹与飞行物碰撞检测*/
    public void bangAction(){
    	for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
			Bullet b = bullets[i];
			bang(b); // 子弹和飞行物之间的碰撞检查
		}
    }
    /** 子弹和飞行物之间的碰撞检查 */
	public void bang(Bullet bullet) {
		int index = -1; // 击中的飞行物索引
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];
			if (obj.shootBy(bullet)) { // 判断是否击中
				index = i; // 记录被击中的飞行物的索引
				break;
			}
		}
		if (index != -1) { // 有击中的飞行物
			FlyingObject one = flyings[index]; // 记录被击中的飞行物

			FlyingObject temp = flyings[index]; // 被击中的飞行物与最后一个飞行物交换
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;

			flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除最后一个飞行物(即被击中的)

				GameListener a = (GameListener) one;
				int type = a.getType(); // 获取奖励类型
				switch (type) {
				case GameListener.DOUBLE_FIRE:
					hero.addDoubleFire(); // 设置双倍火力
					break;
				case GameListener.LIFE:
					hero.addLife(); // 设置加命
					break;
				}
			}
		}
	/**删除越界飞行物*/
	public void outOfBoundsAction(){
		int index=0; //索引
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; //活着的飞行物
		for(int i=0;i<flyings.length;i++){
			FlyingObject f = flyings[i];
			if (!f.outOfBounds()) {
				flyingLives[index++] = f; // 不越界的留着
			}
		}
		flyings = Arrays.copyOf(flyingLives, index); // 将不越界的飞行物都留着
		
		index=0; //索引重置为0
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); // 将不越界的子弹留着
	}
	/**检查游戏是否结束*/
	public void checkGameOverAction(){
		if(isGameOver()==true){
			state=GAME_OVER; //改变状态
		}
	}
    public boolean isGameOver() {
		
		for (int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if (hero.hit(obj)) { // 检查英雄机与飞行物是否碰撞
				hero.subtractLife(); // 减命
				hero.setDoubleFire(0); // 双倍火力解除
				index = i; // 记录碰上的飞行物索引
			}
			if (index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t; // 碰上的与最后一个飞行物交换

				flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除碰上的飞行物
			}
		}
		return hero.getLife() <= 0;
	}
}
