import java.io.*;
import java.util.*;

/**
 COMPSCI 105 S2 C, 2012
 Assignment Three Question 1

Author: Ksenia Kovaleva
UPI: kkov003
Date: 16th October 2012

part 1) Complete
part 2.1) Working
part 2.3) Working
Part 3 Working
Part 4 working


**/

public class TextZip {

  /**
 * This method generates the huffman tree for the text: "abracadabra!"
 *
 * @return the root of the huffman tree
 */
 public static TreeNode<CharFreq> abracadbraTree() {
  TreeNode<CharFreq> n0 = new TreeNode<CharFreq>(new CharFreq('!', 1));
  TreeNode<CharFreq> n1 = new TreeNode<CharFreq>(new CharFreq('c', 1));
  TreeNode<CharFreq> n2 = new TreeNode<CharFreq>(new CharFreq('\u0000', 2), n0, n1);
  TreeNode<CharFreq> n3 = new TreeNode<CharFreq>(new CharFreq('r', 2));
  TreeNode<CharFreq> n4 = new TreeNode<CharFreq>(new CharFreq('\u0000', 4), n3, n2);
  TreeNode<CharFreq> n5 = new TreeNode<CharFreq>(new CharFreq('d', 1));
  TreeNode<CharFreq> n6 = new TreeNode<CharFreq>(new CharFreq('b', 2));
  TreeNode<CharFreq> n7 = new TreeNode<CharFreq>(new CharFreq('\u0000', 3), n5, n6);
  TreeNode<CharFreq> n8 = new TreeNode<CharFreq>(new CharFreq('\u0000', '7'), n7, n4);
  TreeNode<CharFreq> n9 = new TreeNode<CharFreq>(new CharFreq('a', 5));
  TreeNode<CharFreq> n10 = new TreeNode<CharFreq>(new CharFreq('\u0000', 12), n9, n8);
  return n10;
 }

  /**
 * This method decompresses a huffman compressed text file.  The compressed
 * file must be read one bit at a time using the supplied BitReader, and
 * then by traversing the supplied huffman tree, each sequence of compressed
 * bits should be converted to their corresponding characters.  The
 * decompressed characters should be written to the FileWriter
 *
 * @param  br:      the BitReader which reads one bit at a time from the
 *                  compressed file
 *         huffman: the huffman tree that was used for compression, and
 *                  hence should be used for decompression
 *         fw:      a FileWriter for storing the decompressed text file
 */
 public static void decompress(BitReader br, TreeNode<CharFreq> huffman, FileWriter fw) throws Exception {
   TreeNode<CharFreq> currentNode = huffman; //current node starts as root
   
   
   
   while(br.hasNext()){ // while bitReader hasNext
     boolean bitIsOne = br.next(); //read a bit true if =1
     //System.out.println("Bit is "+ bitIsOne);
     
     if(bitIsOne){ // 1 take right branch
       //System.out.println(" Equals 1 = take right branch");
       currentNode = currentNode.getRight();
       //System.out.println(currentNode);
     }
     if(!bitIsOne){ // 0 take left branch
       //System.out.println("Equals 0 = take Left branch");
       currentNode = currentNode.getLeft();
       //System.out.println(currentNode);
     }
     if(currentNode.isLeaf()){ // node is a leaf
       //System.out.println("Node is a leaf");
       
      
         char leafChar = (currentNode.getItem()).getChar(); // read char in leaf node
         //System.out.println(leafChar);
         fw.write(leafChar); //write output to txt file
         currentNode = huffman; //reset current node to root
         
       
       
       
     }// end if
   } // end while
   
  
   
 } // end decompress method

  /**
 * This method traverses the supplied huffman tree and prints out the
 * codes associated with each character
 *
 * @param  t:    the root of the huffman tree to be traversed
 *         code: a String used to build the code for each character as
 *               the tree is traversed recursively
 */
 public static void traverse(TreeNode<CharFreq> t, String code) {
   
  
 TreeNode<CharFreq> currentNode = t;
  //Part 2.3
   if (currentNode != null) {  
     if(currentNode.isLeaf()){
       System.out.println(currentNode.getItem().getChar() + " : " + code);
       code = ""; //clears code
       currentNode = t;
       return;
       
     }
      traverse(currentNode.getLeft(),(code+0));
      
      traverse(currentNode.getRight(),(code+1)); // does inorder traversal L subtrees then Right
  
 }
 }

 /**
 * This method traverses the supplied huffman tree and stores the codes
 * associated with each character in the supplied array.
 *
 * @param  t:     the root of the huffman tree to be traversed
 *         code:  a String used to build the code for each character as
 *                the tree is traversed recursively
 *         codes: an array to store the code for each character.  The indexes
 *                of this array range from 0 to 127 and represent the ASCII
 *                value of the characters.
 */
 public static void traverse(TreeNode<CharFreq> t, String code, String[] codes) {
   
   //System.out.println("Second traverse method");
   TreeNode<CharFreq> currentNode = t;
   //Part 3
   if (currentNode != null) {  
     if(currentNode.isLeaf()){
       //System.out.println(currentNode.getItem().getChar() + " : " + code);
       // store code in corresponding array index
       int charIndex = currentNode.getItem().getChar(); 
 // char is the index of array
       //System.out.print("charIndex: " +charIndex);
       codes[charIndex] = code;// stores corresponding code
       //System.out.println(" code: " + code);
       code = ""; //clears code
       currentNode = t;
       
       //testing how much of the array is filled
       /*int counter = 0;
       for( int i = 0; i < codes.length; i++){
         if(codes[i] != null){
           counter++;
           System.out.println("filled portion of array: " + counter);
         }
       }*/
       
       return;
       
     }
     traverse(currentNode.getLeft(),(code+0),codes);
     
     traverse(currentNode.getRight(),(code+1),codes); // does inorder traversal L subtrees then Right
     
   }
   
 }

  /**
 * This method removes the TreeNode, from an ArrayList of TreeNodes,  which
 * contains the smallest item.  The items stored in each TreeNode must
 * implement the Comparable interface.
 * The ArrayList must contain at least one element.
 *
 * @param  a: an ArrayList containing TreeNode objects
 *
 * @return the TreeNode in the ArrayList which contains the smallest item.
 *         This TreeNode is removed from the ArrayList.
 */
 public static TreeNode<CharFreq> removeMin(ArrayList<TreeNode<CharFreq>> a) {
  int minIndex = 0;
  for (int i = 0; i < a.size(); i++) {
   TreeNode<CharFreq> ti = a.get(i);
   TreeNode<CharFreq> tmin = a.get(minIndex);
   if ((ti.getItem()).compareTo(tmin.getItem()) < 0)
    minIndex = i;
  }
  TreeNode<CharFreq> n = a.remove(minIndex);
  return n;
 }

  /**
 * This method counts the frequencies of each character in the supplied
 * FileReader, and produces an output text file which lists (on each line)
 * each character followed by the frequency count of that character.  This
 * method also returns an ArrayList which contains TreeNodes.  The item stored
 * in each TreeNode in the returned ArrayList is a CharFreq object, which
 * stores a character and its corresponding frequency
 *
 * @param  fr: the FileReader for which the character frequencies are being
 *             counted
 *         pw: the PrintWriter which is used to produce the output text file
 *             listing the character frequencies
 *
 * @return the ArrayList containing TreeNodes.  The item stored in each
 *         TreeNode is a CharFreq object.
 */
 public static ArrayList<TreeNode<CharFreq>> countFrequencies(FileReader fr, PrintWriter pw) throws Exception {
   
   int [] asciiArray = new int [128];//creates int Array for ascii codes where index is ASCII code
   ArrayList<TreeNode<CharFreq>> asciiFreq = new ArrayList<TreeNode<CharFreq>>();
   
   
   int index = fr.read(); 
   while(index != -1){
     asciiArray[index]++;
     
     index = fr.read();
   } // end while loop
   
   for(int i=0; i< asciiArray.length; i++){
     int frequency = asciiArray[i];
     if(frequency != 0){ // if char has a frequency
       pw.write(i); // write to file index as ASCII char
       pw.print(" "); // space
       pw.println(frequency);// write to file the frequency of Ascii Character
       
       asciiFreq.add(new TreeNode<CharFreq>(new CharFreq((char)i,frequency)));
       
     }
   }
   for(int i= 0; i< asciiFreq.size();i++){ // testing arrayList created properly
     System.out.println("ArrayList: index "+ i+" "+ asciiFreq.get(i));
   }
   return asciiFreq;
 }

  /**
 * This method builds a huffman tree from the supplied ArrayList of TreeNodes.
 * Initially, the items in each TreeNode in the ArrayList store a CharFreq object.
 * As the tree is built, the smallest two items in the ArrayList are removed,
 * merged to form a tree with a CharFreq object storing the sum of the frequencies
 * as the root, and the two original CharFreq objects as the children.  The right
 * child must be the second of the two elements removed from the ArrayList (where
 * the ArrayList is scanned from left to right when the minimum element is found).
 * When the ArrayList contains just one element, this will be the root of the
 * completed huffman tree.
 *
 * @param  trees: the ArrayList containing the TreeNodes used in the algorithm
 *                for generating the huffman tree
 *
 * @return the TreeNode referring to the root of the completed huffman tree
 */
 public static TreeNode<CharFreq> buildTree(ArrayList<TreeNode<CharFreq>> trees) throws IOException {
   TreeNode<CharFreq> root = null;
   
   if(trees.size() == 1){ // only one ASCII char situation in array list 
    TreeNode<CharFreq> leftSubtree = removeMin(trees); 
    int weight = leftSubtree.getItem().getFreq();
    root = new TreeNode<CharFreq>(new CharFreq((char)0,weight));
    root.setLeft(leftSubtree);
    trees.add(root);
    return root;
   }// if array list only has one element at the start - add on as left subtree and use its frequency (weight) as the root
   
   while( trees.size() >= 2){  // array list has at least 2 elements
     System.out.println("size of array list :" + trees.size()); //TESTING
     TreeNode<CharFreq> leftSubtree = removeMin(trees); // size of ArrayList issues
     System.out.println(leftSubtree);
     TreeNode<CharFreq> rightSubtree = removeMin(trees);
     System.out.println(rightSubtree);
     int weight = leftSubtree.getItem().getFreq()+ rightSubtree.getItem().getFreq();
     System.out.println(weight);
     ;
     //CharFreq treeWeight = new CharFreq(c,weight);
     //System.out.println(treeWeight);
     root = new TreeNode<CharFreq>(new CharFreq((char)0,weight));
     root.setLeft(leftSubtree);
     root.setRight(rightSubtree);
     
     trees.add(root);// appends merged tree onto ArrayList
   }
   if(trees.size() ==1){
    root = trees.get(0);
   }
   System.out.println("returned root: " +root);
   System.out.println("char is: " +root.getItem().getChar());
   return root; // what to do about IO exception?? 
 }

  /**
 * This method compresses a text file using huffman encoding.  Initially, the
 * supplied huffman tree is traversed to generate a lookup table of codes for
 * each character.  The text file is then read one character at a time, and
 * each character is encoded by using the lookup table.  The encoded bits for
 * each character are written one at a time to the specified BitWriter.
 *
 * @param  fr:      the FileReader which contains the text file to be encoded
 *         huffman: the huffman tree that was used for compression, and
 *                  hence should be used for decompression
 *         bw:      the BitWriter used to write the compressed bits to file
 */
 public static void compress(FileReader fr, TreeNode<CharFreq> huffman, BitWriter bw) throws Exception {
   
   
   String [] codes = new String[128];
   String code = "";
   traverse(huffman,code,codes); // stores codes and chars into String array
   
   int index = fr.read();
   
   
   while(index != -1){
     code = codes[index];
     //System.out.print("index: " + index);
     //System.out.println(" code : " + charCode);
     
     for(int i = 0;i< code.length();i++){
       //System.out.println("string length: "+charCode.length());
       
       int bit = Character.getNumericValue(code.charAt(i));
       //System.out.print("bit : " +bit+" ");
       
       bw.writeBit(bit);
     }
     index = fr.read();
   }
   
   
 }

  /**
 * This method reads a frequency file (such as those generated by the
 * countFrequencies() method) and initialises an ArrayList of TreeNodes
 * where the item of each TreeNode is a CharFreq object storing a character
 * from the frequency file and its corresponding frequency.  This method provides
 * the same functionality as the countFrequencies() method, but takes in a
 * frequency file as parameter rather than a text file.
 *
 * @param  inputFreqFile: the frequency file which stores characters and their
 *                        frequency (one character per line)
 *
 * @return the ArrayList containing TreeNodes.  The item stored in each
 *         TreeNode is a CharFreq object.
 */
 public static ArrayList<TreeNode<CharFreq>> readFrequencies(String inputFreqFile) throws Exception {
   
   ArrayList<TreeNode<CharFreq>> freqArrayList = new ArrayList<TreeNode<CharFreq>>();
   
   BufferedReader bR = new BufferedReader(new FileReader(inputFreqFile));
   
   
   int ch = bR.read(); // reads first char in file
   //System.out.print("First char in file: " + ch);
   
   if(ch == -1){ // nothing in the frequency file given - file is empty
     System.out.println("Frequency file is empty.");
     return freqArrayList; // returns a null array
   
   
   }else{ // frequency file contains at least 1 char and frequency
     
     //System.out.print("char: " + ch +" ");
     //System.out.println((char)ch);
     bR.skip(1);
     String frequency = bR.readLine();
     //System.out.println(" frequency: " + frequency);
     
     
     
     
     
     
     while( ch != -1 && frequency != null){
       
       
       
       //System.out.print("char: " + ch);
       //System.out.println((char)ch);
       
       
       System.out.println(" input: " + frequency);
        int freq = Integer.parseInt(frequency); // turns frequency into int
       
       freqArrayList.add(new TreeNode<CharFreq>(new CharFreq((char)ch,freq)));
       
       ch = bR.read();
       bR.skip(1);
       frequency = bR.readLine();
       
       
     }
   }
   bR.close();
   
   
   
   
   return freqArrayList;
   
 }
 
  /**
 * This method prints out the sizes (in bytes) of the compressed and the 
 * original files, and computes and prints out the compressed ratio.
 *
 * @param  file1: full name of the first file
 *         file2: full name of the second file
 */ 
 public static void statistics(String file1, String file2) {
  
  File compressed = new File(file1);
  File decompressed = new File(file2);// if file exists?
  double compFileSize = (double)compressed.length();
  double decompFileSize = (double)decompressed.length();
  double compRatio = (compFileSize/decompFileSize)*100; //ratio as a percentage
  System.out.println("Size of the compressed file: "+(int)compFileSize+ " bytes");
  System.out.println("Size of the original file: "+(int)decompFileSize+ " bytes");
  System.out.println("Compressed ratio: "+compRatio+"%");
  
  
  
 }
 

 /* This TextZip application should support the following command line flags:

 QUESTION 1 PART 1
 =================
   -a : this uses a default prefix code tree and its compressed
        file, "a.txz", and decompresses the file, storing the output
        in the text file, "a.txt".  It should also print out the size
        of the compressed file (in bytes), the size of the decompressed
        file (in bytes) and the compression ratio

 QUESTION 1 PART 2
 =================
   -f : given a text file (args[1]) and the name of an output frequency file
        (args[2]) this should count the character frequencies in the text file
        and store these in the frequency file (with one character and its
        frequency per line).  It should then build the huffman tree based on
        the character frequencies, and then print out the prefix code for each
        character

 QUESTION 1 PART 3
 =================
   -c : given a text file (args[1]) and the name of the output compressed 
        file (args[2]) and the name of an output frequency file (args[3]), 
        this should compress the file

 QUESTION 1 PART 4
 =================
   -d : given a compressed file (args[1]) and its corresponding frequency file
        (args[2]) and the name of the output decompressed text file (args[3]),
        this should decompress the file

 */

 public static void main(String[] args) throws Exception {

  /* This is a standard sample command line implementation.
  */
  
  if (args[0].equals("-a")) {
   BitReader br = new BitReader("a.txz");
   FileWriter fw = new FileWriter("a.txt");
   // Get the default prefix code tree
   TreeNode<CharFreq> tn = abracadbraTree();
   // Decompress the default file "a.txz"
   System.out.println("a.txz decompressed by kkov003");
   decompress(br, tn, fw);
   // Close the ouput file
   fw.close();
   // Output the compression ratio
   statistics("a.txz", "a.txt");
  }

  else if (args[0].equals("-f")) {
   FileReader fr = new FileReader(args[1]);
   PrintWriter pw = new PrintWriter(new FileWriter(args[2]));
   // Calculate the frequencies from the .txt file
   ArrayList<TreeNode<CharFreq>> trees = countFrequencies(fr, pw);
   // Close the files
   fr.close();
   pw.close();
   // Build the huffman tree
   TreeNode<CharFreq> n = buildTree(trees);
   // Display the codes
   System.out.println(args[1]+" prefix codes by kkov003");
   System.out.println("character code:");
   traverse(n, "");
  }

  else if (args[0].equals("-c")) {
   FileReader fr = new FileReader(args[1]);
   PrintWriter pw = new PrintWriter(new FileWriter(args[3]));
   // Calculate the frequencies from the .txt file
   ArrayList<TreeNode<CharFreq>> trees = countFrequencies(fr, pw);
   fr.close();
   pw.close();
   // Build the huffman tree
   TreeNode<CharFreq> n = buildTree(trees);
   // Compress the .txt file
   fr = new FileReader(args[1]);
   BitWriter bw = new BitWriter(args[2]);
   System.out.println(args[1] + " compressed by kkov003");
   compress(fr, n, bw);
   bw.close();
   fr.close();
   // Output the compression ratio
   statistics(args[2], args[1]);
  }

  else if (args[0].equals("-d")) {
   // Read in the frequencies from the .freq file
   ArrayList<TreeNode<CharFreq>> a = readFrequencies(args[2]);
   // Build the huffman tree
   TreeNode<CharFreq> tn = buildTree(a);
   // Decompress the .txz file
   BitReader br = new BitReader(args[1]);
   FileWriter fw = new FileWriter(args[3]);
   System.out.println(args[1]+" decompressed by kkov003");
   decompress(br, tn, fw);
   fw.close();
   // Output the compression ratio
   statistics(args[1], args[3]);
  }
 } // end main method
}