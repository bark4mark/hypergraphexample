package com.markhoward.hypergraphexample;

import org.hypergraphdb.HGEnvironment;
import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGSearchResult;
import org.hypergraphdb.HGValueLink;
import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.algorithms.HGDepthFirstTraversal;
import org.hypergraphdb.algorithms.SimpleALGenerator;
import org.hypergraphdb.query.AtomTypeCondition;
import org.hypergraphdb.query.HGQueryCondition;
import org.hypergraphdb.util.Pair;

public class GraphExample {
    public static final String graphLocation = "/home/mhoward/Hypergraph";
    final private HyperGraph graph;
    
    GraphExample(){
        graph = HGEnvironment.get(graphLocation);
        addSomeProducts();
        queryGraph();
        
        /*
         * No need to close the graph as Hypergraph should automatically close when the Java VM ends.
         */
        graph.close();
    }
    
    private void queryGraph(){
        /*
         * Query to return all atoms that are of the Product type.
         */
        HGQueryCondition condition = new AtomTypeCondition(Product.class);
        HGSearchResult<HGHandle> resultSet = graph.find(condition);
        try
        {
            while(resultSet.hasNext()){
                HGHandle productHandle = resultSet.next();
                /*
                 * For each product traverse the graph starting with this 
                 * product and print the results.
                 */
                printCategory(productHandle);
            }
        }
        catch(Exception exception){
            /*
             * Print any exceptions that occur, I have come across type casting
             * problems, which would occur at runtime.
             */
            exception.printStackTrace();
        }
        finally{
            resultSet.close();
        }
    }
    
    private void printCategory(HGHandle productHandle){
        /*
         * Set up a depth first traversal starting with our product.
         */
        HGDepthFirstTraversal traversal = new HGDepthFirstTraversal(productHandle, new SimpleALGenerator(graph));
        
        while(traversal.hasNext()){
            Pair<HGHandle, HGHandle> current = traversal.next();
            HGHandle categoryHandle = (HGHandle) current.getSecond();
            System.out.println(graph.get(categoryHandle));
        }
    }
    
    private void addSomeProducts(){
        /*
         * Create categories add them to the graph and save the handles.
         */
        Category digitalCamera = new Category(DIGITAL_CAMERA);
        Category computer = new Category(COMPUTER);
        HGHandle digitalCameraCategoryHandle = graph.add(digitalCamera);
        HGHandle computerCategoryHandle = graph.add(computer);
        
        /*
         * Create Products and add them to the graph (saving the handles).
         */
        Product camera1 = new Product("Canon IXUS", "10 Megapixel, 3x Zoom", 100.00);
        Product camera2 = new Product("Nikon Coolpix", "12 Megapixel, 3x Zoom", 120.00);
        Product computer1 = new Product("Apple Macbook Pro", "2Ghz, 500GB", 1200.00);
        Product computer2 = new Product("Dell XPS", "3Ghz, 1TB", 600.00);
        
        HGHandle camera1Handle = graph.add(camera1);
        HGHandle camera2Handle = graph.add(camera2);
        HGHandle computer1Handle = graph.add(computer1);
        HGHandle computer2Handle = graph.add(computer2);
        
        /*
         * Make a link from each product to the relevant category.
         */
        HGValueLink link1 = new HGValueLink("category", digitalCameraCategoryHandle, camera1Handle);
        HGValueLink link2 = new HGValueLink("category", digitalCameraCategoryHandle, camera2Handle);
        HGValueLink link3 = new HGValueLink("category", computerCategoryHandle, computer1Handle);
        HGValueLink link4 = new HGValueLink("category", computerCategoryHandle, computer2Handle);
        
        /*
         * Add the link to the graph.
         */
        graph.add(link1);
        graph.add(link2);
        graph.add(link3);
        graph.add(link4);
    }
    
    public static final String DIGITAL_CAMERA = "Digital Camera";
    public static final String COMPUTER = "Computer";
}
