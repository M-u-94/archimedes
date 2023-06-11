package la.practice.algorithm.tree;

/**
 * 二叉树写入/读取 文件
 * @author la
 */
public class BinaryTreeIO {

    int maxLevel = 2;
    int curLevel = 0;



    void createTree(TreeNode root,TreeNode left,TreeNode right){

        if(++curLevel > maxLevel){
            curLevel = 0;
            return;
        }


        root.left = left;
          left.value = "l";

          root.right = right;
          right.value = "r";

         createTree(left,new TreeNode(),new TreeNode());
         createTree(right,new TreeNode(),new TreeNode());



    }




    /**
     * 树结构
     */
    class TreeNode{
        String value;
        TreeNode left;
        TreeNode right;




    }

}
