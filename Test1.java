给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。

找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充
class Solution {
    private int[] dx={1,0,-1,0};
    private int[] dy={0,1,0,-1};
    private int row;
    private int col;
    public void solve(char[][] board) {
        row=board.length;
        if(row==0) return;
        col=board[0].length;
        for(int i=0;i<row;i++){
            if(board[i][0]=='O'){
                dfs(board,i,0);
            }
            if(board[i][col-1]=='O'){
                dfs(board,i,col-1);
            }
        }
        for(int i=1;i<col-1;i++){
            if(board[0][i]=='O'){
                dfs(board,0,i);
            }
            if(board[row-1][i]=='O'){
                dfs(board,row-1,i);
            }
        }

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(board[i][j]=='O'){
                    board[i][j]='X';
                }
                if(board[i][j]=='*'){
                    board[i][j]='O';
                }   
            }
        }
    }
    private void dfs(char[][] board,int i,int j){
        if(i<0||j<0||i>=row||j>=col||board[i][j]!='O'){
            return;
        }
        board[i][j]='*';
        for(int k=0;k<4;k++){
            int x=i+dx[k];
            int y=j+dy[k];
            dfs(board,x,y);
        }
    }
}

给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null) return null;
        if(root==p||root==q){
            return root;
        }
        TreeNode left=lowestCommonAncestor(root.left,p,q);
        TreeNode right=lowestCommonAncestor(root.right,p,q);
        if(left!=null&&right!=null){
            return root;
        }
        if(left!=null) return left;
        if(right!=null) return right;
        return null;
    }
}

给定一个非空二叉树，返回其最大路径和。

本题中，路径被定义为一条从树中任意节点出发，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    private int max=Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        traversal(root);
        return max;
    }
    private int traversal(TreeNode root){
        if(root==null) return 0;
        int left=Math.max(0,traversal(root.left));
        int right=Math.max(0,traversal(root.right));
        max=Math.max(max,left+right+root.val);
        return root.val+Math.max(left,right);
    }
}

班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。

给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
class Solution {
    public int findCircleNum(int[][] M) {
        int n=M.length;
        boolean[] isVisited=new boolean[n];
        int res=0;
        for(int i=0;i<n;i++){
            if(!isVisited[i]){
                dfs(M,isVisited,i,n);
                res++;
            }
        }
        return res;
    }
    private void dfs(int[][] arr,boolean[] isVisited,int i,int n){
        for(int j=0;j<n;j++){
            if(arr[i][j]==1&&!isVisited[j]){
                isVisited[j]=true;
                dfs(arr,isVisited,j,n);
            }
        }
    }
}

你这个学期必须选修 numCourse 门课程，记为 0 到 numCourse-1 。

在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们：[0,1]

给定课程总量以及它们的先决条件，请你判断是否可能完成所有课程的学习？
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> grap=new ArrayList<>();
        int[] degrees=new int[numCourses];
        for(int i=0;i<numCourses;i++){
            grap.add(new ArrayList<>());
        }
        for(int[] i:prerequisites){
            grap.get(i[1]).add(i[0]);
            degrees[i[0]]++;
        }
        
        Queue<Integer> queue=new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(degrees[i]==0){
                queue.offer(i);
            }
        }
        
        while(!queue.isEmpty()){
            int i=queue.poll();
            numCourses--;
            for(int num:grap.get(i)){
                degrees[num]--;
                if(degrees[num]==0){
                    queue.offer(num);
                }
            }
        }
        return numCourses==0;
    }
}

现在你总共有 n 门课需要选，记为 0 到 n-1。

在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]

给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。

可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/course-schedule-ii
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Queue<Integer> queue=new LinkedList<>();
        List<List<Integer>> grap=new ArrayList<>();
        int[] indegrees=new int[numCourses];

        for(int i=0;i<numCourses;i++){
            grap.add(new ArrayList<>());
        }
        for(int[] pre:prerequisites){
            indegrees[pre[0]]++;
            grap.get(pre[1]).add(pre[0]);
        }
        for(int i=0;i<numCourses;i++){
            if(indegrees[i]==0){
                queue.offer(i);
            }
        }
        List<Integer> res=new ArrayList<>();
        while(!queue.isEmpty()){
            int i=queue.poll();
            numCourses--;
            res.add(i);
            for(int num:grap.get(i)){
                if(--indegrees[num]==0){
                    queue.offer(num);
                }
            }
        }

        if(numCourses==0){
            int[] arr=new int[res.size()];
            for(int i=0;i<res.size();i++){
                arr[i]=res.get(i);
            }
            return arr;
        }
        return new int[0];
    }
}

给定一个二叉树，找出其最小深度。

最小深度是从根节点到最近叶子节点的最短路径上的节点数量。

说明: 叶子节点是指没有子节点的节点。
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        Queue<TreeNode> queue=new LinkedList<>();
        queue.offer(root);
        int res=1;
        while(!queue.isEmpty()){
            int size=queue.size();
            while(size--!=0){
                TreeNode cur=queue.poll();
                if(cur.left==null&&cur.right==null){
                    return res;
                }
                if(cur.left!=null) queue.offer(cur.left);
                if(cur.right!=null) queue.offer(cur.right);
            }
            res++;
        }
        return 0;
    }
}

