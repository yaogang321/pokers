package poker;


	import org.junit.Test; 
	import java.util.*; 
	 
	public class Poker {
		//扑克牌比较
	    List<String> poker = new ArrayList<>();
	    String[] number = {"2","3","4","5","6","7","8","9","10","J","Q","K","Ace"};
	    int[] value = {2,3,4,5,6,7,8,9,10,11,12,13,14};
	    String[] denoted = {"C","D","H","S"};
	    HashMap<Integer,String> map = new HashMap<>();
	    public void pokerSolution(){
	        //生成一副牌
	        for (int i = 0; i < denoted.length; i++) {
	            for (int j = 0; j < number.length; j++) {
	                poker.add(denoted[i]+number[j]);
	            }
	        }
	        map.put(1,"high card");
	        map.put(2,"pair");
	        map.put(3,"two pairs");
	        map.put(4,"three kind");
	        map.put(5,"straight");
	        map.put(6,"flush");
	        map.put(7,"full house");
	        map.put(8,"four of kind");
	        map.put(9,"straight flush");
	 
	        //两人各获取五张牌
	        List<String> player1 = new ArrayList<>();
	        List<String> player2 = new ArrayList<>();
	        Random rd = new Random();
	        getPoker(poker, player1, rd);
	        getPoker(poker, player2, rd);
	 
	        //输出结果
	        System.out.print("Black:  ");
	        for (String s:player1
	             ) {
	            System.out.print(s+" ");
	        }
	        System.out.print("   ");
	        System.out.print("White:  ");
	        for (String s:player2
	        ) {
	            System.out.print(s+" ");
	        }
	        System.out.println();
	 
	        //开始比较 给出结果
	        compare(player1,player2);
	 
	    }
	 
	    private void compare(List<String> player1, List<String> player2) {
	        List<String> list1 = new ArrayList<>();
	        List<String> list2 = new ArrayList<>();
	        List<String> list3 = new ArrayList<>();
	        List<String> list4 = new ArrayList<>();
	        for (int i = 0; i < player1.size(); i++) {
	            list1.add(player1.get(i).substring(0,1));
	            list2.add(player1.get(i).substring(1));
	            list3.add(player2.get(i).substring(0,1));
	            list4.add(player2.get(i).substring(1));
	        }
	        int[] level1 = pokerLevel(list1,list2);
	        int[] level2 = pokerLevel(list3,list4);
	 
	        if(level1[0]>level2[0]){
	            System.out.println("Black win --"+map.get(level1[0]));
	        }else if(level1[0]==level2[0]){
	            if(level1[1]>level2[1]){
	                System.out.println("Black win --"+map.get(level1[0])+":"+number[level1[1]-2]);
	            }if(level1[1]==level2[1]){
	                for (int i = 1; i < level1.length-1; i++) {
	                    if(level1[1+i]==level2[1+i]){
	                        continue;
	                    }else if(level1[1+i]>level2[1+i]){
	                        System.out.println("Black win --"+map.get(level1[0])+":"+number[level1[1+i]-2]);
	                    }else{
	                        System.out.println("White win --"+map.get(level2[0])+":"+number[level2[1+i]-2]);
	                    }
	                }
	            }else{
	                System.out.println("White win --"+map.get(level2[0])+":"+number[level2[1]-2]);
	            }
	        }else{
	            System.out.println("White win --"+map.get(level2[0])+":"+number[level2[1]-2]);
	        }
	    }
	 
	    private void getPoker(List<String> poker, List<String> player1, Random rd) {
	        for (int i = 0; i < 5; i++) {
	            int index = rd.nextInt(poker.size());
	            if(!player1.contains(poker.get(index))){
	                player1.add(poker.get(index));
	            }else{
	                i--;
	            }
	        }
	    }
	 
	    private int[] pokerLevel(List<String> list1,List<String> list2){
	        List<Integer> list2Value = new ArrayList();
	        for (int i = 0; i < list2.size(); i++) {
	            for (int j = 0; j < number.length; j++) {
	                if(number[j].equals(list2.get(i))){
	                    list2Value.add(value[j]);
	                    break;
	                }
	            }
	        }
	 
	        Collections.sort(list2Value);
	 
	        // 同花及同花顺
	        String pokerColor = list1.get(0);
	        boolean flag = true; //判断花色一致
	        for (int i = 1; i < list1.size(); i++) {
	            if(!pokerColor.equals(list1.get(i))){
	                flag = false;
	            }
	        }
	        if(flag){
	            if(list2Value.get(list2.size()-1)-list2Value.get(0)==4){
	                return new int[]{9,list2Value.get(4)};
	            }else{
	                return new int[]{6,list2Value.get(4),list2Value.get(3),list2Value.get(2),list2Value.get(1),list2Value.get(0)};
	            }
	        }
	 
	        //铁支
	        if(list2Value.get(4)==list2Value.get(1)||list2Value.get(3)==list2Value.get(0)){
	            return list2Value.get(4)==list2Value.get(1)?new int[]{8,list2Value.get(4),list2Value.get(0)}:new int[]{8,list2Value.get(3),list2Value.get(4)};
	        }
	 
	        //葫芦
	        if(list2Value.get(0)==list2Value.get(2)&&list2Value.get(3)==list2Value.get(4)||
	                list2Value.get(0)==list2Value.get(1)&&list2Value.get(2)==list2Value.get(4)){
	            return list2Value.get(0)==list2Value.get(2)&&list2Value.get(3)==list2Value.get(4)?new int[]{7,list2Value.get(2),list2Value.get(3)}:new int[]{7,list2Value.get(4),list2Value.get(1)};
	        }
	 
	        //顺子
	        if(list2Value.get(list2.size()-1)-list2Value.get(0)==4){
	            return new int[]{5,list2Value.get(4)};
	        }
	 
	        //三条
	        boolean d1 = list2Value.get(0)==list2Value.get(2);
	        boolean d2 = list2Value.get(1)==list2Value.get(3);
	        boolean d3 = list2Value.get(2)==list2Value.get(4);
	        if(d1||d2||d3){
	            if(d1){
	                return new int[]{4,list2Value.get(2)};
	            }else if(d2){
	                return new int[]{4,list2Value.get(3)};
	            }else{
	                return new int[]{4,list2Value.get(4)};
	            }
	        }
	 
	        //两对
	        boolean f1 = list2Value.get(0)==list2Value.get(1)&&list2Value.get(2)==list2Value.get(3);
	        boolean f2 = list2Value.get(0)==list2Value.get(1)&&list2Value.get(3)==list2Value.get(4);
	        boolean f3 = list2Value.get(1)==list2Value.get(2)&&list2Value.get(3)==list2Value.get(4);
	        if(f1||f2||f3){
	            if(f1){
	                return new int[]{3,list2Value.get(3),list2Value.get(1),list2Value.get(4)};
	            }
	            if(f2){
	                return new int[]{3,list2Value.get(4),list2Value.get(1),list2Value.get(2)};
	            }
	            if(f3){
	                return new int[]{3,list2Value.get(4),list2Value.get(1),list2Value.get(0)};
	            }
	        }
	 
	        //对子
	        boolean g1 = list2Value.get(0)==list2Value.get(1);
	        boolean g2 = list2Value.get(1)==list2Value.get(2);
	        boolean g3 = list2Value.get(2)==list2Value.get(3);
	        boolean g4 = list2Value.get(3)==list2Value.get(4);
	        if(g1||g2||g3||g4){
	            if(g1){
	                return new int[]{2,list2Value.get(1),list2Value.get(4),list2Value.get(3),list2Value.get(2)};
	            }
	            if(g2){
	                return new int[]{2,list2Value.get(2),list2Value.get(4),list2Value.get(3),list2Value.get(0)};
	            }
	            if(g3){
	                return new int[]{2,list2Value.get(3),list2Value.get(4),list2Value.get(1),list2Value.get(0)};
	            }
	            if(g4){
	                return new int[]{2,list2Value.get(4),list2Value.get(2),list2Value.get(1),list2Value.get(0)};
	            }
	        }
	        else{
	            return new int[]{1,list2Value.get(4),list2Value.get(3),list2Value.get(2),list2Value.get(1),list2Value.get(0)};
	        }
	        return new int[]{};
	    }
	 
	    @Test
	    public void pokerSolutionTest1(){
	        pokerSolution();
	 
	        List<String> player1 = new ArrayList<>();
	        List<String> player2 = new ArrayList<>();
	        player1.add("2H");
	        player1.add("3D");
	        player1.add("5S");
	        player1.add("9C");
	        player1.add("KD");
	 
	        player2.add("2S");
	        player2.add("8S");
	        player2.add("AS");
	        player2.add("QS");
	        player2.add("3S");
	 
	        compare(player1,player2);
	    }
	    
	    @Test
	    public void pokerSolutionTest2(){
	        pokerSolution();
	 
	        List<String> player1 = new ArrayList<>();
	        List<String> player2 = new ArrayList<>();
	        player1.add("2H");
	        player1.add("3D");
	        player1.add("5S");
	        player1.add("9C");
	        player1.add("KD");
	 
	        player2.add("2C");
	        player2.add("3H");
	        player2.add("4S");
	        player2.add("8C");
	        player2.add("AH");
	 
	        compare(player1,player2);
	    }
	    
	    @Test
	    public void pokerSolutionTest3(){
	        pokerSolution();
	 
	        List<String> player1 = new ArrayList<>();
	        List<String> player2 = new ArrayList<>();
	        player1.add("2H");
	        player1.add("4S");
	        player1.add("4C");
	        player1.add("2D");
	        player1.add("4H");
	 
	        player2.add("2S");
	        player2.add("8S");
	        player2.add("AS");
	        player2.add("QS");
	        player2.add("3S");
	 
	        compare(player1,player2);
	    }
}