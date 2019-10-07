package linked_list_set;

import kotlinx.atomicfu.AtomicRef;

public class SetImpl implements Set {
    private class AtomicReference {
        private class NodeHolder {
            final Node node;
            final boolean mark;

            NodeHolder(Node node, boolean initialMark) {
                this.node = node;
                this.mark = initialMark;
            }
        }

        AtomicRef<NodeHolder> ref;

        AtomicReference(Node node, boolean initialMark) {
            this.ref = new AtomicRef<>(new NodeHolder(node, initialMark));
        }

        Node getReference() {
            return ref.getValue().node;
        }

        boolean isRemoved() {
            return ref.getValue().mark;
        }

        boolean compareAndSet(Node expectedReference, Node newReference, boolean expectedMark, boolean newMark) {
            NodeHolder holder = ref.getValue();
            return expectedReference == holder.node &&
                    expectedMark == holder.mark &&
                    ref.compareAndSet(holder, new NodeHolder(newReference, newMark));
        }
    }

    private class Node {
        AtomicReference next;
        int x;

        Node(int x, Node next) {
            this.next = new AtomicReference(next, false);
            this.x = x;
        }
    }

    private class Window {
        Node cur, next;

        Window(Node cur, Node next) {
            this.cur = cur;
            this.next = next;
        }
    }

    private final Node head = new Node(Integer.MIN_VALUE, new Node(Integer.MAX_VALUE, null));

    /**
     * Returns the {@link Window}, where cur.x < x <= next.x
     */
    private Window findWindow(int x) {
        while (true) {
            Node cur = head;
            Node next = cur.next.getReference();
            while (next.x < x) {
                AtomicReference node = next.next;
                if (node.isRemoved()) {
                    if (!cur.next.compareAndSet(next, node.getReference(), false, false))
                        break;
                    next = node.getReference();
                } else {
                    cur = next;
                    next = cur.next.getReference();
                }
            }
            if (next.next.isRemoved()) {
                cur.next.compareAndSet(next, next.next.getReference(), false, false);
            } else {
                return new Window(cur, next);
            }
        }

    }

    @Override
    public boolean add(int x) {
        while (true) {
            Window w = findWindow(x);
            if (w.next.x == x)
                return false;
            Node node = new Node(x, w.next);
            if (w.cur.next.compareAndSet(w.next, node, false, false))
                return true;
        }
    }

    @Override
    public boolean remove(int x) {
        while (true) {
            Window w = findWindow(x);
            if (w.next.x != x)
                return false;
            AtomicReference nextNext = w.next.next;
            if (w.next.next.compareAndSet(nextNext.getReference(), nextNext.getReference(), false, true)) {
                w.cur.next.compareAndSet(w.next, nextNext.getReference(), false, false);
                return true;
            }
        }
    }

    @Override
    public boolean contains(int x) {
        return findWindow(x).next.x == x;
    }
}

