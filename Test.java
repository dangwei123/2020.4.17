给一非空的单词列表，返回前 k 个出现次数最多的单词。

返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String,Integer> map=new HashMap<>();
        for(String s:words){
            map.put(s,map.getOrDefault(s,0)+1);
        }
        PriorityQueue<String> q=new PriorityQueue<>(
            new Comparator<String>(){
                public int compare(String a,String b){
                    if(map.get(a)!=map.get(b)){
                        return map.get(a)-map.get(b);
                    }else {
                        return b.compareTo(a);
                    }
                }
            }
        );
        for(Map.Entry<String,Integer> m:map.entrySet()){
            q.offer(m.getKey());
            if(q.size()>k){
                q.poll();
            }
        }
        LinkedList<String> res=new LinkedList<>();
        while(!q.isEmpty()){
            res.addFirst(q.poll());
        }
        return res;
    }
}

火车进站
public class Main{
    private static void back(int[] arr,int n,int index,
                             List<int[]> res,int[] tmp,boolean[] visit){
        if(index==n){
            res.add(tmp.clone());
            return;
        }
        for(int i=0;i<n;i++){
            if(visit[i]){
                continue;
            }
            tmp[index]=arr[i];
            visit[i]=true;
            back(arr,n,index+1,res,tmp,visit);
            visit[i]=false;
        }
    }

    private static boolean isValid(int[] target,int[] arr){
        Stack<Integer> stack=new Stack<>();
        int j=0;
        for(int i=0;i<target.length;i++){
            stack.push(target[i]);
            while(j<arr.length&&!stack.isEmpty()&&stack.peek()==arr[j]){
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
            int n=sc.nextInt();
            int[] arr=new int[n];
            for(int i=0;i<n;i++){
                arr[i]=sc.nextInt();
            }
            List<int[]> res=new ArrayList<>();
            back(arr,n,0,res,new int[n],new boolean[n]);
            for (int[] re : res) {
                if (isValid(arr, re)) {
                    for (int j = 0; j < n; j++) {
                        System.out.print(re[j] + " ");
                    }
                    System.out.println();
                }
            }
        }
    }
}

单词接龙
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> words=new HashSet<>(wordList);
        if(!words.contains(endWord)){
            return 0;
        }
        
        Queue<String> up=new LinkedList<>();
        Queue<String> down=new LinkedList<>();
        Set<String> visit1=new HashSet<>();
        Set<String> visit2=new HashSet<>();
        
        up.offer(beginWord);
        down.offer(endWord);
        visit1.add(beginWord);
        visit2.add(endWord);
        
        int res=1;
        while(!up.isEmpty()&&!down.isEmpty()){
            if(up.size()>down.size()){
                Queue<String> tmp=up;
                up=down;
                down=tmp;
                
                Set<String> v=visit1;
                visit1=visit2;
                visit2=v;
            }
            int size=up.size();
            res++;
            while(size--!=0){
                String str=up.poll();
                char[] chars=str.toCharArray();
                for(int i=0;i<str.length();i++){
                    char ch=chars[i];
                    for(char c='a';c<='z';c++){
                        chars[i]=c;
                        String t=new String(chars);
                        chars[i]=ch;
                        if(visit1.contains(t)){
                            continue;
                        }
                        
                        if(words.contains(t)){
                            up.offer(t);
                            visit1.add(t);
                            
                            if(visit2.contains(t)){
                                return res;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }
}