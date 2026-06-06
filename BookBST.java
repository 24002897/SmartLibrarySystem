class BookBST {
    private BSTNode root;

    public void insert(int isbn, String title, String author) {
        root = ins(root, isbn, title, author);
    }

    private BSTNode ins(BSTNode r, int i, String t, String a) {
        if (r == null) return new BSTNode(new Book(i, t, a));
        if (i < r.book.isbn)
            r.left = ins(r.left, i, t, a);
        else if (i > r.book.isbn)
            r.right = ins(r.right, i, t, a);
        else
            System.out.println("ISBN already exists!");
        return r;
    }

    public void displayAll() {
        if (root == null) {
            System.out.println("Catalogue is empty.");
        } else {
            System.out.println("All Books (sorted by ISBN):");
            inOrder(root);
        }
    }

    private void inOrder(BSTNode r) {
        if (r == null) return;
        inOrder(r.left);
        System.out.println("ISBN: " + r.book.isbn + " | Title: " + r.book.title + " | Author: " + r.book.author);
        inOrder(r.right);
    }
}

