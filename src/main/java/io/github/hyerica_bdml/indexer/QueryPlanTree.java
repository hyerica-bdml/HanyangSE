package io.github.hyerica_bdml.indexer;

/**
 * Query plan tree
 * 
 * @author Younghoon Kim
 *
 */
public class QueryPlanTree {
    /**
     * Node type of query plan trees
     *
     * OPRAND: an operand; i.e., a term
     *
     * OP_AND: boolean 'and' operation - binary operator
     *
     * OP_SHIFTED_AND: positional 'and' operation - binary
     *
     * OP_REMOVE_POS: projection with doc ids; remove all
     *             positional information from inverted
     *             list and keep doc ids only - unary
     *             
     * @author Younghoon Kim
     */
    public enum NODE_TYPE {
    	/**
    	 * Operand
    	 */
    	OPRAND,
    	/**
    	 * Intersection without considering position (binary)
    	 */
    	OP_AND,
    	/**
    	 * Positional intersection (binary)
    	 */
    	OP_SHIFTED_AND,
    	/**
    	 * Changing a positional list to a non-positional list (unary)
    	 */
    	OP_REMOVE_POS
    }

    /**
     * Class for the node in query plan tree
     * 
     * @author Younghoon Kim
     *
     */
    public class QueryPlanNode {
        /**
         * The type of node
         */
        public NODE_TYPE type;
        /**
         * The left node
         */
        public QueryPlanNode left;
        /**
         * The right node
         */
        public QueryPlanNode right;
        /**
         * shift has a value when type = OP_SHIFTED_AND
		 * constraint on the positions of terms in the query string
		 * 
		 * shift = (right term's pos - left term's pos)
         */
        public int shift;
        /**
         * termid has a value when type = OPRAND
         */
        public int termid;
    }

    /**
     * Example of a query plan tree
     * 1) hanyang (=3813)
     *
     *             root = { type = OP_REMOVE_POS }
     *               |
     *             left = { type = OPRAND, termid = 3813 }
     *
     * 2) hanyang university erica (=3813 12 4931)
     *
     *                         root = { type = OP_AND }
     *                    /                             \
     *                  left                           right = { type = OP_REMOVE_POS }
     *                  = {type=OP_AND}
     *          /                    \                    |
     *       left                    right              left = { type = OPRAND, termid = 4931 }
     *       = {type=OP_REMOVE_POS}  = {type=OP_REMOVE_POS}
     *        |                       |
     *       left                    left
     *       = {type = OPRAND,       = {type=OPRAND,
     *          termid = 3813}          termid=12}
     *
     *  3) "hanyang university" (="3813 12")
     *
     *                    root = {type = OP_REMOVE_POS }
     *                     |
     *                    left = {type = OP_SHIFTED_AND, shift = 1 }
     *               /             \
     *           left             right
     *           ={type=OPRAND,   ={type=OPRAND,
     *             termid=3813}     termid=12}
     *
     *  4) "hanyang university" erica =  (="3813 12" 4931)
     *
     *                              root  = { type = OP_AND }
     *                             /                        \
     *              left = {type = OP_REMOVE_POS }         right = {type = OP_REMOVE_POS }
     *                 |                                     |
     *             left = {type = OP_SHIFTED_AND
     *                     shift= 1 }                       left
     *         /             \                              ={type=OPRAND,
     *      left             right                            termid=4931}
     *      ={type=OPRAND,   ={type=OPRAND,
     *        termid=3813}     termid=12}
     */
    public QueryPlanNode root = null;
}
